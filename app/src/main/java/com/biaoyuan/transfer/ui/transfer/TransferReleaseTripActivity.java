package com.biaoyuan.transfer.ui.transfer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.ChooseAddress;
import com.biaoyuan.transfer.domain.City;
import com.biaoyuan.transfer.domain.District;
import com.biaoyuan.transfer.domain.Province;
import com.biaoyuan.transfer.domain.SelectTime;
import com.biaoyuan.transfer.domain.Street;
import com.biaoyuan.transfer.http.Transfer;
import com.biaoyuan.transfer.ui.send.ChooseMapAddressAty;
import com.biaoyuan.transfer.util.ChooseAddresDataHelper;
import com.biaoyuan.transfer.util.MyLocationUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 发布行程页面
 * Create : 2017/4/26
 * Author ：enmaoFu
 */
public class TransferReleaseTripActivity extends BaseAty implements GeocodeSearch.OnGeocodeSearchListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    @Bind(R.id.tv_time)
    TextView mTvTime;

    @Bind(R.id.tv_start)
    TextView mTvStart;
    @Bind(R.id.tv_end)
    TextView mTvEnd;
    @Bind(R.id.tv_start_address)
    TextView mTvStartAddress;
    @Bind(R.id.tv_end_address)
    TextView mTvEndAddress;


    private OptionsPickerView mDialogBuilderTime;

    //日期 yyyy.MM.dd
    private String dateString = null;
    private String timeString = null;

    //0代表出发 1代表到达
    private int chooseType = 0;

    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<City>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<District>>> options3Items = new ArrayList<>();

    private ArrayList<Street> startStreets = new ArrayList<>();
    private ArrayList<Street> endStreets = new ArrayList<>();


    //区域编码
    private int startDistrictCode;
    private int endDistrictCode;

    //城市名
    private String startCityName;
    private String endCityName;

    //区域名
    private String startDistrictName;
    private String endDistrictName;

    //详情名
    private String startDetailName;
    private String endDetailName;


    //街道编码
    private int startStreetCode;
    private int endStreetCode;


    private OptionsPickerView mPvOptionsCity;


    private GeocodeSearch mGeocodeSearch;

    private String locationStreet = "";
    private String locationAddress = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_release_trip;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "发布行程");
        mGeocodeSearch = new GeocodeSearch(this);
        mGeocodeSearch.setOnGeocodeSearchListener(this);

    }


    private void showCityDialog() {

        if (mPvOptionsCity == null) {


            if (ChooseAddresDataHelper.isLoad) {
                options1Items = ChooseAddresDataHelper.options1Items;
                options2Items = ChooseAddresDataHelper.options2Items;
                options3Items = ChooseAddresDataHelper.options3Items;
                if (options1Items.isEmpty() || options2Items.isEmpty() || options3Items.isEmpty()) {
                    showErrorToast("数据未读取成功请开启读写权限");
                    return;
                }
                mPvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText() +
                                options2Items.get(options1).get(options2).getPickerViewText() +
                                options3Items.get(options1).get(options2).get(options3).getPickerViewText();


                        if (chooseType == 0) {
                            if (startDistrictName == null || !startDistrictName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {
                                //出发
                                mTvStart.setText(tx);
                                mTvStart.setTextColor(getResources().getColor(R.color.font_black666));

                                startCityName = options2Items.get(options1).get(options2).getPickerViewText();
                                startDistrictName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                startDistrictCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();

                                startStreets = ChooseAddresDataHelper.getStreet(startDistrictCode);
                                mTvStartAddress.setText(getResources().getString(R.string.go_start_tip));

                                startDetailName = getResources().getString(R.string.go_start_tip);
                                mTvStartAddress.setTextColor(getResources().getColor(R.color.font_gray));

                                startStreetCode = 0;
                            }
                        } else {
                            if (endDistrictName == null || !endDistrictName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {

                                //到达
                                mTvEnd.setText(tx);
                                mTvEnd.setTextColor(getResources().getColor(R.color.font_black666));

                                endCityName = options2Items.get(options1).get(options2).getPickerViewText();
                                endDistrictName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                endDistrictCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();


                                endStreets = ChooseAddresDataHelper.getStreet(endDistrictCode);

                                endDetailName = getResources().getString(R.string.go_end_tip);
                                mTvEndAddress.setText(getResources().getString(R.string.go_end_tip));
                                mTvEndAddress.setTextColor(getResources().getColor(R.color.font_gray));

                                endStreetCode = 0;
                            }
                        }


                    }
                })

                        .setTitleText("城市选择")
                        .setContentTextSize(14)
                        .setOutSideCancelable(true)// default is true
                        .build();
                setSelectCity(mPvOptionsCity);
                mPvOptionsCity.setPicker(options1Items, options2Items, options3Items);//三级选择器
            } else {
                showToast("正在加载地址...");
                return;
            }


        }


        mPvOptionsCity.show();
    }

    /**
     * 通过定位默认选中
     *
     * @param pvOptionsCity
     */
    private void setSelectCity(OptionsPickerView pvOptionsCity) {
        try {
            String address[] = UserManger.getAddress().split("_");
            String province = address[0].substring(0, 2);
            String city = address[1].substring(0, 2);
            String country = address[2].substring(0, 2);

            Logger.v("country==" + country);
            int op1 = 0;
            int op2 = 0;
            int op3 = 0;

            for (int i = 0; i < options1Items.size(); i++) {
                Province options1Item = options1Items.get(i);
                if (options1Item.getAreaName().substring(0, 2).equals(province)) {
                    op1 = i;
                    break;
                }
            }

            for (int i = 0; i < options2Items.get(op1).size(); i++) {

                City city1 = options2Items.get(op1).get(i);

                if (city1.getAreaName().substring(0, 2).equals(city)) {
                    op2 = i;
                    break;
                }
            }


            for (int i = 0; i < options3Items.get(op1).get(op2).size(); i++) {
                District district = options3Items.get(op1).get(op2).get(i);
                if (district.getAreaName().substring(0, 2).equals(country)) {
                    op3 = i;
                    break;
                }
            }
            pvOptionsCity.setSelectOptions(op1, op2, op3);
        } catch (Exception e) {


        }

    }

    private void showDate() {
        Calendar calendar = Calendar.getInstance();
        TimePickerView mPickerViewDate = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                dateString = DateTool.dateToStr(date, "yyyy.MM.dd");
                mTvDate.setText(dateString);
                mTvDate.setTextColor(getResources().getColor(R.color.font_black666));
                initTime();
                timeString = getNumber(hours.get(0).getHour()) + ":" + getNumber(allmins.get(0).get(0));
                mTvTime.setText(timeString);
                mTvTime.setTextColor(getResources().getColor(R.color.font_black666));
            }
        }).setRangDate(calendar, null).setType(new boolean[]{true, true, true, false, false, false}).setTitleText("选择出发日期").
                setContentSize(14).
                setOffSize(30).
                build();
        mPickerViewDate.show();
    }

    /**
     * 得到字符串里面的数字
     *
     * @param a
     * @return
     */
    private String getNumber(String a) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);

        return m.replaceAll("").trim();
    }

    @OnClick({R.id.ll_date, R.id.ll_time, R.id.ll_start, R.id.ll_end, R.id.tv_start_address, R.id.tv_end_address
            , R.id.tv_commit, R.id.tv_location
    })
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_location:

                //定位当前
                opCheckPermission(200);


                break;
            case R.id.tv_start_address:

                if (startDistrictCode == 0) {
                    showErrorToast("请选择出发城市");
                    return;
                }
                Bundle bundle = new Bundle();
                startDetailName = mTvStartAddress.getText().toString();
                if (!TextUtils.isEmpty(startDetailName) && !startDetailName.equals(getResources().getString(R.string.go_start_tip))) {
                    bundle.putString("address", startDetailName);
                }
                bundle.putString("city", startCityName);
                bundle.putString("district", startDistrictName);
                startActivityForResult(ChooseMapAddressAty.class, bundle, 1);


                break;
            case R.id.tv_end_address:

                if (endDistrictCode == 0) {
                    showErrorToast("请选择到达城市");
                    return;
                }
                Bundle bun = new Bundle();
                endDetailName = mTvEndAddress.getText().toString();
                if (!TextUtils.isEmpty(endDetailName) && !endDetailName.equals(getResources().getString(R.string.go_end_tip))) {
                    bun.putString("address", endDetailName);
                }
                bun.putString("city", endCityName);
                bun.putString("district", endDistrictName);
                startActivityForResult(ChooseMapAddressAty.class, bun, 2);


                break;
            case R.id.ll_start:
                chooseType = 0;
                showCityDialog();
                break;
            case R.id.ll_end:
                chooseType = 1;
                showCityDialog();
                break;
            case R.id.ll_date:
                showDate();
                break;
            case R.id.ll_time:
                if (dateString == null) {
                    showErrorToast("请先选择日期");
                    return;
                }
                initTime();
                initTimeDialog();
                break;
            case R.id.tv_commit:
                if (dateString == null) {
                    showErrorToast("请选择日期");
                    return;
                }
                if (timeString == null) {
                    showErrorToast("请选择时间");
                    return;
                }
                long time = DateTool.strTimeToTimestamp(dateString + " " + timeString, "yyyy.MM.dd HH:mm");
                if (System.currentTimeMillis() > time) {
                    showErrorToast("时间已过期,请重选");
                    initTime();
                    initTimeDialog();
                    return;

                }


                if (startDistrictCode == 0) {
                    showErrorToast("请选择出发城市");
                    return;
                }
                if (endDistrictCode == 0) {
                    showErrorToast("请选择到达城市");
                    return;
                }

                if (startStreetCode == 0) {
                    startStreetCode = startDistrictCode;
                }
                if (endStreetCode == 0) {
                    endStreetCode = endDistrictCode;
                }


                showLoadingDialog(null);

                doHttp(RetrofitUtils.createApi(Transfer.class).addPath(time + "", startStreetCode + "", endStreetCode + ""), 1);

                //
                break;

        }

    }


    // 权限
    public void opCheckPermission(int requestcode) {


        showLoadingDialog(null);
        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(requestcode)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION
                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(TransferReleaseTripActivity.this, rationale).show();
                               }
                           }
                )
                .send();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            if (requestCode == 200) {


                MyLocationUtil locationUtil = new MyLocationUtil(TransferReleaseTripActivity.this, new MyLocationUtil.MyLocationListener() {
                    @Override
                    public void sussessLocation(double lat, double lon, final AMapLocation location) {

                        if (location != null) {
                            UserManger.setAddress(location.getProvince() + "_" + location.getCity() + "_" + location.getDistrict());

                            locationStreet = location.getStreet() + location.getStreetNum();

                            if (!TextUtils.isEmpty(location.getAoiName())) {
                                locationAddress = location.getAoiName();
                            } else {
                                locationAddress = location.getPoiName();
                            }


                            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                            LatLonPoint point = new LatLonPoint(lat, lon);
                            RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);

                            mGeocodeSearch.getFromLocationAsyn(query);


                        } else {
                            dismissLoadingDialog();
                            showErrorToast("定位失败");
                        }
                    }

                    @Override
                    public void error() {
                        dismissLoadingDialog();
                        showErrorToast("定位失败");
                    }
                });

            }

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // initMap();
            showErrorToast("未开启权限");

            dismissLoadingDialog();
        }
    };


    ArrayList<SelectTime> hours = new ArrayList<>();
    ArrayList<ArrayList<String>> allmins = new ArrayList<>();


    private void initTime() {
        hours.clear();
        allmins.clear();

        //判断是否是今天
        String today = DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy.MM.dd");

        if (today.equals(dateString)) {

            //得到现在的小时 和 分钟
            int hour = Integer.parseInt(DateTool.getNowHour());
            int min = Integer.parseInt(DateTool.getNowMin());


            for (int i = hour; i <= 23; i++) {
                SelectTime selectTime = new SelectTime();
                if (i < 10) {
                    selectTime.setHour("0" + i + "     时");
                } else {
                    selectTime.setHour(i + "     时");
                }
                ArrayList<String> mins;
                if (i == hour) {
                    mins = new ArrayList<>();
                    for (int j = min; j < 60; j++) {
                        if (j < 10) {
                            mins.add("0" + j + "     分");
                        } else {
                            mins.add(j + "     分");
                        }
                    }
                } else {
                    mins = new ArrayList<>();
                    for (int j = 0; j < 60; j++) {
                        if (j < 10) {
                            mins.add("0" + j + "     分");
                        } else {
                            mins.add(j + "     分");
                        }
                    }

                }

                allmins.add(mins);
                selectTime.setMin(mins);
                hours.add(selectTime);
            }


        } else {


            for (int i = 0; i <= 23; i++) {
                SelectTime selectTime = new SelectTime();

                if (i < 10) {
                    selectTime.setHour("0" + i + "     时");
                } else {
                    selectTime.setHour(i + "     时");
                }


                ArrayList<String> mins;
                mins = new ArrayList<>();
                for (int j = 0; j < 60; j++) {
                    if (j < 10) {
                        mins.add("0" + j + "     分");
                    } else {
                        mins.add(j + "     分");
                    }
                }
                allmins.add(mins);
                selectTime.setMin(mins);
                hours.add(selectTime);
            }

        }
    }

    /**
     * 选择时间
     */
    public void initTimeDialog() {

        if (mDialogBuilderTime == null) {
            mDialogBuilderTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    timeString = getNumber(hours.get(options1).getHour()) + ":" + getNumber(allmins.get(options1).get(options2));
                    mTvTime.setText(timeString);
                }
            })
                    .setTitleText("选择出发时间")
                    .setContentTextSize(14)
                    .setOutSideCancelable(true)// default is true
                    .build();

        }
        mDialogBuilderTime.setPicker(hours, allmins);
        mDialogBuilderTime.show();
    }

    @Override
    public void requestData() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //出发
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            ChooseAddress chooseAddress = data.getParcelableExtra("data");

            startDetailName = chooseAddress.getAddressAll();

            mTvStartAddress.setTextColor(getResources().getColor(R.color.font_black666));
            mTvStartAddress.setText(startDetailName);


            //匹配街道
            Street street = getStreet(chooseAddress.getStreet(), startStreets);

            if (street != null) {
                startStreetCode = street.getAreaCode();
            } else {
                showErrorToast("未匹配到地址");
                startStreetCode = 0;
            }

            //到达
        } else if (resultCode == RESULT_OK && requestCode == 2 && data != null) {

            ChooseAddress chooseAddress = data.getParcelableExtra("data");

            endDetailName = chooseAddress.getAddress();

            mTvEndAddress.setTextColor(getResources().getColor(R.color.font_black666));
            mTvEndAddress.setText(endDetailName);


            //匹配街道
            Street street = getStreet(chooseAddress.getStreet(), endStreets);

            if (street != null) {
                endStreetCode = street.getAreaCode();
            } else {
                showErrorToast("未匹配到地址");
                endStreetCode = 0;
            }


        }
    }

    private Street getStreet(String streetName, ArrayList<Street> streets) {
        if (streetName == null || streetName.length() == 0) {
            return null;
        }
        Logger.v("streetName==" + streetName);
        for (Street street : streets) {
            Logger.v("street==" + street.getAreaName());
            if (street.getAreaName().substring(0, 2).equals(streetName.substring(0, 2))) {
                return street;
            }
        }
        return null;
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                startActivity(TransferPublishSuccessActivity.class, null);
                break;

        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {

            //去除城市
            String address = regeocodeResult.getRegeocodeAddress().getFormatAddress().replace(regeocodeResult.getRegeocodeAddress().getCity() + regeocodeResult.getRegeocodeAddress().getDistrict() + regeocodeResult.getRegeocodeAddress().getTownship(), "");
            if (TextUtils.isEmpty(locationAddress)) {
                locationAddress = address;
            }
            setLocationCity(regeocodeResult.getRegeocodeAddress().getProvince(), regeocodeResult.getRegeocodeAddress().getCity(), regeocodeResult.getRegeocodeAddress().getDistrict(), regeocodeResult.getRegeocodeAddress().getTownship(), locationStreet + locationAddress);
        }
    }

    /**
     * 通过定位设置
     */
    private void setLocationCity(String provinceStr, String cityStr, String countryStr, String streetStr, String detailsAddress) {

        if (mPvOptionsCity == null) {


            if (ChooseAddresDataHelper.isLoad) {
                options1Items = ChooseAddresDataHelper.options1Items;
                options2Items = ChooseAddresDataHelper.options2Items;
                options3Items = ChooseAddresDataHelper.options3Items;
                if (options1Items.isEmpty() || options2Items.isEmpty() || options3Items.isEmpty()) {
                    showErrorToast("数据未读取成功请开启读写权限");
                    return;
                }
                mPvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText() +
                                options2Items.get(options1).get(options2).getPickerViewText() +
                                options3Items.get(options1).get(options2).get(options3).getPickerViewText();


                        if (chooseType == 0) {
                            if (startDistrictName == null || !startDistrictName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {
                                //出发
                                mTvStart.setText(tx);
                                mTvStart.setTextColor(getResources().getColor(R.color.font_black666));

                                startCityName = options2Items.get(options1).get(options2).getPickerViewText();
                                startDistrictName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                startDistrictCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();

                                startStreets = ChooseAddresDataHelper.getStreet(startDistrictCode);
                                mTvStartAddress.setText(getResources().getString(R.string.go_start_tip));

                                startDetailName = getResources().getString(R.string.go_start_tip);
                                mTvStartAddress.setTextColor(getResources().getColor(R.color.font_gray));

                                startStreetCode = 0;
                            }
                        } else {
                            if (endDistrictName == null || !endDistrictName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {

                                //到达
                                mTvEnd.setText(tx);
                                mTvEnd.setTextColor(getResources().getColor(R.color.font_black666));

                                endCityName = options2Items.get(options1).get(options2).getPickerViewText();
                                endDistrictName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                endDistrictCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();


                                endStreets = ChooseAddresDataHelper.getStreet(endDistrictCode);

                                endDetailName = getResources().getString(R.string.go_end_tip);
                                mTvEndAddress.setText(getResources().getString(R.string.go_end_tip));
                                mTvEndAddress.setTextColor(getResources().getColor(R.color.font_gray));

                                endStreetCode = 0;
                            }
                        }


                    }
                })

                        .setTitleText("城市选择")
                        .setContentTextSize(14)
                        .setOutSideCancelable(true)// default is true
                        .build();
                setSelectCity(mPvOptionsCity);
                mPvOptionsCity.setPicker(options1Items, options2Items, options3Items);//三级选择器
            } else {
                showToast("正在加载地址...");
                return;
            }


        }
        String province = provinceStr.substring(0, 2);
        String city = cityStr.substring(0, 2);
        String country = countryStr.substring(0, 2);


        int op1 = 0;
        int op2 = 0;
        int op3 = 0;

        for (int i = 0; i < options1Items.size(); i++) {
            Province options1Item = options1Items.get(i);
            if (options1Item.getAreaName().substring(0, 2).equals(province)) {
                op1 = i;
                break;
            }
        }

        for (int i = 0; i < options2Items.get(op1).size(); i++) {

            City city1 = options2Items.get(op1).get(i);

            if (city1.getAreaName().substring(0, 2).equals(city)) {
                op2 = i;
                break;
            }
        }


        for (int i = 0; i < options3Items.get(op1).get(op2).size(); i++) {
            District district = options3Items.get(op1).get(op2).get(i);
            if (district.getAreaName().substring(0, 2).equals(country)) {
                op3 = i;
                break;
            }
        }

        mPvOptionsCity.setSelectOptions(op1, op2, op3);


        //返回的分别是三个级别的选中位置
        String tx = options1Items.get(op1).getPickerViewText() +
                options2Items.get(op1).get(op2).getPickerViewText() +
                options3Items.get(op1).get(op2).get(op3).getPickerViewText();


        //出发
        mTvStart.setText(tx);
        mTvStart.setTextColor(getResources().getColor(R.color.font_black666));

        startCityName = options2Items.get(op1).get(op2).getPickerViewText();
        startDistrictName = options3Items.get(op1).get(op2).get(op3).getPickerViewText();
        startDistrictCode = options3Items.get(op1).get(op2).get(op3).getAreaCode();

        startStreets = ChooseAddresDataHelper.getStreet(startDistrictCode);

        Street mStreet = getStreet(streetStr, startStreets);
        if (mStreet != null) {
            startStreetCode = mStreet.getAreaCode();

            mTvStartAddress.setText(detailsAddress);
            startDetailName = detailsAddress;
            mTvStartAddress.setTextColor(getResources().getColor(R.color.font_black666));

        } else {
            //     showErrorToast("未匹配到街道");
            startStreetCode = 0;
            mTvStartAddress.setText(getResources().getString(R.string.go_start_tip));
            startDetailName = getResources().getString(R.string.go_start_tip);
            mTvStartAddress.setTextColor(getResources().getColor(R.color.font_gray));
        }


        dismissLoadingDialog();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
