package com.sinostar.assistant.bean;

import java.util.List;

/**
 * 文书列表实体类
 */
public class Document {


    /**
     * ID : 920000000174465
     * SXSM : 行政处罚审批
     * BZID : 920000000174435
     * SCSJ : 2018-05-03T18:09:01
     * SXZT : 待审批
     * ActionID : 36f22ff6-ab96-438e-adb3-57096800eae5
     * DYSX : [{"DocId":"920000000174449","DocTypeId":"920000000091312","DocTypeDetailsId":"920000000091314","DocTitle":"简单案件审批表"},{"DocId":"920000000174466","DocTypeId":"5520000000396","DocTypeDetailsId":"5520000000429","DocTitle":"告知单拟稿（简单）"},{"DocId":"920000000174467","DocTypeId":"5520000000423","DocTypeDetailsId":"5520000000432","DocTitle":"决定书拟稿（简单）"}]
     * AttachmentCount : 0
     */

    private String ID;
    private String SXSM;
    private String BZID;
    private String SCSJ;
    private String SXZT;
    private String ActionID;
    private int AttachmentCount;
    private List<DYSXBean> DYSX;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSXSM() {
        return SXSM;
    }

    public void setSXSM(String SXSM) {
        this.SXSM = SXSM;
    }

    public String getBZID() {
        return BZID;
    }

    public void setBZID(String BZID) {
        this.BZID = BZID;
    }

    public String getSCSJ() {
        return SCSJ;
    }

    public void setSCSJ(String SCSJ) {
        this.SCSJ = SCSJ;
    }

    public String getSXZT() {
        return SXZT;
    }

    public void setSXZT(String SXZT) {
        this.SXZT = SXZT;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String ActionID) {
        this.ActionID = ActionID;
    }

    public int getAttachmentCount() {
        return AttachmentCount;
    }

    public void setAttachmentCount(int AttachmentCount) {
        this.AttachmentCount = AttachmentCount;
    }

    public List<DYSXBean> getDYSX() {
        return DYSX;
    }

    public void setDYSX(List<DYSXBean> DYSX) {
        this.DYSX = DYSX;
    }

    public static class DYSXBean {
        /**
         * DocId : 920000000174449
         * DocTypeId : 920000000091312
         * DocTypeDetailsId : 920000000091314
         * DocTitle : 简单案件审批表
         */

        private String DocId;
        private String DocTypeId;
        private String DocTypeDetailsId;
        private String DocTitle;

        public String getDocId() {
            return DocId;
        }

        public void setDocId(String DocId) {
            this.DocId = DocId;
        }

        public String getDocTypeId() {
            return DocTypeId;
        }

        public void setDocTypeId(String DocTypeId) {
            this.DocTypeId = DocTypeId;
        }

        public String getDocTypeDetailsId() {
            return DocTypeDetailsId;
        }

        public void setDocTypeDetailsId(String DocTypeDetailsId) {
            this.DocTypeDetailsId = DocTypeDetailsId;
        }

        public String getDocTitle() {
            return DocTitle;
        }

        public void setDocTitle(String DocTitle) {
            this.DocTitle = DocTitle;
        }
    }

    private ItemInfoBean itemInfo;
    private List<ButtonListBean> buttonList;

    public ItemInfoBean getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfoBean itemInfo) {
        this.itemInfo = itemInfo;
    }

    public List<ButtonListBean> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<ButtonListBean> buttonList) {
        this.buttonList = buttonList;
    }

    public static class ItemInfoBean {
        /**
         * ID : 920000000211660
         * SXSM : 调查终结审批
         * BZID : 920000000198766
         * SCSJ : 2018-08-10T18:36:42
         * SXZT : 待审批
         * ActionID : f0f9ef6c-eaa7-45dd-a4ad-4911eb87c766
         * DYSX : [{"DocId":"920000000211563","DocTypeId":"5520000003233","DocTypeDetailsId":"5520000003235","DocTitle":"调查终结报告"}]
         * AttachmentCount : 0
         */

        private String ID;
        private String SXSM;
        private String BZID;
        private String SCSJ;
        private String SXZT;
        private String ActionID;
        private int AttachmentCount;
        private List<DYSXBean> DYSX;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getSXSM() {
            return SXSM;
        }

        public void setSXSM(String SXSM) {
            this.SXSM = SXSM;
        }

        public String getBZID() {
            return BZID;
        }

        public void setBZID(String BZID) {
            this.BZID = BZID;
        }

        public String getSCSJ() {
            return SCSJ;
        }

        public void setSCSJ(String SCSJ) {
            this.SCSJ = SCSJ;
        }

        public String getSXZT() {
            return SXZT;
        }

        public void setSXZT(String SXZT) {
            this.SXZT = SXZT;
        }

        public String getActionID() {
            return ActionID;
        }

        public void setActionID(String ActionID) {
            this.ActionID = ActionID;
        }

        public int getAttachmentCount() {
            return AttachmentCount;
        }

        public void setAttachmentCount(int AttachmentCount) {
            this.AttachmentCount = AttachmentCount;
        }

        public List<DYSXBean> getDYSX() {
            return DYSX;
        }

        public void setDYSX(List<DYSXBean> DYSX) {
            this.DYSX = DYSX;
        }

        public static class DYSXBean {
            /**
             * DocId : 920000000211563
             * DocTypeId : 5520000003233
             * DocTypeDetailsId : 5520000003235
             * DocTitle : 调查终结报告
             */

            private String DocId;
            private String DocTypeId;
            private String DocTypeDetailsId;
            private String DocTitle;

            public String getDocId() {
                return DocId;
            }

            public void setDocId(String DocId) {
                this.DocId = DocId;
            }

            public String getDocTypeId() {
                return DocTypeId;
            }

            public void setDocTypeId(String DocTypeId) {
                this.DocTypeId = DocTypeId;
            }

            public String getDocTypeDetailsId() {
                return DocTypeDetailsId;
            }

            public void setDocTypeDetailsId(String DocTypeDetailsId) {
                this.DocTypeDetailsId = DocTypeDetailsId;
            }

            public String getDocTitle() {
                return DocTitle;
            }

            public void setDocTitle(String DocTitle) {
                this.DocTitle = DocTitle;
            }
        }
    }

    public static class ButtonListBean {
        /**
         * Action : {"ParamDefine":"<ErrorType>0<\/ErrorType><Type>Recall<\/Type><InputModel>ZS_BAPT_YB.YB_CHXX<\/InputModel><Right>CH<\/Right>","DisplayOrder":20,"ActionId":"fe8ffb87-b0c7-4105-89d0-1c7d0c253279","ActionName":"YB_DC_DCZJ_DSP_CH","ActionTitle":"撤回","BeginState":"b79ce012-e01e-4f7b-812f-f1e93cae1298","EndState":"cae3c119-94cc-46cb-afb2-56cc96fb7da8","ActionType":"业务流处理","EndStateName":null,"UserType":1,"UnitId":null}
         */

        private ActionBean Action;

        public ActionBean getAction() {
            return Action;
        }

        public void setAction(ActionBean Action) {
            this.Action = Action;
        }

        public static class ActionBean {
            /**
             * ParamDefine : <ErrorType>0</ErrorType><Type>Recall</Type><InputModel>ZS_BAPT_YB.YB_CHXX</InputModel><Right>CH</Right>
             * DisplayOrder : 20
             * ActionId : fe8ffb87-b0c7-4105-89d0-1c7d0c253279
             * ActionName : YB_DC_DCZJ_DSP_CH
             * ActionTitle : 撤回
             * BeginState : b79ce012-e01e-4f7b-812f-f1e93cae1298
             * EndState : cae3c119-94cc-46cb-afb2-56cc96fb7da8
             * ActionType : 业务流处理
             * EndStateName : null
             * UserType : 1
             * UnitId : null
             */

            private String ParamDefine;
            private int DisplayOrder;
            private String ActionId;
            private String ActionName;
            private String ActionTitle;
            private String BeginState;
            private String EndState;
            private String ActionType;
            private Object EndStateName;
            private int UserType;
            private Object UnitId;

            public String getParamDefine() {
                return ParamDefine;
            }

            public void setParamDefine(String ParamDefine) {
                this.ParamDefine = ParamDefine;
            }

            public int getDisplayOrder() {
                return DisplayOrder;
            }

            public void setDisplayOrder(int DisplayOrder) {
                this.DisplayOrder = DisplayOrder;
            }

            public String getActionId() {
                return ActionId;
            }

            public void setActionId(String ActionId) {
                this.ActionId = ActionId;
            }

            public String getActionName() {
                return ActionName;
            }

            public void setActionName(String ActionName) {
                this.ActionName = ActionName;
            }

            public String getActionTitle() {
                return ActionTitle;
            }

            public void setActionTitle(String ActionTitle) {
                this.ActionTitle = ActionTitle;
            }

            public String getBeginState() {
                return BeginState;
            }

            public void setBeginState(String BeginState) {
                this.BeginState = BeginState;
            }

            public String getEndState() {
                return EndState;
            }

            public void setEndState(String EndState) {
                this.EndState = EndState;
            }

            public String getActionType() {
                return ActionType;
            }

            public void setActionType(String ActionType) {
                this.ActionType = ActionType;
            }

            public Object getEndStateName() {
                return EndStateName;
            }

            public void setEndStateName(Object EndStateName) {
                this.EndStateName = EndStateName;
            }

            public int getUserType() {
                return UserType;
            }

            public void setUserType(int UserType) {
                this.UserType = UserType;
            }

            public Object getUnitId() {
                return UnitId;
            }

            public void setUnitId(Object UnitId) {
                this.UnitId = UnitId;
            }
        }
    }
}
