package com.ssm.git_client.common;

import lombok.Getter;

@Getter
public enum Component {
    SPS(2377),
    FPS(1814),
    PMS(1763),
    TEPS(2656),
    XDS(2557),
    CHPS(2849),
    CEP(2826),
    Spotlight(2593),
    JOS(1459),
    RPS(3634),
    NEPS(2809),
    MFCS(3886),
    ACS(778),
    Inventory(435),
    USS(952),
    USA(951),
    NTS(2793),
    TCS(820),
    SAL(1674),
    CUS(791),
    RIW(2495),
    WES(2264),
    CONFIG(2531);

    private final int id;

    Component(int id) {
        this.id = id;
    }
}
