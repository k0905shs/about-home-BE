package com.myHome.service;

import com.myHome.model.homeCheck.HomeCheckDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeCheckServiceImplTest {

    @Autowired
    private HomeCheckService homeCheckService;

    @Test
    public void buildingLedger() throws Exception {
        HomeCheckDto.checkBuildingLedgerParam checkBuildingLedgerParam = new HomeCheckDto.checkBuildingLedgerParam("176", "1138010700101780010024489");

        HomeCheckDto.checkBuildingLedgerResult checkBuildingLedgerResult =
                homeCheckService.checkBuildingLedger(checkBuildingLedgerParam);
    }

}