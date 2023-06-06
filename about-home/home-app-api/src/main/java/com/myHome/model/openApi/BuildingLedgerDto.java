package com.myHome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myHome.common.ResponseFormat;
import com.myHome.util.AddressCodeUtils;
import lombok.*;

import java.util.List;

/**
 * 공공데이터 건축물대장 표제부 조회 정보 Data Transfer Object
 */
public class BuildingLedgerDto {

    @Getter
    @ToString
    public static class openApiRequestParam extends GovCommonDto{
        private String sigunguCd; // 행정표준코드 11680
        private String bjdongCd; // 행정표준코드 10300
        private int platGbCd; // 0:대지 1:산 2:블록
        private String bun; //번
        private String ji; //지

        //TODO : 아래 코드는 추구 기능 추가 할때 추가 예정
//        private String startDate;
//        private String endDate;

        @Builder
        public openApiRequestParam(String buildingCode, String jibun, int platGbCd) {
            super(1000, 1, ResponseFormat.XML);
            this.sigunguCd = buildingCode.substring(0, 5);
            this.bjdongCd = buildingCode.substring(5, 10);
            this.platGbCd = platGbCd;
            this.bun = AddressCodeUtils.getBun(jibun);
            this.ji = AddressCodeUtils.getJi(jibun);
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
        // FIXME : 우선 전체 필드중 필요해 보이는 것만 추가가한거임
        private String etcPurps; //용도정보(건축물대장 주용도 정보)
        private String mainPurpsCdNm; //주용도코드명
        private String grndFlrCnt; //지상층수
        private String ugrndFlrCnt; //지하층수
        private String hhldCnt; //세대수(세대)
        private String fmlyCnt; //가구수(가구)
        private String hoCnt; //호수

        private String stcnsDay; //착공일
        private String useAprDay; //사용 승인일

        private String pmsnoYear; //허가번호년
        private String pmsDay; //허가일

        private String atchBldCnt;//부속 건축물 수

        private String platArea; //대지면적(㎡)
        private String archArea; //건축면적(㎡)
        private String bcRat; //건폐율(%)
        private String totArea; //연면적(㎡)
        private String vlRatEstmTotArea; //용적률산정연면적(㎡)
        private String vlRat; //용적률(%)
        private String totDongTotArea; //총 동 연면적
        private String atchBldArea; //부속 건축물 면적

        private String platPlc; //대지위치
        private String sigunguCd; //시군구코드
        private String bjdongCd; //법정동코드
        private String platGbCd; //대지구분코드
        private String bun; //번
        private String ji; //지
        private String dongNm; //동이름
        
        private String mgmBldrgstPk; //관리건축물대장PK
        private String regstrGbCd; //대장구분코드
        private String regstrGbCdNm; //대장구분코드명
        private String regstrKindCd; //대장종류코드
        private String regstrKindCdNm; //대장종류코드명

    }

}
