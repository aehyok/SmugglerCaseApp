package com.sinostar.assistant.bean;

/**
 * 审批通过返回数据实体类
 */
public class ApproveAgreeSend {

    /**
     * _errorMessage : 环节流转出错！
     * ErrorMessage : 环节流转出错！
     * NextStateOrder : null
     * bzid : 920000000196709
     * SuccessInfoMessage : null
     */

    private String _errorMessage;
    private String ErrorMessage;
    private Object NextStateOrder;
    private String bzid;
    private Object SuccessInfoMessage;

    public String get_errorMessage() {
        return _errorMessage;
    }

    public void set_errorMessage(String _errorMessage) {
        this._errorMessage = _errorMessage;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public Object getNextStateOrder() {
        return NextStateOrder;
    }

    public void setNextStateOrder(Object NextStateOrder) {
        this.NextStateOrder = NextStateOrder;
    }

    public String getBzid() {
        return bzid;
    }

    public void setBzid(String bzid) {
        this.bzid = bzid;
    }

    public Object getSuccessInfoMessage() {
        return SuccessInfoMessage;
    }

    public void setSuccessInfoMessage(Object SuccessInfoMessage) {
        this.SuccessInfoMessage = SuccessInfoMessage;
    }
}
