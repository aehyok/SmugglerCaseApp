package com.sinostar.assistant.bean;

import java.util.List;

/**
 * 审批通过模型实体类
 */
public class ApproveAgreeModel {
    /**
     * MD_Define : {"Id":"9014771010752","NameSpace":"ZS_BAPT_YB","ModelName":"SPTY","ModelType":"FORM","Descript":"","DisplayName":"审批同意","DisplayOrder":1012,"DeleteRule":"","DWDM":"","InitGuideLine":"","GetDataGuideLine":"7018951052325","GetNewRecordGuideLine":"9014771010786","Param":"<TABLE><\/TABLE><ORDER><\/ORDER><TYPE>FORM<\/TYPE><PARAMTYPE>OTHER<\/PARAMTYPE><INITZB><\/INITZB><GETZB>7018951052325<\/GETZB><NEWZB>9014771010786<\/NEWZB>","ParamterType":"OTHER","IsMixModel":false,"ResourceType":"","IntegretedApplication":"","BeforeWrite":"","AfterWrite":"","Columns":[],"Groups":[{"$id":"1","ModelId":"9014771010752","GroupId":"9014771010753","DisplayTitle":"审批信息","DisplayOrder":1,"Columns":[{"ColumnId":"7018951022873","ColumnName":"BZID","DisplayName":"BZID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010848","ColumnName":"SXID","DisplayName":"SXID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010768","ColumnName":"ACTIONID_MB_COM","DisplayName":"ACTIONID_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010766","ColumnName":"BZID_COM","DisplayName":"BZID_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010765","ColumnName":"SQSPRID","DisplayName":"SQSPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010764","ColumnName":"ID_INFO","DisplayName":"ID_INFO","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010757","ColumnName":"CQID","DisplayName":"CQID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951023026","ColumnName":"DWID_RY_MB_COM","DisplayName":"DWID_RY_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010759","ColumnName":"SPSX","DisplayName":"SPSX","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010760","ColumnName":"SPDWID","DisplayName":"SPDWID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010761","ColumnName":"SPRID","DisplayName":"SPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951022452","ColumnName":"XJYJ","DisplayName":"呈请意见","DisplayOrder":1,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009226","ColumnName":"SPDW","DisplayName":"审批单位","DisplayOrder":5,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010758","ColumnName":"SPR","DisplayName":"审批人员","DisplayOrder":10,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010762","ColumnName":"SPSJ","DisplayName":"审批日期","DisplayOrder":20,"MaxInputLength":0,"ColumnType":"DATE","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"TIME","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010763","ColumnName":"SPYJ","DisplayName":"审批意见","DisplayOrder":30,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010769","ColumnName":"MB_COM","DisplayName":"目标单位","DisplayOrder":40,"MaxInputLength":0,"ColumnType":"目标单位","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009267","ColumnName":"RY_MB_COM","DisplayName":"办案人员","DisplayOrder":50,"MaxInputLength":0,"ColumnType":"指派人员","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"Current","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true}],"GroupType":"DEFAULT","AppRegUrl":"","GroupParam":"<ShowHeaderString>Hidden<\/ShowHeaderString>"}],"ChildInputModel":[],"WriteTableNames":[{"$id":"2","Id":"9014771010770","TableName":"JSYW_XZXS_AJSPXXB","TableTitle":"审批信息","IsLock":true,"DisplayOrder":0,"InputModelId":"9014771010752","Columns":[{"Id":"9014771010775","SrcColumn":"SPRID","DesColumn":"SPRID","Method":"","Descript":""},{"Id":"9014771010776","SrcColumn":"SPSJ","DesColumn":"SPSJ","Method":"","Descript":""},{"Id":"9014771010777","SrcColumn":"SPYJ","DesColumn":"SPYJ","Method":"","Descript":""},{"Id":"9014771010778","SrcColumn":"SPDWID","DesColumn":"SPDWID","Method":"","Descript":""},{"Id":"9014771010779","SrcColumn":"CQID","DesColumn":"CQID","Method":"","Descript":""},{"Id":"9014771010780","SrcColumn":"BZID","DesColumn":"BZID","Method":"","Descript":""},{"Id":"9014771010781","SrcColumn":"ID_INFO","DesColumn":"ID","Method":"","Descript":""},{"Id":"9014771010782","SrcColumn":"","DesColumn":"ODS_ID","Method":"","Descript":""},{"Id":"9014771010783","SrcColumn":"","DesColumn":"ODS_WHXH","Method":"","Descript":""},{"Id":"9014771010774","SrcColumn":"SPSX","DesColumn":"SPSX","Method":"","Descript":""},{"Id":"9014771010784","SrcColumn":"","DesColumn":"WHXH","Method":"","Descript":""},{"Id":"9014771010771","SrcColumn":"","DesColumn":"ODS_ETLTIME","Method":"","Descript":""},{"Id":"9014771010772","SrcColumn":"SQSPRID","DesColumn":"SQSPRID","Method":"","Descript":""},{"Id":"9014771010773","SrcColumn":"","DesColumn":"SQSPDWID","Method":"","Descript":""}],"SaveMode":"DEFAULT"}],"OrderField":"","TableName":""}
     * MD_Data : {"InputModelName":"ZS_BAPT_YB.SPTY","InputData":{"ID_INFO":"920000000198633","SPSX":"1","SPYJ":"同意。","SPSJ":"2018/7/19 14:30:50","SPR":"赵福地<旧>","SPRID":"13345","SPDWID":"52140","SPDW":"南京海关缉私局苏州海关缉私分局昆山缉私中队","SQSPRID":"","BZID_COM":"920000000198500","BZID":"920000000198500","CQID":"920000000198514","SXID":"920000000198514","XJYJ":"呈请领导审批。","ACTIONID_MB_COM":"02efa6e3-f374-4189-aab2-7dd773cdf151","DWID_MB_COM":"52140","DWID_RY_MB_COM":"52140","RY_MB_COM":"13345"},"IsNewData":true,"IsNewFlow":false,"ChildInputData":null}
     * MB_COM : [{"Id":"","FLSMAPID":null,"ActionId":null,"ActionName":null,"TargetUnitId":"52140","TargetUnitName":"南京海关缉私局苏州海关缉私分局昆山缉私中队","RuleId":null,"DataShowMeta":null}]
     * RY_MB_COM : [{"Key":"13345","Value":"赵福地<旧>"},{"Key":"63335","Value":"胡凯"}]
     */

    private MDDefineBean MD_Define;
    private MDDataBean MD_Data;
    private List<MBCOMBean> MB_COM;
    private List<RYMBCOMBean> RY_MB_COM;

    public MDDefineBean getMD_Define() {
        return MD_Define;
    }

    public void setMD_Define(MDDefineBean MD_Define) {
        this.MD_Define = MD_Define;
    }

    public MDDataBean getMD_Data() {
        return MD_Data;
    }

    public void setMD_Data(MDDataBean MD_Data) {
        this.MD_Data = MD_Data;
    }

    public List<MBCOMBean> getMB_COM() {
        return MB_COM;
    }

    public void setMB_COM(List<MBCOMBean> MB_COM) {
        this.MB_COM = MB_COM;
    }

    public List<RYMBCOMBean> getRY_MB_COM() {
        return RY_MB_COM;
    }

    public void setRY_MB_COM(List<RYMBCOMBean> RY_MB_COM) {
        this.RY_MB_COM = RY_MB_COM;
    }

    public static class MDDefineBean {
        /**
         * Id : 9014771010752
         * NameSpace : ZS_BAPT_YB
         * ModelName : SPTY
         * ModelType : FORM
         * Descript :
         * DisplayName : 审批同意
         * DisplayOrder : 1012
         * DeleteRule :
         * DWDM :
         * InitGuideLine :
         * GetDataGuideLine : 7018951052325
         * GetNewRecordGuideLine : 9014771010786
         * Param : <TABLE></TABLE><ORDER></ORDER><TYPE>FORM</TYPE><PARAMTYPE>OTHER</PARAMTYPE><INITZB></INITZB><GETZB>7018951052325</GETZB><NEWZB>9014771010786</NEWZB>
         * ParamterType : OTHER
         * IsMixModel : false
         * ResourceType :
         * IntegretedApplication :
         * BeforeWrite :
         * AfterWrite :
         * Columns : []
         * Groups : [{"$id":"1","ModelId":"9014771010752","GroupId":"9014771010753","DisplayTitle":"审批信息","DisplayOrder":1,"Columns":[{"ColumnId":"7018951022873","ColumnName":"BZID","DisplayName":"BZID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010848","ColumnName":"SXID","DisplayName":"SXID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010768","ColumnName":"ACTIONID_MB_COM","DisplayName":"ACTIONID_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010766","ColumnName":"BZID_COM","DisplayName":"BZID_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010765","ColumnName":"SQSPRID","DisplayName":"SQSPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010764","ColumnName":"ID_INFO","DisplayName":"ID_INFO","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010757","ColumnName":"CQID","DisplayName":"CQID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951023026","ColumnName":"DWID_RY_MB_COM","DisplayName":"DWID_RY_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010759","ColumnName":"SPSX","DisplayName":"SPSX","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010760","ColumnName":"SPDWID","DisplayName":"SPDWID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010761","ColumnName":"SPRID","DisplayName":"SPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951022452","ColumnName":"XJYJ","DisplayName":"呈请意见","DisplayOrder":1,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009226","ColumnName":"SPDW","DisplayName":"审批单位","DisplayOrder":5,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010758","ColumnName":"SPR","DisplayName":"审批人员","DisplayOrder":10,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010762","ColumnName":"SPSJ","DisplayName":"审批日期","DisplayOrder":20,"MaxInputLength":0,"ColumnType":"DATE","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"TIME","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010763","ColumnName":"SPYJ","DisplayName":"审批意见","DisplayOrder":30,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010769","ColumnName":"MB_COM","DisplayName":"目标单位","DisplayOrder":40,"MaxInputLength":0,"ColumnType":"目标单位","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009267","ColumnName":"RY_MB_COM","DisplayName":"办案人员","DisplayOrder":50,"MaxInputLength":0,"ColumnType":"指派人员","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"Current","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true}],"GroupType":"DEFAULT","AppRegUrl":"","GroupParam":"<ShowHeaderString>Hidden<\/ShowHeaderString>"}]
         * ChildInputModel : []
         * WriteTableNames : [{"$id":"2","Id":"9014771010770","TableName":"JSYW_XZXS_AJSPXXB","TableTitle":"审批信息","IsLock":true,"DisplayOrder":0,"InputModelId":"9014771010752","Columns":[{"Id":"9014771010775","SrcColumn":"SPRID","DesColumn":"SPRID","Method":"","Descript":""},{"Id":"9014771010776","SrcColumn":"SPSJ","DesColumn":"SPSJ","Method":"","Descript":""},{"Id":"9014771010777","SrcColumn":"SPYJ","DesColumn":"SPYJ","Method":"","Descript":""},{"Id":"9014771010778","SrcColumn":"SPDWID","DesColumn":"SPDWID","Method":"","Descript":""},{"Id":"9014771010779","SrcColumn":"CQID","DesColumn":"CQID","Method":"","Descript":""},{"Id":"9014771010780","SrcColumn":"BZID","DesColumn":"BZID","Method":"","Descript":""},{"Id":"9014771010781","SrcColumn":"ID_INFO","DesColumn":"ID","Method":"","Descript":""},{"Id":"9014771010782","SrcColumn":"","DesColumn":"ODS_ID","Method":"","Descript":""},{"Id":"9014771010783","SrcColumn":"","DesColumn":"ODS_WHXH","Method":"","Descript":""},{"Id":"9014771010774","SrcColumn":"SPSX","DesColumn":"SPSX","Method":"","Descript":""},{"Id":"9014771010784","SrcColumn":"","DesColumn":"WHXH","Method":"","Descript":""},{"Id":"9014771010771","SrcColumn":"","DesColumn":"ODS_ETLTIME","Method":"","Descript":""},{"Id":"9014771010772","SrcColumn":"SQSPRID","DesColumn":"SQSPRID","Method":"","Descript":""},{"Id":"9014771010773","SrcColumn":"","DesColumn":"SQSPDWID","Method":"","Descript":""}],"SaveMode":"DEFAULT"}]
         * OrderField :
         * TableName :
         */

        private String Id;
        private String NameSpace;
        private String ModelName;
        private String ModelType;
        private String Descript;
        private String DisplayName;
        private int DisplayOrder;
        private String DeleteRule;
        private String DWDM;
        private String InitGuideLine;
        private String GetDataGuideLine;
        private String GetNewRecordGuideLine;
        private String Param;
        private String ParamterType;
        private boolean IsMixModel;
        private String ResourceType;
        private String IntegretedApplication;
        private String BeforeWrite;
        private String AfterWrite;
        private String OrderField;
        private String TableName;
        private List<?> Columns;
        private List<GroupsBean> Groups;
        private List<?> ChildInputModel;
        private List<WriteTableNamesBean> WriteTableNames;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getNameSpace() {
            return NameSpace;
        }

        public void setNameSpace(String NameSpace) {
            this.NameSpace = NameSpace;
        }

        public String getModelName() {
            return ModelName;
        }

        public void setModelName(String ModelName) {
            this.ModelName = ModelName;
        }

        public String getModelType() {
            return ModelType;
        }

        public void setModelType(String ModelType) {
            this.ModelType = ModelType;
        }

        public String getDescript() {
            return Descript;
        }

        public void setDescript(String Descript) {
            this.Descript = Descript;
        }

        public String getDisplayName() {
            return DisplayName;
        }

        public void setDisplayName(String DisplayName) {
            this.DisplayName = DisplayName;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public String getDeleteRule() {
            return DeleteRule;
        }

        public void setDeleteRule(String DeleteRule) {
            this.DeleteRule = DeleteRule;
        }

        public String getDWDM() {
            return DWDM;
        }

        public void setDWDM(String DWDM) {
            this.DWDM = DWDM;
        }

        public String getInitGuideLine() {
            return InitGuideLine;
        }

        public void setInitGuideLine(String InitGuideLine) {
            this.InitGuideLine = InitGuideLine;
        }

        public String getGetDataGuideLine() {
            return GetDataGuideLine;
        }

        public void setGetDataGuideLine(String GetDataGuideLine) {
            this.GetDataGuideLine = GetDataGuideLine;
        }

        public String getGetNewRecordGuideLine() {
            return GetNewRecordGuideLine;
        }

        public void setGetNewRecordGuideLine(String GetNewRecordGuideLine) {
            this.GetNewRecordGuideLine = GetNewRecordGuideLine;
        }

        public String getParam() {
            return Param;
        }

        public void setParam(String Param) {
            this.Param = Param;
        }

        public String getParamterType() {
            return ParamterType;
        }

        public void setParamterType(String ParamterType) {
            this.ParamterType = ParamterType;
        }

        public boolean isIsMixModel() {
            return IsMixModel;
        }

        public void setIsMixModel(boolean IsMixModel) {
            this.IsMixModel = IsMixModel;
        }

        public String getResourceType() {
            return ResourceType;
        }

        public void setResourceType(String ResourceType) {
            this.ResourceType = ResourceType;
        }

        public String getIntegretedApplication() {
            return IntegretedApplication;
        }

        public void setIntegretedApplication(String IntegretedApplication) {
            this.IntegretedApplication = IntegretedApplication;
        }

        public String getBeforeWrite() {
            return BeforeWrite;
        }

        public void setBeforeWrite(String BeforeWrite) {
            this.BeforeWrite = BeforeWrite;
        }

        public String getAfterWrite() {
            return AfterWrite;
        }

        public void setAfterWrite(String AfterWrite) {
            this.AfterWrite = AfterWrite;
        }

        public String getOrderField() {
            return OrderField;
        }

        public void setOrderField(String OrderField) {
            this.OrderField = OrderField;
        }

        public String getTableName() {
            return TableName;
        }

        public void setTableName(String TableName) {
            this.TableName = TableName;
        }

        public List<?> getColumns() {
            return Columns;
        }

        public void setColumns(List<?> Columns) {
            this.Columns = Columns;
        }

        public List<GroupsBean> getGroups() {
            return Groups;
        }

        public void setGroups(List<GroupsBean> Groups) {
            this.Groups = Groups;
        }

        public List<?> getChildInputModel() {
            return ChildInputModel;
        }

        public void setChildInputModel(List<?> ChildInputModel) {
            this.ChildInputModel = ChildInputModel;
        }

        public List<WriteTableNamesBean> getWriteTableNames() {
            return WriteTableNames;
        }

        public void setWriteTableNames(List<WriteTableNamesBean> WriteTableNames) {
            this.WriteTableNames = WriteTableNames;
        }

        public static class GroupsBean {
            /**
             * $id : 1
             * ModelId : 9014771010752
             * GroupId : 9014771010753
             * DisplayTitle : 审批信息
             * DisplayOrder : 1
             * Columns : [{"ColumnId":"7018951022873","ColumnName":"BZID","DisplayName":"BZID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010848","ColumnName":"SXID","DisplayName":"SXID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010768","ColumnName":"ACTIONID_MB_COM","DisplayName":"ACTIONID_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010766","ColumnName":"BZID_COM","DisplayName":"BZID_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010765","ColumnName":"SQSPRID","DisplayName":"SQSPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010764","ColumnName":"ID_INFO","DisplayName":"ID_INFO","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010757","ColumnName":"CQID","DisplayName":"CQID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951023026","ColumnName":"DWID_RY_MB_COM","DisplayName":"DWID_RY_MB_COM","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010759","ColumnName":"SPSX","DisplayName":"SPSX","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010760","ColumnName":"SPDWID","DisplayName":"SPDWID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010761","ColumnName":"SPRID","DisplayName":"SPRID","DisplayOrder":0,"MaxInputLength":0,"ColumnType":"NUMBER","InputModelId":"9014771010752","CanSave":true,"CanDisplay":false,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951022452","ColumnName":"XJYJ","DisplayName":"呈请意见","DisplayOrder":1,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009226","ColumnName":"SPDW","DisplayName":"审批单位","DisplayOrder":5,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010758","ColumnName":"SPR","DisplayName":"审批人员","DisplayOrder":10,"MaxInputLength":0,"ColumnType":"VARCHAR","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010762","ColumnName":"SPSJ","DisplayName":"审批日期","DisplayOrder":20,"MaxInputLength":0,"ColumnType":"DATE","InputModelId":"9014771010752","CanSave":false,"CanDisplay":true,"IsCompute":false,"ReadOnly":true,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":1,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"TIME","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010763","ColumnName":"SPYJ","DisplayName":"审批意见","DisplayOrder":30,"MaxInputLength":0,"ColumnType":"多行文本","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":2,"TextAlign":0,"EditFormat":"","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"9014771010769","ColumnName":"MB_COM","DisplayName":"目标单位","DisplayOrder":40,"MaxInputLength":0,"ColumnType":"目标单位","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"","Required":true,"ToolTipText":"","DisplayFormat":"","DefaultShow":true},{"ColumnId":"7018951009267","ColumnName":"RY_MB_COM","DisplayName":"办案人员","DisplayOrder":50,"MaxInputLength":0,"ColumnType":"指派人员","InputModelId":"9014771010752","CanSave":true,"CanDisplay":true,"IsCompute":false,"ReadOnly":false,"DWDM":"","DefaultValue":"","InputRule":"","DataChangedEvent":"","CanEditRule":"","Width":2,"LineHeight":1,"TextAlign":0,"EditFormat":"Current","Required":false,"ToolTipText":"","DisplayFormat":"","DefaultShow":true}]
             * GroupType : DEFAULT
             * AppRegUrl :
             * GroupParam : <ShowHeaderString>Hidden</ShowHeaderString>
             */

            private String $id;
            private String ModelId;
            private String GroupId;
            private String DisplayTitle;
            private int DisplayOrder;
            private String GroupType;
            private String AppRegUrl;
            private String GroupParam;
            private List<ColumnsBean> Columns;

            public String get$id() {
                return $id;
            }

            public void set$id(String $id) {
                this.$id = $id;
            }

            public String getModelId() {
                return ModelId;
            }

            public void setModelId(String ModelId) {
                this.ModelId = ModelId;
            }

            public String getGroupId() {
                return GroupId;
            }

            public void setGroupId(String GroupId) {
                this.GroupId = GroupId;
            }

            public String getDisplayTitle() {
                return DisplayTitle;
            }

            public void setDisplayTitle(String DisplayTitle) {
                this.DisplayTitle = DisplayTitle;
            }

            public int getDisplayOrder() {
                return DisplayOrder;
            }

            public void setDisplayOrder(int DisplayOrder) {
                this.DisplayOrder = DisplayOrder;
            }

            public String getGroupType() {
                return GroupType;
            }

            public void setGroupType(String GroupType) {
                this.GroupType = GroupType;
            }

            public String getAppRegUrl() {
                return AppRegUrl;
            }

            public void setAppRegUrl(String AppRegUrl) {
                this.AppRegUrl = AppRegUrl;
            }

            public String getGroupParam() {
                return GroupParam;
            }

            public void setGroupParam(String GroupParam) {
                this.GroupParam = GroupParam;
            }

            public List<ColumnsBean> getColumns() {
                return Columns;
            }

            public void setColumns(List<ColumnsBean> Columns) {
                this.Columns = Columns;
            }

            public static class ColumnsBean {
                /**
                 * ColumnId : 7018951022873
                 * ColumnName : BZID
                 * DisplayName : BZID
                 * DisplayOrder : 0
                 * MaxInputLength : 0
                 * ColumnType : VARCHAR
                 * InputModelId : 9014771010752
                 * CanSave : true
                 * CanDisplay : false
                 * IsCompute : false
                 * ReadOnly : false
                 * DWDM :
                 * DefaultValue :
                 * InputRule :
                 * DataChangedEvent :
                 * CanEditRule :
                 * Width : 1
                 * LineHeight : 1
                 * TextAlign : 0
                 * EditFormat :
                 * Required : false
                 * ToolTipText :
                 * DisplayFormat :
                 * DefaultShow : true
                 */

                private String ColumnId;
                private String ColumnName;
                private String DisplayName;
                private int DisplayOrder;
                private int MaxInputLength;
                private String ColumnType;
                private String InputModelId;
                private boolean CanSave;
                private boolean CanDisplay;
                private boolean IsCompute;
                private boolean ReadOnly;
                private String DWDM;
                private String DefaultValue;
                private String InputRule;
                private String DataChangedEvent;
                private String CanEditRule;
                private int Width;
                private int LineHeight;
                private int TextAlign;
                private String EditFormat;
                private boolean Required;
                private String ToolTipText;
                private String DisplayFormat;
                private boolean DefaultShow;

                public String getColumnId() {
                    return ColumnId;
                }

                public void setColumnId(String ColumnId) {
                    this.ColumnId = ColumnId;
                }

                public String getColumnName() {
                    return ColumnName;
                }

                public void setColumnName(String ColumnName) {
                    this.ColumnName = ColumnName;
                }

                public String getDisplayName() {
                    return DisplayName;
                }

                public void setDisplayName(String DisplayName) {
                    this.DisplayName = DisplayName;
                }

                public int getDisplayOrder() {
                    return DisplayOrder;
                }

                public void setDisplayOrder(int DisplayOrder) {
                    this.DisplayOrder = DisplayOrder;
                }

                public int getMaxInputLength() {
                    return MaxInputLength;
                }

                public void setMaxInputLength(int MaxInputLength) {
                    this.MaxInputLength = MaxInputLength;
                }

                public String getColumnType() {
                    return ColumnType;
                }

                public void setColumnType(String ColumnType) {
                    this.ColumnType = ColumnType;
                }

                public String getInputModelId() {
                    return InputModelId;
                }

                public void setInputModelId(String InputModelId) {
                    this.InputModelId = InputModelId;
                }

                public boolean isCanSave() {
                    return CanSave;
                }

                public void setCanSave(boolean CanSave) {
                    this.CanSave = CanSave;
                }

                public boolean isCanDisplay() {
                    return CanDisplay;
                }

                public void setCanDisplay(boolean CanDisplay) {
                    this.CanDisplay = CanDisplay;
                }

                public boolean isIsCompute() {
                    return IsCompute;
                }

                public void setIsCompute(boolean IsCompute) {
                    this.IsCompute = IsCompute;
                }

                public boolean isReadOnly() {
                    return ReadOnly;
                }

                public void setReadOnly(boolean ReadOnly) {
                    this.ReadOnly = ReadOnly;
                }

                public String getDWDM() {
                    return DWDM;
                }

                public void setDWDM(String DWDM) {
                    this.DWDM = DWDM;
                }

                public String getDefaultValue() {
                    return DefaultValue;
                }

                public void setDefaultValue(String DefaultValue) {
                    this.DefaultValue = DefaultValue;
                }

                public String getInputRule() {
                    return InputRule;
                }

                public void setInputRule(String InputRule) {
                    this.InputRule = InputRule;
                }

                public String getDataChangedEvent() {
                    return DataChangedEvent;
                }

                public void setDataChangedEvent(String DataChangedEvent) {
                    this.DataChangedEvent = DataChangedEvent;
                }

                public String getCanEditRule() {
                    return CanEditRule;
                }

                public void setCanEditRule(String CanEditRule) {
                    this.CanEditRule = CanEditRule;
                }

                public int getWidth() {
                    return Width;
                }

                public void setWidth(int Width) {
                    this.Width = Width;
                }

                public int getLineHeight() {
                    return LineHeight;
                }

                public void setLineHeight(int LineHeight) {
                    this.LineHeight = LineHeight;
                }

                public int getTextAlign() {
                    return TextAlign;
                }

                public void setTextAlign(int TextAlign) {
                    this.TextAlign = TextAlign;
                }

                public String getEditFormat() {
                    return EditFormat;
                }

                public void setEditFormat(String EditFormat) {
                    this.EditFormat = EditFormat;
                }

                public boolean isRequired() {
                    return Required;
                }

                public void setRequired(boolean Required) {
                    this.Required = Required;
                }

                public String getToolTipText() {
                    return ToolTipText;
                }

                public void setToolTipText(String ToolTipText) {
                    this.ToolTipText = ToolTipText;
                }

                public String getDisplayFormat() {
                    return DisplayFormat;
                }

                public void setDisplayFormat(String DisplayFormat) {
                    this.DisplayFormat = DisplayFormat;
                }

                public boolean isDefaultShow() {
                    return DefaultShow;
                }

                public void setDefaultShow(boolean DefaultShow) {
                    this.DefaultShow = DefaultShow;
                }
            }
        }

        public static class WriteTableNamesBean {
            /**
             * $id : 2
             * Id : 9014771010770
             * TableName : JSYW_XZXS_AJSPXXB
             * TableTitle : 审批信息
             * IsLock : true
             * DisplayOrder : 0
             * InputModelId : 9014771010752
             * Columns : [{"Id":"9014771010775","SrcColumn":"SPRID","DesColumn":"SPRID","Method":"","Descript":""},{"Id":"9014771010776","SrcColumn":"SPSJ","DesColumn":"SPSJ","Method":"","Descript":""},{"Id":"9014771010777","SrcColumn":"SPYJ","DesColumn":"SPYJ","Method":"","Descript":""},{"Id":"9014771010778","SrcColumn":"SPDWID","DesColumn":"SPDWID","Method":"","Descript":""},{"Id":"9014771010779","SrcColumn":"CQID","DesColumn":"CQID","Method":"","Descript":""},{"Id":"9014771010780","SrcColumn":"BZID","DesColumn":"BZID","Method":"","Descript":""},{"Id":"9014771010781","SrcColumn":"ID_INFO","DesColumn":"ID","Method":"","Descript":""},{"Id":"9014771010782","SrcColumn":"","DesColumn":"ODS_ID","Method":"","Descript":""},{"Id":"9014771010783","SrcColumn":"","DesColumn":"ODS_WHXH","Method":"","Descript":""},{"Id":"9014771010774","SrcColumn":"SPSX","DesColumn":"SPSX","Method":"","Descript":""},{"Id":"9014771010784","SrcColumn":"","DesColumn":"WHXH","Method":"","Descript":""},{"Id":"9014771010771","SrcColumn":"","DesColumn":"ODS_ETLTIME","Method":"","Descript":""},{"Id":"9014771010772","SrcColumn":"SQSPRID","DesColumn":"SQSPRID","Method":"","Descript":""},{"Id":"9014771010773","SrcColumn":"","DesColumn":"SQSPDWID","Method":"","Descript":""}]
             * SaveMode : DEFAULT
             */

            private String $id;
            private String Id;
            private String TableName;
            private String TableTitle;
            private boolean IsLock;
            private int DisplayOrder;
            private String InputModelId;
            private String SaveMode;
            private List<ColumnsBeanX> Columns;

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

            public String getTableName() {
                return TableName;
            }

            public void setTableName(String TableName) {
                this.TableName = TableName;
            }

            public String getTableTitle() {
                return TableTitle;
            }

            public void setTableTitle(String TableTitle) {
                this.TableTitle = TableTitle;
            }

            public boolean isIsLock() {
                return IsLock;
            }

            public void setIsLock(boolean IsLock) {
                this.IsLock = IsLock;
            }

            public int getDisplayOrder() {
                return DisplayOrder;
            }

            public void setDisplayOrder(int DisplayOrder) {
                this.DisplayOrder = DisplayOrder;
            }

            public String getInputModelId() {
                return InputModelId;
            }

            public void setInputModelId(String InputModelId) {
                this.InputModelId = InputModelId;
            }

            public String getSaveMode() {
                return SaveMode;
            }

            public void setSaveMode(String SaveMode) {
                this.SaveMode = SaveMode;
            }

            public List<ColumnsBeanX> getColumns() {
                return Columns;
            }

            public void setColumns(List<ColumnsBeanX> Columns) {
                this.Columns = Columns;
            }

            public static class ColumnsBeanX {
                /**
                 * Id : 9014771010775
                 * SrcColumn : SPRID
                 * DesColumn : SPRID
                 * Method :
                 * Descript :
                 */

                private String Id;
                private String SrcColumn;
                private String DesColumn;
                private String Method;
                private String Descript;

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getSrcColumn() {
                    return SrcColumn;
                }

                public void setSrcColumn(String SrcColumn) {
                    this.SrcColumn = SrcColumn;
                }

                public String getDesColumn() {
                    return DesColumn;
                }

                public void setDesColumn(String DesColumn) {
                    this.DesColumn = DesColumn;
                }

                public String getMethod() {
                    return Method;
                }

                public void setMethod(String Method) {
                    this.Method = Method;
                }

                public String getDescript() {
                    return Descript;
                }

                public void setDescript(String Descript) {
                    this.Descript = Descript;
                }
            }
        }
    }

    public static class MDDataBean {
        /**
         * InputModelName : ZS_BAPT_YB.SPTY
         * InputData : {"ID_INFO":"920000000198633","SPSX":"1","SPYJ":"同意。","SPSJ":"2018/7/19 14:30:50","SPR":"赵福地<旧>","SPRID":"13345","SPDWID":"52140","SPDW":"南京海关缉私局苏州海关缉私分局昆山缉私中队","SQSPRID":"","BZID_COM":"920000000198500","BZID":"920000000198500","CQID":"920000000198514","SXID":"920000000198514","XJYJ":"呈请领导审批。","ACTIONID_MB_COM":"02efa6e3-f374-4189-aab2-7dd773cdf151","DWID_MB_COM":"52140","DWID_RY_MB_COM":"52140","RY_MB_COM":"13345"}
         * IsNewData : true
         * IsNewFlow : false
         * ChildInputData : null
         */

        private String InputModelName;
        private InputDataBean InputData;
        private boolean IsNewData;
        private boolean IsNewFlow;
        private Object ChildInputData;

        public String getInputModelName() {
            return InputModelName;
        }

        public void setInputModelName(String InputModelName) {
            this.InputModelName = InputModelName;
        }

        public InputDataBean getInputData() {
            return InputData;
        }

        public void setInputData(InputDataBean InputData) {
            this.InputData = InputData;
        }

        public boolean isIsNewData() {
            return IsNewData;
        }

        public void setIsNewData(boolean IsNewData) {
            this.IsNewData = IsNewData;
        }

        public boolean isIsNewFlow() {
            return IsNewFlow;
        }

        public void setIsNewFlow(boolean IsNewFlow) {
            this.IsNewFlow = IsNewFlow;
        }

        public Object getChildInputData() {
            return ChildInputData;
        }

        public void setChildInputData(Object ChildInputData) {
            this.ChildInputData = ChildInputData;
        }

        public static class InputDataBean {
//            /**
//             * ID_INFO : 920000000198633
//             * SPSX : 1
//             * SPYJ : 同意。
//             * SPSJ : 2018/7/19 14:30:50
//             * SPR : 赵福地<旧>
//             * SPRID : 13345
//             * SPDWID : 52140
//             * SPDW : 南京海关缉私局苏州海关缉私分局昆山缉私中队
//             * SQSPRID :
//             * BZID_COM : 920000000198500
//             * BZID : 920000000198500
//             * CQID : 920000000198514
//             * SXID : 920000000198514
//             * XJYJ : 呈请领导审批。
//             * ACTIONID_MB_COM : 02efa6e3-f374-4189-aab2-7dd773cdf151
//             * DWID_MB_COM : 52140
//             * DWID_RY_MB_COM : 52140
//             * RY_MB_COM : 13345
//             */
//
//            private String ID_INFO;
//            private String SPSX;
//            private String SPYJ;
//            private String SPSJ;
//            private String SPR;
//            private String SPRID;
//            private String SPDWID;
//            private String SPDW;
//            private String SQSPRID;
//            private String BZID_COM;
//            private String BZID;
//            private String CQID;
//            private String SXID;
//            private String XJYJ;
//            private String ACTIONID_MB_COM;
//            private String DWID_MB_COM;
//            private String DWID_RY_MB_COM;
//            private String RY_MB_COM;
//
//            public String getMB_COM() {
//                return MB_COM;
//            }
//
//            public void setMB_COM(String MB_COM) {
//                this.MB_COM = MB_COM;
//            }
//
//            private String MB_COM;
//
//
//
//            public String getID_INFO() {
//                return ID_INFO;
//            }
//
//            public void setID_INFO(String ID_INFO) {
//                this.ID_INFO = ID_INFO;
//            }
//
//            public String getSPSX() {
//                return SPSX;
//            }
//
//            public void setSPSX(String SPSX) {
//                this.SPSX = SPSX;
//            }
//
//            public String getSPYJ() {
//                return SPYJ;
//            }
//
//            public void setSPYJ(String SPYJ) {
//                this.SPYJ = SPYJ;
//            }
//
//            public String getSPSJ() {
//                return SPSJ;
//            }
//
//            public void setSPSJ(String SPSJ) {
//                this.SPSJ = SPSJ;
//            }
//
//            public String getSPR() {
//                return SPR;
//            }
//
//            public void setSPR(String SPR) {
//                this.SPR = SPR;
//            }
//
//            public String getSPRID() {
//                return SPRID;
//            }
//
//            public void setSPRID(String SPRID) {
//                this.SPRID = SPRID;
//            }
//
//            public String getSPDWID() {
//                return SPDWID;
//            }
//
//            public void setSPDWID(String SPDWID) {
//                this.SPDWID = SPDWID;
//            }
//
//            public String getSPDW() {
//                return SPDW;
//            }
//
//            public void setSPDW(String SPDW) {
//                this.SPDW = SPDW;
//            }
//
//            public String getSQSPRID() {
//                return SQSPRID;
//            }
//
//            public void setSQSPRID(String SQSPRID) {
//                this.SQSPRID = SQSPRID;
//            }
//
//            public String getBZID_COM() {
//                return BZID_COM;
//            }
//
//            public void setBZID_COM(String BZID_COM) {
//                this.BZID_COM = BZID_COM;
//            }
//
//            public String getBZID() {
//                return BZID;
//            }
//
//            public void setBZID(String BZID) {
//                this.BZID = BZID;
//            }
//
//            public String getCQID() {
//                return CQID;
//            }
//
//            public void setCQID(String CQID) {
//                this.CQID = CQID;
//            }
//
//            public String getSXID() {
//                return SXID;
//            }
//
//            public void setSXID(String SXID) {
//                this.SXID = SXID;
//            }
//
//            public String getXJYJ() {
//                return XJYJ;
//            }
//
//            public void setXJYJ(String XJYJ) {
//                this.XJYJ = XJYJ;
//            }
//
//            public String getACTIONID_MB_COM() {
//                return ACTIONID_MB_COM;
//            }
//
//            public void setACTIONID_MB_COM(String ACTIONID_MB_COM) {
//                this.ACTIONID_MB_COM = ACTIONID_MB_COM;
//            }
//
//            public String getDWID_MB_COM() {
//                return DWID_MB_COM;
//            }
//
//            public void setDWID_MB_COM(String DWID_MB_COM) {
//                this.DWID_MB_COM = DWID_MB_COM;
//            }
//
//            public String getDWID_RY_MB_COM() {
//                return DWID_RY_MB_COM;
//            }
//
//            public void setDWID_RY_MB_COM(String DWID_RY_MB_COM) {
//                this.DWID_RY_MB_COM = DWID_RY_MB_COM;
//            }
//
//            public String getRY_MB_COM() {
//                return RY_MB_COM;
//            }
//
//            public void setRY_MB_COM(String RY_MB_COM) {
//                this.RY_MB_COM = RY_MB_COM;
//            }
        }
    }

    public static class MBCOMBean {
        /**
         * Id :
         * FLSMAPID : null
         * ActionId : null
         * ActionName : null
         * TargetUnitId : 52140
         * TargetUnitName : 南京海关缉私局苏州海关缉私分局昆山缉私中队
         * RuleId : null
         * DataShowMeta : null
         */

        private String Id;
        private Object FLSMAPID;
        private Object ActionId;
        private Object ActionName;
        private String TargetUnitId;
        private String TargetUnitName;
        private Object RuleId;
        private Object DataShowMeta;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public Object getFLSMAPID() {
            return FLSMAPID;
        }

        public void setFLSMAPID(Object FLSMAPID) {
            this.FLSMAPID = FLSMAPID;
        }

        public Object getActionId() {
            return ActionId;
        }

        public void setActionId(Object ActionId) {
            this.ActionId = ActionId;
        }

        public Object getActionName() {
            return ActionName;
        }

        public void setActionName(Object ActionName) {
            this.ActionName = ActionName;
        }

        public String getTargetUnitId() {
            return TargetUnitId;
        }

        public void setTargetUnitId(String TargetUnitId) {
            this.TargetUnitId = TargetUnitId;
        }

        public String getTargetUnitName() {
            return TargetUnitName;
        }

        public void setTargetUnitName(String TargetUnitName) {
            this.TargetUnitName = TargetUnitName;
        }

        public Object getRuleId() {
            return RuleId;
        }

        public void setRuleId(Object RuleId) {
            this.RuleId = RuleId;
        }

        public Object getDataShowMeta() {
            return DataShowMeta;
        }

        public void setDataShowMeta(Object DataShowMeta) {
            this.DataShowMeta = DataShowMeta;
        }
    }

    public static class RYMBCOMBean {
        /**
         * Key : 13345
         * Value : 赵福地<旧>
         */

        private String Key;
        private String Value;

        public String getKey() {
            return Key;
        }

        public void setKey(String Key) {
            this.Key = Key;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
    }
}
