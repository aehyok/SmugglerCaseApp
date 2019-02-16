package com.sinostar.assistant.ui.cassAssistant.announce.link;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.ToastUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图显示（详细地址）
 */

public class MapActivity extends AppCompatActivity {
    AMap aMap;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.map_title_text)
    TextView mapTitleText;
    @BindView(R.id.map_info_text)
    TextView mapInfoText;
    @BindView(R.id.mapView)
    MapView mapView;



    private Context context;
    private Dialog mapDialog;
    private LatLng latLng;

    private float mapZoom = 17;
    private BaseActivity.PermissionListener mListener;
    private EMLocationMessageBody locationBody;
    private String locationName;
    private LatLng myLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = MapActivity.this;
        ButterKnife.bind(this);
        titleBarText.setText("地图显示");
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        Intent intent=getIntent();
        List<EMMessage> data = intent.getParcelableArrayListExtra("locationData");
        if(data!=null&&data.size()!=0){
            locationBody = (EMLocationMessageBody) data.get(0).getBody();
            mapTitleText.setText(locationBody.getAddress());
            mapInfoText.setText(intent.getStringExtra(Constent.MAP_ADDRESS));
            latLng=new LatLng(locationBody.getLatitude(),locationBody.getLongitude());
            locationName=locationBody.getAddress();
        }else{
            mapTitleText.setText("创新大厦");
            mapInfoText.setText("广东省深圳市南山区创新大厦");
            locationName="创新大厦";
            latLng=new LatLng(22.549225,113.926031);
        }

        initMap();
        permission();




    }

    private void initMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);//设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        aMap.setPointToCenter(500, 500);//x、y均为屏幕坐标，屏幕左上角为坐标原点，即(0,0)点。
        aMap.getUiSettings().setGestureScaleByMapCenter(true);  //滑动手势
        aMap.getUiSettings().setZoomControlsEnabled(false);  //缩放按钮的显示与否
        aMap.getUiSettings().setMyLocationButtonEnabled(false);  //定位按钮
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.chat_map_anchors)));
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));   //将视野移动到目标区域
    }

    /**
     * 获取定位授权
     */
    private void permission() {
        requestRunPermisssion(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, new BaseActivity.PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                getLocation();
            }


            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    ToastUtil.showToast(context, "有部分权限没有授权，请授权后再进行相关操作");
                }
            }
        });
    }

    public void requestRunPermisssion(String[] permissions, BaseActivity.PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }

        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), 100);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

//    private void initListener() {
//        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult RegeocodeResult, int i) {
//
//            }
//
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//                if (i == 1000) {
//                    mapInfoText.setText(geocodeResult.getGeocodeAddressList().get(0).getFormatAddress());
//                    //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
//                    LatLonPoint latLonPoint = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
//                    latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//                    location = new double[]{latLng.latitude, latLng.longitude};
//                    locationName = geocodeResult.getGeocodeAddressList().get(0).getFormatAddress();
//                    //移动视角到当前地点
//                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(latLng);
//                    markerOptions.title(geocodeResult.getGeocodeQuery().getLocationName());
//                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                            .decodeResource(getResources(), R.drawable.chat_map_anchors)));
//                    aMap.addMarker(markerOptions);
//                }
//            }
//        });
//    }

    private void getLocation() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                 myLatLng = new LatLng(location.getLatitude(), location.getLongitude());


            }
        });
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.map_open_app_button,R.id.map_location_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.map_open_app_button:
                showMapDialog(latLng);
                break;
            case R.id.map_location_button:
                mapZoom=aMap.getCameraPosition().zoom;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, mapZoom));
                break;
        }
    }


    /**
     * 显示地图选择弹窗
     *
     */
    public void showMapDialog(final LatLng latLng) {
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
                startNavi(gaoDeToBaidu(latLng.latitude, latLng.longitude));
                mapDialog.dismiss();
            }
        });
        view.findViewById(R.id.map_dialog_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNaviGao(latLng);
                mapDialog.dismiss();
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

    /**
     * 调用百度地图
     *
     * @param location
     */
    public void startNavi(double[] location) {
        Intent intent;
        if (isAvilible("com.baidu.BaiduMap")) {//传入指定应用包名

            try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + location[0] + "," + location[1] + "|name:" + locationName +       //终点
                        "&mode=driving&" +          //导航路线方式
//                        "region=北京" +           //城市
                        "&src=我的位置#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            ToastUtil.showToast(context, "您尚未安装百度地图");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }

    /**
     * 调用高德地图
     *
     */
    public void startNaviGao(LatLng latLng) {
        Intent intent;
        if (isAvilible("com.autonavi.minimap")) {
            try {
                intent = Intent.getIntent("amapuri://route/plan/?sourceApplication=Assistant&sname=我的位置&dlat=" +
                        latLng.latitude + "&dlon=" + latLng.longitude + "&dname=" + locationName + "&dev=0&t=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.showToast(context, "您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageName：应用包名
     * @return
     */
    public boolean isAvilible(String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * @author Administrator
     * @describe 高德转百度（火星坐标gcj02ll–>百度坐标bd09ll）
     */
    public static double[] gaoDeToBaidu(double gd_lat, double gd_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat, tempLon};
        return gps;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }



}
