package com.likelion.boomarble.domain.universityInfo.controller;

import com.likelion.boomarble.domain.model.ChineseType;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.ChineseQ;
import com.likelion.boomarble.domain.universityInfo.domain.EnglishQ;
import com.likelion.boomarble.domain.universityInfo.domain.JapaneseQ;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UniversityInfoFromExcelController {

    private final UniversityInfoRepository universityInfoRepository;

    @GetMapping("/excel")
    public String main() {
        return "excelInputForm.html";
    }


    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model) throws IOException { // 2

        List<UniversityInfo> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            throw new IOException("지원하지 않는 엑셀 형식");
        }

        String fileName = file.getOriginalFilename();
        String semester = fileName.split("_")[0];

        // 영어권 데이터 넣기
        if (fileName != null && fileName.contains("english")) {
            Sheet worksheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = worksheet.iterator();

            boolean dataStarted = false;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // "구분"으로 시작하는 행을 찾아 데이터 시작을 확인
                Cell cell = row.getCell(0);
                if (cell != null){
                    if (cell.getCellType() == CellType.BLANK && dataStarted){
                        break;
                    }
                    if ("구분".equals(cell.getStringCellValue())  && !dataStarted) {
                        dataStarted = true;
                        rowIterator.next();
                        rowIterator.next();
                        continue;
                    }


                    if (dataStarted) {
                        UniversityInfo universityInfo = new UniversityInfo();
                        String cellValue = row.getCell(0).getStringCellValue();
                        // "교환/파견" 유형인 데이터만 추가
                        if (cellValue != null && !cellValue.contains("총 선발인원") && (cellValue.contains("교환") || cellValue.contains("학교선발"))) {

                            // 전처리 필요한 데이터

                            float grade = Float.parseFloat(row.getCell(5).getStringCellValue().split("이상")[0].split(" ")[0]);

                            // 모든 영어성적들 "(" 기준 잘라서 넣기, 잘린 내용은 기타에 붙이기
                            Cell ibtCell = row.getCell(6);
                            String ibtEtc = "";
                            int ibt = -1;
                            if (ibtCell != null){
                                if (ibtCell.getCellType() == CellType.NUMERIC){
                                    ibt = (int)ibtCell.getNumericCellValue();
                                } else {
                                    String[] ibts = ibtCell.getStringCellValue().split("\\(");
                                    ibt = Integer.parseInt(ibts[0].replace("\n", "").replace(" ", ""));
                                    if (ibts.length > 1) {
                                        ibtEtc = "\n( ibt : " + ibts[1].replace("\n", " ");
                                    }
                                }
                            }

                            Cell toeflCell = row.getCell(7);
                            String toeflEtc = "";
                            int toefl = -1;
                            if (toeflCell != null){
                                if (toeflCell.getCellType() == CellType.NUMERIC){
                                    toefl = (int)toeflCell.getNumericCellValue();
                                }
                            }

                            Cell ieltsCell = row.getCell(8);
                            String ieltsEtc = "";
                            float ielts = -1;
                            if (ieltsCell != null){
                                if (ieltsCell.getCellType() == CellType.NUMERIC){
                                    ielts = (float)ieltsCell.getNumericCellValue();
                                }
                                else {
                                    String[] ieltss = ieltsCell.getStringCellValue().split("\\(");
                                    ielts = Float.parseFloat(ieltss[0].replace("\n", "").replace(" ", ""));
                                    if (ieltss.length > 1) {
                                        ieltsEtc = "\n( ielts" + ieltss[1].replace("\n", " ");
                                    }
                                }
                            }

                            String qEtc = row.getCell(9).getStringCellValue() + ibtEtc + toeflEtc + ieltsEtc;

                            // 데이터 저장
                            String exTypeStr = row.getCell(0).getStringCellValue();
                            if (exTypeStr.contains("교환")) {
                                universityInfo.setExType(ExType.exchange);
                            }
                            else if (exTypeStr.contains("7+1") && exTypeStr.contains("학교선발")) {
                                universityInfo.setExType(ExType.seven);
                            }
                            universityInfo.setCountry(findCountryByName(row.getCell(1).getStringCellValue()));
                            universityInfo.setSemester(semester);
                            universityInfo.setName(row.getCell(2).getStringCellValue());
                            Cell recruitNumCell = row.getCell(3);
                            String recruitNumcellValue = "";
                            if (recruitNumCell != null) {
                                // 셀 타입 확인
                                if (recruitNumCell.getCellType() == CellType.NUMERIC) {
                                    // 숫자 값 처리
                                    int numericValue = (int) recruitNumCell.getNumericCellValue();
                                    recruitNumcellValue = String.valueOf(numericValue);
                                } else {
                                    // 문자열 값 처리
                                    recruitNumcellValue = recruitNumCell.getStringCellValue();
                                    if (recruitNumcellValue.contains("학교선발")){
                                        int recnumIndex = -1;
                                        String[] recruitNumcellValueSplit = recruitNumcellValue.split(" ");
                                        for (int j = 0; j < recruitNumcellValueSplit.length; j++){
                                            if (recruitNumcellValueSplit[j].equals("학교선발:")){
                                                recnumIndex = j;
                                                break;
                                            }
                                        }
                                        recruitNumcellValue = recruitNumcellValueSplit[recnumIndex+1];
                                    }
                                }
                            }
                            universityInfo.setRecruitNum(recruitNumcellValue);
                            universityInfo.setPeriod(row.getCell(4).getStringCellValue());
                            universityInfo.setGradeQ(grade);
                            EnglishQ englishQ = new EnglishQ(ibt, toefl, ielts);
                            universityInfo.setEnglishQ(englishQ);
                            universityInfo.setQualificationEtc(qEtc);
                            Cell expCostCell = row.getCell(10);
                            String expCostValue = "";
                            if (expCostCell != null){
                                if (expCostCell.getCellType() == CellType.FORMULA) {
                                    expCostValue = String.valueOf(expCostCell.getNumericCellValue());
                                } else if (expCostCell.getCellType() == CellType.NUMERIC) {
                                    expCostValue = String.valueOf(expCostCell.getNumericCellValue());
                                } else {
                                    expCostValue = expCostCell.getStringCellValue();
                                }
                            }
                            universityInfo.setExpCost(expCostValue);
                            universityInfo.setExpCostDesc(row.getCell(11).getStringCellValue());
                            universityInfo.setBenefit(row.getCell(12).getStringCellValue());
                            universityInfo.setEtc(row.getCell(13).getStringCellValue());
                            universityInfo.setSemester(fileName.split("_")[0]);
                            dataList.add(universityInfo);
                        }
                    }
                }
            }

        }


        else if (fileName != null && fileName.contains("asia")) {
            for (int i = 0; i < 2; i++) {

                // 일본어권 대학
                Sheet worksheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = worksheet.iterator();

                boolean dataStarted = false;

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    // "구분"으로 시작하는 열을 찾아 데이터 시작을 확인
                    Cell cell = row.getCell(0);
                    if (cell != null){
                        if (cell.getCellType() == CellType.BLANK && dataStarted){
                            break;
                        }
                        if ("구분".equals(cell.getStringCellValue()) && !dataStarted) {
                            dataStarted = true;
                            rowIterator.next();
                            continue; // 데이터 헤더 행을 건너뜁니다.
                        }


                        if (dataStarted) {
                            UniversityInfo universityInfo = new UniversityInfo();
                            String cellValue = row.getCell(0).getStringCellValue();
                            // "교환" 유형인 데이터만 추가
                            if (cellValue != null && row.getCell(0).getStringCellValue().contains("교환")) {
                                // 전처리 필요한 데이터

                                float grade = Float.parseFloat(row.getCell(5).getStringCellValue().split("이상")[0].split(" ")[0]);

                                String scores = row.getCell(6).getStringCellValue();
                                String scoreEtc = "";
                                String japanese = "";
                                List<ChineseQ> chineseQList = new ArrayList<>();
                                // 일본어권
                                if (i == 0){
                                    String[] score = scores.split("\\(");
                                    if (score[0].contains("우대")){
                                        scoreEtc = String.join("(", score);
                                    }
                                    else if (score[0].contains("-")){
                                        continue;
                                    }
                                    else {
                                        japanese = score[0].split(" ")[0].replace(" ", "");
                                        if (score.length > 1){
                                            scoreEtc = score[1].replace(")", "");
                                        }
                                    }
                                }
                                // 중국어권
                                else if (i == 1) {
                                    String chineseLevel = "";
                                    String chineseScore = "";
                                    ChineseType chineseType;
                                    int hskIndex = -1;
                                    String[] score = scores.split("\n");
                                    String score_first = score[0];
                                    // 학부나 어학연수로 구분되지 않은 경우
                                    if (!score_first.contains("학부") && !score_first.contains("어학연수")){
                                        chineseType = ChineseType.etc;
                                        if (score_first.contains("우대") || score_first.contains("불필요")){
                                            scoreEtc = String.join("(", score);
                                        }
                                        else {
                                            String[] score_first_split = score_first.split(" ");
                                            for (int j = 0; j < score_first_split.length; j++) {
                                                if (score_first_split[j].equals("HSK")) {
                                                    hskIndex = j;
                                                    break;
                                                }
                                            }
                                            chineseLevel = score_first_split[hskIndex+1];
                                            if (score_first_split[hskIndex+2].contains("점")) {
                                                chineseScore = score_first_split[hskIndex + 2];
                                            }
                                        }
                                        chineseQList.add(new ChineseQ(chineseType, chineseLevel, chineseScore));
                                    }
                                    // 어학연수과정만 가능한 경우
                                    else if (score_first.contains("어학연수과정만")){
                                        chineseType = ChineseType.LT;
                                        if (score_first.contains("불필요") || score_first.contains("우대")){
                                            scoreEtc = String.join("(", score);
                                        }
                                        else {
                                            String[] score_first_split = score_first.split(" ");
                                            for (int j = 0; j < score_first_split.length; j++) {
                                                if (score_first_split[j].equals("HSK")) {
                                                    hskIndex = j;
                                                    break;
                                                }
                                            }
                                            chineseLevel = score_first_split[hskIndex+1];
                                            if (score_first_split[hskIndex+2].contains("점")) {
                                                chineseScore = score_first_split[hskIndex + 2];
                                            }
                                        }
                                        chineseQList.add(new ChineseQ(chineseType, chineseLevel, chineseScore));
                                    }
                                    // 학부, 어학연수 과정 모두 가능한 경우
                                    else if (score_first.contains("학부")){
                                        // 첫 번째 줄은 학부
                                        chineseType = ChineseType.UG;
                                        String[] score_first_split = score_first.split(" ");
                                        if (score_first.contains("불필요") || score_first.contains("우대")){
                                            scoreEtc += "\n학부 수업 : " + score_first;
                                        }
                                        else {
                                            for (int j = 0; j < score_first_split.length; j++) {
                                                if (score_first_split[j].equals("HSK")) {
                                                    hskIndex = j;
                                                    break;
                                                }
                                            }
                                            chineseLevel = score_first_split[hskIndex + 1];
                                            if (score_first_split[hskIndex + 2].contains("점")) {
                                                chineseScore = score_first_split[hskIndex + 2];
                                            }
                                        }
                                        chineseQList.add(new ChineseQ(chineseType, chineseLevel, chineseScore));

                                        // 두 번째 줄은 어학연수
                                        String score_second = score[1];
                                        chineseType = ChineseType.LT;
                                        String[] score_second_split = score_second.split(" ");
                                        if (score_second.contains("불필요") || score_second.contains("우대")){
                                            scoreEtc += "\n어학연수 : " + score_second;
                                        }
                                        else {
                                            for (int j = 0; j < score_second_split.length; j++) {
                                                if (score_second_split[j].equals("HSK")) {
                                                    hskIndex = j;
                                                    break;
                                                }
                                            }
                                            chineseLevel = score_second_split[hskIndex + 1];
                                            if (score_second_split[hskIndex + 2].contains("점")) {
                                                chineseScore = score_second_split[hskIndex + 2];
                                            }
                                        }
                                        chineseQList.add(new ChineseQ(chineseType, chineseLevel, chineseScore));
                                    }
                                }

                                String qEtc = row.getCell(9).getStringCellValue() + "\n" + scoreEtc;

                                // 데이터 저장
                                universityInfo.setExType(ExType.exchange);
                                universityInfo.setCountry(findCountryByName(row.getCell(1).getStringCellValue()));
                                universityInfo.setName(row.getCell(2).getStringCellValue());
                                Cell recruitNumCell = row.getCell(3);
                                String recruitNumcellValue = "";
                                if (recruitNumCell != null) {
                                    // 셀 타입 확인
                                    if (recruitNumCell.getCellType() == CellType.NUMERIC) {
                                        // 숫자 값 처리
                                        int numericValue = (int) recruitNumCell.getNumericCellValue();
                                        recruitNumcellValue = String.valueOf(numericValue);
                                    } else {
                                        // 문자열 값 처리
                                        recruitNumcellValue = recruitNumCell.getStringCellValue();
                                    }
                                }
                                universityInfo.setRecruitNum(recruitNumcellValue);
                                universityInfo.setPeriod(row.getCell(4).getStringCellValue());
                                universityInfo.setGradeQ(grade);
                                if (i == 0) {
                                    universityInfo.setJapaneseQ(new JapaneseQ(japanese));
                                }
                                if (i == 1){
                                    universityInfo.setChineseQList(chineseQList);
                                }
                                universityInfo.setQualificationEtc(qEtc);
                                universityInfo.setExpCost(row.getCell(10).getStringCellValue());
                                universityInfo.setExpCostDesc(row.getCell(11).getStringCellValue());
                                universityInfo.setEtc(row.getCell(12).getStringCellValue());
                                universityInfo.setSemester(fileName.split("_")[0]);
                                dataList.add(universityInfo);
                            }
                        }
                    }
                }

            }

        }
        // UniversityInfoRepository에 저장
        universityInfoRepository.saveAll(dataList);

        model.addAttribute("datas", dataList);
        return "redirect:/info";
    }

    private static Country findCountryByName(String name) {
        return Arrays.stream(Country.values())
                .filter(country -> country.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
