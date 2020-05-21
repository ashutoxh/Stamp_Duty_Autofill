package com.ashutoxh.stampduty;

public class StampDutyPartyBean {
    @Override
    public String toString() {
        return "StampDutyPartyBean{" +
                "PAN_NO='" + PAN_NO + '\'' +
                ", KEY_NAME='" + KEY_NAME + '\'' +
                ", BLOCK_NO='" + BLOCK_NO + '\'' +
                ", ROAD='" + ROAD + '\'' +
                ", CITY='" + CITY + '\'' +
                ", PIN='" + PIN + '\'' +
                ", KEY_ID='" + KEY_ID + '\'' +
                '}';
    }

    public StampDutyPartyBean(String KEY_ID, String KEY_NAME, String PAN_NO, String BLOCK_NO, String ROAD, String CITY, String PIN) {
        this.KEY_ID = KEY_ID;
        this.PAN_NO = PAN_NO;
        this.KEY_NAME = KEY_NAME;
        this.BLOCK_NO = BLOCK_NO;
        this.ROAD = ROAD;
        this.CITY = CITY;
        this.PIN = PIN;
    }

    public void setPAN_NO(String PAN_NO) {
        this.PAN_NO = PAN_NO;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public void setBLOCK_NO(String BLOCK_NO) {
        this.BLOCK_NO = BLOCK_NO;
    }

    public void setROAD(String ROAD) {
        this.ROAD = ROAD;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getPAN_NO() {
        return PAN_NO;
    }

    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public String getBLOCK_NO() {
        return BLOCK_NO;
    }

    public String getROAD() {
        return ROAD;
    }

    public String getCITY() {
        return CITY;
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getPIN() {
        return PIN;
    }

    private String PAN_NO;
    private String KEY_NAME;
    private String BLOCK_NO;
    private String ROAD;
    private String CITY;
    private String PIN;
    private String KEY_ID;
}
