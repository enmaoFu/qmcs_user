package com.biaoyuan.transfer.util;

import android.os.Handler;
import android.os.Message;

import com.and.yzy.frame.application.BaseApplication;
import com.biaoyuan.transfer.domain.City;
import com.biaoyuan.transfer.domain.District;
import com.biaoyuan.transfer.domain.Province;
import com.biaoyuan.transfer.domain.Street;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Title  :
 * Create : 2017/5/25
 * Author ：chen
 */

public class ChooseAddresDataHelper {


    private static HashMap<Integer, ArrayList<Street>> mMap = new HashMap<>();
    public static ArrayList<Province> options1Items = new ArrayList<>();
    public static ArrayList<ArrayList<City>> options2Items = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<District>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;

    //判断是否加载成功
    public static boolean isLoad = false;
    //判断是否开始
    public static boolean isBegin = false;


    public static void clearData() {
        if (options1Items != null) {
            options1Items.clear();
            options2Items.clear();
            options3Items.clear();
            mMap.clear();
            isLoad = false;
            isBegin = false;
        }
    }

    public void initAddressData() {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();
        mMap.clear();
        isBegin = true;
        mHandler.sendEmptyMessage(0x0001);
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了


                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoad = true;
                    isBegin = false;
                    Logger.v("加载成功");
                   EventBus.getDefault().post(new City());
                    break;
            }
        }
    };

    // 获取本地json文件
    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(BaseApplication.getApplicationCotext().getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static ArrayList<Street> getStreet(int parentCode) {
        return mMap.get(parentCode);
    }


    private void initJsonData() {//解析数据

        try {
            /**
             * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
             * 关键逻辑在于循环体
             *
             * */
            String JsonData = getJson("address.json");//获取assets目录下的json文件数据

            ArrayList<Province> jsonBean = (ArrayList<Province>) AppJsonUtil.getMyArrayList(JsonData, Province.class);//用Gson 转成实体

            /**
             * 添加省份数据
             *
             * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
             * PickerView会通过getPickerViewText方法获取字符串显示出来。
             */
            options1Items = jsonBean;

            for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                ArrayList<City> CityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<District>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市

                    CityList.add(jsonBean.get(i).getCityList().get(c));//添加城市

                    ArrayList<District> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (jsonBean.get(i).getCityList().get(c).getAreaName() == null
                            || jsonBean.get(i).getCityList().get(c).getDistricts().size() == 0) {
                        City_AreaList.add(new District());
                    } else {

                        for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getDistricts().size(); d++) {//该城市对应地区所有数据
                            City_AreaList.add(jsonBean.get(i).getCityList().get(c).getDistricts().get(d));//添加该城市所有地区数据
                            for (Street street : jsonBean.get(i).getCityList().get(c).getDistricts().get(d).getStreets()) {
                                mMap.put(street.getAreaParentCode(), jsonBean.get(i).getCityList().get(c).getDistricts().get(d).getStreets());
                            }
                        }
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
                options3Items.add(Province_AreaList);
            }

            mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
