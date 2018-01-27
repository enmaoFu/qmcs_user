package com.biaoyuan.transfer.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;

import com.and.yzy.frame.adapter.AdapterCallback;
import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.PicInfo;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Title  :
 * Create : 2017/6/2
 * Author ：chen
 */

public class AddPicAdapter extends CommonAdapter<PicInfo> {


    public AddPicAdapter(Context context, List<PicInfo> mDatas, int itemLayoutId, AdapterCallback adapterCallback) {
        super(context, mDatas, itemLayoutId, adapterCallback);
    }


    @Override
    public void convert(ViewHolder holder, final PicInfo item, int positon) {
        Logger.v("=======");
        holder.setImageByBitmap(R.id.img, BitmapFactory.decodeFile(item.getPath()));
        holder.setOnClick(R.id.img_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeInfo(item);
                adapterCallback.adapterInfotoActiity(null, 1);
            }
        });
    }
}
