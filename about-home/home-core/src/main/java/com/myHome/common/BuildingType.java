package com.myHome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 조회가 가능한 건물 타입 Enum
 */
@AllArgsConstructor
@Getter
public enum BuildingType {
    APART("아파트", GovRequestUri.BUILDING_SALE_APART, GovRequestUri.BUILDING_RENT_APART, List.of("아파트")),
    TOWNHOUSE("다세대주택 / 연립주택 / 빌라 / 도시형 생활주택", GovRequestUri.BUILDING_SALE_TOWNHOUSE, GovRequestUri.BUILDING_RENT_TOWNHOUSE, List.of("다세대주택", "연립주택", "빌라", "도시형 생활주택")),
    OFFICETEL("오피스텔", GovRequestUri.BUILDING_SALE_OFFICETEL, GovRequestUri.BUILDING_RENT_OFFICETEL, List.of("오피스텔")),
    HOUSE("단독주택 / 다가구주택 / 다중주택", GovRequestUri.BUILDING_SALE_HOUSE, GovRequestUri.BUILDING_RENT_HOUSE, List.of("단독주택", "다가구주택", "다중주택")),

    //검색이 불가능한 건물 사용처
    EMPTY("아직 검색이 불가능한 건물입니다.", null, null, Collections.EMPTY_LIST);

    private final String desc; //설명
    private final GovRequestUri buildingSaleUri; // 실거래 정보
    private final GovRequestUri buildingRentUri; // 전/월세 정보
    private final List<String> usageList;

    /**
     * 입력받은 건물 용도에 따른 건물타입 ENUM 타입 return
     */
    public static BuildingType getType(String usage) {
        return Arrays.stream(BuildingType.values())
                .filter(BuildingType -> BuildingType.hasUsage(usage))
                .findAny()
                .orElseGet(() -> BuildingType.EMPTY);
    }

    /**
     * 공공데이터로 가져온 건축물 사용처 확인
     */
    private boolean hasUsage(String usage) {
        return usageList.stream()
                .anyMatch(v -> v.equals(usage));
    }
}
