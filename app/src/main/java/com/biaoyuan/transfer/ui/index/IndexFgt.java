package com.biaoyuan.transfer.ui.index;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.and.yzy.frame.transformer.BGAPageTransformer;
import com.and.yzy.frame.transformer.TransitionEffect;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.AdInfo;
import com.biaoyuan.transfer.domain.AddressMarker;
import com.biaoyuan.transfer.http.Image;
import com.biaoyuan.transfer.http.IndexAddress;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.ui.WebViewActivity;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.util.AllLocationUtil;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.JumpInterpolator;
import com.biaoyuan.transfer.util.MyLocationUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
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
 * Title  :首页
 * Create : 2017/4/24
 * Author ：chen
 */

public class IndexFgt extends BaseFgt implements GeocodeSearch.OnGeocodeSearchListener {


    @Bind(R.id.map)
    TextureMapView mMapView;
    @Bind(R.id.tv_city)
    TextView mTvCity;
    @Bind(R.id.messgae)
    ImageView mMessgae;
    @Bind(R.id.img_locatin)
    ImageView mImgLocatin;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.ll_address)
    LinearLayout mLlAddress;

    @Bind(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @Bind(R.id.tv_my_location)
    ImageView mTvMyLocation;
    private AMap aMap;
    private Bundle savedInstanceState;

    private String mcity = "";


    private Marker mClickMarker;

    private List<Marker> mMarkers = new ArrayList<>();
    private int mCode = 0;
    private GeocodeSearch mGeocodeSearch;


    //通过点击切换城市获得，不在拖拽中刷新
    private boolean isCheck = false;
    private boolean isHide = false;
    private ObjectAnimator mAnimator;
    private List<AdInfo> mAdInfos;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_index_main;
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
            mMapView.onDestroy();

        if (mAllLocationUtil != null) {
            mAllLocationUtil.stopLocation();
            mAllLocationUtil = null;
        }
        isCheck = false;
        isStart = false;
        isHide = false;


    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (mMapView != null) {
            mMapView.onResume();
        }
        if (mConvenientBanner != null && mAdInfos != null && mAdInfos.size() > 1) {
            mConvenientBanner.startTurning(5000);
        }

    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();
        if (mMapView != null) {
            mMapView.onPause();
        }
        if (mConvenientBanner != null && mAdInfos != null && mAdInfos.size() > 1) {
            mConvenientBanner.stopTurning();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    AllLocationUtil mAllLocationUtil;

    @Override
    public void initData() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getResources().getDrawable(R.drawable.placeholder_pic));
        drawables.add(getResources().getDrawable(R.drawable.placeholder_pic));

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mConvenientBanner.setPageTransformer(BGAPageTransformer.getPageTransformer(TransitionEffect.Accordion));


    }


    float touchX;
    float touchY;

    private void initMap() {

        if (aMap == null) {

            mMapView.onCreate(savedInstanceState);

            aMap = mMapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);// 放大缩小开关
            aMap.getUiSettings().setScaleControlsEnabled(false);// 标尺开关
            aMap.getUiSettings().setCompassEnabled(false);// 指南针开关
            if (UserManger.getLat() != null && Double.parseDouble(UserManger.getLat()) > 0) {
                LatLng latLng = new LatLng(Double.parseDouble(UserManger.getLat()), Double.parseDouble(UserManger.getLng()));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }


            mGeocodeSearch = new GeocodeSearch(getActivity());


            mGeocodeSearch.setOnGeocodeSearchListener(this);

            aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
                @Override
                public void onTouch(MotionEvent motionEvent) {

                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mLlAddress.setVisibility(View.GONE);
                            touchX = motionEvent.getX();
                            touchY = motionEvent.getY();
                            break;
                        case MotionEvent.ACTION_UP:

                            //防止移动距离过小，而进行频繁定位

                            if (Math.abs(motionEvent.getX() - touchX) < 50 && Math.abs(motionEvent.getY() - touchY) < 50) {
                                isHide = true;
                            }

                            break;
                    }

                }
            });
            aMap.getUiSettings().setMyLocationButtonEnabled(false);

            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                    mLlAddress.setVisibility(View.GONE);
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {

                    if (isHide) {
                        isHide = false;
                        return;
                    }

                    if (!isCheck) {
                        clearMarker();
                        //判断code
                        if (mCode == 0) {
                            isShowOnFailureToast = false;
                            doHttp(RetrofitUtils.createApi(IndexAddress.class).getAffilliate(cameraPosition.target.longitude, cameraPosition.target.latitude), 1);
                        }

                    }
                    isCheck = false;
                    // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);

                    mGeocodeSearch.getFromLocationAsyn(query);
                }
            });


            MyLocationUtil locationUtil = new MyLocationUtil(getActivity(), new MyLocationUtil.MyLocationListener() {
                @Override
                public void sussessLocation(double lat, double lon, final AMapLocation location) {

                    if (location != null) {

                        UserManger.setAddress(location.getProvince() + "_" + location.getCity() + "_" + location.getDistrict());

                        //移动到指定位置
                        LatLng latLng = new LatLng(lat, lon);
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        mTvCity.setText(location.getCity());

                    } else {
                        showErrorToast("定位失败");
                    }
                }

                @Override
                public void error() {

                    showErrorToast("定位失败");
                }
            });


            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    isHide = true;
                    if (mClickMarker != null && mClickMarker.isInfoWindowShown()) {
                        mClickMarker.hideInfoWindow();
                    }
                    mLlAddress.setVisibility(View.GONE);
                }
            });

            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    mClickMarker = marker;
                    return false;
                }
            });
        }

    }

    /**
     * 清除
     */
    private void clearMarker() {
        for (Marker marker : mMarkers) {
            marker.remove();
        }
    }

    @Override
    public void requestData() {
        opCheckPermission();

        //请求广告页
        doHttp(RetrofitUtils.createApi(Image.class).listAdvertisement("1", "用户端首页"), 3);


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
                                   AndPermission.rationaleDialog(getActivity(), rationale).show();
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


                if (mAllLocationUtil == null) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAllLocationUtil = new AllLocationUtil(getActivity(), new AllLocationUtil.MyLocationListener() {
                                @Override
                                public void sussessLocation(double lat, double lon, AMapLocation location) {
                                    UserManger.setLat(lat + "");
                                    UserManger.setLng(lon + "");
                                }

                                @Override
                                public void error() {

                                }
                            });
                        }
                    },5000);

                }


                if (aMap == null) {
                    initMap();
                } else {
                    clearMarker();
                    //判断code
                    if (mCode == 0) {
                        MyLocationUtil locationUtil = new MyLocationUtil(getActivity(), new MyLocationUtil.MyLocationListener() {
                            @Override
                            public void sussessLocation(double lat, double lon, final AMapLocation location) {

                                if (location != null) {

                                    mTvCity.setText(location.getCity());

                                    //回到当前位置
                                    //移动到指定位置
                                    LatLng latLng = new LatLng(lat, lon);

                                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

                                    isShowOnFailureToast = false;
                                    doHttp(RetrofitUtils.createApi(IndexAddress.class).getAffilliate(location.getLongitude(), location.getLatitude()), 1);

                                } else {
                                    showErrorToast("定位失败");
                                }
                            }

                            @Override
                            public void error() {

                                showErrorToast("定位失败");
                            }
                        });
                    } else {
                        isShowOnFailureToast = false;
                        doHttp(RetrofitUtils.createApi(IndexAddress.class).home(mCode), 2);
                    }
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // initMap();
            showErrorToast("定位未授权");
            dismissLoadingDialog();
        }
    };


    @OnClick({R.id.messgae, R.id.tv_city, R.id.tv_go_send, R.id.img_locatin, R.id.tv_my_location})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.messgae:
                if (UserManger.isLogin()) {
                    startActivity(IndexMessageActivity.class, null);
                } else {
                    startActivity(LoginAty.class, null);
                }
                break;
            case R.id.tv_city:
                startActivityForResult(IndexChooseCityAty.class, null, 1);
                break;
            case R.id.tv_go_send:
                MainAty.radioButtons.get(1).setChecked(true);
                break;
            case R.id.img_locatin:
                if (mLlAddress.getVisibility() == View.VISIBLE) {
                    mLlAddress.setVisibility(View.GONE);
                } else {
                    mLlAddress.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_my_location:

                opCheckPermission();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == 1 && data != null) {


            mcity = data.getStringExtra("name");
            mTvCity.setText(mcity);
            //判断有code没
            mCode = data.getIntExtra("code", 0);

            if (mCode != 0 && data.getDoubleExtra("lat", 0) != 0) {

                //移动到指定位置
                LatLng latLng = new LatLng(data.getDoubleExtra("lat", 0), data.getDoubleExtra("lng", 0));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                LatLonPoint point = new LatLonPoint(data.getDoubleExtra("lat", 0), data.getDoubleExtra("lng", 0));
                RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
                mGeocodeSearch.getFromLocationAsyn(query);

            }

            isCheck = true;
            opCheckPermission();

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // showLoadingContentDialog();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                List<AddressMarker> addressMarkers = AppJsonUtil.getArrayList(result, "affiliateBasicBeanList", AddressMarker.class);

                if (addressMarkers != null) {
                    initMarker(addressMarkers);
                }


                break;
            case 2:

                List<AddressMarker> addressMarker = AppJsonUtil.getArrayList(result, "affiliateBasicBeanList", AddressMarker.class);

                if (addressMarker != null && addressMarker.size() > 0) {


                    initMarker(addressMarker);


                }
                //mcode=0
                mCode = 0;


                break;
            case 3:

                mAdInfos = AppJsonUtil.getArrayList(result, "advertisementList", AdInfo.class);


                if (mAdInfos == null && mAdInfos.size() == 0) {
                    return;
                }

                //设置尺寸
                String[] size = mAdInfos.get(0).getAdPicSize().split("\\*");

                int width = Integer.parseInt(size[0]);
                int height = Integer.parseInt(size[1]);
                //得到屏幕的宽度
                //减去两边的间距
                int screenWidth = DensityUtils.getScreenWidth(getActivity()) - DensityUtils.dp2px(getActivity(), 24);
                int bannerHeight = screenWidth / (width / height);


                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mConvenientBanner.getLayoutParams();

                layoutParams.height = bannerHeight;


                mConvenientBanner.setLayoutParams(layoutParams);


                mConvenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, mAdInfos).setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        AdInfo adInfo = mAdInfos.get(position);

                        if (!TextUtils.isEmpty(adInfo.getAdUrl())) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", WebViewActivity.TYPE_OTHER);
                            bundle.putString("url", adInfo.getAdUrl());
                            bundle.putString("title", adInfo.getAdDiscription());
                            startActivity(WebViewActivity.class, bundle);

                        }

                    }
                });

                if (mAdInfos.size() > 1) {
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    mConvenientBanner.setPageIndicator(new int[]{R.drawable.banner_more_noraml_08, R.drawable.banner_more_active_06})
                            //设置指示器的方向
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                    mConvenientBanner.startTurning(5000);
                } else {
                    mConvenientBanner.setCanLoop(false);
                }


                break;


        }
    }


    /**
     * 初始化marker
     *
     * @param addressMarkers
     */
    private void initMarker(List<AddressMarker> addressMarkers) {


        for (AddressMarker addressMarker : addressMarkers) {


            LatLng latLng = new LatLng(addressMarker.getLat(), addressMarker.getLng());

            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(addressMarker.getName());

            markerOption.draggable(false);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.icon_side)));

            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            Marker marker = aMap.addMarker(markerOption);
            mMarkers.add(marker);
            Animation animation = new AlphaAnimation(0, 1);
            long duration = 2000L;
            animation.setDuration(duration);
            animation.setInterpolator(new LinearInterpolator());
            marker.setAnimation(animation);
            marker.startAnimation();

        }


    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        isCheck = false;
        mCode = 0;
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        mCode = 0;
        isCheck = false;
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            //去除城市

            mTvCity.setText(regeocodeResult.getRegeocodeAddress().getCity());
            String address = regeocodeResult.getRegeocodeAddress().getFormatAddress().replace(regeocodeResult.getRegeocodeAddress().getCity() + regeocodeResult.getRegeocodeAddress().getDistrict(), "");
            if (isStart) {
                mTvAddress.setText(address);
            } else {

                mTvAddress.setText(address);
                beginTransAtranslationYnimation();
            }


        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    boolean isStart;

    private void beginTransAtranslationYnimation() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(mImgLocatin, "translationY", DensityUtils.dp2px(getActivity(), -90), 0);
            mAnimator.setInterpolator(new JumpInterpolator());
            mAnimator.setDuration(1200);
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mLlAddress.setVisibility(View.GONE);
                    isStart = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    isStart = false;
                    if (mTvAddress != null && !TextUtils.isEmpty(mTvAddress.getText().toString().trim()))
                        mLlAddress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isStart = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    isStart = false;
                }
            });
        }

        mAnimator.start();


    }

    class LocalImageHolderView implements Holder<AdInfo> {
        private SimpleDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleDraweeView(context);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, AdInfo data) {
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(DensityUtils.dp2px(getActivity(), 6));
            GenericDraweeHierarchy hierarchy = builder
                    .setFadeDuration(300)
                    .setPlaceholderImage(R.drawable.placeholder_pic)
                    .setRoundingParams(roundingParams)
                    .setFailureImage(R.drawable.placeholder_pic)
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                    .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .build();
            imageView.setHierarchy(hierarchy);
            imageView.setImageURI(Uri.parse(data.getAdPicUrl()));
        }
    }
}
