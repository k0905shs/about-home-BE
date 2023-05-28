package com.myHome.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildingTypeTest {

    @Test
    public void 건물용도타입테스트() {
        BuildingType buildingType = BuildingType.getType("아파트");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.APART);

        buildingType = BuildingType.getType("오피스텔");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.OFFICETEL);

        buildingType = BuildingType.getType("단독주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.HOUSE);
        buildingType = BuildingType.getType("다가구주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.HOUSE);
        buildingType = BuildingType.getType("다중주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.HOUSE);

        buildingType = BuildingType.getType("다세대주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.TOWNHOUSE);
        buildingType = BuildingType.getType("연립주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.TOWNHOUSE);
        buildingType = BuildingType.getType("빌라");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.TOWNHOUSE);
        buildingType = BuildingType.getType("도시형 생활주택");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.TOWNHOUSE);

        buildingType = BuildingType.getType("근린 2종");
        Assertions.assertThat(buildingType).isEqualTo(BuildingType.EMPTY);
    
    }
    
}
