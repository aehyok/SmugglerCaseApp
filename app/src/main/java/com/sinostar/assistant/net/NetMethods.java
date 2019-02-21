package com.sinostar.assistant.net;

import com.google.gson.JsonObject;
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
    public static void NetMethods(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public static void  getTest(MyObserver<Login> observer, Map<String,String> loginMap) {
        NetMethods(Net.getInstance().serviceProvider(). getLogin(loginMap),observer);
    }

    public static void  getLogin(MyObserver<Login> observer, Map<String,String> loginMap) {
        NetMethods(Net.getInstance().serviceProvider(). getLogin(loginMap),observer);
    }
    public static void getApprovalPending(MyObserver<List<Apporove>> observer,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getApprovalPending(userId),observer);
    }
    public static void getLinkBacklPending(MyObserver<List<Apporove>> observer,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getLinkBacklPending(userId),observer);
    }
    public static void getLinkBackPended(MyObserver<List<Apporove>> observer,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getLinkBackPended(userId),observer);
    }
    public static void getApprovalPended(MyObserver<List<Apporove>> observer,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getApprovalPended(userId),observer);
    }
    public static void getSuperiorApprovalPended(MyObserver<List<Apporove>> observer,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getSuperiorApprovalPended(userId),observer);
    }
    public static void getDocumentInfo(MyObserver<Document> observer, String itemId) {
        NetMethods(Net.getInstance().serviceProvider().getDocumentInfo(itemId),observer);
    }

    public static void getLinkBackInfo(MyObserver<LinkBackInfo> observer, String bzid,String userId) {
        NetMethods(Net.getInstance().serviceProvider().getLinkBackInfo(bzid,userId),observer);
    }

    public static void getApproveReportInfo(MyObserver<Document> observer, String itemId, String userId) {
        NetMethods(Net.getInstance().serviceProvider().getApproveReportInfo(itemId,userId),observer);
    }

    public static void  getApproveAgreeModel(MyObserver<Object> observer, String userId, String bzId, String actionId) {
        NetMethods(Net.getInstance().serviceProvider(). getApproveAgreeModel(userId,bzId,actionId),observer);
    }
    public static void  getPassButton(MyObserver<List<LinkBackInfo.ButtonListBean>> observer, String bzId,  String userId) {
        NetMethods(Net.getInstance().serviceProvider(). getPassButton(bzId,userId),observer);
    }
    public static void  getSuperiorApprovalUserList(MyObserver<List<ApproveAgreeModel.RYMBCOMBean>> observer, String unitId) {
        NetMethods(Net.getInstance().serviceProvider(). getSuperiorApprovalUserList(unitId),observer);
    }
    public static void  getApproAgreeModelHandlerList(MyObserver<List<ApproveAgreeModel.RYMBCOMBean>> observer, String unitId) {
        NetMethods(Net.getInstance().serviceProvider(). getApproAgreeModelHandlerList(unitId),observer);
    }

    public static void  getApproveAgreeSend(MyObserver<ApproveAgreeSend>observer, String jsonString) {
        NetMethods(Net.getInstance().serviceProvider(). getApproveAgreeSend(jsonString),observer);
    }
    public static void  getDataQuery(MyObserver<DataQureyPerson> observer, String name, String dateTime, String queryType) {
        NetMethods(Net.getInstance().serviceProvider().getDataQueryPerson(name,dateTime,queryType),observer);
    }
    public static void  getDataQueryPersonNoSource(MyObserver<List<DataQueryPersonNoResource>> observer, String name, String dateTime) {
        NetMethods(Net.getInstance().serviceProvider().getDataQueryPersonNoSource(name,dateTime),observer);
    }

    public static void  getDataQueryCompany(MyObserver<DataQueryCompanyBean> observer, String companyName, String queryType) {
        NetMethods(Net.getInstance().serviceProvider().getDataQueryCompany(companyName,queryType),observer);
    }
    public static void  getDataQueryCompanyNoSource(MyObserver<List<DataQueryPersonNoResource>> observer, String companyName) {
        NetMethods(Net.getInstance().serviceProvider().getDataQueryCompanyNoSource(companyName),observer);
    }
    public static void  getObtainEvidence(MyObserver<List<ObtainEvidenceBean>> observer, String userID) {
        NetMethods(Net.getInstance().serviceProvider().getObtainEvidence(userID),observer);
    }
    public static void  getGuideLine(MyObserver<List<ObtainEvidenceBean>> observer, String guidelineId,String body) {
        NetMethods(Net.getInstance().serviceProvider().getGuideLine(guidelineId,body),observer);
    }
    public static void  getGuideLinePagging(MyObserver<List<ObtainEvidenceBean>> observer, String guidelineId,String body,double pageIndex,double pageSize) {
        NetMethods(Net.getInstance().serviceProvider().getGuideLinePagging(guidelineId,body,pageIndex,pageSize),observer);
    }
    public static void  getLinkCase(MyObserver<CaseLinkCase> observer, String caseId, String caseType) {
        NetMethods(Net.getInstance().serviceProvider().getLinkCase(caseId,caseType),observer);
    }
    public static void  getLinkAction(MyObserver<List<CaseLinkAction>> observer, String linkId) {
        NetMethods(Net.getInstance().serviceProvider().getLinkAction(linkId),observer);
    }
    public static void  getLinkActionInfo(MyObserver<List<CaseInfoActionInfo>> observer, String actionId) {
        NetMethods(Net.getInstance().serviceProvider().getLinkActionInfo(actionId),observer);
    }

    public static void  getUploadImage(MyObserver<Object> observer, String userId,String caseId, Map<String, RequestBody> params) {
        NetMethods(Net.getInstance().serviceProvider().getUploadImage(userId,caseId,params),observer);
    }

    public static void  getToken(MyObserver<JsonObject> observer, String clientId, String clientSecret, String grantType, String url) {
        NetMethods(Net.getInstance().serviceProvider(url).getToken(clientId,clientSecret,grantType),observer);
    }
}