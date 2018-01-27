package com.biaoyuan.transfer.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.AddAddressItem;
import com.biaoyuan.transfer.domain.CommitOrderEvent;
import com.biaoyuan.transfer.domain.QsettingInfo;
import com.biaoyuan.transfer.domain.TakeTime;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.WebViewActivity;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.view.NumberButton;
import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :
 * Create : 2017/4/24
 * Author ：chen
 */
public class SendFgt extends BaseFgt {

    //重量选择
    @Bind(R.id.nb_weight)
    NumberButton mNbWeight;
    //体积选择
    @Bind(R.id.nb_volume)
    NumberButton mNbVolume;

    @Bind(R.id.tv_file)
    TextView mTvFile;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_send)
    TextView mTvSend;
    @Bind(R.id.tv_send_name)
    TextView mTvSendName;
    @Bind(R.id.tv_send_phone)
    TextView mTvSendPhone;
    @Bind(R.id.tv_send_city)
    TextView mTvSendCity;
    @Bind(R.id.tv_send_street)
    TextView mTvSendStreet;
    @Bind(R.id.ll_send_data)
    LinearLayout mLlSendData;
    @Bind(R.id.tv_send_default)
    TextView mTvSendDefault;
    @Bind(R.id.tv_receive)
    TextView mTvReceive;
    @Bind(R.id.tv_recive_name)
    TextView mTvReciveName;
    @Bind(R.id.tv_recive_phone)
    TextView mTvRecivePhone;
    @Bind(R.id.tv_recive_city)
    TextView mTvReciveCity;
    @Bind(R.id.tv_recive_street)
    TextView mTvReciveStreet;
    @Bind(R.id.ll_recive_data)
    LinearLayout mLlReciveData;


    private OptionsPickerView mDialogBuilderFile;
    private OptionsPickerView mDialogBuilderTime;


    //发送地址
    private AddAddressItem sendAddress;

    //收件地址
    private AddAddressItem receiveAddress;


    //尺寸大小
    private int goodsSize;

    //体积
    private int goodsWeight;

    //类型
    private int goodsType;
    private String goodsTypeStr;

    //取件时间
    private long requiredTime;
    private String requiredTimeStr;

    //选中的类型集合
    private int selectPosition = 0;


    private ArrayList<TakeTime> opTime01 = new ArrayList<>();


    private ArrayList<List<String>> opTime02 = new ArrayList<>();
    private ArrayList<QsettingInfo> mOrderTypes;
    private double mMDistance;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_send_go;
    }

    @Override
    public void initData() {

        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(CommitOrderEvent orderEvent) {
        //提交订单成功清楚信息
        clear();
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        //获取类型
        doHttp(RetrofitUtils.createApi(Send.class).getOrderType(), 1);
    }

    @OnClick({R.id.ll_file, R.id.ll_time, R.id.tv_send_default, R.id.tv_receive_default,
            R.id.tv_send, R.id.tv_receive, R.id.textView, R.id.ll_send_data, R.id.ll_recive_data
            , R.id.tv_valuation})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        if (!UserManger.isLogin()) {
            startActivity(LoginAty.class, null);
            return;
        }


        switch (view.getId()) {
            case R.id.tv_send_default:
                Bundle b = new Bundle();
                b.putInt("type", 1);
                startActivityForResult(SendCommonAddressActivity.class, b, 1);
                break;
            case R.id.tv_valuation:
                Bundle bb = new Bundle();
                bb.putInt("type", WebViewActivity.TYPE_VALUATION);
                startActivity(WebViewActivity.class, bb);
                break;
            case R.id.tv_receive_default:
                Bundle b1 = new Bundle();
                b1.putInt("type", 2);
                startActivityForResult(SendCommonAddressActivity.class, b1, 2);
                break;
            case R.id.tv_send:
            case R.id.ll_send_data:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                if (sendAddress != null) {
                    bundle.putParcelable("data", sendAddress);
                }
                startActivityForResult(SendAddAddressAty.class, bundle, 1);
                break;
            case R.id.tv_receive:
            case R.id.ll_recive_data:
                Bundle bun = new Bundle();
                bun.putInt("type", 2);
                if (receiveAddress != null) {
                    bun.putParcelable("data", receiveAddress);
                }
                startActivityForResult(SendAddAddressAty.class, bun, 2);
                break;
            case R.id.ll_file:
                mDialogBuilderFile.show();
                break;
            case R.id.ll_time:
                initTime();
                initTimeDialog();
                break;
            case R.id.textView:
                if (sendAddress == null) {
                    showErrorToast("请填写寄件人信息");
                    return;
                }
                if (receiveAddress == null) {
                    showErrorToast("请填写收件人信息");
                    return;
                }

                if (requiredTime == 0) {
                    showErrorToast("请选择取件时间");
                    return;
                }

                //尺寸大小
                goodsSize = mOrderTypes.get(selectPosition).getSizeList().get(mNbVolume.getPosition());
                //重量
                goodsWeight = mOrderTypes.get(selectPosition).getWeightList().get(mNbWeight.getPosition());


                //计算价格
                LatLng s = new LatLng(sendAddress.getLat(), sendAddress.getLng());
                LatLng e = new LatLng(receiveAddress.getLat(), receiveAddress.getLng());

                mMDistance = AMapUtils.calculateLineDistance(s, e) / 1000;

                Logger.v("distance==" + mMDistance);
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Send.class).orderFee(goodsType, goodsSize, goodsWeight, mMDistance, sendAddress.getAreaId(), receiveAddress.getAreaId()), 2);


                break;
        }
    }

    /**
     * 重置数据
     */
    public void clear() {
        sendAddress = null;
        receiveAddress = null;

        mNbWeight.setData(mOrderTypes.get(0).getWeightStringList());
        mNbVolume.setData(mOrderTypes.get(0).getSizeStringList());

        //尺寸大小
        goodsSize = mOrderTypes.get(0).getSizeList().get(0);
        //重量
        goodsWeight = mOrderTypes.get(0).getWeightList().get(0);
        //类型
        goodsType = mOrderTypes.get(0).getQsettingParamValue();
        goodsTypeStr = mOrderTypes.get(0).getQsettingParamDesc();
        mTvFile.setText(mOrderTypes.get(0).getQsettingParamDesc());
        requiredTime = 0;
        if (mDialogBuilderFile != null) {
            mDialogBuilderFile.setPicker(mOrderTypes);
        }

        mTvReceive.setVisibility(View.VISIBLE);
        mLlReciveData.setVisibility(View.GONE);


        mTvSend.setVisibility(View.VISIBLE);
        mLlSendData.setVisibility(View.GONE);
        mTvTime.setText("");


    }


    /**
     * 计算时间
     */
    private void initTime() {


        opTime01.clear();
        opTime02.clear();

       /* 取件时间选择：

        今天	1个小时内 （这里需要判断如果是当前时间是17点30分，那么就只能选择明天了，如果是17点，那么可以选择1个小时。
        如果现在时间是8点前那么显示就是9点前，10点前...）
        2个小时内
        现在时间加3个小时，如现在时间是10点那么这里显示14点之前
        现在时间加4个小时，直到18点
        明天	9点前
        10点前
        ......
        18 点*/

        //得到现在的时间
        long nowTime = System.currentTimeMillis();


        //得到8:00的时间
        long time8_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "8:00:00");


        //得到16:00 的时间

        long time16_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "16:00:00");


        //得到17:00 的时间

        long time17_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "17:00:00");

        //今晚12点的时间
        long time0_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000 + "", "yyyy-MM-dd ") + "0:00:00");


        //今天8点之前显示
        if (nowTime < time8_00) {

            TakeTime today = new TakeTime();
            today.setType("今日");
            TakeTime moday = new TakeTime();
            moday.setType("明日");


            opTime01.add(today);
            opTime01.add(moday);


            //得到9:00的时间
            long time9_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "9:00:00");
            //显示今日9点前 10前 。。。。。18点前
            String lastTimestr = DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "18:00:00";
            long lastTime = DateTool.strTimeToTimestamp(lastTimestr);

            ArrayList<Long> longList = new ArrayList<>();

            while (time9_00 < lastTime) {
                longList.add(time9_00);
                //加1小时
                time9_00 += 60 * 60 * 1000L;
            }

            longList.add(lastTime);

            today.setLongTime(longList);

            ArrayList<String> toTimes = new ArrayList<>();
            for (int i = 0; i < longList.size(); i++) {
                toTimes.add(DateTool.timesToStrTime(longList.get(i) + "", "HH:mm") + "之前");
            }
            today.setTimes(toTimes);
            opTime02.add(toTimes);

            addModayTime(moday);


            // 今天8点到18点前
        } else if (nowTime >= time8_00 && nowTime <= time16_00) {

            TakeTime today = new TakeTime();
            today.setType("今日");
            TakeTime moday = new TakeTime();
            moday.setType("明日");


            opTime01.add(today);
            opTime01.add(moday);


            ArrayList<String> toTime = new ArrayList<>();
            ArrayList<Long> toLongTime = new ArrayList<>();

            toTime.add("1小时之内");
            toTime.add("2小时之内");


            toLongTime.add(System.currentTimeMillis() + 60 * 60 * 1 * 1000);
            toLongTime.add(System.currentTimeMillis() + 60 * 60 * 2 * 1000);

            //得到今天晚上18:00的时间

            String lastTimestr = DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ") + "18:00:00";
            long lastTime = DateTool.strTimeToTimestamp(lastTimestr);
            //得到现在的分钟
            int newMin = Integer.parseInt(DateTool.getNowMin());

            //如果小于30 -30   大于30 让他==00 小时加1小时
            long time = System.currentTimeMillis() + 60 * 60 * 2 * 1000;

            if (newMin < 30) {

            } else if (newMin >= 30) {
                //加1小时
                time += 60 * 60 * 1 * 1000;
            }


            String newTiemStr = DateTool.timesToStrTime(time + "", "yyyy-MM-dd HH:") + "00:00";

            //  Logger.v("newTime=="+newTiemStr);

            long newTime = DateTool.strTimeToTimestamp(newTiemStr);


            while (newTime < lastTime) {
                toLongTime.add(newTime);
                //加1小时
                newTime += 60 * 60 * 1000L;
            }
            toLongTime.add(lastTime);
            today.setLongTime(toLongTime);

            for (int i = 2; i < toLongTime.size(); i++) {
                toTime.add(DateTool.timesToStrTime(toLongTime.get(i) + "", "HH:mm") + "之前");
            }

            today.setTimes(toTime);
            opTime02.add(toTime);

            addModayTime(moday);


            //今天16点到17点
        } else if (nowTime > time16_00 && nowTime <= time17_00) {
            //显示一个小时之内


            TakeTime today = new TakeTime();
            today.setType("今日");
            TakeTime moday = new TakeTime();
            moday.setType("明日");


            opTime01.add(today);
            opTime01.add(moday);


            ArrayList<String> toTime = new ArrayList<>();
            ArrayList<Long> toLongTime = new ArrayList<>();
            toTime.add("1小时之内");
            toLongTime.add(System.currentTimeMillis() + 60 * 60 * 1 * 1000);


            today.setTimes(toTime);
            today.setLongTime(toLongTime);
            opTime02.add(toTime);

            addModayTime(moday);


            //今天17到0：00点
        } else if (nowTime > time17_00 && nowTime <= time0_00) {
            //0点之前显示明天9点前10点前
            TakeTime moday = new TakeTime();
            moday.setType("明日");
            opTime01.add(moday);
            addModayTime(moday);

        }


    }

    /**
     * 加入明天的时间
     *
     * @param takeTime
     */
    private void addModayTime(TakeTime takeTime) {


        //明天的18 9点
        long time18_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000 + "", "yyyy-MM-dd ") + "18:00:00");
        long time9_00 = DateTool.strTimeToTimestamp(DateTool.timesToStrTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000 + "", "yyyy-MM-dd ") + "9:00:00");


        ArrayList<Long> longList = new ArrayList<>();

        while (time9_00 < time18_00) {
            longList.add(time9_00);
            //加1小时
            time9_00 += 60 * 60 * 1000L;
        }

        longList.add(time18_00);

        takeTime.setLongTime(longList);
        ArrayList<String> moTimes = new ArrayList<>();
        for (int i = 0; i < longList.size(); i++) {
            moTimes.add(DateTool.timesToStrTime(longList.get(i) + "", "HH:mm") + "之前");
        }
        takeTime.setTimes(moTimes);
        opTime02.add(moTimes);


    }

    /**
     * 快件类型
     */
    public void initFileDialog() {


        mTvFile.setText(mOrderTypes.get(0).getQsettingParamDesc());

        mDialogBuilderFile = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                goodsTypeStr = mOrderTypes.get(options1).getQsettingParamDesc();
                goodsType = mOrderTypes.get(options1).getQsettingParamValue();
                //尺寸大小
                goodsSize = mOrderTypes.get(options1).getSizeList().get(0);
                //重量
                goodsWeight = mOrderTypes.get(options1).getWeightList().get(0);

                selectPosition = options1;
                String tx = mOrderTypes.get(options1).getQsettingParamDesc();
                mTvFile.setText(tx);
                mNbWeight.setData(mOrderTypes.get(options1).getWeightStringList());
                mNbVolume.setData(mOrderTypes.get(options1).getSizeStringList());
            }
        }
        )
                .setTitleText("选择物品类型")
                .setContentTextSize(14)

                .setOutSideCancelable(true)// default is true

                .build();

        mDialogBuilderFile.setPicker(mOrderTypes);
    }

    /**
     * 选择时间
     */
    public void initTimeDialog() {

        if (mDialogBuilderTime == null) {
            mDialogBuilderTime = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {

                    requiredTime = opTime01.get(options1).getLongTime().get(options2);
                    requiredTimeStr = opTime01.get(options1).getType() + opTime01.get(options1).getTimes().get(options2);
                    mTvTime.setText(requiredTimeStr);
                }
            })
                    .setTitleText("选择上门取件时间")
                    .setContentTextSize(14)
                    .setOutSideCancelable(true)// default is true
                    .build();

        }

        mDialogBuilderTime.setPicker(opTime01, opTime02);
        mDialogBuilderTime.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {

            switch (requestCode) {
                case 2:
                    //收件
                    receiveAddress = data.getParcelableExtra("data");
                    if (mLlReciveData.getVisibility() == View.GONE) {
                        mTvReceive.setVisibility(View.GONE);
                        mLlReciveData.setVisibility(View.VISIBLE);
                    }
                    mTvReciveName.setText(receiveAddress.getName());
                    mTvRecivePhone.setText(receiveAddress.getPhone() + "");

                    String add[] = receiveAddress.getAddress().split("\\|");
                    mTvReciveCity.setText(add[0] + add[1] + add[2]);
                    if (add.length == 6) {
                        mTvReciveStreet.setText(add[3] + add[4] + add[5]);
                    } else {
                        mTvReciveStreet.setText(add[3] + add[4]);
                    }


                    break;
                case 1:
                    //发件
                    sendAddress = data.getParcelableExtra("data");

                    if (mLlSendData.getVisibility() == View.GONE) {
                        mTvSend.setVisibility(View.GONE);
                        mLlSendData.setVisibility(View.VISIBLE);
                    }
                    mTvSendName.setText(sendAddress.getName());
                    mTvSendPhone.setText(sendAddress.getPhone() + "");


                    String address[] = sendAddress.getAddress().split("\\|");
                    mTvSendCity.setText(address[0] + address[1] + address[2]);
                    if (address.length == 6) {
                        mTvSendStreet.setText(address[3] + address[4] + address[5]);

                    } else {
                        mTvSendStreet.setText(address[3] + address[4]);
                    }

                    break;
            }


        }

    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                //获取类型并处理
                List<QsettingInfo> qsettingInfos = AppJsonUtil.getArrayList(result, QsettingInfo.class);

                //1.先得到order_type的数据
                mOrderTypes = new ArrayList<>();

                for (QsettingInfo qsettingInfo : qsettingInfos) {

                    if (qsettingInfo.getQsettingParameter().equals("orderType")) {

                        mOrderTypes.add(qsettingInfo);
                    }
                }

                //1.得到对应的重量和尺寸限制

                for (QsettingInfo mOrderType : mOrderTypes) {

                    for (QsettingInfo qsettingInfo : qsettingInfos) {

                        if (qsettingInfo.getQsettingParamFathernode() == mOrderType.getQsettingParamFathernode()) {

                            //得到尺寸size
                            if (qsettingInfo.getQsettingParameter().equals("sizeLimit")) {
                                mOrderType.setSizeMax(qsettingInfo.getQsettingParamValue());
                            }
                            if (qsettingInfo.getQsettingParameter().equals("standardSize")) {
                                mOrderType.setSizeMin(qsettingInfo.getQsettingParamValue());
                            }

                            if (qsettingInfo.getQsettingParameter().equals("overSize")) {
                                mOrderType.setSizeStep(qsettingInfo.getQsettingParamValue());
                            }

                            //得到weight
                            if (qsettingInfo.getQsettingParameter().equals("weightLimit")) {
                                mOrderType.setWeightMax(qsettingInfo.getQsettingParamValue());
                            }
                            if (qsettingInfo.getQsettingParameter().equals("standardWeight")) {
                                mOrderType.setWeightMin(qsettingInfo.getQsettingParamValue());
                            }

                            if (qsettingInfo.getQsettingParameter().equals("overWeight")) {
                                mOrderType.setWeightStep(qsettingInfo.getQsettingParamValue());
                            }

                        }

                    }

                }
                //设置他们相应的范围

                for (QsettingInfo mOrderType : mOrderTypes) {

                    ArrayList<Integer> sizeList = new ArrayList<>();
                    ArrayList<String> sizeStrinList = new ArrayList<>();

                    int size = mOrderType.getSizeMin();

                    while (size <= mOrderType.getSizeMax()) {
                        sizeList.add(size);
                        sizeStrinList.add(size + "cm之内");
                        size += mOrderType.getSizeStep();
                    }


                    ArrayList<Integer> weightList = new ArrayList<>();
                    ArrayList<String> weightStrinList = new ArrayList<>();
                    int weight = mOrderType.getWeightMin();

                    while (weight <= mOrderType.getWeightMax()) {
                        weightList.add(weight);
                        weightStrinList.add(weight + "kg");
                        weight += mOrderType.getWeightStep();
                    }

                    mOrderType.setWeightList(weightList);
                    mOrderType.setSizeList(sizeList);
                    mOrderType.setSizeStringList(sizeStrinList);
                    mOrderType.setWeightStringList(weightStrinList);
                }

                //设置默认值
                initFileDialog();
                mNbWeight.setData(mOrderTypes.get(0).getWeightStringList());
                mNbVolume.setData(mOrderTypes.get(0).getSizeStringList());

                //尺寸大小
                goodsSize = mOrderTypes.get(0).getSizeList().get(0);
                //重量
                goodsWeight = mOrderTypes.get(0).getWeightList().get(0);
                //类型
                goodsType = mOrderTypes.get(0).getQsettingParamValue();
                goodsTypeStr = mOrderTypes.get(0).getQsettingParamDesc();
                break;
            case 2:

                double mPrice = AppJsonUtil.getDouble(AppJsonUtil.getString(result, "data"), "price");

                Bundle bbb = new Bundle();

                bbb.putParcelable("sendAddress", sendAddress);
                bbb.putParcelable("receiveAddress", receiveAddress);
                bbb.putInt("type", goodsType);
                bbb.putInt("size", goodsSize);
                bbb.putInt("weight", goodsWeight);
                bbb.putLong("longTime", requiredTime);
                bbb.putString("strTime", requiredTimeStr);
                bbb.putString("typeStr", goodsTypeStr);
                bbb.putDouble("distance", mMDistance);
                bbb.putDouble("price", mPrice);
                startActivity(SendSubmitOrderActivity.class, bbb);
                break;


        }
    }
}
