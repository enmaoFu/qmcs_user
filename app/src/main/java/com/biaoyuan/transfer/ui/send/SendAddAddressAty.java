package com.biaoyuan.transfer.ui.send;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.and.yzy.frame.util.MatchStingUtil;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.AddAddressItem;
import com.biaoyuan.transfer.domain.ChooseAddress;
import com.biaoyuan.transfer.domain.City;
import com.biaoyuan.transfer.domain.District;
import com.biaoyuan.transfer.domain.Province;
import com.biaoyuan.transfer.domain.Street;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.ChooseAddresDataHelper;
import com.biaoyuan.transfer.util.ChooseSendAddresDataHelper;
import com.biaoyuan.transfer.util.MyLocationUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :添加收件人和寄件人信息信息
 * Create : 2017/4/26
 * Author ：chen
 */

public class SendAddAddressAty extends BaseAty implements GeocodeSearch.OnGeocodeSearchListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.tv_city)
    TextView mTvCity;
    @Bind(R.id.tv_street)
    TextView mTvStreet;
    @Bind(R.id.details_address)
    TextView mAddress;
    @Bind(R.id.cb_save)
    CheckBox mCbSave;
    @Bind(R.id.et_home)
    EditText mEtHome;
    @Bind(R.id.v_diver)
    View mVDiver;
    @Bind(R.id.tv_location)
    TextView mTvLocation;


    private ArrayList<Street> options4Items = new ArrayList<>();
    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<City>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<District>>> options3Items = new ArrayList<>();
    private OptionsPickerView mPvOptionsCity;
    private OptionsPickerView mPvOptionsStreet;

    private int cityParentCode = 0;
    private int streetParentCode = 0;

    //1发件 2 收件
    private int type;

    private String cityName;
    private String provinceName;
    private String districtName;

    private double lng = 0;
    private double lat = 0;


    private String mName;
    private String mPhone;
    private String mDetailsAddress;
    private String mGetCityParen;
    private String mGetStreeParen;

    private String mGetHome;

    private AddAddressItem mItem;

    private GeocodeSearch mGeocodeSearch;

    private String locationStreet = "";
    private String locationAddress = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_add_address;
    }

    @Override
    public void initData() {


        mItem = getIntent().getParcelableExtra("data");

        if (mItem != null) {

            mName = mItem.getName();
            mEtName.setText(mName);

            mPhone = mItem.getPhone() + "";
            mEtPhone.setText(mPhone);

            String address[] = mItem.getAddress().split("\\|");


            provinceName = address[0];
            cityName = address[1];
            districtName = address[2];


            mGetCityParen = provinceName + cityName + districtName;

            mTvCity.setText(mGetCityParen);
            mTvCity.setTextColor(getResources().getColor(R.color.font_black666));

            mDetailsAddress = address[4];
            mAddress.setText(mDetailsAddress);

            mAddress.setTextColor(getResources().getColor(R.color.font_black666));
            mGetStreeParen = address[3];

            mTvStreet.setText(mGetStreeParen);
            mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));
            if (address.length == 6) {
                mGetHome = address[5];
                mEtHome.setText(mGetHome);
            }
            cityParentCode = mItem.getAreaCode();
            streetParentCode = mItem.getAreaId();


            lat = mItem.getLat();
            lng = mItem.getLng();
            options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);


        }


        type = getIntent().getIntExtra("type", 1);
        switch (type) {
            case 2:
                initToolbar(mToolbar, "收件人信息");
                mVDiver.setVisibility(View.GONE);
                mTvLocation.setVisibility(View.GONE);
                break;
            case 1:
                initToolbar(mToolbar, "寄件人信息");
                mGeocodeSearch = new GeocodeSearch(this);
                mGeocodeSearch.setOnGeocodeSearchListener(this);
                break;
        }


    }

    private void hideKeyBoard() {
        if (null != this.getCurrentFocus()) {
            /**
             隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void showCityDialog() {

        hideKeyBoard();
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            showErrorToast("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showErrorToast("请填写联系电话");
            return;
        }


        if (mPvOptionsCity == null) {

            //收件
            if (type == 2) {
                if (ChooseAddresDataHelper.isLoad) {
                    options1Items = ChooseAddresDataHelper.options1Items;
                    options2Items = ChooseAddresDataHelper.options2Items;
                    options3Items = ChooseAddresDataHelper.options3Items;
                    if (options1Items.isEmpty()||options2Items.isEmpty()||options3Items.isEmpty()){
                        showErrorToast("数据未读取成功请开启读写权限");
                        return;
                    }

                    mPvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {


                            //判断districtName是不是当前的
                            if (districtName == null || !districtName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {
                                //返回的分别是三个级别的选中位置
                                String tx = options1Items.get(options1).getPickerViewText() +
                                        options2Items.get(options1).get(options2).getPickerViewText() +
                                        options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                provinceName = options1Items.get(options1).getAreaName();
                                districtName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                cityName = options2Items.get(options1).get(options2).getPickerViewText();

                                mTvCity.setText(tx);
                                mTvCity.setTextColor(getResources().getColor(R.color.font_black666));
                                cityParentCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();
                                if (type == 2) {
                                    //收件
                                    options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
                                } else {
                                    //发件
                                    options4Items = ChooseSendAddresDataHelper.getStreet(cityParentCode);
                                }
                                streetParentCode = options4Items.get(0).getAreaCode();
                                mGetStreeParen = options4Items.get(0).getAreaName();
                                mTvStreet.setText(mGetStreeParen);
                                mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));

                                mDetailsAddress = getResources().getString(R.string.add_address_tip);
                                mAddress.setText(mDetailsAddress);
                                mAddress.setTextColor(getResources().getColor(R.color.font_hint));
                            }


                        }
                    })

                            .setTitleText("城市选择")
                            .setContentTextSize(14)
                            .setOutSideCancelable(true)// default is true
                            .build();

                    mPvOptionsCity.setPicker(options1Items, options2Items, options3Items);//三级选择器
                } else {
                    showToast("正在加载地址...");
                    return;
                }

            } else {
                //发件

                if (!ChooseSendAddresDataHelper.isBegin) {
                    if (ChooseSendAddresDataHelper.isLoad) {
                        options1Items = ChooseSendAddresDataHelper.options1Items;
                        options2Items = ChooseSendAddresDataHelper.options2Items;
                        options3Items = ChooseSendAddresDataHelper.options3Items;
                        if (options1Items.isEmpty()||options2Items.isEmpty()||options3Items.isEmpty()){
                            showErrorToast("数据未读取成功请开启读写权限");
                            return;
                        }

                        mPvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                                if (districtName == null || !districtName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {

                                    //返回的分别是三个级别的选中位置
                                    String tx = options1Items.get(options1).getPickerViewText() +
                                            options2Items.get(options1).get(options2).getPickerViewText() +
                                            options3Items.get(options1).get(options2).get(options3).getPickerViewText();

                                    provinceName = options1Items.get(options1).getAreaName();
                                    districtName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                    cityName = options2Items.get(options1).get(options2).getPickerViewText();

                                    mTvCity.setText(tx);
                                    mTvCity.setTextColor(getResources().getColor(R.color.font_black666));
                                    cityParentCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();
                                    if (type == 2) {
                                        //收件
                                        options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
                                    } else {
                                        //发件
                                        options4Items = ChooseSendAddresDataHelper.getStreet(cityParentCode);
                                    }
                                    streetParentCode = options4Items.get(0).getAreaCode();
                                    mGetStreeParen = options4Items.get(0).getAreaName();
                                    mTvStreet.setText(mGetStreeParen);
                                    mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));

                                    mDetailsAddress = getResources().getString(R.string.add_address_tip);
                                    mAddress.setText(mDetailsAddress);
                                    mAddress.setTextColor(getResources().getColor(R.color.font_hint));
                                }
                            }
                        })

                                .setTitleText("城市选择")
                                .setContentTextSize(14)
                                .setOutSideCancelable(true)// default is true
                                .build();

                        //根据定位设置默认选中

                        mPvOptionsCity.setPicker(options1Items, options2Items, options3Items);//三级选择器
                    } else {
                        showToast("正在加载地址...");
                        return;
                    }
                } else {
                    showToast("正在加载地址...");
                    //载入开通城市
                    //获取城市地址版本号
                    doHttp(RetrofitUtils.createApi(Send.class).getAddressVersion(), 2);
                    return;
                }


            }


        }


        mPvOptionsCity.show();
    }




    /**
     * 通过定位设置
     */
    private void setLocationCity(String provinceStr, String cityStr, String countryStr, String streetStr, String detailsAddress) {
        if (mPvOptionsCity == null) {

            //发件
            if (!ChooseSendAddresDataHelper.isBegin) {
                if (ChooseSendAddresDataHelper.isLoad) {
                    options1Items = ChooseSendAddresDataHelper.options1Items;
                    options2Items = ChooseSendAddresDataHelper.options2Items;
                    options3Items = ChooseSendAddresDataHelper.options3Items;
                    if (options1Items.isEmpty()||options2Items.isEmpty()||options3Items.isEmpty()){
                        showErrorToast("数据未读取成功请开启读写权限");
                        return;
                    }


                    mPvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            if (districtName == null || !districtName.equals(options3Items.get(options1).get(options2).get(options3).getPickerViewText())) {
                                //返回的分别是三个级别的选中位置
                                String tx = options1Items.get(options1).getPickerViewText() +
                                        options2Items.get(options1).get(options2).getPickerViewText() +
                                        options3Items.get(options1).get(options2).get(options3).getPickerViewText();

                                provinceName = options1Items.get(options1).getAreaName();
                                districtName = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                cityName = options2Items.get(options1).get(options2).getPickerViewText();

                                mTvCity.setText(tx);
                                mTvCity.setTextColor(getResources().getColor(R.color.font_black666));
                                cityParentCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();
                                if (type == 2) {
                                    //收件
                                    options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
                                } else {
                                    //发件
                                    options4Items = ChooseSendAddresDataHelper.getStreet(cityParentCode);
                                }
                                streetParentCode = options4Items.get(0).getAreaCode();
                                mGetStreeParen = options4Items.get(0).getAreaName();
                                mTvStreet.setText(mGetStreeParen);
                                mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));

                                mDetailsAddress = getResources().getString(R.string.add_address_tip);
                                mAddress.setText(mDetailsAddress);
                                mAddress.setTextColor(getResources().getColor(R.color.font_hint));
                            }
                        }
                    })

                            .setTitleText("城市选择")
                            .setContentTextSize(14)
                            .setOutSideCancelable(true)// default is true
                            .build();

                    mPvOptionsCity.setPicker(options1Items, options2Items, options3Items);//三级选择器
                } else {
                    showToast("正在加载地址...");
                    return;
                }
            } else {
                showToast("正在加载地址...");
                //载入开通城市
                //获取城市地址版本号
                doHttp(RetrofitUtils.createApi(Send.class).getAddressVersion(), 2);
                return;
            }

        }

        String province = provinceStr.substring(0, 2);
        String city = cityStr.substring(0, 2);
        String country = countryStr.substring(0, 2);


        int op1 = -1;
        int op2 = -1;
        int op3 = 0;
        int op4 = 0;

        for (int i = 0; i < options1Items.size(); i++) {
            Province options1Item = options1Items.get(i);
            if (options1Item.getAreaName().substring(0, 2).equals(province)) {
                op1 = i;
                break;
            }
        }
        if (op1 == -1) {
            showErrorToast("您所在的城市暂未开通");
            dismissLoadingDialog();
            return;
        }

        for (int i = 0; i < options2Items.get(op1).size(); i++) {

            City city1 = options2Items.get(op1).get(i);

            if (city1.getAreaName().substring(0, 2).equals(city)) {
                op2 = i;
                break;
            }
        }
        if (op2 == -1) {
            showErrorToast("您所在的城市暂未开通");
            dismissLoadingDialog();
            return;
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

        provinceName = options1Items.get(op1).getAreaName();
        districtName = options3Items.get(op1).get(op2).get(op3).getPickerViewText();
        cityName = options2Items.get(op1).get(op2).getPickerViewText();

        mTvCity.setText(tx);
        mTvCity.setTextColor(getResources().getColor(R.color.font_black666));
        cityParentCode = options3Items.get(op1).get(op2).get(op3).getAreaCode();
        if (type == 2) {
            //收件
            options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
        } else {
            //发件
            options4Items = ChooseSendAddresDataHelper.getStreet(cityParentCode);
        }
        //查到对应的街道

        Street mStreet = null;

        for (int i = 0; i < options4Items.size(); i++) {
            if (options4Items.get(i).getAreaName().substring(0, 2).equals(streetStr.substring(0, 2))) {
                mStreet = options4Items.get(i);
                op4 = i;
                break;
            }
        }


        if (mStreet != null) {
            streetParentCode = mStreet.getAreaCode();
            mGetStreeParen = mStreet.getAreaName();
            mTvStreet.setText(mGetStreeParen);
            mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));
        } else {
            streetParentCode = options4Items.get(0).getAreaCode();
            mGetStreeParen = options4Items.get(0).getAreaName();
            mTvStreet.setText(mGetStreeParen);
            mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));
        }

        if (TextUtils.isEmpty(detailsAddress)) {
            mDetailsAddress = getResources().getString(R.string.add_address_tip);
            mAddress.setText(mDetailsAddress);
            mAddress.setTextColor(getResources().getColor(R.color.font_hint));
        } else {
            mDetailsAddress = detailsAddress;
            mAddress.setText(mDetailsAddress);
            mAddress.setTextColor(getResources().getColor(R.color.font_black666));
        }

        if (mPvOptionsStreet == null) {

            mPvOptionsStreet = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {

                    if (mGetStreeParen == null || !mGetStreeParen.equals(options4Items.get(options1).getPickerViewText())) {

                        mGetStreeParen = options4Items.get(options1).getPickerViewText();

                        mTvStreet.setText(mGetStreeParen);
                        mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));

                        streetParentCode = options4Items.get(options1).getAreaCode();
                        options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
                        mDetailsAddress = getResources().getString(R.string.add_address_tip);
                        mAddress.setText(mDetailsAddress);
                        mAddress.setTextColor(getResources().getColor(R.color.font_hint));
                    }

                }
            })
                    .setTitleText("街道选择")
                    .setContentTextSize(14)
                    .setOutSideCancelable(true)// default is true
                    .build();

        }

        if (options4Items!=null&&options4Items.size()>0){
            mPvOptionsStreet.setPicker(options4Items);//三级选择器
            mPvOptionsStreet.setSelectOptions(op4);
        }else {
            showErrorToast("该区域未开通");
        }

        dismissLoadingDialog();
    }

    private void showStreetDialog() {
        hideKeyBoard();
        if (cityParentCode == 0) {
            showErrorToast("请选择城市");
            return;
        }

        if (mPvOptionsStreet == null) {


            mPvOptionsStreet = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {

                    if (mGetStreeParen == null || !mGetStreeParen.equals(options4Items.get(options1).getPickerViewText())) {

                        mGetStreeParen = options4Items.get(options1).getPickerViewText();

                        mTvStreet.setText(mGetStreeParen);
                        mTvStreet.setTextColor(getResources().getColor(R.color.font_black666));

                        streetParentCode = options4Items.get(options1).getAreaCode();
                        options4Items = ChooseAddresDataHelper.getStreet(cityParentCode);
                        mDetailsAddress = getResources().getString(R.string.add_address_tip);
                        mAddress.setText(mDetailsAddress);
                        mAddress.setTextColor(getResources().getColor(R.color.font_hint));
                    }
                }
            })
                    .setTitleText("街道选择")
                    .setContentTextSize(14)
                    .setOutSideCancelable(true)// default is true
                    .build();

        }

        if (options4Items!=null&&options4Items.size()>0){
            mPvOptionsStreet.setPicker(options4Items);//三级选择器
            mPvOptionsStreet.show();
        }else {
            showErrorToast("该区域未开通");
        }

    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {


    }


    @OnClick({R.id.ll_city, R.id.ll_street, R.id.commit_btn, R.id.ll_address_detail, R.id.tv_location})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.ll_address_detail:
                if (cityParentCode == 0) {
                    showErrorToast("请选择城市");
                    return;
                }
                Bundle bundle = new Bundle();
                mDetailsAddress = mAddress.getText().toString();
                if (!TextUtils.isEmpty(mDetailsAddress) && !mDetailsAddress.equals(getResources().getString(R.string.add_address_tip))) {
                    bundle.putString("address", mDetailsAddress);
                }
                bundle.putString("city", cityName);
                bundle.putString("district", districtName);
                startActivityForResult(ChooseMapAddressAty.class, bundle, 1);
                break;
            case R.id.tv_location:

                //定位当前
                opCheckPermission(200);


                break;
            case R.id.ll_city:
                showCityDialog();
                break;
            case R.id.ll_street:
                showStreetDialog();
                break;
            case R.id.commit_btn:
                mName = mEtName.getText().toString().trim();
                mPhone = mEtPhone.getText().toString().trim();
                mDetailsAddress = mAddress.getText().toString().trim();
                mGetCityParen = mTvCity.getText().toString().trim();
                mGetStreeParen = mTvStreet.getText().toString().trim();
                mGetHome = mEtHome.getText().toString().trim();
                if (TextUtils.isEmpty(mName)) {
                    showErrorToast("请填写姓名");
                    return;
                }
                if (!MatchStingUtil.isMobile(mPhone)) {
                    showErrorToast("请填写正确的手机号");
                    return;
                }
                if (cityParentCode == 0 || TextUtils.isEmpty(mGetCityParen)) {
                    showErrorToast("请选择城市");
                    return;
                }
                if (streetParentCode == 0 || TextUtils.isEmpty(mGetStreeParen)) {
                    showErrorToast("请选择街道");
                    return;
                }
                if (TextUtils.isEmpty(mDetailsAddress) || mDetailsAddress.equals(getResources().getString(R.string.add_address_tip))) {
                    showErrorToast("请填写您的小区/大厦/学校");
                    return;
                }


                showLoadingDialog(null);

                //判断是否保存
                if (mCbSave.isChecked()) {
                    if (TextUtils.isEmpty(mGetHome)) {
                        doHttp(RetrofitUtils.createApi(Send.class).addAddress(mName, Long.parseLong(mPhone), provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress, lng, lat, type, cityParentCode, streetParentCode), 1);
                    } else {
                        doHttp(RetrofitUtils.createApi(Send.class).addAddress(mName, Long.parseLong(mPhone), provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress + "|" + mGetHome, lng, lat, type, cityParentCode, streetParentCode), 1);
                    }

                } else {
                    dismissLoadingDialog();
                    AddAddressItem addAddressItem = new AddAddressItem();
                    if (TextUtils.isEmpty(mGetHome)) {
                        addAddressItem.setAddress(provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress);
                    } else {
                        addAddressItem.setAddress(provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress + "|" + mGetHome);
                    }
                    addAddressItem.setAreaCode(cityParentCode);
                    addAddressItem.setAreaId(streetParentCode);
                    addAddressItem.setLat(lat);
                    addAddressItem.setLng(lng);
                    addAddressItem.setName(mName);
                    addAddressItem.setPhone(Long.parseLong(mPhone));
                    addAddressItem.setType(type);
                    Intent intent = getIntent();
                    intent.putExtra("data", addAddressItem);
                    setResult(RESULT_OK, intent);
                    finish();
                }


                break;
        }
    }

    // 权限
    public void opCheckPermission(int requestcode) {


        hideKeyBoard();
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            showErrorToast("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showErrorToast("请填写联系电话");
            return;
        }
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
                                   AndPermission.rationaleDialog(SendAddAddressAty.this, rationale).show();
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


                MyLocationUtil locationUtil = new MyLocationUtil(SendAddAddressAty.this, new MyLocationUtil.MyLocationListener() {
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

                            SendAddAddressAty.this.lat = lat;
                            SendAddAddressAty.this.lng = lon;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu, menu);
        menu.getItem(0).setTitle("常用地址");
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                startActivityForResult(SendCommonAddressActivity.class, bundle, 2);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    long version;

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                //保存地址
                AddAddressItem addAddressItem = new AddAddressItem();
                if (TextUtils.isEmpty(mGetHome)) {
                    addAddressItem.setAddress(provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress);
                } else {
                    addAddressItem.setAddress(provinceName + "|" + cityName + "|" + districtName + "|" + mGetStreeParen + "|" + mDetailsAddress + "|" + mGetHome);
                }

                addAddressItem.setAreaCode(cityParentCode);
                addAddressItem.setAreaId(streetParentCode);
                addAddressItem.setLat(lat);
                addAddressItem.setLng(lng);
                addAddressItem.setName(mName);
                addAddressItem.setPhone(Long.parseLong(mPhone));
                addAddressItem.setType(type);
                Intent intent = getIntent();
                intent.putExtra("data", addAddressItem);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case 2:
                version = AppJsonUtil.getLong(result, "data");
                if (AppJsonUtil.getLong(result, "data") != UserManger.getSendAddressVersion()) {

                    doHttp(RetrofitUtils.createApi(Send.class).getAddress(), 3);
                } else {
                    new ChooseSendAddresDataHelper().initAddressData(null, version);
                }
                break;
            case 3:
                new ChooseSendAddresDataHelper().initAddressData(result, version);
                break;

        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        ChooseSendAddresDataHelper.isBegin = false;
    }

    ChooseAddress chooseAddress = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            chooseAddress = data.getParcelableExtra("data");

            mDetailsAddress = chooseAddress.getAddressAll();
            lat = chooseAddress.getLat();
            lng = chooseAddress.getLng();
            mAddress.setTextColor(getResources().getColor(R.color.font_black666));
            mAddress.setText(mDetailsAddress);


            Logger.v("address==" + chooseAddress.getStreet());
            //匹配街道
            Street street = getStreet(chooseAddress.getStreet());

            if (street != null) {
                mTvStreet.setText(street.getAreaName());
                mGetStreeParen = street.getAreaName();
                streetParentCode = street.getAreaCode();
            }

        } else if (resultCode == RESULT_OK && requestCode == 2 && data != null) {

            setResult(RESULT_OK, data);
            finish();


        }
    }

    private Street getStreet(String streetName) {

        if (streetName==null||streetName.length()==0){
           return null;
        }

        for (Street street : options4Items) {

            if (street.getAreaName().substring(0, 2).equals(streetName.substring(0, 2))) {
                return street;
            }
        }
        return null;
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

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
