package com.sinostar.assistant.ui.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.ui.BlogMessage.BlogMessageActivity;
import com.sinostar.assistant.ui.BlogMessage.BlogRemindActivity;
import com.sinostar.assistant.ui.HMSPushHelper;
import com.sinostar.assistant.ui.LoginActivity;
import com.sinostar.assistant.ui.addressList.AdressList;
import com.sinostar.assistant.ui.addressList.ChatLogin;
import com.sinostar.assistant.ui.cassAssistant.CaseAssistantActivity;
import com.sinostar.assistant.ui.mobile.MobileApproveActivity;
import com.sinostar.assistant.ui.query.DataQuery;
import com.sinostar.assistant.ui.research.ObtainEvidence;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.FileUtil;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.ToastUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity {
    HomeGridViewAdapter homeGridViewAdapter;
    AppCompatActivity context;
    List<String> list = new ArrayList<>();
    String[] homeText = {"Blog Message", "Assistant", "Blog Remind", "Blog Manager", "Gallery", "Approval", "Query", "Contacts"};
    int[] homeImage = {R.drawable.tztg, R.drawable.bazs, R.drawable.yjts,
            R.drawable.zfda, R.drawable.xcqz, R.drawable.ydsp, R.drawable.sjcx, R.drawable.txl};
    String[] homeTextSZ = {"通知公告", "预警提示", "现场取证", "移动审批", "数据查询", "通讯录"};
    int[] homeImageSZ = {R.drawable.tztg, R.drawable.yjts, R.drawable.xcqz, R.drawable.ydsp, R.drawable.sjcx, R.drawable.txl};
    @BindView(R.id.home_gridview)
    GridView homeGridview;
    @BindView(R.id.home_title)
    TextView homeTitle;
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private FileOutputStream fos;
    private BufferedInputStream bis;
    private boolean isStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        context = HomeActivity.this;
        Intent intent = getIntent();
        String personName = intent.getStringExtra(Constent.PERSON_NAME);
        if (personName != null && !personName.equals("")) {
            Intent intent1 = new Intent(context, AdressList.class);
            intent1.putExtra(Constent.PERSON_NAME, personName);
            startActivity(intent1);
        }

        ApplicationUtil applicationUtil = new ApplicationUtil();
        //华为推送初始化
        HMSPushHelper.getInstance().initHMSAgent(applicationUtil);

        //取消严格模式  FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        homeGridview = findViewById(R.id.home_gridview);
        if (Constent.isSZ) {
            homeGridViewAdapter = new HomeGridViewAdapter(context, homeTextSZ, homeImageSZ);
            homeTitle.setText(R.string.home_title_sz);
        } else {
            homeGridViewAdapter = new HomeGridViewAdapter(context, homeText, homeImage);
            homeTitle.setText(R.string.home_title);
        }

        homeGridview.setAdapter(homeGridViewAdapter);
        permission();
        initListener();
//        checkVersion();


    }

    private void initListener() {
        homeGridview.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                if(Constent.isSZ){
                    switch (i) {
                        case 0:  //通知公告
                            break;
                        case 1:  //预警提示
                            break;

                        case 2:  //现场取证
                            isLogin(ObtainEvidence.class);
                            break;
                        case 3:  //移动审批
                            isLogin(MobileApproveActivity.class);
                            break;
                        case 4:  //数据查询
                            isLogin(DataQuery.class);
                            break;
                        case 5: //通讯录
//                    isLogin(AdressList.class);
                            startActivity(new Intent(context, ChatLogin.class));
                            break;

                    }
                }else{
                    switch (i) {
                        case 0:  //通知公告
                            isLogin( BlogMessageActivity.class );
                            break;
                        case 1:  //办案助手
                            isLogin(CaseAssistantActivity.class);
                            break;
                        case 2:  //预警提示
                            isLogin(BlogRemindActivity.class);
                            break;
                        case 3:  //执法档案
                            break;
                        case 4:  //现场取证
                            isLogin(ObtainEvidence.class);
                            break;
                        case 5:  //移动审批
                            isLogin(MobileApproveActivity.class);
                            break;
                        case 6:  //数据查询
                            isLogin(DataQuery.class);
                            break;
                        case 7: //通讯录
//                    isLogin(AdressList.class);
                            startActivity(new Intent(context, ChatLogin.class));
                            break;

                    }
                }

            }
        });

    }

    private void permission() {
        requestRunPermisssion(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //表示所有权限都授权了


                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String permission : deniedPermission) {
                            ToastUtil.showToast(context, "有部分权限没有授权，请授权后再进行相关操作");
                        }
                    }
                });
    }


    /**
     * 判断是否登录
     *
     * @param cls 要跳转的页面
     */

    private void isLogin(Class<?> cls) {
        if (ApplicationUtil.getUserId() != null && !ApplicationUtil.getUserId().equals("")) {
            Intent intent = new Intent(context, cls);
            startActivity(intent);
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("cls", cls.getName());
            startActivity(intent);

        }
    }


    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        if (getVersionCode() < 3) {
            showDialogUpdate();//弹出提示版本更新的对话框

        }
    }


    /**
     * 提示版本更新的对话框
     */
    private void showDialogUpdate() {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
                        setIcon(R.mipmap.ic_launcher).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "选择确定哦", 0).show();
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();


    }

    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress() {
        final String uri = "https://downapp.baidu.com/appsearch/AndroidPhone/1.0.73.221/1/1012271b/20180927115602/" +
                "appsearch_AndroidPhone_1-0-73-221_1012271b.apk?responseContentDisposition=attachment%3Bfilename%" +
                "3D%22appsearch_AndroidPhone_1012271b.apk%22&responseContentType=application%2Fvnd.android.package" +
                "-archive&request_id=1539070029_3999186152&type=static";
//        final   String uri=" https://downs.muzhiwan.com/2018/10/08/com.bigfishgames.enchanteddarkseedgoog5bbac6ebd2f0c.gpk";
        final ProgressDialog pd;    //进度条对话框
        final UpdateThread updateThread = new UpdateThread();
        pd = new ProgressDialog(this);

        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");


        pd.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isStop = true;
                pd.dismiss();

            }
        });
        updateThread.getData(uri, pd);
        updateThread.start();


    }
    //启动子线程下载任务

    class UpdateThread extends Thread {
        private String uri;
        private ProgressDialog pd;

        UpdateThread() {
        }

        public void getData(String uri, ProgressDialog pd) {
            this.uri = uri;
            this.pd = pd;

        }

        @Override
        public void run() {
            try {
                File file = getFileFromServer(uri, pd);
                sleep(1000);
                installApk(file);
                pd.dismiss(); //结束掉进度条对话框
            } catch (Exception e) {
                //下载apk失败
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isStop) {
                            Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                e.printStackTrace();
            }

        }


    }

    ;

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public File getFileFromServer(String uri, final ProgressDialog progressDialog) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            inputStream = httpURLConnection.getInputStream();
            //获取到文件的大小
            int apkLength = httpURLConnection.getContentLength();
            progressDialog.setMax(apkLength);

            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), "assistant" + getVersionName() +
                    ".apk");
            if (FileUtil.fileIsExists(file.getPath()) && file.length() >= httpURLConnection.getContentLength()) { //判断是否已经下载
                return file;
            } else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.show();
                    }
                });

                fos = new FileOutputStream(file);
                bis = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];
                int len;
                int total = 0;
                while ((len = bis.read(buffer)) != -1) {
                    if (isStop) {
                        break;
                    } else {
                        fos.write(buffer, 0, len);
                        total += len;
                        progressDialog.setProgress(total);
                        float all = apkLength / 1024 / 1024;
                        float percent = total / 1024 / 1024;
                        progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all));
                    }

                }
                fos.close();
                bis.close();
                inputStream.close();
            }
            if (FileUtil.fileIsExists(file.getPath()) && file.length() >= httpURLConnection.getContentLength()) {
                return file;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }


    /*
     * 获取当前程序的版本名
     */
    private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionName;

    }


    /*
     * 获取当前程序的版本号
     */
    private int getVersionCode() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
