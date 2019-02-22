package com.sinostar.assistant.net;

import com.google.gson.JsonObject;
import com.sinostar.assistant.bean.Apporove;
import com.sinostar.assistant.bean.ApproveAgreeModel;
import com.sinostar.assistant.bean.CaseInfoActionInfo;
import com.sinostar.assistant.bean.CaseLinkCase;
import com.sinostar.assistant.bean.CaseLinkAction;
import com.sinostar.assistant.bean.ChatToken;
import com.sinostar.assistant.bean.ChatUplodData;
import com.sinostar.assistant.bean.DataQueryCompanyBean;
import com.sinostar.assistant.bean.DataQueryPersonNoResource;
import com.sinostar.assistant.bean.DataQureyPerson;
import com.sinostar.assistant.bean.Document;
import com.sinostar.assistant.bean.GuideLine;
import com.sinostar.assistant.bean.LinkBackInfo;
import com.sinostar.assistant.bean.Login;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.bean.ApproveAgreeSend;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络接口封装
 */

public interface RetrofitService {


    /**
     * 测试登录
     * @param loginMap
     * @return
     */
    @POST("/api/Account/Login")
    Observable<Login> getTest(@Body()Map<String,String> loginMap);
    /**
     * 登陆
     * @param loginMap
     * @return
     */
    @POST("/api/Account/Login")
    Observable<Login> getLogin(@Body()Map<String,String> loginMap);

    /**
     * 待审批事件列表(文书审批)
     * @param userId  登陆返回的data
     * @return
     */
     @GET("/api/Case/ApprovalPending")
     Observable<List<Apporove>> getApprovalPending(@Query("userId")String userId);

    /***
     * 待审批事件列表（环节回退）
     * @param userId
     * @return
     */
    @GET("/api/Case/BackApprovalPending")
    Observable<List<Apporove>> getLinkBacklPending(@Query("userId")String userId);

     /**
      * 已审批事件列表(文书审批)
      * @return
      */
     @GET("/api/Case/ApprovalPended")
     Observable<List<Apporove>> getApprovalPended(@Query("userId")String userId);

    /**
     * 已审批事件列表(环节回退)
     * @param userId
     * @return
     */
    @GET("/api/Case/BackApprovalPended")
    Observable<List<Apporove>> getLinkBackPended(@Query("userId")String userId);

     /**
      * 报上级审批事件列表
      * @return
      */
     @GET("/api/Case/SuperiorApprovalPended")
     Observable<List<Apporove>> getSuperiorApprovalPended(@Query("userId")String userId);

    /**
     * 审批事项详情（文书审批）
     * @param itemId
     * @return
     */
    @GET("/api/Case/GetDocList")
    Observable<Document> getDocumentInfo(@Query("itemId") String itemId);

    /***
     * 审批事项详情（环节回退）
     * @return
     */
    @GET("/api/Case/BackApprovalPendingDetailInfo")
    Observable<LinkBackInfo> getLinkBackInfo(@Query("bzid") String bzid, @Query("userId")String userId);

    /**
     * 报上级审批详情（文书）
     * @param itemId
     * @param userId
     * @return
     */
    @GET("/api/Case/SuperiorApprovalDetail")
    Observable<Document> getApproveReportInfo(@Query("itemId") String itemId, @Query("userId")String userId);

    /**
     * 获取操作按钮（通过、不通过、报上级审批）
     * @param bzid

     * @param userId
     * @return
     */

    @GET("/api/Case/GetApprovalPendingOperationButtonList")
    Observable<List<LinkBackInfo.ButtonListBean>> getPassButton(@Query("bzid") String bzid, @Query("userId") String userId);



    /**
     * 审批通过模版
     * @param userId
     * @param bzId
     * @param actionId
     * @return
     */
    @GET("/api/Case/GetApprovalAgreeModel")
    Observable<Object> getApproveAgreeModel(@Query("userId") String userId, @Query("bzid") String bzId, @Query("actionId") String actionId);

    /**
     * 获取审批办案人员列表
     * @param unitId
     * @return
     */


    @GET("/api/Case/GetApprovalHandleCaseUserList")
    Observable<List<ApproveAgreeModel.RYMBCOMBean>> getApproAgreeModelHandlerList(@Query("unitId") String unitId);

    /**
     * 获取报上级人员列表
     * @param unitId
     * @return
     */
    @GET("/api/Case/GetSuperiorApprovalUserList")
    Observable<List<ApproveAgreeModel.RYMBCOMBean>> getSuperiorApprovalUserList(@Query("unitId") String unitId);

    /**
     * 审批同意
     * @param list
     * @return
     */
    @POST("/api/Case/ApprovalAgree")
    Observable<ApproveAgreeSend> getApproveAgreeSend(@Body() String list);


    /**
     * 数据查询（个人-有数据来源）
     * @param name
     * @param dateTime
     * @param queryType
     * @return
     */
    @GET("/api/Query/PersonSmugglerRecord")
    Observable<DataQureyPerson> getDataQueryPerson(@Query("name") String name, @Query("dateTime") String dateTime, @Query("queryType") String queryType);

    /**
     * 数据查询（个人-无数据来源）
     * @param name
     * @param dateTime
     * @return
     */
    @GET("/api/Query/GetPersonSmugglerRecord")
    Observable<List<DataQueryPersonNoResource>> getDataQueryPersonNoSource(@Query("name") String name, @Query("dateTime") String dateTime);
    /**
     * 数据查询（公司-有数据来源）
     * @param companyName
     * @param queryType
     * @return
     */
    @GET("/api/Query/CompanySumgglerRecord")
    Observable<DataQueryCompanyBean> getDataQueryCompany(@Query("companyName") String companyName, @Query("queryType") String queryType);

    /**
     * 数据查询（公司-无数据来源）
     * @param companyName
     * @return
     */
    @GET("/api/Query/GetCompanySmugglerRecord")
    Observable<List<DataQueryPersonNoResource>> getDataQueryCompanyNoSource(@Query("companyName") String companyName);

    /**
     * 现场取证案件列表
     * @param userId
     * @return
     */
    @POST("/api/Evidence/List")
    Observable<List<ObtainEvidenceBean>> getObtainEvidence(@Query("userId") String userId);

    /**
     * 办案助手待办事件列表
     * @param guidelineId
     * @return
     */
    @POST("/api/Guideline/QueryGuidelineData")
    Observable<GuideLine> getGuideLine(@Query("guidelineId") String guidelineId, @Body()String body);

    /**
     * 办案助手待办事件列表（分页）
     * @param guidelineId
     * @param body
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("/api/Guideline/QueryGuidelineDataByPagging")
    Observable<GuideLine> getGuideLinePagging(@Query("guidelineId") String guidelineId, @Body()String body,
                                              @Query("pageIndex")double pageIndex, @Query("pageSize") double pageSize);

    /**
     * 查询案件环节
     * @param caseId
     * @param caseType
     * @return
     */
    @POST("/api/Assistant/QueryCaseOfLink")
    Observable<CaseLinkCase> getLinkCase(@Query("caseId") String caseId, @Query("caseType")String caseType);

    /**
     * 取案件环节可执行动作
     * @param linkId
     * @return
     */
    @POST("/api/Assistant/QueryActionInLink")
    Observable<List<CaseLinkAction>> getLinkAction(@Query("linkId") String linkId);

    /**
     * 案件环节动作详情
     * @param actionId
     * @return
     */
    @POST("/api/Assistant/QueryActionPrompt")
    Observable<List<CaseInfoActionInfo>> getLinkActionInfo(@Query("actionId") String actionId);

    /**
     * 上传图片
     * @param userId
     * @param caseId
     * @param params
     * @return
     */
    @Multipart
    @POST("/api/Evidence/UploadEvidenceAsync")
    Observable<Object> getUploadImage(@Query("userId") String userId,
                                      @Query("caseId") String caseId,
                                      @PartMap Map<String, RequestBody> params);

    /**
     * 获取环信Token
     * @param org_name
     * @param app_name
     * @param map
     * @return
     */
    @POST("/{org_name}/{app_name}/token")
    Observable<ChatToken>getChatToken(@Path("org_name")String org_name,
                                      @Path("app_name") String app_name,
                                      @Body Map<String,String>map);

    @Multipart
    @POST("/{org_name}/{app_name}/chatfiles")
    Observable<ChatUplodData>getChatUploadData(@Path("org_name")String org_name,
                                               @Path("app_name") String app_name,
                                               @Header("Authorization")String BearerToken,
                                               @Header("restrict-access")boolean restrict_access,
                                               @PartMap Map<String, RequestBody> params
                                    );

    /**
     * 获取博客园（文书审批）
     * @param pageIndex
     * @return
     */
    @GET("/api/Blog/Article/{pageIndex}")
    Observable<JsonObject> getToken(@Path("pageIndex") Integer pageIndex);


}
