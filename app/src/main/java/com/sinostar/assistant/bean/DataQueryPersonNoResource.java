package com.sinostar.assistant.bean;

public class DataQueryPersonNoResource {
    /**
     * AJBH : 42301012005002061
     * ODS_ID : 4319998
     * AJMC : 张5涉走配车test t
     * LARQ : 2005-04-22T00:00:00
     * BADW : 青岛海关缉私局烟台海关缉私分局侦查一科
     * AJFL : 行政
     * ZWMC : 张5
     * CSRQ : 1977-04-07T00:00:00
     * ZJHM : 350047test test
     */

    private String AJBH;
    private int ODS_ID;
    private String AJMC;
    private String LARQ;
    private String BADW;
    private String AJFL;
    private String ZWMC;
    private String CSRQ;
    private String ZJHM;
    private String DWMC;
    private String QYDM;
    private String DZ;

    public String getDWMC() {
        return DWMC;
    }

    public void setDWMC(String DWMC) {
        this.DWMC = DWMC;
    }

    public String getQYDM() {
        return QYDM;
    }

    public void setQYDM(String QYDM) {
        this.QYDM = QYDM;
    }

    public String getDZ() {
        return DZ;
    }

    public void setDZ(String DZ) {
        this.DZ = DZ;
    }

    public String getAJBH() {
        return AJBH;
    }

    public void setAJBH(String AJBH) {
        this.AJBH = AJBH;
    }

    public int getODS_ID() {
        return ODS_ID;
    }

    public void setODS_ID(int ODS_ID) {
        this.ODS_ID = ODS_ID;
    }

    public String getAJMC() {
        return AJMC;
    }

    public void setAJMC(String AJMC) {
        this.AJMC = AJMC;
    }

    public String getLARQ() {
        return LARQ.replaceAll("T", "  ");
    }

    public void setLARQ(String LARQ) {
        this.LARQ = LARQ;
    }

    public String getBADW() {
        return BADW;
    }

    public void setBADW(String BADW) {
        this.BADW = BADW;
    }

    public String getAJFL() {
        return AJFL;
    }

    public void setAJFL(String AJFL) {
        this.AJFL = AJFL;
    }

    public String getZWMC() {
        return ZWMC;
    }

    public void setZWMC(String ZWMC) {
        this.ZWMC = ZWMC;
    }

    public String getCSRQ() {
        return CSRQ;
    }

    public void setCSRQ(String CSRQ) {
        this.CSRQ = CSRQ;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }
}
