package com.sinostar.assistant.ui.addressList.chat.chatMap;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.ui.addressList.chat.Chat.RESULT_CODE_MAP;

/**
 * 聊天地图
 */

public class ChatMap extends BaseActivity {

    @BindView(R.id.title_bar_back_image)
    ImageView titilBarBackImage;
    @BindView(R.id.title_bar_back_text)
    TextView titilBarBackText;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_rightText)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_right_image)
    ImageView titleBarRightImage;
    @BindView(R.id.chat_map_search)
    EditText chatMapSearch;
    @BindView(R.id.chat_map_mapview)
    MapView mapView;
    @BindView(R.id.chat_map_list)
    ListView chatMapList;
    @BindView(R.id.chat_map_refreshLayout)
    SmartRefreshLayout chatMapRefreshLayout;
    @BindView(R.id.chat_map_anchor)
    ImageView chatMapAnchor;
    private AMap aMap;
    private GeocodeSearch geocoderSearch;
    private Context context;
    private MarkerOptions markerOptions;

    private PoiSearch poiSearch;
    private PoiSearch.Query query;
    private BaseActivity.PermissionListener mListener;
    private Marker marker;
    private ChatMapAdapter chatMapAdapter;
    private ChatMapDialogAdapter chatMapDialogAdapter;

    private boolean isDrag = true;
    private LatLng starLaLng;
    private float mapZoom = 17;
    private LatLng myLocation;
    private int page = 1;
    private int pageCount = 10;
    private ListView chatDialogList;
    private String city;
    private SmartRefreshLayout refreshLayoutDialog;
    private int pageDialog = 1;
    private String dialogSearchText;
    private Dialog dialog;
    private LatLonPoint selectLanPoint;
    private String title;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_map);
        ButterKnife.bind(this);
        context = this;
        markerOptions = new MarkerOptions();
        init();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(context);
        chatMapAdapter = new ChatMapAdapter(context);
        chatMapList.setAdapter(chatMapAdapter);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();
        mapChange();
        permission();
        initListener();
        loadMorePOIData();

    }

    private void initListener() {
        chatMapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<PoiItem>  result = chatMapAdapter.mList;
                LatLng latLng = new LatLng(chatMapAdapter.mList.get(i).getLatLonPoint().getLatitude(),
                        chatMapAdapter.mList.get(i).getLatLonPoint().getLongitude());
                selectLanPoint=result.get(i).getLatLonPoint();
                title=result.get(i).getTitle();
                address=result.get(i).getProvinceName()+result.get(i).getCityName()+result.get(i).getAdName()+result.get(i).getSnippet();
                marker.setPosition(latLng);
                chatMapAdapter.refreshUI(i);
                isDrag = false;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, aMap.getCameraPosition().zoom));

            }
        });
    }

    /**
     * 加载更多周边位置
     */
    private void loadMorePOIData() {
        chatMapRefreshLayout.setEnableRefresh(false);
//        chatMapRefreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        chatMapRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = page + 1;
                        getPOIData(starLaLng, page);
                        chatMapRefreshLayout.finishLoadMore();
                    }
                }, 500);
            }
        });

    }

    /**
     * 获取周边地点
     * @param latLng 选择的位置坐标
     * @param page 当前的页数
     */
    private void getPOIData(LatLng latLng, final int page) {
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query = new PoiSearch.Query("",
//                "120000," +    //商务住宅相关
//                        "070000," +   //生活服务相关
//                        "130000," + //政府机构相关
//                        "140000",   //科教文化场所
                "");
        query.setPageSize(pageCount);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int i) {
                if (result.getPois() != null && result.getPois().size() != 0) {
                    if (page != 1) {
                        chatMapAdapter.addData(result.getPois());
                        chatMapList.setSelection(pageCount * (page - 1));
                    } else {
                        city = result.getPois().get(0).getCityName();
                        chatMapAdapter.getData(result.getPois());
                        chatMapAdapter.refreshUI(0);
                        chatMapList.setSelection(0);
                    }
                     selectLanPoint=result.getPois().get(0).getLatLonPoint();
                     title=result.getPois().get(0).getTitle();
                     address=result.getPois().get(0).getProvinceName()+result.getPois().get(0).getCityName()+result.getPois().get(0).getAdName();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude, latLng.longitude), 2000));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }

    /**
     * 地图移动监听
     */
    private void mapChange() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                setMarket(cameraPosition.target);

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                setMarket(cameraPosition.target);
                //地图的移动距离
                float moveLength = AMapUtils.calculateLineDistance(starLaLng, cameraPosition.target);
                starLaLng = cameraPosition.target;
                if (mapZoom != cameraPosition.zoom) {
                    mapZoom = cameraPosition.zoom;
                } else if (moveLength > 30 && isDrag) {
                    page = 1;
                    getPOIData(cameraPosition.target, page);
                }
                isDrag = true;


            }
        });
    }

    /**
     * 根据地图移动放置标记
     * @param latLng  标记位置
     */
    private void setMarket(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        markerOptions.draggable(false);//设置Marker可拖动
        markerOptions.visible(true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.chat_map_anchors)));
        marker = aMap.addMarker(markerOptions);
        marker.setPosition(latLng);
    }


    /**
     * 初始化地图
     */
    private void initMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
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

    }

    /**
     * 定位到当前位置
     */
    private void getLocation() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                starLaLng = latLng;
                myLocation = latLng;
                setMarket(latLng);
                getPOIData(new LatLng(location.getLatitude(), location.getLongitude()), page);
                //移动视角到当前地点
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
            }
        });
    }

    private void init() {
        titilBarBackText.setVisibility(View.GONE);
        titleBarText.setText("位置");
        titleBarRightText.setText("发送");
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

    @Override
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

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_rightText, R.id.chat_map_search, R.id.chat_map_location_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_rightText:
                Intent intent=new Intent();
                intent.putExtra("latitude",selectLanPoint.getLatitude());
                intent.putExtra("longitude",selectLanPoint.getLongitude());
                intent.putExtra(Constent.MAP_TITLE,title);
                intent.putExtra(Constent.MAP_ADDRESS,address);
                setResult(RESULT_CODE_MAP,intent);
                finish();
                break;
            case R.id.chat_map_search:
                showMapDialog();
                break;
            case R.id.chat_map_location_button:
                isDrag = true;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, mapZoom));
                break;
        }
    }


    /**
     * 显示地图选择弹窗
     */
    public void showMapDialog() {
        dialog = new Dialog(context, R.style.MapDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.TOP);
//        window.setWindowAnimations(R.style.MapDialog);
        View view = View.inflate(context, R.layout.chat_map_dialog, null);
        EditText searchText = view.findViewById(R.id.chat_map_dialog_search);
        chatDialogList = view.findViewById(R.id.chat_map_dialog_list);
        RelativeLayout emptyLayout = view.findViewById(R.id.chat_map_dialog_empty_layout);
        chatMapDialogAdapter = new ChatMapDialogAdapter(context);
        TextView cancalText = view.findViewById(R.id.chat_map_dialog_cancal);
        refreshLayoutDialog = view.findViewById(R.id.chat_map_dialog_refreshLayout);
        refreshLayoutDialog.setEnableRefresh(false);
        refreshLayoutDialog.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        chatDialogList.setAdapter(chatMapDialogAdapter);
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        /**
         * 根据输入动态获取地点信息
         */
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    chatMapDialogAdapter.clear();
                } else {
                    dialogSearchText = charSequence.toString();
                    pageDialog = 1;
                    getDialogSearchData(charSequence.toString(), pageDialog);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialogLoadMore();
        dialogListListener();
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//设置横向全屏
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    private void dialogListListener() {
        chatDialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LatLng latLng = new LatLng(chatMapDialogAdapter.mList.get(i).getLatLonPoint().getLatitude(),
                        chatMapDialogAdapter.mList.get(i).getLatLonPoint().getLongitude());
                marker.setPosition(latLng);
                chatMapAdapter.refreshUI(i);
                isDrag = true;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, aMap.getCameraPosition().zoom));
                dialog.dismiss();
            }
        });
    }

    /**
     * 搜索结果上划加载
     */
    private void dialogLoadMore() {
        refreshLayoutDialog.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageDialog = pageDialog + 1;
                getDialogSearchData(dialogSearchText, pageDialog);
            }
        });
    }

    /**
     * 获取搜索结果
     * @param searchText 搜索地点
     * @param page 当前页面所在页
     */
    private void getDialogSearchData(String searchText, final int page) {
        PoiSearch.Query queryDialog = new PoiSearch.Query(searchText, "", city);
        queryDialog.setPageSize(pageCount);// 设置每页最多返回多少条poiitem
        queryDialog.setPageNum(page);//设置查询页码
        PoiSearch poiSearchDialog = new PoiSearch(this, queryDialog);
        poiSearchDialog.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int i) {
                if (result.getPois() != null && result.getPois().size() != 0) {
                    refreshLayoutDialog.finishLoadMore();
                    if (page != 1) {
                        chatMapDialogAdapter.addData(result.getPois());

                    } else {
                        chatMapDialogAdapter.getData(result.getPois());

                    }

                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearchDialog.searchPOIAsyn();
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
