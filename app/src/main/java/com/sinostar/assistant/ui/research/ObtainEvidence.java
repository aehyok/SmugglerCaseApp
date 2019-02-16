package com.sinostar.assistant.ui.research;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.query.DataQueryAssistantAdapter;
import com.sinostar.assistant.ui.research.image.ImageSelect;
import com.sinostar.assistant.utils.AppKeyBoardMgr;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObtainEvidence extends BaseActivity {


    @BindView(R.id.obtain_evidence_search)
    EditText obtainEvidenceSearch;
    @BindView(R.id.obtain_evidence_list)
    ListView obtainEvidenceList;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.obtain_evidence_back_image)
    ImageButton obtainEvidenceBackImage;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int REQUEST_CODE = 1;
    Context context;
    private Gson gson;
    DataQueryAssistantAdapter adapter;
    private String obtainEvidenceJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtain_evidence);
        ButterKnife.bind(this);
        context = this;
        gson = new Gson();
        adapter = new DataQueryAssistantAdapter(context);
        obtainEvidenceList.setAdapter(adapter);
        getData();
        initListener();
    }

    private void initListener() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        obtainEvidenceSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                AppKeyBoardMgr.hideKeybord(obtainEvidenceSearch);
                ToastUtil.showToast(context, "点击了搜索");
                return false;
            }
        });
        obtainEvidenceList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                List<ObtainEvidenceBean> list = gson.fromJson(obtainEvidenceJson, new TypeToken<List<ObtainEvidenceBean>>() {
                }.getType());
                Intent intent = new Intent(context, ImageSelect.class);
                intent.putExtra("caseId", list.get(i).getAJID() + "");
                startActivity(intent);
            }
        });
        setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                obtainEvidenceBackImage.requestFocusFromTouch();
            }
        });

    }

    private void getData() {
        ObserverOnNextListener listener = new ObserverOnNextListener<List<ObtainEvidenceBean>>() {
            @Override
            public void onNext(List<ObtainEvidenceBean> result) {
                if (result != null && result.size() != 0) {
                    obtainEvidenceList.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
                    titleText.setText("当前办案列表");
                    obtainEvidenceJson = gson.toJson(result);
                    adapter.getObtainEvidence(result, 3);
                }
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        NetMethods.getObtainEvidence(new MyObserver<List<ObtainEvidenceBean>>(context, listener), ApplicationUtil.getUserId());

    }


    /**
     * 获取权限获取的结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:  //二维码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(ObtainEvidence.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ToastUtil.showToast(context, "没有获取到相机权限，无法使用二维码识别功能");

                }
                break;
            case 101:  //语音识别
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initSpeech(context);
                } else {
                    ToastUtil.showToast(context, "没有获取到麦克风权限，无法使用语音识别功能");

                }
                break;
        }
    }

    @OnClick({R.id.obtain_evidence_back_image, R.id.obtain_evidence_voice, R.id.obtain_evidence_qr_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.obtain_evidence_back_image:
                finish();
                break;
            case R.id.obtain_evidence_voice:  //语音输入
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                101);

                    } else {
                        initSpeech(context);
                    }
                } else {
                    initSpeech(context);
                }

                break;
            case R.id.obtain_evidence_qr_code:  //二维码识别
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                100);

                    } else {
                        Intent intent = new Intent(ObtainEvidence.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(ObtainEvidence.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }

                break;
        }
    }


    /**
     * 初始化语音识别
     */
    public void initSpeech(Context context) {
        //1、初始化窗口
        RecognizerDialog dialog = new RecognizerDialog(this, null);
        //清空参数
        dialog.setParameter(SpeechConstant.PARAMS, null);
        //2、设置听写参数，详见官方文档
        //识别中文听写可设置为"zh_cn"，en_us（英语）、zh_cn（汉语）
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        dialog.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        dialog.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        dialog.setParameter(SpeechConstant.ASR_PTT, "0");
        //3、开始听写
        dialog.setListener(recognizerDialogListener);
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Intent intent = new Intent(ObtainEvidence.this, CaptureActivity.class);
            startActivityForResult(intent, RESULT_OK);
        }
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    obtainEvidenceSearch.setText(result);
                    obtainEvidenceSearch.setSelection(obtainEvidenceSearch.getText().length());//将光标移至文字末尾
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast(context, "解析二维码失败");
                }
            }
        }
    }


    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

        public void onResult(RecognizerResult results, boolean isLast) {
            String result = parseVoice(results.getResultString());
            if (result != null && !result.equals("")) {
                obtainEvidenceSearch.setText(parseVoice(results.getResultString()));
                obtainEvidenceSearch.setSelection(obtainEvidenceSearch.getText().length());
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {

        }
    };


    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }
}
