package com.sinostar.assistant.ui.research.image;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.image.ImageCheck;
import com.sinostar.assistant.net.NetMethods;

import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.OnMultiClickListener;
import com.sinostar.assistant.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;

public class ImageSelect extends AppCompatActivity {


    @BindView(R.id.image_list)
    RecyclerView imageList;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_rightText)
    TextView titleBarRightText;
    @BindView(R.id.image_delete_image)
    ImageView imageDeleteImage;
    @BindView(R.id.image_delete_text)
    TextView imageDeleteText;
    @BindView(R.id.image_delete_layout)
    LinearLayout imageDeleteLayout;
    ImageSelectAdapter imageAdapter;
    Context context;

    private ItemTouchHelper itemTouchHelper;
    private int RESULT_CODE_PHOTO = 100;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private String caseId;
    private Dialog mapDialog;
    private int RESULT_CODE_CAMERA = 101;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private Map<String, RequestBody> map;
    private QMUITipDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        ButterKnife.bind(this);
        context = ImageSelect.this;
        Intent intent = getIntent();
        caseId = intent.getStringExtra("caseId");
        imageList.setLayoutManager(new GridLayoutManager(context, 3));
        imageAdapter = new ImageSelectAdapter(context);
        imageList.setAdapter(imageAdapter);
        init();
        initListener();


    }

    private void initListener() {

        /**
         * 根据点击位置选择浏览照片或者是打开相册
         */
        imageList.addOnItemTouchListener(new OnRecyclerItemClickListener(imageList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder) {

                if (holder.getLayoutPosition() != images.size()) {
                    imageUrl.clear();
                    for (int i = 0; i < images.size(); i++) {
                        String s = "file://" + images.get(i).path;
                        imageUrl.add(s);
                    }
                    Intent intent = new Intent(context, ImageCheck.class);
                    intent.putExtra("imagePosition", holder.getLayoutPosition() + "");
                    intent.putStringArrayListExtra("imageUrl", (ArrayList<String>) imageUrl);
                    startActivity(intent);
                } else {//打开相机或者相册
                    showImageDialog();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder holder) {
//                    如果item不是最后一个，则执行拖拽
                if (holder.getLayoutPosition() != images.size()) {
                    itemTouchHelper.startDrag(holder);
                }
            }
        });
        titleBarRightText.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onClick1(View v) {
                map = new LinkedHashMap<>();
                List<String> list = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    list.add("file://" + images.get(i).path);
                }
                compressImage(list);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //返回结果是相机拍摄的
        if (data != null && requestCode == RESULT_CODE_CAMERA) {
            ArrayList<ImageItem> list = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (list != null) {
                images.addAll(list);
                initList();
                imageAdapter.getImage(images);
            }

        }
        //返回结果是相册回调的
        if (data != null && requestCode == RESULT_CODE_PHOTO) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            initList();
            imageAdapter.getImage(images);
        }

    }

    /**
     * 根据拖拽情况判断底部框的显示与否
     */
    private void initList() {
        MyCallBack myCallBack = new MyCallBack(imageAdapter, images);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(imageList);

        myCallBack.setDragListener(new MyCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    imageDeleteText.setText(getString(R.string.image_delete2));
                    imageDeleteImage.setBackgroundResource(R.drawable.ic_delete_forever_white_36dp);
                } else {
                    imageDeleteText.setText(getString(R.string.image_delet1));
                    imageDeleteImage.setBackgroundResource(R.drawable.ic_delete_white_36dp);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    imageDeleteLayout.setVisibility(View.VISIBLE);
                } else {
                    imageDeleteLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {

            }
        });
//


    }

    private void init() {
//        titleBarText.setText(caseId);
        titleBarRightText.setText("上传");
    }

    public void showImageDialog() {
        mapDialog = new Dialog(context, R.style.MapDialog);
        mapDialog.setCanceledOnTouchOutside(true);
        mapDialog.setCancelable(true);

        Window window = mapDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.MapDialog);
        View view = View.inflate(context, R.layout.image_dialog, null);
        view.findViewById(R.id.image_dialog_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, RESULT_CODE_CAMERA);
                mapDialog.dismiss();
            }
        });
        view.findViewById(R.id.image_dialog_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //直接打开相册
                Intent intent = new Intent(context, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intent, RESULT_CODE_PHOTO);
                mapDialog.dismiss();
            }
        });
        ;
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mapDialog.show();
    }

    /**
     * 上传图片
     *
     * @param map
     */
    private void uploadImage(Map<String, RequestBody> map) {
        ObserverOnNextListener listener = new ObserverOnNextListener<Boolean>() {
            @Override
            public void onNext(Boolean result) {
                if (result) {
                    ToastUtil.toast("上传成功");
                    dialog.dismiss();
                    finish();
                } else {
                    ToastUtil.showToast(context, "上传失败");
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };

        NetMethods.getUploadImage(new MyObserver<Object>(context, listener), ApplicationUtil.getUserId(), caseId, map);

    }

    /**
     * 压缩图片
     *
     * @param photos
     */
    private void compressImage(final List<String> photos) {

        Luban.with(this)
                .load(photos)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        showDialog();
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        StringBuffer path = new StringBuffer(file.getPath());
                        path.insert(6, "//");
                        map.put(getFileNameNoEx(file.getName()),  //上传图片的名称
                                RequestBody.create(MediaType.parse("multipart/form-data"), path.toString()));  //上传的图片
                        uploadImage(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    private void showDialog() {
       dialog= new QMUITipDialog.Builder(context)
                .setTipWord("上传中")
                .setIconType(ICON_TYPE_LOADING)
                .create();
       dialog.show();


    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     * */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;

        }
    }


}
