package com.sinostar.assistant.bean;

import java.util.List;

public class LinkBackInfo {
    private List<ButtonListBean> ButtonList;
    private List<ItemInfoBean> ItemInfo;

    public List<ButtonListBean> getButtonList() {
        return ButtonList;
    }

    public void setButtonList(List<ButtonListBean> ButtonList) {
        this.ButtonList = ButtonList;
    }

    public List<ItemInfoBean> getItemInfo() {
        return ItemInfo;
    }

    public void setItemInfo(List<ItemInfoBean> ItemInfo) {
        this.ItemInfo = ItemInfo;
    }

    public static class ButtonListBean {
        /**
         * Action : {"ParamDefine":"<ErrorType>0<\/ErrorType><InputModel>ZS_BAPT_YB.SPTY_FZ<\/InputModel><Right>SP<\/Right>","DisplayOrder":50,"ActionId":"9dd9e408-23e7-4d25-810e-748a4ab65335","ActionName":"COM_HJHT_DSP_TY","ActionTitle":"同意","BeginState":"f89e9b78-dde3-4711-b562-e1162d4910cd","EndState":"f89e9b78-dde3-4711-b562-e1162d4910cd","ActionType":"业务流处理","EndStateName":null,"UserType":1,"UnitId":null}
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
             * ParamDefine : <ErrorType>0</ErrorType><InputModel>ZS_BAPT_YB.SPTY_FZ</InputModel><Right>SP</Right>
             * DisplayOrder : 50
             * ActionId : 9dd9e408-23e7-4d25-810e-748a4ab65335
             * ActionName : COM_HJHT_DSP_TY
             * ActionTitle : 同意
             * BeginState : f89e9b78-dde3-4711-b562-e1162d4910cd
             * EndState : f89e9b78-dde3-4711-b562-e1162d4910cd
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

    public static class ItemInfoBean {
        /**
         * AJBH : 23301342017000042
         * AJMC : uvhuvzfsv
         * BZID : 5520000607216
         * CQID : 5520000612371
         * SXID : null
         * XJYJ : 哟哟哟哟
         * SOURCESTATE : 案件审理
         */

        private String AJBH;
        private String AJMC;
        private long BZID;
        private long CQID;
        private Object SXID;
        private String XJYJ;
        private String SOURCESTATE;

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

        public long getBZID() {
            return BZID;
        }

        public void setBZID(long BZID) {
            this.BZID = BZID;
        }

        public long getCQID() {
            return CQID;
        }

        public void setCQID(long CQID) {
            this.CQID = CQID;
        }

        public Object getSXID() {
            return SXID;
        }

        public void setSXID(Object SXID) {
            this.SXID = SXID;
        }

        public String getXJYJ() {
            return XJYJ;
        }

        public void setXJYJ(String XJYJ) {
            this.XJYJ = XJYJ;
        }

        public String getSOURCESTATE() {
            return SOURCESTATE;
        }

        public void setSOURCESTATE(String SOURCESTATE) {
            this.SOURCESTATE = SOURCESTATE;
        }
    }
}
