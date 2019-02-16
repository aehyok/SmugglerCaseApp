package com.sinostar.assistant.bean;

import java.util.List;

public class CaseLinkCase {
    /**
     * CurrentStatusOrder : 1200
     * CaseOfLink : [{"Id":10003,"LinkName":"案件登记","CaseType":"行政一般案件","DisplayOrder":1100},{"Id":10004,"LinkName":"案件登记","CaseType":"行政一般案件","DisplayOrder":1100},{"Id":10005,"LinkName":"调查取证","CaseType":"行政一般案件","DisplayOrder":1400},{"Id":10006,"LinkName":"案件审理","CaseType":"行政一般案件","DisplayOrder":1500},{"Id":10007,"LinkName":"告知送达","CaseType":"行政一般案件","DisplayOrder":1600},{"Id":10008,"LinkName":"案件执行","CaseType":"行政一般案件","DisplayOrder":1700},{"Id":10279,"LinkName":"线索zzz","CaseType":"行政一般案件","DisplayOrder":1000}]
     */

    private int CurrentStatusOrder;
    private List<CaseOfLinkBean> CaseOfLink;

    public int getCurrentStatusOrder() {
        return CurrentStatusOrder;
    }

    public void setCurrentStatusOrder(int CurrentStatusOrder) {
        this.CurrentStatusOrder = CurrentStatusOrder;
    }

    public List<CaseOfLinkBean> getCaseOfLink() {
        return CaseOfLink;
    }

    public void setCaseOfLink(List<CaseOfLinkBean> CaseOfLink) {
        this.CaseOfLink = CaseOfLink;
    }

    public static class CaseOfLinkBean {
        /**
         * Id : 10003
         * LinkName : 案件登记
         * CaseType : 行政一般案件
         * DisplayOrder : 1100
         */

        private int Id;
        private String LinkName;
        private String CaseType;
        private int DisplayOrder;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getLinkName() {
            return LinkName;
        }

        public void setLinkName(String LinkName) {
            this.LinkName = LinkName;
        }

        public String getCaseType() {
            return CaseType;
        }

        public void setCaseType(String CaseType) {
            this.CaseType = CaseType;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }
    }
}
