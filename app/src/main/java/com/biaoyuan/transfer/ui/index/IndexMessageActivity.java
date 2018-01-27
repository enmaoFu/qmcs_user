package com.biaoyuan.transfer.ui.index;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.callback.ItemDragAndSwipeCallback;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemSwipeListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.IndexMessageAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.IndexMessageInfo;
import com.biaoyuan.transfer.domain.IndexMessageNewInfo;
import com.biaoyuan.transfer.http.Index;
import com.biaoyuan.transfer.ui.mine.refund.MineRefundDetailAty;
import com.biaoyuan.transfer.ui.mine.send.MineAddPinLunAty;
import com.biaoyuan.transfer.ui.mine.send.MineSendNoPayAty;
import com.biaoyuan.transfer.ui.mine.send.MineSendWaitSignAty;
import com.biaoyuan.transfer.ui.mine.send.MineSendWaitTakeAty;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 消息中心页面
 * Create : 2017/5/24
 * Author ：enmaoFu
 */
public class IndexMessageActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.ptr_frame)
    PtrFrameLayout ptrFrameLayout;

    @Bind(R.id.rv_data)
    RecyclerView mRecyclerView;
    private IndexMessageAdapter indexMessageAdapter;

    private int targetPage = 1;

    //用来标记是否在加载
    private boolean isLoading = false;

    private String messageId;
    private long orderId;
    private int excepType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_index_message;
    }

    @Override
    public void initData() {

        initToolbar(mToolbar, "消息中心");

        PtrInitHelper.initPtr(this, ptrFrameLayout);

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               /* ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 2000);*/

                //刷新页码归一，重新开启下拉加载
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Index.class).userMessage(UserManger.pageSize, targetPage), 1);
            }
        });

        //实例化布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化适配器
        indexMessageAdapter = new IndexMessageAdapter(R.layout.item_index_message, new ArrayList<IndexMessageNewInfo>());
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置间隔样式
        /*mTekeRecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());*/
        //大小不受适配器影响
        mRecyclerView.setHasFixedSize(true);
        //设置加载动画类型
        //      indexMessageAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置删除动画类型
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // 开启滑动删除
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(indexMessageAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        indexMessageAdapter.enableSwipeItem();

        indexMessageAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                messageId = String.valueOf(indexMessageAdapter.getItem(pos).getUmessageId());
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Index.class).deleteMessage(messageId), 3);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        });
        //设置item点击事件
        /*mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = indexMessageAdapter.getItem(position).getUmessageType();
                switch (type) {
                    case 0:
                        showLoadingDialog(null);
                        orderId = indexMessageAdapter.getItem(position).getOrderId();
                        doHttp(RetrofitUtils.createApi(Index.class).getOrderStatus(orderId), 4);
                        break;
                    case 3:
                        showLoadingDialog(null);
                        orderId = indexMessageAdapter.getItem(position).getOrderId();
                        excepType = indexMessageAdapter.getItem(position).getExcepType();
                        //    doHttp(RetrofitUtils.createApi(Index.class).getOrderStatus(orderId),5);
                        Bundle bundleNew = new Bundle();
                        bundleNew.putLong("orderId", orderId);
                        bundleNew.putInt("excepType", excepType);
                        switch (excepType) {

                            case 1:
                                //   helper.setTextViewText(R.id.tv_state, "取件拒收");

                                startActivity(MineRefundDetailAty.class, bundleNew);


                                break;
                            case 2:
                                //无偿取消

                                startActivity(MineRefundDetailAty.class, bundleNew);


                                break;

                            case 5:
                                //    helper.setTextViewText(R.id.tv_state, "快件丢失");
                                startActivity(MineRefundDetailAty.class, bundleNew);

                                break;
                            case 6:
                                //    helper.setTextViewText(R.id.tv_state, "快件破损");
                                startActivity(MineRefundDetailAty.class, bundleNew);
                                break;
                            case 9:
                                //有偿取消

                                startActivity(MineRefundDetailAty.class, bundleNew);

                                break;
                            case 10:
                                //   helper.setTextViewText(R.id.tv_state, "配送超时");


                                startActivity(MineRefundDetailAty.class, bundleNew);


                                break;
                            case 12:
                                //  接件人拒收


                                startActivity(MineRefundDetailAty.class, bundleNew);


                                break;

                        }

                        break;
                }
            }
        });*/
        setEmptyView(indexMessageAdapter, null);
        //设置adapter
        mRecyclerView.setAdapter(indexMessageAdapter);

        //上拉加载更多
        indexMessageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRecyclerView == null) {
                            return;
                        }
                        if (targetPage == 1) {
                            indexMessageAdapter.loadMoreEnd();
                            return;
                        }
                        doHttp(RetrofitUtils.createApi(Index.class).userMessage(UserManger.pageSize, targetPage), 2);
                    }
                });

            }
        }, mRecyclerView);

    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Index.class).userMessage(UserManger.pageSize, targetPage), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                ptrFrameLayout.refreshComplete();
                indexMessageAdapter.removeAll();
                List<IndexMessageInfo> indexMessageInfos = AppJsonUtil.getArrayList(result, "userMessage", IndexMessageInfo.class);

                if (indexMessageInfos != null) {
                    IndexMessageNewInfo indexMessageNewInfo;
                    List<IndexMessageNewInfo> indexMessageNewInfos = new ArrayList<>();
                    for (IndexMessageInfo imis : indexMessageInfos) {
                        indexMessageNewInfo = new IndexMessageNewInfo();
                        if (imis.getUmessageType() == 0) {
                            String content = imis.getUmessageContent();
                            String contentText = content.substring(0, content.lastIndexOf("！"));
                            String contenOrderId = content.substring(content.indexOf("{") + 1, content.lastIndexOf("}"));
                            String[] contenOrders = contenOrderId.split(",");
                            indexMessageNewInfo.setOrderId(Integer.parseInt(contenOrders[0]));
                            indexMessageNewInfo.setUmessageContent(contentText);
                            indexMessageNewInfo.setOrderStatus(Integer.parseInt(contenOrders[1]));
                            indexMessageNewInfo.setPayStatus(Integer.parseInt(contenOrders[2]));
                        } else if (imis.getUmessageType() == 3) {
                            String content = imis.getUmessageContent();
                            String contentText = content.substring(0, content.lastIndexOf("！"));
                            String contenOrderId = content.substring(content.indexOf("{") + 1, content.lastIndexOf("}"));
                            String[] contenExcepTypes = contenOrderId.split(",");
                            indexMessageNewInfo.setOrderId(Integer.parseInt(contenExcepTypes[0]));
                            indexMessageNewInfo.setExcepType(Integer.parseInt(contenExcepTypes[1]));
                            indexMessageNewInfo.setUmessageContent(contentText);
                        }
                        indexMessageNewInfo.setUmessageTitle(imis.getUmessageTitle());
                        indexMessageNewInfo.setUmessageCreatTime(imis.getUmessageCreatTime());
                        indexMessageNewInfo.setUmessageContent(imis.getUmessageContent());
                        indexMessageNewInfo.setUmessageType(imis.getUmessageType());
                        indexMessageNewInfo.setUmessageId(imis.getUmessageId());
                        indexMessageNewInfos.add(indexMessageNewInfo);
                    }
                    indexMessageAdapter.setNewData(indexMessageNewInfos);
                    if (indexMessageInfos.size() < Integer.parseInt(UserManger.pageSize)) {
                        indexMessageAdapter.loadMoreEnd();
                    }
                } else {
                    indexMessageAdapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;

                break;
            case 2:

                //加载更多
                List<IndexMessageInfo> indexMessageInfosTwo = AppJsonUtil.getArrayList(result, "userMessage", IndexMessageInfo.class);
                if (indexMessageInfosTwo != null && indexMessageInfosTwo.size() > 0) {
                    IndexMessageNewInfo indexMessageNewInfo;
                    List<IndexMessageNewInfo> indexMessageNewInfos = new ArrayList<>();
                    for (IndexMessageInfo imis : indexMessageInfosTwo) {
                        indexMessageNewInfo = new IndexMessageNewInfo();
                        if (imis.getUmessageType() == 0) {
                            String content = imis.getUmessageContent();
                            String contentText = content.substring(0, content.lastIndexOf("！"));
                            String contenOrderId = content.substring(content.indexOf("{") + 1, content.lastIndexOf("}"));
                            String[] contenOrders = contenOrderId.split(",");
                            indexMessageNewInfo.setOrderId(Integer.parseInt(contenOrders[0]));
                            indexMessageNewInfo.setUmessageContent(contentText);
                            indexMessageNewInfo.setOrderStatus(Integer.parseInt(contenOrders[1]));
                            indexMessageNewInfo.setPayStatus(Integer.parseInt(contenOrders[2]));
                        } else if (imis.getUmessageType() == 3) {
                            String content = imis.getUmessageContent();
                            String contentText = content.substring(0, content.lastIndexOf("！"));
                            String contenOrderId = content.substring(content.indexOf("{") + 1, content.lastIndexOf("}"));
                            String[] contenExcepTypes = contenOrderId.split(",");
                            indexMessageNewInfo.setOrderId(Integer.parseInt(contenExcepTypes[0]));
                            indexMessageNewInfo.setExcepType(Integer.parseInt(contenExcepTypes[1]));
                            indexMessageNewInfo.setUmessageContent(contentText);
                        }
                        indexMessageNewInfo.setUmessageTitle(imis.getUmessageTitle());
                        indexMessageNewInfo.setUmessageCreatTime(imis.getUmessageCreatTime());
                        indexMessageNewInfo.setUmessageContent(imis.getUmessageContent());
                        indexMessageNewInfo.setUmessageType(imis.getUmessageType());
                        indexMessageNewInfos.add(indexMessageNewInfo);
                    }
                    indexMessageAdapter.addDatas(indexMessageNewInfos);
                    indexMessageAdapter.loadMoreComplete();
                } else {
                    indexMessageAdapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;

                break;
            case 3:
                showToast("删除成功");
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Index.class).userMessage(UserManger.pageSize, targetPage), 1);
                break;
            case 4:
                int getPay = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "pay");
                int getStatus = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "status");
                String orderNo = AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "orderNo");


                Bundle bundle = new Bundle();
                bundle.putLong("orderId", orderId);
                bundle.putInt("orderStatus", getStatus);
                bundle.putInt("payStatus", getPay);
                bundle.putString("orderNo", orderNo);

                switch (getPay) {
                    case 0:
                        //未支付
                        startActivity(MineSendNoPayAty.class, bundle);

                        break;
                    case 1:

                        //追加支付
                        long addtionId = AppJsonUtil.getLong(AppJsonUtil.getString(result, "data"), "addtionId");
                        bundle.putLong("addtionId", addtionId);
                        startActivity(MineSendNoPayAty.class, bundle);


                        break;
                    case 2:

                        switch (getStatus) {
                            case 1:
                                //待确认
                                startActivity(MineSendWaitTakeAty.class, bundle);

                                break;
                            case 2:
                                //待取件
                                startActivity(MineSendWaitTakeAty.class, bundle);

                                break;
                            case 3:
                            case 4:
                            case 15:
                                //取件派送
                            case 5:
                                //传送员传送中
                            case 6:
                            case 7:
                            case 12:
                                //送达网点派送
                                startActivity(MineSendWaitSignAty.class, bundle);
                                break;


                            case 8:
                                //待评价
                                startActivity(MineAddPinLunAty.class, bundle);
                                break;
                            case 9:
                                //已完成
                                startActivity(MineSendWaitSignAty.class, bundle);
                                break;
                        }


                        break;
                }

                break;
            case 5:


                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        isLoading = false;
        if (ptrFrameLayout != null) {
            ptrFrameLayout.refreshComplete();
            indexMessageAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        isLoading = false;
        if (ptrFrameLayout != null) {
            ptrFrameLayout.refreshComplete();
            indexMessageAdapter.loadMoreComplete();
        }
    }

}
