package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineCommonBankInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MineCommonBankAdapter extends BaseQuickAdapter<MineCommonBankInfo,BaseViewHolder>{

    public MineCommonBankAdapter(int layoutResId, List<MineCommonBankInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineCommonBankInfo item, int position) {

        String getCardNumber = item.getCardNo();
        String getCardNumberStr = getCardNumber.substring(getCardNumber.length() - 4,getCardNumber.length());

        String[] sourceStrArray = item.getCardBank().split("·");
        String getCardName = sourceStrArray[0];
        String getCardNumberName = sourceStrArray[1];
        //Logger.v(sourceStrArray[0] + "------------" + sourceStrArray[1]);

        helper.setTextViewText(R.id.card_name,getCardName);
        helper.setTextViewText(R.id.card_number_name,getCardNumberName);
        helper.setTextViewText(R.id.carde_number_d,getCardNumberStr);

        if(getCardName.contains("光大")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_guangda3x);
        }else if(getCardName.contains("恒丰")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_hengfeng3x);
        }else if(getCardName.contains("华夏")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_huaxia3x);
        }else if(getCardName.contains("工商")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_icbc3x);
        }else if(getCardName.contains("建设")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_jianshe3x);
        }else if(getCardName.contains("交通")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_jiaotong3x);
        }else if(getCardName.contains("民生")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_minsheng3x);
        }else if(getCardName.contains("南京")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_nanjing3x);
        }else if(getCardName.contains("宁波")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_ningbio3x);
        }else if(getCardName.contains("农业")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_nongye3x);
        }else if(getCardName.contains("浦东")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_pudong3x);
        }else if(getCardName.contains("人民")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_renmin3x);
        }else if(getCardName.contains("上海")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_shanghai3x);
        }else if(getCardName.contains("深圳")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_shenzhen3x);
        }else if(getCardName.contains("招商")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_zhaoshang3x);
        }else if(getCardName.contains("浙商")){
            helper.setImageByResource(R.id.mine_img,R.drawable.icon_zheshang3x);
        }else if(getCardName.contains("兴业")){
            helper.setImageByResource(R.id.mine_img,R.drawable.xingye3x);
        }else if(getCardName.contains("邮政")){
            helper.setImageByResource(R.id.mine_img,R.drawable.youzheng3x);
        }else if(getCardName.contains("中国")){
            helper.setImageByResource(R.id.mine_img,R.drawable.zhongguo3x);
        }else if(getCardName.contains("中信")){
            helper.setImageByResource(R.id.mine_img,R.drawable.zhongxin3x);
        }else{
            helper.setImageByResource(R.id.mine_img,R.drawable.yinhka);
        }

        int i = position % 3;
        if(i == 0){
            helper.setBackgroundDrawable(R.id.re,mContext.getResources().getDrawable(R.drawable.shape_bule_round4));
        }else if(i == 1){
            helper.setBackgroundDrawable(R.id.re,mContext.getResources().getDrawable(R.drawable.shape_red_round4));
        }else if(i == 2){
            helper.setBackgroundDrawable(R.id.re,mContext.getResources().getDrawable(R.drawable.shape_hui_round4));
        }

        helper.addOnClickListener(R.id.delete);

    }
}
