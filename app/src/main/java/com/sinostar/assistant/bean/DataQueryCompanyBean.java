package com.sinostar.assistant.bean;

import java.util.List;

public class DataQueryCompanyBean {

    private List<BAPTBean> BAPT;
    private List<XSYJBean> XSYJ;
    private List<ZHTJBean> ZHTJ;

    public List<BAPTBean> getBAPT() {
        return BAPT;
    }

    public void setBAPT(List<BAPTBean> BAPT) {
        this.BAPT = BAPT;
    }

    public List<XSYJBean> getXSYJ() {
        return XSYJ;
    }

    public void setXSYJ(List<XSYJBean> XSYJ) {
        this.XSYJ = XSYJ;
    }

    public List<ZHTJBean> getZHTJ() {
        return ZHTJ;
    }

    public void setZHTJ(List<ZHTJBean> ZHTJ) {
        this.ZHTJ = ZHTJ;
    }

    public static class BAPTBean {
        /**
         * AJBH : 23301342015001083
         * ODS_ID : 863609
         * AJMC : osnUguv512623281134
         * LARQ : 2015-09-07T10:40:15
         * AJFL : 行政
         * DWMC : 青岛琴琴加工贸易有限公司
         * QYDM : 23234234
         * LXDZ : null
         */

        private String AJBH;
        private int ODS_ID;
        private String AJMC;
        private String LARQ;
        private String AJFL;
        private String DWMC;
        private String QYDM;
        private Object LXDZ;
        private String BADW;

        public String getBADW() {
            return BADW;
        }

        public void setBADW(String BADW) {
            this.BADW = BADW;
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

        public String getAJFL() {
            return AJFL;
        }

        public void setAJFL(String AJFL) {
            this.AJFL = AJFL;
        }

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

        public Object getLXDZ() {
            return LXDZ;
        }

        public void setLXDZ(Object LXDZ) {
            this.LXDZ = LXDZ;
        }
    }

    public static class XSYJBean {
        /**
         * ID : 62ab4d47-805f-4b96-bb26-78faa846f759
         * XSBH : 5300201212250003
         * XSMC : 青岛琴琴加工贸易有限公司涉嫌违规案件
         * DJSJ : 2012-12-25T10:24:34
         * DWMC : 青岛琴琴加工贸易有限公司
         * QYBM : 243242
         * LXDZ : null
         * AJFL : 线索
         * BADW : 缉私局罗湖海关缉私分局
         */

        private String ID;
        private String XSBH;
        private String XSMC;
        private String DJSJ;
        private String DWMC;
        private String QYBM;
        private Object LXDZ;
        private String AJFL;
        private String BADW;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getXSBH() {
            return XSBH;
        }

        public void setXSBH(String XSBH) {
            this.XSBH = XSBH;
        }

        public String getXSMC() {
            return XSMC;
        }

        public void setXSMC(String XSMC) {
            this.XSMC = XSMC;
        }

        public String getDJSJ() {
            return DJSJ;
        }

        public void setDJSJ(String DJSJ) {
            this.DJSJ = DJSJ;
        }

        public String getDWMC() {
            return DWMC;
        }

        public void setDWMC(String DWMC) {
            this.DWMC = DWMC;
        }

        public String getQYBM() {
            return QYBM;
        }

        public void setQYBM(String QYBM) {
            this.QYBM = QYBM;
        }

        public Object getLXDZ() {
            return LXDZ;
        }

        public void setLXDZ(Object LXDZ) {
            this.LXDZ = LXDZ;
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
    }

    public static class ZHTJBean {
        /**
         * AJBH : 42309012007005261
         * ODS_ID : 53064714
         * AJMC : 青岛琴琴加工贸易有限公司涉嫌违规案件
         * LARQ : 2007-12-25T00:00:00
         * BADW : 青岛海关缉私局流亭机场海关缉私分局法制科
         * AJFL : 行政
         * DWMC : 青岛琴琴加工贸易有限公司
         * QYDM : 3702948817
         * DZ : 城5区庄路华test t
         */

        private String AJBH;
        private int ODS_ID;
        private String AJMC;
        private String LARQ;
        private String BADW;
        private String AJFL;
        private String DWMC;
        private String QYDM;
        private String DZ;

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
            return LARQ;
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
    }
}
