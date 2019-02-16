package com.sinostar.assistant.ui.mobile.agree;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.ApproveAgreeModel;
import com.sinostar.assistant.R;

import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.sinostar.assistant.base.BaseActivity.setOnEdittFoucusChange;

/**
 * 审批通过模版Adapter
 */
public class ApproveAgreeAdapter extends BaseAdapter {
    Context context;

    private ApproveAgreeModel data;  //审批通过模版数据
    private List<ApproveAgreeModel.MDDefineBean.GroupsBean.ColumnsBean> mList = new ArrayList<>();
    private RadioGroupViewHolder radioGroupViewHolder;
    private SpinnerViewHolder spinnerViewHolder;
    private Gson gson;
    private Map<String, String> dataMap = new HashMap<String, String>();    //数据集合
    private Map<String, Integer> radioItemMap = new HashMap<String, Integer>();    //radioGroup 名称跟位置的集合
    private onSpinnerChangeListener onSpinnerChangeListener;
    private int mTouchItemPosition = -1; //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
    private int index=-1;
    private String agreeType;

    ApproveAgreeAdapter(Context context) {
        this.context = context;
    }

    public void getData(List<ApproveAgreeModel.MDDefineBean.GroupsBean.ColumnsBean> list, ApproveAgreeModel data, String inputDataString,boolean isReport) {
        mList.clear();
        mList.addAll(list);
        this.data = data;
        gson = new GsonBuilder().disableHtmlEscaping().create();

        /**
         * 通过“，”和“：”进行字符分割，将动态数据通过 key :value 的格式存进Map数组
         */
        String[] bb = inputDataString.split(",");//将所有,符号截取出来变成一个数组
        String cc = null;//获取每个,之内的内容
        String[] dd = null;//获取每个:号的内容
        String key = null;
        String value = null;

        for (int j = 0; j < bb.length; j++) {
            cc = bb[j];//获取每个,的内容
            dd = cc.split(":");//拆分:号
            key = dd[0].substring(1, dd[0].length() - 1);//=号前面的值
            value = dd[1].substring(1, dd[1].length() - 1);//=号后面的值
            dataMap.put(key, value);//将值放入map中
        }
        if(isReport){
            dataMap.put("CustomData", "BSJ");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;

        /***
         * 根据type判断表单样式
         * type=0:editText显示（单行、多行、时间、数字）
         * type=1:spinner
         * type=2:radioGroup
         */

        switch (mList.get(position).getColumnType()) {
            case "VARCHAR":
                type = 0;
                break;
            case "多行文本":
                type = 0;
                break;
            case "DATE":
                type = 0;
                break;
            case "目标单位":
                type = 1;
                break;
            case "指派人员":
                type = 2;
                break;
            case "目标人员":
                type = 2;
                break;
            case "NUMBER":
                type = 0;
                break;
        }

        return type;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        switch (getItemViewType(i)) {
            /**
             *  editText显示
             */
            case 0:
                final EdittextViewHolder edittextViewHolder;
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.approve_agree_model_editext, null);
                    edittextViewHolder = new EdittextViewHolder(view);
                    view.setTag(edittextViewHolder);
                } else {
                    edittextViewHolder = (EdittextViewHolder) view.getTag();
                }
               final EditText editText= edittextViewHolder.approveAgreeModleEditextEdit;

                //文本框标题
                edittextViewHolder.approveAgreeModelEditextText.setText(mList.get(i).getDisplayName());

                //文本框高度
                if (mList.get(i).getLineHeight() == 1) {
                   editText.setSingleLine();
                } else {
                    editText.setMaxLines(mList.get(i).getLineHeight());
                    editText.setMinLines(mList.get(i).getLineHeight());
                }

                //文本框是否只读
                if (mList.get(i).isReadOnly()) {
                    editText.setFocusable(false);
                    editText.setTextColor(context.getResources().
                            getColor(R.color.grey));
                    //判断返回类型是否为DATE，是的话直接获取本地时间
                    if (mList.get(i).getColumnType().equals("DATE")) {
                        String time = DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
                        editText.setText(time);
                        dataMap.put(mList.get(i).getColumnName(), time);
                    } else {
                        editText.setText(dataMap.get(mList.get(i).getColumnName()));
                    }
                } else {
                    editText.setFocusable(true);
                    editText.setTextColor(context.getResources().
                            getColor(R.color.homeTitleBar));
                    editText.setText(dataMap.get(mList.get(i).getColumnName()));
                }



                //点击edittext时获取焦点，让删除按钮获取焦点
                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction()==ACTION_UP){
                            editText.requestFocus();
                            editText.requestFocusFromTouch();
                            editText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    dataMap.put(mList.get(i).getColumnName(), editable.toString());
                                }
                            });
                        }

                        return false;
                    }
                });




                break;

            /**
             * spinner显示
             */
            case 1:
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.approve_agree_modle_spinner, null);
                    spinnerViewHolder = new SpinnerViewHolder(view);
                    view.setTag(spinnerViewHolder);
                } else {
                    spinnerViewHolder = (SpinnerViewHolder) view.getTag();
                }
                //设置spinner框的标题
                spinnerViewHolder.approveAgreeModelSpinnerText.setText(mList.get(i).getDisplayName());
                List<String> list = new ArrayList<>();
                for (int j = 0; j < data.getMB_COM().size(); j++) {
                    list.add(data.getMB_COM().get(j).getTargetUnitName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
                spinnerViewHolder.approveAgreeModelSpinner.setAdapter(adapter);

                //默认选中spinner的第一个，并将数据写入数据集合
                dataMap.put(mList.get(i).getColumnName(), data.getMB_COM().get(0).getTargetUnitId());

                //Spinner点击事件
                initListener(i);
                break;

            /**
             * radioGroup显示
             */
            case 2:
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.approve_agree_model_radiogroup, null);
                    radioGroupViewHolder = new RadioGroupViewHolder(view);
                    view.setTag(radioGroupViewHolder);
                } else {
                    radioGroupViewHolder = (RadioGroupViewHolder) view.getTag();
                }
                String radioItemName = mList.get(i).getColumnName();
                radioItemMap.put(radioItemName, i);
                radioGroupViewHolder.approveAgreeModelRadiogroupText.setText(mList.get(i).getDisplayName());
                setRadioButton(radioItemName, data.getRY_MB_COM(), data.getRY_MB_COM().size());
                break;
        }
        return view;
    }

    /**
     * spinner点击事件
     *
     * @param position 对应spinner在list的位置
     */
    private void initListener(final int position) {
        String RY_MB_COM = null;
        String s=mList.get(position).getEditFormat();
        if(s.length()!=0){
            agreeType=s.substring(s.indexOf(",")+1,s.length());
             RY_MB_COM = s.substring(0, s.indexOf(","));
        }
        final String finalRY_MB_COM = RY_MB_COM;
        final String finalRY_MB_COM1 = RY_MB_COM;
        spinnerViewHolder.approveAgreeModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String MB_COM = data.getMB_COM().get(i).getTargetUnitId();
                dataMap.put(mList.get(position).getColumnName(), MB_COM);
                ObserverOnNextListener listener = new ObserverOnNextListener<List<ApproveAgreeModel.RYMBCOMBean>>() {
                    @Override
                    public void onNext(List<ApproveAgreeModel.RYMBCOMBean> result) {
                        if(result!=null&&result.size()!=0){
                            data.getRY_MB_COM().clear();
                            data.getRY_MB_COM().addAll(result);
                            dataMap.put(finalRY_MB_COM, result.get(0).getKey());
                            if (!finalRY_MB_COM.equals("") && radioItemMap.size() != 0) {
                                //通过接口将spinner点击事件返回的结果发送到Activity，Activity再对spinner所对应的radioGroup进行局部刷新
                                onSpinnerChangeListener.onRadioResponce(finalRY_MB_COM,
                                        radioItemMap.get(finalRY_MB_COM1));
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                };

                if(agreeType!=null){
                switch (agreeType){
                    case "BA"://办案人员
                        NetMethods.getApproAgreeModelHandlerList(new MyObserver<List<ApproveAgreeModel.RYMBCOMBean>>(context, listener),
                                data.getMB_COM().get(i).getTargetUnitId());
                        break;
                    case "BSJ"://报上级人员
                        NetMethods.getSuperiorApprovalUserList(new MyObserver<List<ApproveAgreeModel.RYMBCOMBean>>(context,listener),
                                data.getMB_COM().get(i).getTargetUnitId());
                        break;
                }

            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    /**
     * 动态添加radioButton
     *
     * @param radioItemName radioGroup对应的Name
     * @param list          radiobutton数据集合
     * @param num           radioButton数量
     */
    private void setRadioButton(final String radioItemName, final List<ApproveAgreeModel.RYMBCOMBean> list, int num) {
        radioGroupViewHolder.approveAgreeModelRadiogroup.removeAllViews();
        for (int i = 0; i < num; i++) {
            final RadioButton tempButton = new RadioButton(context);
//                    tempButton.setBackgroundResource(R.drawable.xxx);	// 设置RadioButton的背景图片
//                    tempButton.setButtonDrawable(R.drawable.radiobutton_image);// 设置按钮的样式
            tempButton.setPadding(10, 10, 10, 10);                // 设置文字距离按钮四周的距离
            tempButton.setText(list.get(i).getValue());
            tempButton.setTextSize(14);
            tempButton.setTextColor(context.getResources().getColor(R.color.grey));
            tempButton.setTag(i);

            radioGroupViewHolder.approveAgreeModelRadiogroup.addView(tempButton, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            //默认选中radiobutton第一个
            if (i == 0) {
                radioGroupViewHolder.approveAgreeModelRadiogroup.check(tempButton.getId());
            }

            //radioButton的点击事件
            tempButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        String s = list.get((Integer) tempButton.getTag()).getKey();
                        dataMap.put(radioItemName, s);
                    }
                }
            });
        }
    }

    /**
     * 根据Spinner返回数据局部刷新radioGroup
     *
     * @param listView 目标listview
     * @param position item在listview中的位置
     */
    public void refreshRadio(ListView listView, int position) {
        /**第一个可见的位置**/

        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }


    /**
     * 直接获取全部数据
     *
     * @return 数据集合
     */
    public String getAgreeData() {

        return gson.toJson(dataMap);
    }


    /**
     * spinner点击事件监听接口
     *
     * @param onSpinnerChangeListener spinner点击事件监听接口
     */
    public void setOnSpinnerChangeListener(onSpinnerChangeListener onSpinnerChangeListener) {
        this.onSpinnerChangeListener = onSpinnerChangeListener;
    }


    public interface onSpinnerChangeListener {
        /**
         * 监听Spinner点击事件
         *
         * @param name     spinner所对应的radioGroup的名称
         * @param position spinner对应的radioGroup在list中的位置
         */
        void onRadioResponce(String name, int position);
    }


    class EdittextViewHolder {
        @BindView(R.id.approve_agree_model_editext_text)
        TextView approveAgreeModelEditextText;
        @BindView(R.id.approve_agree_modle_editext_edit)
        EditText approveAgreeModleEditextEdit;



        EdittextViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class SpinnerViewHolder {
        @BindView(R.id.approve_agree_model_spinner_text)
        TextView approveAgreeModelSpinnerText;
        @BindView(R.id.approve_agree_model_spinner)
        Spinner approveAgreeModelSpinner;

        SpinnerViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class RadioGroupViewHolder {
        @BindView(R.id.approve_agree_model_radiogroup_text)
        TextView approveAgreeModelRadiogroupText;
        @BindView(R.id.approve_agree_model_radiogroup)
        RadioGroup approveAgreeModelRadiogroup;
        RadioGroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
