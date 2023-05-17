package com.myhome.service;


import com.myhome.collection.BuildingSale;
import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.repository.LandPrice.LandPriceRepositorySupport;
import com.myhome.repository.buildingSale.BuildingSaleRepository;
import com.myhome.repository.buildingSale.BuildingSaleRepositorySupport;
import com.myhome.util.BuildingCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeCheckServiceImpl implements HomeCheckService {

    private final LandPriceRepositorySupport landPriceRepositorySupport;
    private final BuildingSaleRepositorySupport buildingSaleRepositorySupport;
    private final GovService govService;

    @Override
    public HomeCheckDto.checkLandPriceResult checkLandPrice(HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        List<LandPrice> checkLandPriceList = landPriceRepositorySupport.findLandPriceList(checkLandPriceParam);

        List<HomeCheckDto.landPriceInfo> landPriceInfoList =
                checkLandPriceList.stream()
                        .map(LandPrice::getResponse)
                        .map(v -> new HomeCheckDto.landPriceInfo(v.getStdrYear(), v.getPblntfPclnd()))
                        .collect(Collectors.toList());

        String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19); //pnu코드
        int searchYear = checkLandPriceParam.getSearchYear();

        // TODO : 뎁스 너무 커서 해당 로직 반드시 리펙토링 해야함!
        if (searchYear != checkLandPriceList.size()) { //요청한 연도 수 만큼의 요청 데이터가 없으면
            //request 기록이 있는 year list 생성
            List<String> savedYearList = checkLandPriceList.stream()
                    .map(v -> v.getRequest().getStdrYear())
                    .collect(Collectors.toList());

            int nowYear = LocalDate.now().getYear(); //올해
            int startYear = nowYear - searchYear; //데이터 요청 시작 연도

            for (int i = startYear; i < nowYear; i++) { //기록이 없는 연도 공공데이터 request
                if (!savedYearList.contains(String.valueOf(i))) {
                    // request 실행
                    LandPriceDto.openApiResponse openApiResponse =
                            govService.requestLandPriceApi(new LandPriceDto.openApiRequestParam(pnu, String.valueOf(i), 1, 1));

                    //return dto List에 결과 추가
                    LandPriceDto.landPrice landPrice = openApiResponse.getField().getLandPriceList().get(0);
                    landPriceInfoList.add(new HomeCheckDto.landPriceInfo(landPrice.getStdrYear(), landPrice.getPblntfPclnd()));
                }
            }
        }
        return new HomeCheckDto.checkLandPriceResult(pnu, landPriceInfoList);
    }

    @Override
    public List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSale(HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) throws Exception {
        List<BuildingSale> buildingSaleList =
                buildingSaleRepositorySupport.findBuildingSaleList(checkBuildingSaleParam); //입력 받은 param으로 추출한 도큐먼트 리스트

        //Document list -> dto List
        List<HomeCheckDto.checkBuildingSaleResult> results =
                buildingSaleList.stream()
                        .map(HomeCheckDto.checkBuildingSaleResult::new)
                        .collect(Collectors.toList());

        //Document list -> 검색이 완료된 'dealYmd'
        List<String> searchedDateList = results.stream()
                .map(HomeCheckDto.checkBuildingSaleResult::getDate)
                .collect(Collectors.toList());

        //검색 시작 년월
        LocalDate searchStartDate = LocalDate.now().minusMonths(checkBuildingSaleParam.getSearchMonth());
        List<String> searchTargetList = new ArrayList<>();

        //검색 시작일을 기준으로 검색해야 하는 날짜 리스트 추가
        for (LocalDate ld = searchStartDate; ld.isBefore(LocalDate.now()); ld = ld.plusMonths(1)) {
            String dealYmd = ld.format(DateTimeFormatter.ofPattern("YYYYMM"));
            if (!searchedDateList.contains(dealYmd)) { //건물 실거래가 api 요청을 하지 않은 년월 리스트 추가
                searchTargetList.add(dealYmd);
            }
        }

        // TODO : ThreadTaskPoolExecutor 과 개별 실행중 뭐가 더 낳은 방법일지 생각을 해보자..
        String lawdCd = BuildingCodeUtils.getLawdCd(checkBuildingSaleParam.getBuildingCode());
        boolean reCheck = false;
        for (String dealYmd : searchTargetList) { //개별 API 재실행
            BuildingSaleDto.openApiResponse openApiResponse =
                    govService.requestBuildingSalesApi(new BuildingSaleDto.openApiRequestParam(lawdCd, dealYmd, checkBuildingSaleParam.getBuildingType()));
            reCheck = true;
        }
        // 전체 로직 실행 후 재검색
        if (reCheck) {
            buildingSaleList =
                    buildingSaleRepositorySupport.findBuildingSaleList(checkBuildingSaleParam); //입력 받은 param으로 추출한 도큐먼트 리스트
            //Document list -> dto List
             results = buildingSaleList.stream()
                     .map(HomeCheckDto.checkBuildingSaleResult::new)
                     .collect(Collectors.toList());
        }

        return results;
    }
}
