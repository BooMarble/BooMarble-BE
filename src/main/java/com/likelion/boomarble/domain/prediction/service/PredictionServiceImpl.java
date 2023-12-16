package com.likelion.boomarble.domain.prediction.service;

import com.likelion.boomarble.domain.model.ChineseType;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.prediction.domain.ChinesePrediction;
import com.likelion.boomarble.domain.prediction.domain.JapanesePrediction;
import com.likelion.boomarble.domain.prediction.domain.Prediction;
import com.likelion.boomarble.domain.prediction.dto.*;
import com.likelion.boomarble.domain.prediction.exception.*;
import com.likelion.boomarble.domain.prediction.repository.PredictionRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.exception.UniversityInfoNotFoundException;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;
    private final UserRepository userRepository;
    private final UniversityInfoRepository universityInfoRepository;

    private static final Map<String, Integer> JLPT_INCREMENTS = Map.of(
            "N1", 400, "N2", 300, "N3", 200, "N4", 100, "N5", 0
    );
    private static final Map<String, Integer> JLPT_MIN_SCORE = Map.of(
            "N1", 100, "N2", 90, "N3", 95, "N4", 90, "N5", 80
    );
    private static final Map<String, Integer> TOCFL_SCORES = Map.of(
            "1급", 500, "2급", 630, "3급", 840, "4급", 970, "5급", 980, "6급", 990
    );
    private static final Map<String, Integer> HSK_INCREMENT = Map.of(
            "1급", 0, "2급", 190, "3급", 320, "4급", 450, "5급", 540, "6급", 670
    );
    private static final Map<String, Integer> HSK_MIN_SCORE = Map.of(
            "1급", 120, "2급", 120, "3급", 180, "4급", 180, "5급", 180, "6급", 180
    );
    @Override
    @Transactional
    public String determineRegionByCountry(BasicInformationDTO basicInformationDTO) {
        Country country = basicInformationDTO.getCountry();
        if (country.equals("JPN")) return "japanese";
        if (country.equals("CHN") || country.equals("TWN")) return "chinese";
        else return "english";
    }

    /* ##################### 일본 모의지원 관련 ##################### */
    @Override
    @Transactional
    public PredictionResultDTO applyJapanesePrediction(long userId, PredictionJapaneseInfoDTO predictionJapaneseInfoDTO) {
        // 여러번 호출되는 메서드는 변수로 따로 설정
        String level;
        int score;
        long universityId = predictionJapaneseInfoDTO.getUniversityId();
        ExType exType = predictionJapaneseInfoDTO.getExType();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        if(user.getPredictionCnt() <= 0){
            throw new NoMoreAvailablePredictionException("모의지원 가능 횟수를 모두 소진하였습니다.");
        } else user.applyPrediction();
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학정보가 존재하지 않습니다."));
        if(predictionRepository.findByUserAndExTypeAndUniversity(user, exType, university) != null){
            throw new AlreadyApplyPredictionException("이미 모의지원을 한 대학입니다.");
        }
        // 추천서 체크했으면, score이랑 level 업데이트
        if(predictionJapaneseInfoDTO.isHasRecommendationLetter()){
            if(university.getQualificationEtc().contains("추천서")){
                level = university.getJapaneseQ().getJapanese();
                score = 100;
            } else throw new NotAvailableRecommendationLetterException("추천서로 대체 가능하지 않습니다.");
        } else{
            level = predictionJapaneseInfoDTO.getLevel();
            score = predictionJapaneseInfoDTO.getScore();
        }
        // JLPT 점수가 잘 들어왔는지 확인
        if(!checkJapaneseScore(level, score)){
            throw new InvalidScoreException("잘못된 JLPT 점수입니다.");
        }
        JapanesePrediction japanesePrediction = JapanesePrediction.builder()
                .level(level)
                .score(score)
                .hasRecommendationLetter(predictionJapaneseInfoDTO.isHasRecommendationLetter())
                .convertedScore(convertJapaneseScore(level, score)+predictionJapaneseInfoDTO.getGrade()).build();
        Prediction prediction = Prediction.builder()
                .user(user)
                .university(university)
                .grade(predictionJapaneseInfoDTO.getGrade())
                .semester(predictionJapaneseInfoDTO.getSemester())
                .country(predictionJapaneseInfoDTO.getCountry())
                .exType(exType)
                .japanesePrediction(japanesePrediction).build();
        predictionRepository.save(prediction);
        // 모의지원한 점수가 해당 대학 자격조건에 맞는지 확인
        List<String> precautions = new ArrayList<>();
        float universityGrade = university.getGradeQ();
        String universityJapaneseQ = university.getJapaneseQ().getJapanese();
        if(universityGrade > predictionJapaneseInfoDTO.getGrade()) precautions.add("학점이 충족되지 않았습니다.");
        if(universityJapaneseQ != null){
            if(universityJapaneseQ.compareTo(level)<0) precautions.add("어학성적이 충족되지 않았습니다.");
        }
        List<Object[]> results = predictionRepository.findJapaneseRankingsByUniversityId(universityId, exType.ordinal());
        return getSurroundingPredictions(userId, results, precautions);
    }
    // JLPT 점수가 잘 들어왔는지 확인
    private boolean checkJapaneseScore(String level, int score){
        return score >= JLPT_MIN_SCORE.getOrDefault(level, 80);
    }
    private double convertJapaneseScore(String level, int score){
        return score + JLPT_INCREMENTS.getOrDefault(level, 0);
    }

    /* ##################### 중국 모의지원 관련 ##################### */
    @Override
    @Transactional
    public PredictionResultDTO applyChinesePrediction(long userId, PredictionChineseInfoDTO predictionChineseInfoDTO) {
        // 여러번 호출되는 메서드는 변수로 따로 설정
        String testType = predictionChineseInfoDTO.getTestType();
        String level = predictionChineseInfoDTO.getLevel();
        int score = 0;
        long universityId = predictionChineseInfoDTO.getUniversityId();
        ExType exType = predictionChineseInfoDTO.getExType();
        ChineseType chineseType = predictionChineseInfoDTO.getChineseType();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        if(user.getPredictionCnt() <= 0){
            throw new NoMoreAvailablePredictionException("모의지원 가능 횟수를 모두 소진하였습니다.");
        } else user.applyPrediction();
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학정보가 존재하지 않습니다."));
        if(predictionRepository.findByUserAndExTypeAndUniversity(user, exType, university) != null){
            throw new AlreadyApplyPredictionException("이미 모의지원을 한 대학입니다.");
        }
        if(testType.equals("HSK")){
            score = predictionChineseInfoDTO.getScore();
            if(!checkChineseScore(level, score)) throw new InvalidScoreException("잘못된 HSK 점수입니다.");
        }
        ChinesePrediction chinesePrediction = ChinesePrediction.builder()
                .chineseType(chineseType)
                .testType(testType)
                .level(level)
                .score(score)
                .convertedScore(convertChineseScore(testType, level, score)+predictionChineseInfoDTO.getGrade()).build();
        Prediction prediction = Prediction.builder()
                .user(user)
                .university(university)
                .grade(predictionChineseInfoDTO.getGrade())
                .semester(predictionChineseInfoDTO.getSemester())
                .country(predictionChineseInfoDTO.getCountry())
                .exType(exType)
                .chinesePrediction(chinesePrediction).build();
        predictionRepository.save(prediction);
        List<String> precautions = new ArrayList<>();
        float universityGrade = university.getGradeQ();
        if(universityGrade > predictionChineseInfoDTO.getGrade()) precautions.add("학점이 충족되지 않았습니다.");
        List<Object[]> results = predictionRepository.findChineseRankingsByUniversityId(universityId, exType.ordinal(), chineseType.ordinal());
        return getSurroundingPredictions(userId, results, precautions);
    }
    // HSK 점수가 잘 들어왔는지 확인
    private boolean checkChineseScore(String level, int score){
        return score >= HSK_MIN_SCORE.getOrDefault(level, 180);
    }
    private double convertChineseScore(String testType, String level, int score){
        if(testType.equals("TOCFL")) return TOCFL_SCORES.getOrDefault(level, 990);
        else return score + HSK_INCREMENT.getOrDefault(level, 0);
    }
    // 같은 대학교에 지원한 사람들끼리 순위를 매겨서 자신의 순위와 자신보다 앞에 두 사람과 뒤에 두 사람 점수 같이 반환
    private PredictionResultDTO getSurroundingPredictions(long userId, List<Object[]> results, List<String> precautions) {
        List<RankScoreDTO> rankings = results.stream()
                .map(result -> convertToRankScoreDTO(result, userId))
                .collect(Collectors.toList());
        RankScoreDTO userRanking = findUserRanking(rankings, userId);
        List<RankScoreDTO> surroundingRankings = findSurroundingRankings(rankings, userRanking.getRankNum());
        return PredictionResultDTO.builder()
                .numOfApplicant(rankings.size())
                .rankings(surroundingRankings)
                .precautions(precautions)
                .build();
    }
    private RankScoreDTO convertToRankScoreDTO(Object[] result, long userId) {
        long resultUserId = ((BigInteger) result[2]).longValue();
        return new RankScoreDTO(
                (Double) result[0],
                ((BigInteger) result[1]).longValue(),
                resultUserId,
                resultUserId == userId
        );
    }
    // 본인의 등수 찾기
    private RankScoreDTO findUserRanking(List<RankScoreDTO> rankings, long userId) {
        return rankings.stream()
                .filter(r -> r.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new RankNotFoundException("사용자 순위를 찾을 수 없습니다."));
    }
    // 자신보다 앞 순위 두명과 뒷 순위 두명 찾기
    private List<RankScoreDTO> findSurroundingRankings(List<RankScoreDTO> rankings, long userRank) {
        return rankings.stream()
                .filter(r -> Math.abs(r.getRankNum() - userRank) <= 2)
                .collect(Collectors.toList());
    }
}
