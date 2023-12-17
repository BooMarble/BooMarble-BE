package com.likelion.boomarble.domain.prediction.service;

import com.likelion.boomarble.domain.model.ChineseType;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.prediction.domain.ChinesePrediction;
import com.likelion.boomarble.domain.prediction.domain.EnglishPrediction;
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
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

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
    private static final NavigableMap<Integer, Integer> TOEFL_TO_IBT = new TreeMap<>(Map.ofEntries(
            entry(673, 120), entry(670, 119), entry(667, 118), entry(660, 117), entry(657, 116),
            entry(650, 115), entry(647, 113), entry(640, 112), entry(637, 110), entry(630, 109),
            entry(623, 108), entry(617, 105), entry(613, 104), entry(607, 102), entry(600, 100),
            entry(597, 99), entry(590, 97), entry(587, 95), entry(580, 93), entry(577, 91),
            entry(570, 89), entry(567, 87), entry(563, 85), entry(557, 83), entry(553, 82),
            entry(550, 80), entry(547, 78), entry(540, 76), entry(537, 75), entry(533, 73),
            entry(527, 71), entry(523, 70), entry(520, 68), entry(517, 67), entry(513, 65),
            entry(507, 64), entry(503, 63), entry(500, 61), entry(497, 60), entry(493, 58),
            entry(487, 57), entry(483, 56), entry(480, 55), entry(477, 53), entry(470, 52),
            entry(467, 51), entry(463, 50), entry(460, 48), entry(457, 47), entry(450, 46),
            entry(447, 44), entry(443, 43), entry(437, 42), entry(433, 40), entry(430, 39),
            entry(423, 38), entry(420, 37), entry(417, 35), entry(410, 34), entry(407, 33),
            entry(400, 32), entry(397, 31), entry(390, 29), entry(387, 28), entry(380, 27),
            entry(377, 25), entry(370, 24), entry(363, 23), entry(357, 22), entry(353, 21),
            entry(347, 20), entry(340, 18), entry(333, 17), entry(330, 16), entry(323, 15),
            entry(317, 14), entry(313, 13), entry(310, 12)
    ));
    private static final NavigableMap<Double, Integer> IELTS_TO_IBT = new TreeMap<>(Map.ofEntries(
            entry(9.0, 120), entry(8.5, 117), entry(8.0, 114), entry(7.5, 109), entry(7.0, 101),
            entry(6.5, 93), entry(6.0, 78), entry(5.5, 59), entry(5.0, 45), entry(4.5, 34), entry(0.0, 31)
    ));

    @Override
    @Transactional
    public String determineRegionByCountry(BasicInformationDTO basicInformationDTO) {
        Country country = basicInformationDTO.getCountry();
        if (country.equals(Country.JPN)) return "japanese";
        if (country.equals(Country.CHN) || country.equals(Country.TWN)) return "chinese";
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
        // 모의지원한 점수가 해당 대학 자격조건에 맞는지 확인
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

    /* ##################### 영어 모의지원 관련 ##################### */
    @Override
    @Transactional
    public PredictionResultDTO applyEnglishPrediction(long userId, PredictionEnglishInfoDTO predictionEnglishInfoDTO) {
        // 여러번 호출되는 메서드는 변수로 따로 설정
        String testType = predictionEnglishInfoDTO.getTestType();
        double score = predictionEnglishInfoDTO.getScore();
        long universityId = predictionEnglishInfoDTO.getUniversityId();
        ExType exType = predictionEnglishInfoDTO.getExType();
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
        EnglishPrediction englishPrediction = EnglishPrediction.builder()
                .testType(testType)
                .score(score)
                .convertedScore(convertEnglishScore(testType, score)+predictionEnglishInfoDTO.getGrade()).build();
        Prediction prediction = Prediction.builder()
                .user(user)
                .university(university)
                .grade(predictionEnglishInfoDTO.getGrade())
                .semester(predictionEnglishInfoDTO.getSemester())
                .country(predictionEnglishInfoDTO.getCountry())
                .exType(exType)
                .englishPrediction(englishPrediction).build();
        predictionRepository.save(prediction);
        // 모의지원한 점수가 해당 대학 자격조건에 맞는지 확인
        List<String> precautions = new ArrayList<>();
        float universityGrade = university.getGradeQ();
        if(universityGrade > predictionEnglishInfoDTO.getGrade()) precautions.add("학점이 충족되지 않았습니다.");
        if(!satisfyEnglishScore(university, testType, score)) precautions.add("어학 성적이 충족되지 않았습니다.");
        List<Object[]> results = predictionRepository.findEnglishRankingsByUniversityId(universityId, exType.ordinal());
        return getSurroundingPredictions(userId, results, precautions);
    }
    private boolean satisfyEnglishScore(UniversityInfo university, String testType, double score){
        if(testType.equals("IELTS")){
            if(university.getEnglishQ().getIeltsQ() == -1) throw new InvalidScoreException("IELTS는 필수 어학 성적이 아닙니다.");
            else return university.getEnglishQ().getIeltsQ() <= score;
        } else if(testType.equals("TOEFL")){
            if(university.getEnglishQ().getToefl() == -1) throw new InvalidScoreException("TOEFL는 필수 어학 성적이 아닙니다.");
            else return university.getEnglishQ().getToefl() <= score;
        } else{
            if(university.getEnglishQ().getIbtQ() == -1) throw new InvalidScoreException("IBT는 필수 어학 성적이 아닙니다.");
            else return university.getEnglishQ().getIbtQ() <= score;
        }
    }
    private double convertEnglishScore(String testType, double score){
        if(testType.equals("IELTS")) return IELTS_TO_IBT.floorEntry(score).getValue();
        else if(testType.equals("TOEFL")) return TOEFL_TO_IBT.floorEntry((int)score).getValue();
        else return score;
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
