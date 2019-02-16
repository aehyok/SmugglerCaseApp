package com.sinostar.assistant.bean;

public class ObtainEvidenceBean {
    /**
     * ID : 5520000615938
     * AJID : 5520000612468
     * XKZZH : 65
     * AJBH : 23301342017000105
     * AJMC : gkc3212
     * CQRQ : 2017-05-13T17:06:48
     * CQDW : 南京海关缉私局苏州海关缉私分局昆山缉私中队
     * CQRY : 赵福地<旧>
     * CQSX : 财物处置审批
     * AJZL : 简单案件
     * STATEORDER : 5230000081236
     */

    private long ID;
    private long AJID;
    private String XKZZH;
    private String AJBH;
    private String AJMC;
    private String CQRQ;
    private String CQDW;
    private String CQRY;
    private String CQSX;
    private String AJZL;
    private long STATEORDER;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getAJID() {
        return AJID;
    }

    public void setAJID(long AJID) {
        this.AJID = AJID;
    }

    public String getXKZZH() {
        return XKZZH;
    }

    public void setXKZZH(String XKZZH) {
        this.XKZZH = XKZZH;
    }

    public String getAJBH() {
        return AJBH;
    }

    public void setAJBH(String AJBH) {
        this.AJBH = AJBH;
    }

    public String getAJMC() {
        return AJMC;
    }

    public void setAJMC(String AJMC) {
        this.AJMC = AJMC;
    }

    public String getCQRQ() {
        return CQRQ.replaceAll("T", "  ");
    }

    public void setCQRQ(String CQRQ) {
        this.CQRQ = CQRQ;
    }

    public String getCQDW() {
        return CQDW;
    }

    public void setCQDW(String CQDW) {
        this.CQDW = CQDW;
    }

    public String getCQRY() {
        return CQRY;
    }

    public void setCQRY(String CQRY) {
        this.CQRY = CQRY;
    }

    public String getCQSX() {
        return CQSX;
    }

    public void setCQSX(String CQSX) {
        this.CQSX = CQSX;
    }

    public String getAJZL() {
        return AJZL;
    }

    public void setAJZL(String AJZL) {
        this.AJZL = AJZL;
    }

    public long getSTATEORDER() {
        return STATEORDER;
    }

    public void setSTATEORDER(long STATEORDER) {
        this.STATEORDER = STATEORDER;
    }
}
