package com.sinostar.assistant.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.ToastUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 地图相关
 */
public class MapBottomDialog {
    private static Dialog mapDialog;

    public  void showMapDialog(final Context context, final String location[]) {
        mapDialog = new Dialog(context, R.style.MapDialog);
        mapDialog.setCanceledOnTouchOutside(true);
        mapDialog.setCancelable(true);

        Window window = mapDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.MapDialog);
        View view = View.inflate(context, R.layout.map_dialog, null);
        view.findViewById(R.id.map_dialog_item1).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startNavi(context,location);
           }
         });
        view.findViewById(R.id.map_dialog_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNaviGao(context,location);
            }
        });
        view.findViewById(R.id.map_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapDialog != null && mapDialog.isShowing()) {
                    mapDialog.dismiss();
                }
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mapDialog.show();
    }
     /** 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public  boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

//    //开启百度导航
    public  void startNavi(Context context,String location[]) {
        Intent intent;
        if(isAvilible(context,"com.baidu.BaiduMap")){//传入指定应用包名

            try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:"+location[0]+","+location[1]+"|name:我的目的地"+        //终点
                        "&mode=driving&" +          //导航路线方式
                        "region=北京" +           //
                        "&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        }else{//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            ToastUtil.showToast(context, "您尚未安装百度地图");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }
    //高德地图,起点就是定位点
    // 终点是LatLng ll = new LatLng("你的纬度latitude","你的经度longitude");
    public  void startNaviGao(Context context, String location[]) {
        Intent intent;
        if (isAvilible(context, "com.autonavi.minimap")) {
            try{
                intent = Intent.getIntent("androidamap://navi?sourceApplication=慧医&poiname=我的目的地&lat="+
                        location[0]+"&lon="+location[1]+"&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e)
            {e.printStackTrace(); }
        }else{
            ToastUtil.showToast(context, "您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }



}




