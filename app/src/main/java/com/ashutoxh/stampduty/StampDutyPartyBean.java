package com.ashutoxh.stampduty;

public class StampDutyPartyBean {
    private String PAN_NO;
    private String KEY_NAME;
    private String BLOCK_NO;
    private String ROAD;
    private String CITY;
    private String PIN;
    private String KEY_ID;

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

    public String getPAN_NO() {
        return PAN_NO;
    }

    public void setPAN_NO(String PAN_NO) {
        this.PAN_NO = PAN_NO;
    }

    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public String getBLOCK_NO() {
        return BLOCK_NO;
    }

    public void setBLOCK_NO(String BLOCK_NO) {
        this.BLOCK_NO = BLOCK_NO;
    }

    public String getROAD() {
        return ROAD;
    }

    public void setROAD(String ROAD) {
        this.ROAD = ROAD;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
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

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }
}
