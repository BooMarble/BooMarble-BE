package com.likelion.boomarble.domain.chatbot.controller;

import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.domain.user.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ChatbotController {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${chatbot.secret-key}")
    private String secretKey;
    @Value("${chatbot.api-url}")
    private String apiUrl;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(@Payload String chatMessage, StompHeaderAccessor accessor) throws IOException {

        URL url = new URL(apiUrl);

        String message = getReqMessage(chatMessage, accessor);
        String encodeBase64String = makeSignature(message, secretKey);
        System.out.println("encodeBase: "+encodeBase64String);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;UTF-8");
        con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

        con.setDoOutput(true);
        System.out.println("con: "+con);
        // 보낼 목적지 설정
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.write(message.getBytes("UTF-8"));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader br;

        if (responseCode == 200){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream()
                    )
            );
            String decodedString;
            String jsonString = "";
            while ((decodedString = in.readLine()) != null){
                jsonString = decodedString;
            }

            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
                JSONArray bubblesArray = (JSONArray) jsonObject.get("bubbles");
                JSONObject bubbles = (JSONObject) bubblesArray.get(0);
                JSONObject data = (JSONObject) bubbles.get("data");
                String description = "";
                description = (String) data.get("description");
                chatMessage = description;
            } catch (Exception e){
                System.out.println("error");
                e.printStackTrace();
            }
            in.close();
        } else {
            chatMessage = con.getResponseMessage();
        }

        return chatMessage;

    }

    // 보낼 메시지를 네이버에서 제공하는 암호화를 거침
    public static String makeSignature(String message, String secretKey){

        String encodeBase64String = "";

        try {
            byte[] secret_key_bytes = secretKey.getBytes("UTF-8");

            SecretKeySpec signingKey = new SecretKeySpec(secret_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;

        } catch (Exception e){
            System.out.println(e);
        }

        return encodeBase64String;

    }

    // 보낼 메시지를 네이버 챗봇 포맷으로 변경
    public String getReqMessage(String voiceMessage, StompHeaderAccessor accessor){

        String requestBody = "";

        // Access STOMP headers
        String authToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");

        // Use authToken for authentication
        Authentication authentication = jwtTokenProvider.getAuthentication(authToken);

        try {
            JSONObject obj = new JSONObject();

            long timestamp = new Date().getTime();
            System.out.println("##"+timestamp);
            System.out.println("authentication"+authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("customUserDetails"+customUserDetails);
            long userId = customUserDetails.getUserPk();

            obj.put("version", "v2");
            obj.put("userId", userId);
            obj.put("timestamp", timestamp);

            JSONObject bubbles_obj = new JSONObject();

            bubbles_obj.put("type", "text");

            JSONObject data_obj = new JSONObject();
            data_obj.put("description", voiceMessage);

            bubbles_obj.put("data", data_obj);

            JSONArray bubbles_array = new JSONArray();
            bubbles_array.add(bubbles_obj);

            obj.put("bubbles", bubbles_array);
            obj.put("event", "send");

            requestBody = obj.toString();

        } catch (Exception e){
            System.out.println("## Exception : " + e);
        }

        return requestBody;

    }

}
