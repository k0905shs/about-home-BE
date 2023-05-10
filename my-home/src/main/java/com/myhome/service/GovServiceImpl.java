package com.myhome.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.openApi.GovApi;
import com.myhome.type.GovRequestUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GovServiceImpl implements GovService{

    private final GovApi govApi;
    private final ObjectMapper objectMapper;

    @Override
    public LandPriceDto.openApiResponse requestLandPriceApi(final LandPriceDto.openApiRequestParam requestParam) throws Exception{
        String response = govApi.requestGov(requestParam, GovRequestUrl.LAND_PRICE);
        return objectMapper.readValue(response, LandPriceDto.openApiResponse.class);
    }

    @Override
    public StanReginDto.openApiResponse requestStanReginApi(final StanReginDto.openApiRequestParam requestParam) throws Exception{
        String response = govApi.requestGov(requestParam, GovRequestUrl.STAN_REGIN);

        // TODO : 해당 API response를 DTO 로 매핑하기는 데이터 자체가 너무 복잡해서 문자열에서 직접 필요한 데이터만 절삭해서 사용함 추후 가능하면 수정
        StringBuilder json = new StringBuilder(response);
        json.delete(0, json.indexOf("row"))
                .delete(json.indexOf("}"), json.length() - 1)
                .delete(0, json.indexOf("[") + 1);

        return objectMapper.readValue(json.toString(), StanReginDto.openApiResponse.class);
    }
}