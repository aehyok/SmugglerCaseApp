package com.sinostar.assistant.net;

import com.sinostar.assistant.bean.ApproveAgreeModel;
import com.sinostar.assistant.bean.CaseInfoActionInfo;
import com.sinostar.assistant.bean.CaseLinkAction;
import com.sinostar.assistant.bean.CaseLinkCase;
import com.sinostar.assistant.bean.ChatToken;
import com.sinostar.assistant.bean.ChatUplodData;
import com.sinostar.assistant.bean.DataQueryCompanyBean;
import com.sinostar.assistant.bean.DataQueryPersonNoResource;
import com.sinostar.assistant.bean.DataQureyPerson;
import com.sinostar.assistant.bean.Document;
import com.sinostar.assistant.bean.LinkBackInfo;
import com.sinostar.assistant.bean.Login;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.bean.ApproveAgreeSend;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.bean.Apporove;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class NetMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    public static void NerMethods(Observable observable, Observer observer) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public static void  getLogin(MyObserver<Login> observer, Map<String,String> loginMap) {
        NerMethods(Net.getInstance().serviceProvider(). getLogin(loginMap),observer);
    }
    public static void getApprovalPending(MyObserver<List<Apporove>> observer,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getApprovalPending(userId),observer);
    }
    public static void getLinkBacklPending(MyObserver<List<Apporove>> observer,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getLinkBacklPending(userId),observer);
    }
    public static void getLinkBackPended(MyObserver<List<Apporove>> observer,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getLinkBackPended(userId),observer);
    }
    public static void getApprovalPended(MyObserver<List<Apporove>> observer,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getApprovalPended(userId),observer);
    }
    public static void getSuperiorApprovalPended(MyObserver<List<Apporove>> observer,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getSuperiorApprovalPended(userId),observer);
    }
    public static void getDocumentInfo(MyObserver<Document> observer, String itemId) {
        NerMethods(Net.getInstance().serviceProvider().getDocumentInfo(itemId),observer);
    }

    public static void getLinkBackInfo(MyObserver<LinkBackInfo> observer, String bzid,String userId) {
        NerMethods(Net.getInstance().serviceProvider().getLinkBackInfo(bzid,userId),observer);
    }

    public static void getApproveReportInfo(MyObserver<Document> observer, String itemId, String userId) {
        NerMethods(Net.getInstance().serviceProvider().getApproveReportInfo(itemId,userId),observer);
    }

    public static void  getApproveAgreeModel(MyObserver<Object> observer, String userId, String bzId, String actionId) {
        NerMethods(Net.getInstance().serviceProvider(). getApproveAgreeModel(userId,bzId,actionId),observer);
    }
    public static void  getPassButton(MyObserver<List<LinkBackInfo.ButtonListBean>> observer, String bzId,  String userId) {
        NerMethods(Net.getInstance().serviceProvider(). getPassButton(bzId,userId),observer);
    }
    public static void  getSuperiorApprovalUserList(MyObserver<List<ApproveAgreeModel.RYMBCOMBean>> observer, String unitId) {
        NerMethods(Net.getInstance().serviceProvider(). getSuperiorApprovalUserList(unitId),observer);
    }
    public static void  getApproAgreeModelHandlerList(MyObserver<List<ApproveAgreeModel.RYMBCOMBean>> observer, String unitId) {
        NerMethods(Net.getInstance().serviceProvider(). getApproAgreeModelHandlerList(unitId),observer);
    }

    public static void  getApproveAgreeSend(MyObserver<ApproveAgreeSend>observer, String jsonString) {
        NerMethods(Net.getInstance().serviceProvider(). getApproveAgreeSend(jsonString),observer);
    }
    public static void  getDataQuery(MyObserver<DataQureyPerson> observer, String name, String dateTime, String queryType) {
        NerMethods(Net.getInstance().serviceProvider().getDataQueryPerson(name,dateTime,queryType),observer);
    }
    public static void  getDataQueryPersonNoSource(MyObserver<List<DataQueryPersonNoResource>> observer, String name, String dateTime) {
        NerMethods(Net.getInstance().serviceProvider().getDataQueryPersonNoSource(name,dateTime),observer);
    }

    public static void  getDataQueryCompany(MyObserver<DataQueryCompanyBean> observer, String companyName, String queryType) {
        NerMethods(Net.getInstance().serviceProvider().getDataQueryCompany(companyName,queryType),observer);
    }
    public static void  getDataQueryCompanyNoSource(MyObserver<List<DataQueryPersonNoResource>> observer, String companyName) {
        NerMethods(Net.getInstance().serviceProvider().getDataQueryCompanyNoSource(companyName),observer);
    }
    public static void  getObtainEvidence(MyObserver<List<ObtainEvidenceBean>> observer, String userID) {
        NerMethods(Net.getInstance().serviceProvider().getObtainEvidence(userID),observer);
    }
    public static void  getGuideLine(MyObserver<List<ObtainEvidenceBean>> observer, String guidelineId,String body) {
        NerMethods(Net.getInstance().serviceProvider().getGuideLine(guidelineId,body),observer);
    }
    public static void  getGuideLinePagging(MyObserver<List<ObtainEvidenceBean>> observer, String guidelineId,String body,double pageIndex,double pageSize) {
        NerMethods(Net.getInstance().serviceProvider().getGuideLinePagging(guidelineId,body,pageIndex,pageSize),observer);
    }
    public static void  getLinkCase(MyObserver<CaseLinkCase> observer, String caseId, String caseType) {
        NerMethods(Net.getInstance().serviceProvider().getLinkCase(caseId,caseType),observer);
    }
    public static void  getLinkAction(MyObserver<List<CaseLinkAction>> observer, String linkId) {
        NerMethods(Net.getInstance().serviceProvider().getLinkAction(linkId),observer);
    }
    public static void  getLinkActionInfo(MyObserver<List<CaseInfoActionInfo>> observer, String actionId) {
        NerMethods(Net.getInstance().serviceProvider().getLinkActionInfo(actionId),observer);
    }

    public static void  getUploadImage(MyObserver<Object> observer, String userId,String caseId, Map<String, RequestBody> params) {
        NerMethods(Net.getInstance().serviceProvider().getUploadImage(userId,caseId,params),observer);
    }






}