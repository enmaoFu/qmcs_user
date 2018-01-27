package com.biaoyuan.transfer.ui.send;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.ChooseAddressAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.ChooseAddress;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  :
 * Create : 2017/6/8
 * Author ：chen
 */

public class ChooseMapAddressAty extends BaseAty implements Inputtips.InputtipsListener, GeocodeSearch.OnGeocodeSearchListener {


    @Bind(R.id.img_return)
    ImageView mImgReturn;
    @Bind(R.id.edit_query)
    EditText mEditQuery;
    @Bind(R.id.rv_data)
    RecyclerView mRvData;


    private ChooseAddressAdapter mAdapter;
    private String mCity;
    private String districtName;
    private ChooseAddress mAddress;


    @Override
    public int getLayoutId() {
        return R.layout.activity_map_choose_address;
    }

    @Override
    public void initData() {
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter = new ChooseAddressAdapter(R.layout.item_chose_address, new ArrayList<ChooseAddress>());


        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化MyRecyclerViewAdapter，并加入数据

        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);

        mRvData.setHasFixedSize(true);
        //        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);
        mRvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                mAddress = mAdapter.getItem(position);


                GeocodeSearch geocodeSearch = new GeocodeSearch(ChooseMapAddressAty.this);


                geocodeSearch.setOnGeocodeSearchListener(ChooseMapAddressAty.this);


                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                LatLonPoint point = new LatLonPoint(mAddress.getLat(), mAddress.getLng());
                RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);

                geocodeSearch.getFromLocationAsyn(query);


            }
        });

        String address = getIntent().getStringExtra("address");
        mCity = getIntent().getStringExtra("city");


        mEditQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.removeAll();
                Logger.v("msg==" + mEditQuery.getText().toString());
                if (!TextUtils.isEmpty(mEditQuery.getText().toString())) {
                    InputtipsQuery inputquery = new InputtipsQuery(mEditQuery.getText().toString(), mCity);
                    inputquery.setCityLimit(true);
                    Inputtips mInputTips = new Inputtips(ChooseMapAddressAty.this, inputquery);
                    mInputTips.setInputtipsListener(ChooseMapAddressAty.this);
                    mInputTips.requestInputtipsAsyn();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        districtName = getIntent().getStringExtra("district");

        if (address != null) {
            //  mAdapter.removeAll();
            mEditQuery.setText(address);
         /*   InputtipsQuery inputquery = new InputtipsQuery(address, mCity);
            inputquery.setCityLimit(true);
            Inputtips mInputTips = new Inputtips(this, inputquery);
            mInputTips.setInputtipsListener(this);
            mInputTips.requestInputtipsAsyn();*/
        }
    }

    @Override
    public void requestData() {

    }


    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        mAdapter.removeAll();
        if (i == 1000) {
            List<ChooseAddress> chooseAddresses = new ArrayList<>();
            for (Tip tip : list) {

                //过滤掉公交车站

                if (tip.getTypeCode().equals("150700")) {
                    continue;
                }
                if (TextUtils.isEmpty(tip.getAddress()) || tip.getDistrict() != null && tip.getDistrict().length() > 2 && !tip.getDistrict().contains(districtName.substring(0, 2))) {
                    continue;
                }

                Logger.v("tip.type==" + tip.getTypeCode());

                ChooseAddress chooseAddress = new ChooseAddress();
                chooseAddress.setAddress(tip.getName());
                chooseAddress.setAddressAll(tip.getAddress() + tip.getName());
                chooseAddress.setLat(tip.getPoint().getLatitude());
                chooseAddress.setLng(tip.getPoint().getLongitude());
                chooseAddresses.add(chooseAddress);
            }
            mAdapter.addDatas(chooseAddresses);
        }

    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {

            mAddress.setStreet(regeocodeResult.getRegeocodeAddress().getTownship());
        }
        Intent intent = getIntent();
        intent.putExtra("data", mAddress);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
