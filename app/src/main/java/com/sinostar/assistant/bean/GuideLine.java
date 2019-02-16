package com.sinostar.assistant.bean;

import java.util.List;

public class GuideLine {


    private DefineBean Define;
    private List<DataBean> Data;

    public DefineBean getDefine() {
        return Define;
    }

    public void setDefine(DefineBean Define) {
        this.Define = Define;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DefineBean {
        /**
         * $id : 1
         * Id : 7030000009602
         * GuideLineName : 个人待办列表
         * GroupName : 办案助手
         * GuideLineMethod : null
         * FatherId : 7030000009601
         * DisplayOrder : 0
         * GuideLineMeta : null
         * Description :
         * Children : []
         * MD_GuideLineGroup : null
         * DetailMeta : null
         * Parameters : []
         * ResultGroups : [{"Fields":[],"GroupName":"DEFAULT","DisplayTitle":"(默认组)","TextAlign":"CENTER","DisplayOrder":1,"CanHide":false,"DefaultStatus":"SHOW"}]
         * DetailDefines : []
         * GuideLineQueryMethod : null
         */

        private String $id;
        private String Id;
        private String GuideLineName;
        private String GroupName;
        private Object GuideLineMethod;
        private String FatherId;
        private int DisplayOrder;
        private Object GuideLineMeta;
        private String Description;
        private Object MD_GuideLineGroup;
        private Object DetailMeta;
        private Object GuideLineQueryMethod;
        private List<?> Children;
        private List<?> Parameters;
        private List<ResultGroupsBean> ResultGroups;
        private List<?> DetailDefines;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getGuideLineName() {
            return GuideLineName;
        }

        public void setGuideLineName(String GuideLineName) {
            this.GuideLineName = GuideLineName;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public Object getGuideLineMethod() {
            return GuideLineMethod;
        }

        public void setGuideLineMethod(Object GuideLineMethod) {
            this.GuideLineMethod = GuideLineMethod;
        }

        public String getFatherId() {
            return FatherId;
        }

        public void setFatherId(String FatherId) {
            this.FatherId = FatherId;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public Object getGuideLineMeta() {
            return GuideLineMeta;
        }

        public void setGuideLineMeta(Object GuideLineMeta) {
            this.GuideLineMeta = GuideLineMeta;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public Object getMD_GuideLineGroup() {
            return MD_GuideLineGroup;
        }

        public void setMD_GuideLineGroup(Object MD_GuideLineGroup) {
            this.MD_GuideLineGroup = MD_GuideLineGroup;
        }

        public Object getDetailMeta() {
            return DetailMeta;
        }

        public void setDetailMeta(Object DetailMeta) {
            this.DetailMeta = DetailMeta;
        }

        public Object getGuideLineQueryMethod() {
            return GuideLineQueryMethod;
        }

        public void setGuideLineQueryMethod(Object GuideLineQueryMethod) {
            this.GuideLineQueryMethod = GuideLineQueryMethod;
        }

        public List<?> getChildren() {
            return Children;
        }

        public void setChildren(List<?> Children) {
            this.Children = Children;
        }

        public List<?> getParameters() {
            return Parameters;
        }

        public void setParameters(List<?> Parameters) {
            this.Parameters = Parameters;
        }

        public List<ResultGroupsBean> getResultGroups() {
            return ResultGroups;
        }

        public void setResultGroups(List<ResultGroupsBean> ResultGroups) {
            this.ResultGroups = ResultGroups;
        }

        public List<?> getDetailDefines() {
            return DetailDefines;
        }

        public void setDetailDefines(List<?> DetailDefines) {
            this.DetailDefines = DetailDefines;
        }

        public static class ResultGroupsBean {
            /**
             * Fields : []
             * GroupName : DEFAULT
             * DisplayTitle : (默认组)
             * TextAlign : CENTER
             * DisplayOrder : 1
             * CanHide : false
             * DefaultStatus : SHOW
             */

            private String GroupName;
            private String DisplayTitle;
            private String TextAlign;
            private int DisplayOrder;
            private boolean CanHide;
            private String DefaultStatus;
            private List<?> Fields;

            public String getGroupName() {
                return GroupName;
            }

            public void setGroupName(String GroupName) {
                this.GroupName = GroupName;
            }

            public String getDisplayTitle() {
                return DisplayTitle;
            }

            public void setDisplayTitle(String DisplayTitle) {
                this.DisplayTitle = DisplayTitle;
            }

            public String getTextAlign() {
                return TextAlign;
            }

            public void setTextAlign(String TextAlign) {
                this.TextAlign = TextAlign;
            }

            public int getDisplayOrder() {
                return DisplayOrder;
            }

            public void setDisplayOrder(int DisplayOrder) {
                this.DisplayOrder = DisplayOrder;
            }

            public boolean isCanHide() {
                return CanHide;
            }

            public void setCanHide(boolean CanHide) {
                this.CanHide = CanHide;
            }

            public String getDefaultStatus() {
                return DefaultStatus;
            }

            public void setDefaultStatus(String DefaultStatus) {
                this.DefaultStatus = DefaultStatus;
            }

            public List<?> getFields() {
                return Fields;
            }

            public void setFields(List<?> Fields) {
                this.Fields = Fields;
            }
        }
    }

    public static class DataBean {
        /**
         * XH : 1
         * AJID : 920000000200949
         * AJBH : 23301342018000136
         * AJMC : lqmTest2018072700001
         * AJZL : 一般案件
         * STATENAME : 案件受理
         * STATEORDER : 6267170
         */

        private int XH;
        private long AJID;
        private String AJBH;
        private String AJMC;
        private String AJZL;
        private String STATENAME;
        private long STATEORDER;

        public int getXH() {
            return XH;
        }

        public void setXH(int XH) {
            this.XH = XH;
        }

        public long getAJID() {
            return AJID;
        }

        public void setAJID(long AJID) {
            this.AJID = AJID;
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

        public String getAJZL() {
            return AJZL;
        }

        public void setAJZL(String AJZL) {
            this.AJZL = AJZL;
        }

        public String getSTATENAME() {
            return STATENAME;
        }

        public void setSTATENAME(String STATENAME) {
            this.STATENAME = STATENAME;
        }

        public long getSTATEORDER() {
            return STATEORDER;
        }

        public void setSTATEORDER(int STATEORDER) {
            this.STATEORDER = STATEORDER;
        }
    }
}
