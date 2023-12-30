package com.likelion.boomarble.global.handler;

import com.likelion.boomarble.domain.chat.repository.RedisRepository;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import com.likelion.boomarble.domain.user.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final RedisRepository redisRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            // "X-AUTH-TOKEN" 헤더에서 JWT 토큰 추출
            String jwtToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");
            if (StringUtils.hasText(jwtToken)) {
                // 토큰 유효성 검증
                if (jwtTokenProvider.validateToken(jwtToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                    long userId = getUserPk(authentication);
                    userRepository.findById(userId)
                            .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
                    String sessionId = (String) message.getHeaders().get("simpSessionId");
                    redisRepository.saveMyInfo(sessionId, userId);
                }
            }
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            // 채팅방에서 나가는 것 처리
            if(redisRepository.existMyInfo(sessionId)) {
                Long userId = redisRepository.getMyInfo(sessionId);
                // 채팅방 퇴장 정보 저장
                if(redisRepository.existChatRoomUserInfo(userId)) {
                    redisRepository.exitUserEnterRoomId(userId);
                }
                redisRepository.deleteMyInfo(sessionId);
            }
        }
        return message;
    }

    // 추가된 getUserPk 메서드
    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}