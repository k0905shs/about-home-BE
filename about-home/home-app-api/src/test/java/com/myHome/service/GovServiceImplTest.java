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
        BuildingLedgerDto.openApiResponse openApiResponse =
                govService.requestBuildingLedgerApi(new BuildingLedgerDto.openApiRequestParam("11680", "10300", "0", "0012", "0000"));
        Assertions.assertThat(openApiResponse.getInfoList().get(0).getArchArea()).isEqualTo("271.63");
    }
}