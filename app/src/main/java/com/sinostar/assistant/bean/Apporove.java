package com.sinostar.assistant.bean;

/**
 * 待审批事项实体类
 */
public class Apporove {

    /**
     * ID : 920000000174465
     * AJID : 920000000174435
     * AJMC : zhuxujuan20180503
     * CQRQ : 2018-05-01T18:10:47
     * CQRY : 赵福地
     * CQSX : 行政处罚审批
     * STATEORDER : 6263032
     */

    private long ID;
    private long AJID;
    private String AJMC;
    private String CQRQ;
    private String CQRY;
    private String CQSX;
    private double STATEORDER;
    /**
     * AJBH : 23301342018000127
     * ODS_ID : 7117565
     * LARQ : 2018-07-19T17:37:47
     * AJFL : 行政
     * BADW : 南京海关缉私局苏州海关缉私分局昆山缉私中队
     * ZWMC : 杩丰楺
     * CSRQ : 1986-06-05T00:00:00
     * ZJHM : 1321321321321
     */

    private String AJBH;
    private int ODS_ID;
    private String LARQ;
    private String AJFL;
    private String BADW;
    private String ZWMC;
    private String CSRQ;
    private String ZJHM;
    /**
     * JBRQ : 2018-08-09T14:36:27
     * SPRQ : 2018-08-09T14:36:46
     * SPR : 赵福地<旧>
     * SQR : null
     * TDW : 南京海关缉私局苏州海关缉私分局昆山缉私中队
     * TSTATE : 案件登记
     * CSTATE : 案件处理
     */

    private String JBRQ;
    private String SPRQ;
    private String SPR;
    private Object SQR;
    private String TDW;
    private String TSTATE;
    private String CSTATE;
    /**
     * SQCQRY : null
     * SJRY : 王汉全
     * SJDW : 南京海关缉私局苏州海关缉私分局昆山缉私中队
     * SXID : 920000000211660
     * XKZZH : 64
     * STATEID : b79ce012-e01e-4f7b-812f-f1e93cae1298
     * STATEORDER : 6268192
     */

    private Object SQCQRY;
    private String SJRY;
    private String SJDW;
    private long SXID;
    private String XKZZH;
    private String STATEID;



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

    public double getSTATEORDER() {
        return STATEORDER;
    }

    public void setSTATEORDER(double STATEORDER) {
        this.STATEORDER = STATEORDER;
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

    public String getLARQ() {
        return LARQ;
    }

    public void setLARQ(String LARQ) {
        this.LARQ = LARQ;
    }

    public String getAJFL() {
        return AJFL;
    }

    public void setAJFL(String AJFL) {
        this.AJFL = AJFL;
    }

    public String getBADW() {
        return BADW;
    }

    public void setBADW(String BADW) {
        this.BADW = BADW;
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

    public String getJBRQ() {
        return JBRQ;
    }

    public void setJBRQ(String JBRQ) {
        this.JBRQ = JBRQ;
    }

    public String getSPRQ() {
        return SPRQ.replaceAll("T", "  ");
    }

    public void setSPRQ(String SPRQ) {
        this.SPRQ = SPRQ;
    }

    public String getSPR() {
        return SPR;
    }

    public void setSPR(String SPR) {
        this.SPR = SPR;
    }

    public Object getSQR() {
        return SQR;
    }

    public void setSQR(Object SQR) {
        this.SQR = SQR;
    }

    public String getTDW() {
        return TDW;
    }

    public void setTDW(String TDW) {
        this.TDW = TDW;
    }

    public String getTSTATE() {
        return TSTATE;
    }

    public void setTSTATE(String TSTATE) {
        this.TSTATE = TSTATE;
    }

    public String getCSTATE() {
        return CSTATE;
    }

    public void setCSTATE(String CSTATE) {
        this.CSTATE = CSTATE;
    }

    public Object getSQCQRY() {
        return SQCQRY;
    }

    public void setSQCQRY(Object SQCQRY) {
        this.SQCQRY = SQCQRY;
    }

    public String getSJRY() {
        return SJRY;
    }

    public void setSJRY(String SJRY) {
        this.SJRY = SJRY;
    }

    public String getSJDW() {
        return SJDW;
    }

    public void setSJDW(String SJDW) {
        this.SJDW = SJDW;
    }

    public long getSXID() {
        return SXID;
    }

    public void setSXID(long SXID) {
        this.SXID = SXID;
    }

    public String getXKZZH() {
        return XKZZH;
    }

    public void setXKZZH(String XKZZH) {
        this.XKZZH = XKZZH;
    }

    public String getSTATEID() {
        return STATEID;
    }

    public void setSTATEID(String STATEID) {
        this.STATEID = STATEID;
    }

}
