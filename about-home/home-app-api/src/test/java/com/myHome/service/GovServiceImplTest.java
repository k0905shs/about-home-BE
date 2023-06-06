package com.myHome.service;

import com.myHome.model.openApi.BuildingLedgerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GovServiceImplTest {

    @Autowired
    private GovServiceImpl govService;

    @Test
    void requestBuildingLedgerApi() throws Exception {
        BuildingLedgerDto.openApiRequestParam openApiRequestParam = new BuildingLedgerDto.openApiRequestParam("1138010700101780010024489", "176", 0);
        System.out.println(openApiRequestParam.toString());
        BuildingLedgerDto.openApiResponse openApiResponse = govService.requestBuildingLedgerApi(openApiRequestParam);
        System.out.println(openApiResponse.toString());
        Assertions.assertThat(openApiResponse.getTotalCount()).isNotEqualTo(0);
    }
}