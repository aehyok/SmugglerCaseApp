package com.sinostar.assistant.bean;

import java.util.List;

public class DataQureyPerson {


    private List<ZHTJBean> ZHTJ;
    private List<BAPTBean> BAPT;
    private List<XSYJBean> XSYJ;

    public List<ZHTJBean> getZHTJ() {
        return ZHTJ;
    }

    public void setZHTJ(List<ZHTJBean> ZHTJ) {
        this.ZHTJ = ZHTJ;
    }

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

    public static class ZHTJBean {
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

    public static class BAPTBean {
        /**
         * AJBH : 80125342018000003
         * ODS_ID : 7103614
         * AJMC : zhuxujuan20180606
         * LARQ : 2018-06-04T16:33:18
         * AJFL : 行政
         * BADW : 重庆海关缉私局查私处查私一科
         * ZWMC : 张5
         * CSRQ : 1977-04-07T00:00:00
         * ZJHM : 212368995466588
         */

        private String AJBH;
        private int ODS_ID;
        private String AJMC;
        private String LARQ;
        private String AJFL;
        private String BADW;
        private String ZWMC;
        private String CSRQ;
        private String ZJHM;

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
    }

    public static class XSYJBean {
        /**
         * ID : 06aa60d5-be5a-47c6-8ae8-a1946f2246d1
         * XSBH : 5300201304130001
         * XSMC : 张5私石英表（电子表）案
         * DJSJ : 2013-04-13T16:29:37
         * RYXM : 张5
         * CSRQ : 1977-04-07T00:00:00
         * ZJHM : H0722301000
         */

        private String ID;
        private String XSBH;
        private String XSMC;
        private String DJSJ;
        private String RYXM;
        private String CSRQ;
        private String ZJHM;
        private String BADW;
        private String AJFL;

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

        public String getRYXM() {
            return RYXM;
        }

        public void setRYXM(String RYXM) {
            this.RYXM = RYXM;
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
}
