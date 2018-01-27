package com.biaoyuan.transfer.ui.transfer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.transformer.BGAPageTransformer;
import com.and.yzy.frame.transformer.TransitionEffect;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.TransferMyTripAdapter;
import com.biaoyuan.transfer.adapter.TransferRecyclerViewAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.AdInfo;
import com.biaoyuan.transfer.domain.ConditionItem;
import com.biaoyuan.transfer.domain.TransferMyTripInfo;
import com.biaoyuan.transfer.domain.TransferRecItem;
import com.biaoyuan.transfer.http.Image;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.http.Transfer;
import com.biaoyuan.transfer.ui.WebViewActivity;
import com.biaoyuan.transfer.ui.index.IndexMessageActivity;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.ui.mine.MineAuthenticationActivity;
import com.biaoyuan.transfer.ui.mine.MineTransferStateAty;
import com.biaoyuan.transfer.ui.mine.MineTripActivity;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.library.EasyGuide;
import com.yuyh.library.support.HShape;
import com.yuyh.library.support.OnStateChangedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :
 * Create : 2017/4/24
 * Author ：chen
 */
public class TransferFgt extends BaseFgt {
    EasyGuide easyGuide;
    @Bind(R.id.transfer_recyclerview)
    RecyclerView mTransferRecyclerview;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    //适配器
    private TransferRecyclerViewAdapter adapter;
    //recyclerView布局管理器
    private RecyclerView.LayoutManager mRecyclerViewlayoutManager;

    //View
    private View view;
    //传送抢单
    private RelativeLayout relativeLayoutRob;
    //发布行程
    private RelativeLayout relativeLayoutTrip;
    private RelativeLayout relativeLayoutTripMore;
    private RelativeLayout rl_rob_more;

    //我的行程适配器 listview
    private TransferMyTripAdapter myTripAdapter;
    //我的行程数据源
    private List<TransferMyTripInfo> myTripInfoList;
    //我的行程Listview
    private ListViewForScrollView myTripListview;
    //我的行程 查看更多
    private TextView my_trip_more;

    ConvenientBanner mConvenientBanner;
    /**
     * 空视图
     */
    private LinearLayout mllNull;

    private List<AdInfo> mAdInfos;

    private boolean onLoadingData1 = false;
    private boolean onLoadingData2 = false;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_transfer_main;
    }

    @Override
    public void initData() {

        PtrInitHelper.initPtr(getActivity(), mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mTransferRecyclerview, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //查询包裹
                doHttp(RetrofitUtils.createApi(Transfer.class).packageView("1", "5", "1", UserManger.getLng(), UserManger.getLat(), null, null, null, null, null, null), 2);

                if (UserManger.isLogin()) {
                    doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 4);
                } else {
                    myTripListview.setVisibility(View.GONE);
                    relativeLayoutTripMore.setVisibility(View.GONE);
                }

            }
        });

        mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //实例化MyRecyclerViewAdapter，并加入数据
        adapter = new TransferRecyclerViewAdapter(R.layout.item_transfer_main, new ArrayList<TransferRecItem>());
        //设置布局管理器
        mTransferRecyclerview.setLayoutManager(mRecyclerViewlayoutManager);

        mTransferRecyclerview.setHasFixedSize(true);
        view = getActivity().getLayoutInflater().inflate(R.layout.fgt_transfer_main_hander, null, false);

        mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mConvenientBanner.setPageTransformer(BGAPageTransformer.getPageTransformer(TransitionEffect.Accordion));

        relativeLayoutRob = (RelativeLayout) view.findViewById(R.id.rob);
        relativeLayoutTrip = (RelativeLayout) view.findViewById(R.id.trip);
        rl_rob_more = (RelativeLayout) view.findViewById(R.id.rl_rob_more);
        relativeLayoutTripMore = (RelativeLayout) view.findViewById(R.id.my_trip);
        mllNull = (LinearLayout) view.findViewById(R.id.ll_null);

        myTripListview = (ListViewForScrollView) view.findViewById(R.id.my_trip_list);
        myTripAdapter = new TransferMyTripAdapter(getActivity(), new ArrayList<TransferMyTripInfo>(), R.layout.item_transfer_my_trip);
        myTripListview.setAdapter(myTripAdapter);

        myTripListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TransferMyTripInfo info = myTripAdapter.getItem(position);

                ConditionItem conditionItem = new ConditionItem();

                conditionItem.setDate(DateTool.timesToStrTime(info.getDepartureTime() + "", "yyyy.MM.dd"));
                conditionItem.setHour(DateTool.timesToStrTime(info.getDepartureTime() + "", "HH:mm"));
                conditionItem.setTime(info.getDepartureTime() + "");
                conditionItem.setStartAddress(info.getOutCity() + info.getOutArea());
                conditionItem.setEndAddress(info.getEntCity() + info.getEntArea());
                conditionItem.setStartCityCode(info.getOutAreaParentCode());
                conditionItem.setEndCityCode(info.getEntAreaParentCode());
                conditionItem.setStartStreetCode(info.getOutAreaCode());
                conditionItem.setEndStreetCode(info.getEntAreaCode());
                Bundle bundle1 = new Bundle();

                bundle1.putParcelable("data", conditionItem);


                startActivity(TransferRecommendRobActivity.class, bundle1);
            }
        });


        my_trip_more = (TextView) view.findViewById(R.id.my_trip_more);

        relativeLayoutRob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TransferRobActivity.class, null);
            }
        });


        rl_rob_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TransferRobActivity.class, null);
            }
        });

        //查看更多行程
        relativeLayoutTripMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求传送员状态
                if (UserManger.isLogin()) {
                    doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 6);
                } else {
                    startActivity(LoginAty.class, null);
                }
            }
        });

        //发布行程
        relativeLayoutTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //请求传送员状态
                if (UserManger.isLogin()) {
                    doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 5);
                } else {
                    startActivity(LoginAty.class, null);
                }


            }
        });


        adapter.addHeaderView(view);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mTransferRecyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mTransferRecyclerview.setAdapter(adapter);

        mTransferRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                TransferRecItem recItem = (TransferRecItem) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("publishId", recItem.getPublishId() + "");
                startActivity(TransferOrderDetailsActivity.class, bundle);
            }
        });


    }


    @Override
    public void onUserInvisible() {
        super.onUserInvisible();
        if (mConvenientBanner != null && mAdInfos != null && mAdInfos.size() > 1) {
            mConvenientBanner.stopTurning();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();
    }


    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {

        //请求广告页
        doHttp(RetrofitUtils.createApi(Image.class).listAdvertisement("1", "用户端传送页"), 7);
        //请求传送员状态
        if (UserManger.isLogin()) {
            doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 4);
        } else {
            myTripListview.setVisibility(View.GONE);
            relativeLayoutTripMore.setVisibility(View.GONE);
        }

        isShowOnFailureToast = false;
        //查询包裹
        doHttp(RetrofitUtils.createApi(Transfer.class).packageView("1", "5", "1", UserManger.getLng(), UserManger.getLat(), null, null, null, null, null, null), 2);


    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        onLoadingData2 = false;
        onLoadingData1 = false;
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        onLoadingData2 = false;
        onLoadingData1 = false;
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
        switch (what) {
            case 3:
                new MaterialDialog(getActivity()).setMDMessage("您还不是传送天使，是否立即前往申请?")
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                startActivity(MineAuthenticationActivity.class, null);
                            }
                        }).show();
                break;

        }
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (mConvenientBanner != null && mAdInfos != null && mAdInfos.size() > 1) {
            mConvenientBanner.startTurning(5000);
        }
        //请求传送员状态
        if (UserManger.isLogin()) {
            if (!onLoadingData1) {
                doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 4);
            }
            onLoadingData1 = true;

        } else {
            myTripListview.setVisibility(View.GONE);
            relativeLayoutTripMore.setVisibility(View.GONE);
        }


        if (!onLoadingData2) {
            //查询包裹
            doHttp(RetrofitUtils.createApi(Transfer.class).packageView("1", "5", "1", UserManger.getLng(), UserManger.getLat(), null, null, null, null, null, null), 2);
        }
        onLoadingData2 = true;
    }

    // 须在View绘制完成之后调用，否则可能无法准确显示
    // offsetX:正数代表从屏幕左侧往右偏移距离，负数表示从屏幕右侧往左偏移距离。Constant.CENTER 表示居中
    // offsetY:同理。正数由上到下，负数由下到上。Constant.CENTER 表示居中
    public void show(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);

        View tipsView = createTipsView();

        if (easyGuide != null && easyGuide.isShowing())
            easyGuide.dismiss();

        easyGuide = new EasyGuide.Builder(getActivity())
                // 增加View高亮区域，可同时显示多个
                .addHightArea(view, HShape.RECTANGLE)
                // 复杂的提示布局，建议通过此方法，较容易控制
                .addView(tipsView, 0, loc[1] + view.getHeight(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                // 若点击作用在高亮区域，是否执行高亮区域的点击事件
                .performViewClick(true)
                .dismissAnyWhere(false)
                .build();

        easyGuide.setOnStateChangedListener(new OnStateChangedListener() {
            @Override
            public void onShow() {

            }

            @Override
            public void onDismiss() {

            }

            @Override
            public void onHeightlightViewClick(View view) {

            }
        });

        easyGuide.show();
    }

    private View createTipsView() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tip_view, null);

        ImageView img01 = (ImageView) view.findViewById(R.id.img_go);
        ImageView img02 = (ImageView) view.findViewById(R.id.img_dismiss);
        img01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyGuide.dismiss();
            }
        });
        img02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyGuide.dismiss();
            }
        });

        return view;
    }

    @OnClick({R.id.messgae})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.messgae:
                if (UserManger.isLogin()) {
                    startActivity(IndexMessageActivity.class, null);
                } else {
                    startActivity(LoginAty.class, null);
                }

                break;

        }
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        onLoadingData2 = false;
        onLoadingData1 = false;
        switch (what) {
            case 1:
                if (mPtrFrame != null) {
                    mPtrFrame.refreshComplete();
                }

                //我的行程
                myTripAdapter.removeAll();
                List<TransferMyTripInfo> tripInfos = AppJsonUtil.getArrayList(result, "pathList", TransferMyTripInfo.class);
                if (tripInfos == null || tripInfos.size() == 0) {
                    myTripListview.setVisibility(View.GONE);
                    relativeLayoutTripMore.setVisibility(View.GONE);
                } else {
                    myTripListview.setVisibility(View.VISIBLE);
                    relativeLayoutTripMore.setVisibility(View.VISIBLE);
                    myTripAdapter.addAll(tripInfos);
                }

                break;
            case 2:
                if (mPtrFrame != null) {
                    mPtrFrame.refreshComplete();
                }
                adapter.removeAll();
                List<TransferRecItem> packageorder = AppJsonUtil.getArrayList(result, "packageorder", TransferRecItem.class);
                if (packageorder != null && packageorder.size() > 0) {
                    mllNull.setVisibility(View.GONE);
                    adapter.addDatas(packageorder);
                } else {
                    mllNull.setVisibility(View.VISIBLE);
                }

                relativeLayoutTrip.post(new Runnable() {
                    @Override
                    public void run() {
                        if (UserManger.getIsFirst()) {
                            show(relativeLayoutTrip);
                            UserManger.setIsFirst(false);
                        }
                    }
                });


                break;
            case 3:

                int state = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "reviewStatus");


                Bundle bb = new Bundle();
                switch (state) {
                    case 0:
                        //申请中
                        bb.putInt("type", MineTransferStateAty.TYPE_COMMIT);

                        break;
                    case 1:

                        //申请成功
                        bb.putInt("type", MineTransferStateAty.TYPE_SUCCESS);
                        break;
                    case 2:
                        //被拒绝

                        bb.putInt("type", MineTransferStateAty.TYPE_ERROR);

                        break;
                }

                startActivity(MineTransferStateAty.class, bb);


                break;
            case 4:

                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));


                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {
                        //请求行程列表
                        isShowOnFailureToast = false;
                        doHttp(RetrofitUtils.createApi(Transfer.class).pathview("2", "1"), 1);

                    } else {
                        myTripListview.setVisibility(View.GONE);
                        relativeLayoutTripMore.setVisibility(View.GONE);
                    }

                } else {
                    myTripListview.setVisibility(View.GONE);
                    relativeLayoutTripMore.setVisibility(View.GONE);
                }


                break;
            case 5:
                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));

                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {
                        startActivityForResult(TransferReleaseTripActivity.class, null, 1);
                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {

                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 3);

                }

                break;
            case 6:
                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));

                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {
                        startActivity(MineTripActivity.class, null);
                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {

                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 3);

                }

                break;
            case 7:


                mAdInfos = AppJsonUtil.getArrayList(result, "advertisementList", AdInfo.class);


                if (mAdInfos == null && mAdInfos.size() == 0) {
                    return;
                }

                //设置尺寸
                String[] size = mAdInfos.get(0).getAdPicSize().split("\\*");

                int width = Integer.parseInt(size[0]);
                int height = Integer.parseInt(size[1]);
                //得到屏幕的宽度
                int screenWidth = DensityUtils.getScreenWidth(getActivity());
                int bannerHeight = screenWidth / (width / height);


                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mConvenientBanner.getLayoutParams();

                layoutParams.height = bannerHeight;


                mConvenientBanner.setLayoutParams(layoutParams);


                mConvenientBanner.setPages(
                        new CBViewHolderCreator<TransferFgt.LocalImageHolderView>() {
                            @Override
                            public TransferFgt.LocalImageHolderView createHolder() {
                                return new TransferFgt.LocalImageHolderView();
                            }
                        }, mAdInfos).setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
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
            GenericDraweeHierarchy hierarchy = builder
                    .setFadeDuration(300)
                    .setPlaceholderImage(R.drawable.placeholder_pic)
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
