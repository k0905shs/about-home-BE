package com.myHome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myHome.common.ResponseFormat;
import lombok.*;

import java.util.List;

/**
 * 공공데이터 건축물대장 표제부 조회 정보 Data Transfer Object
 */
public class BuildingLedgerDto {

    @Getter
    public static class openApiRequestParam extends GovCommonDto{
        private String sigunguCd; // 행정표준코드 // 11680
        private String bjdongCd; // 행정표준코드 // 10300
        private String platGbCd; // 0:대지 1:산 2:블록
        private String bun; //번
        private String ji; //지

        //TODO : 아래 코드는 추구 기능 추가 할때 추가 예정
//        private String startDate;
//        private String endDate;

        @Builder
        public openApiRequestParam(String sigunguCd, String bjdongCd, String platGbCd, String bun, String ji) {
            super(1000, 1, ResponseFormat.XML);
            this.sigunguCd = sigunguCd;
            this.bjdongCd = bjdongCd;
            this.platGbCd = platGbCd;
            this.bun = bun;
            this.ji = ji;
        }
    }

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown=true)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class openApiResponse{
        private List<BuildingLedgerDto.ledgerInfo> infoList;
        private int totalCount;

        @Builder
        public openApiResponse(List<BuildingLedgerDto.ledgerInfo> infoList, int totalCount) {
            this.infoList = infoList;
            this.totalCount = totalCount;
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class ledgerInfo{
        private String archArea;
        private String atchBldArea;
        private String atchBldCnt;
        private String bcRat;
        private String bjdongCd;
        private String bldNm;
        private String block;
        private String bun;
        private String bylotCnt;
        private String crtnDay;
        private String dongNm;
        private String emgenUseElvtCnt;
        private String engrEpi;
        private String engrGrade;
        private String engrRat;
        private String etcPurps;
        private String etcRoof;
        private String etcStrct;
        private String fmlyCnt;
        private String gnBldCert;
        private String gnBldGrade;
        private String grndFlrCnt;
        private String heit;
        private String ji;
        private String mainAtchGbCdNm;
        private String mainPurpsCd;
        private String mainPurpsCdNm;
        private String mgmBldrgstPk;
        private String platPlc;
        private String pmsDay;
        private String regstrGbCdNm;
        private String regstrKindCd;
        private String regstrKindCdNm;
        private String rnum;
        private String roofCd;
        private String sigunguCd;
        private String strctCd;
        private String strctCdNm;
        private String totArea;
        private String totDongTotArea;
        private String useAprDay;
        private String vlRatEstmTotArea;
    }

}
