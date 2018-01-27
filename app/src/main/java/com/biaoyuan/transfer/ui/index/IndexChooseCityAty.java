package com.biaoyuan.transfer.ui.index;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.CityAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.City;
import com.biaoyuan.transfer.domain.OpenCity;
import com.biaoyuan.transfer.util.ChooseAddresDataHelper;
import com.biaoyuan.transfer.util.MyLocationUtil;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Title  :选择城市
 * Create : 2017/5/26
 * Author ：chen
 */

public class IndexChooseCityAty extends BaseAty implements Inputtips.InputtipsListener, PoiSearch.OnPoiSearchListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.indexableLayout)
    IndexableLayout indexableLayout;
    @Bind(R.id.tv_city)
    TextView mTvCity;

    private String cityName;


    private CityAdapter mAdapter;
    private List<OpenCity> mList;


    private OpenCity chooseCity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_city_aty;
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void initData() {


        EventBus.getDefault().register(this);

        initToolbar(mToolbar, "选择城市");

        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        // setAdapter
        mAdapter = new CityAdapter(this);
        indexableLayout.setAdapter(mAdapter);

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();


        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<OpenCity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, OpenCity entity) {
                if (originalPosition >= 0) {
                    chooseCity = entity;
                    PoiSearch.Query query = new PoiSearch.Query(chooseCity.getAreaName(), "", chooseCity.getAreaName());


                    PoiSearch poiSearch = new PoiSearch(IndexChooseCityAty.this, query);
                    poiSearch.setOnPoiSearchListener(IndexChooseCityAty.this);
                    poiSearch.searchPOIAsyn();


                } else {
                    //   ToastUtil.showShort(PickCityActivity.this, "选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getDatas(List<OpenCity> openCities) {


        mAdapter.setDatas(openCities, new IndexableAdapter.IndexCallback<OpenCity>() {
            @Override
            public void onFinished(List<EntityWrapper<OpenCity>> datas) {
                // 数据处理完成后回调
                dismissLoadingContentDialog();

            }
        });
    }


    // 权限
    public void opCheckPermission() {


        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION
                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(IndexChooseCityAty.this, rationale).show();
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

                MyLocationUtil locationUtil = new MyLocationUtil(IndexChooseCityAty.this, new MyLocationUtil.MyLocationListener() {
                    @Override
                    public void sussessLocation(double lat, double lon, final AMapLocation location) {

                        if (location != null) {
                            cityName = location.getCity();
                            mTvCity.setText(location.getCity());
                            Logger.v("code==" + location.getAdCode());
                            mTvCity.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = getIntent();
                                    intent.putExtra("name", mTvCity.getText().toString());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                        } else {
                            mTvCity.setText("定位失败点击刷新");
                            mTvCity.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    opCheckPermission();
                                }
                            });
                        }
                    }

                    @Override
                    public void error() {
                        mTvCity.setText("定位失败点击刷新");
                        mTvCity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                opCheckPermission();
                            }
                        });
                    }
                });

            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {

            //授权失败
            mTvCity.setText("定位失败点击刷新");
            mTvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opCheckPermission();
                }
            });
        }
    };


    @Override
    public void requestData() {
        showLoadingContentDialog();
        opCheckPermission();

        //得到所有的城市
        if (ChooseAddresDataHelper.isLoad) {

            mList = new ArrayList<>();


            for (ArrayList<City> options2Item : ChooseAddresDataHelper.options2Items) {

                for (City city : options2Item) {
                    OpenCity openCity = new OpenCity();
                    openCity.setAreaCode(city.getAreaCode());
                    openCity.setAreaName(city.getAreaName());
                    mList.add(openCity);
                }
            }
            getDatas(mList);
        }


    }

    public void onEventMainThread(City c) {

        //判断是否已经加载了
        if (mList != null && mList.size() != 0) {
            return;
        }

        //得到所有的城市
        if (ChooseAddresDataHelper.isLoad) {

            mList = new ArrayList<>();

            for (ArrayList<City> options2Item : ChooseAddresDataHelper.options2Items) {
                for (City city : options2Item) {
                    OpenCity openCity = new OpenCity();
                    openCity.setAreaCode(city.getAreaCode());
                    openCity.setAreaName(city.getAreaName());
                    mList.add(openCity);
                }
            }

            getDatas(mList);
        }
    }


    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000) {


        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {


        Intent intent = getIntent();

        LatLonPoint point = null;
        if (poiResult.getPois() != null && poiResult.getPois().size() > 0) {
            for (PoiItem poiItem : poiResult.getPois()) {
                if (poiItem.getLatLonPoint() != null) {
                    point = poiItem.getLatLonPoint();
                    break;
                }
            }

        }
        if (point != null) {
            intent.putExtra("lat", point.getLatitude());
            intent.putExtra("lng", point.getLongitude());
        }


        //判断定位是不是当前城市
        if (cityName != null) {

            if (cityName.substring(0, 2).equals(chooseCity.getAreaName().substring(0, 2))) {

                intent.putExtra("name", cityName);

            } else {
                intent.putExtra("name", chooseCity.getAreaName());
                intent.putExtra("code", chooseCity.getAreaCode());
            }


        } else {
            intent.putExtra("name", chooseCity.getAreaName());
            intent.putExtra("code", chooseCity.getAreaCode());
        }


        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
