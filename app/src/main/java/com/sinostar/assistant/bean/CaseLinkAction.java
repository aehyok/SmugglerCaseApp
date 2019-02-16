package com.sinostar.assistant.bean;

import java.util.List;

public class CaseLinkAction {

    /**
     * Id : 10027
     * ActionName : 受案审查
     * FatherActionId : 0
     * LinkId : 10004
     * DisplayOrder : 102
     * IsNecessary : true
     * ChildAction : [{"Id":10029,"ActionName":"审查","FatherActionId":10027,"LinkId":10004,"DisplayOrder":1000,"IsNecessary":true,"ChildAction":[]},{"Id":10030,"ActionName":"延长审查","FatherActionId":10027,"LinkId":10004,"DisplayOrder":1001,"IsNecessary":false,"ChildAction":[]}]
     */

    private int Id;
    private String ActionName;
    private int FatherActionId;
    private int LinkId;
    private int DisplayOrder;
    private boolean IsNecessary;
    private List<ChildActionBean> ChildAction;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String ActionName) {
        this.ActionName = ActionName;
    }

    public int getFatherActionId() {
        return FatherActionId;
    }

    public void setFatherActionId(int FatherActionId) {
        this.FatherActionId = FatherActionId;
    }

    public int getLinkId() {
        return LinkId;
    }

    public void setLinkId(int LinkId) {
        this.LinkId = LinkId;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int DisplayOrder) {
        this.DisplayOrder = DisplayOrder;
    }

    public boolean isIsNecessary() {
        return IsNecessary;
    }

    public void setIsNecessary(boolean IsNecessary) {
        this.IsNecessary = IsNecessary;
    }

    public List<ChildActionBean> getChildAction() {
        return ChildAction;
    }

    public void setChildAction(List<ChildActionBean> ChildAction) {
        this.ChildAction = ChildAction;
    }

    public static class ChildActionBean {
        /**
         * Id : 10029
         * ActionName : 审查
         * FatherActionId : 10027
         * LinkId : 10004
         * DisplayOrder : 1000
         * IsNecessary : true
         * ChildAction : []
         */

        private int Id;
        private String ActionName;
        private int FatherActionId;
        private int LinkId;
        private int DisplayOrder;
        private boolean IsNecessary;
        private List<?> ChildAction;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getActionName() {
            return ActionName;
        }

        public void setActionName(String ActionName) {
            this.ActionName = ActionName;
        }

        public int getFatherActionId() {
            return FatherActionId;
        }

        public void setFatherActionId(int FatherActionId) {
            this.FatherActionId = FatherActionId;
        }

        public int getLinkId() {
            return LinkId;
        }

        public void setLinkId(int LinkId) {
            this.LinkId = LinkId;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public boolean isIsNecessary() {
            return IsNecessary;
        }

        public void setIsNecessary(boolean IsNecessary) {
            this.IsNecessary = IsNecessary;
        }

        public List<?> getChildAction() {
            return ChildAction;
        }

        public void setChildAction(List<?> ChildAction) {
            this.ChildAction = ChildAction;
        }
    }
}
