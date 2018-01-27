package com.biaoyuan.transfer.util;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.City;
import com.biaoyuan.transfer.domain.District;
import com.biaoyuan.transfer.domain.Province;
import com.biaoyuan.transfer.domain.Street;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Title  :获取开通城市
 * Create : 2017/5/25
 * Author ：chen
 */

public class ChooseSendAddresDataHelper {


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


    public static String data;
    public static long version;

    /**
     * 查看是否有保存地址文件
     *
     * @return
     */
    public boolean isSaveFile() {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "userData/send_address.json";
        } else
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "userData/send_address.json";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }


    public static void clearData() {
        if (options1Items != null) {
            options1Items.clear();
            options2Items.clear();
            options3Items.clear();
            mMap.clear();
            isLoad = false;
            isBegin = false;
            data = null;
        }
    }

    public void initAddressData(String data,long version) {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        this.data = data;
        mMap.clear();
        isBegin = true;
        isLoad = false;
        this.version=version;
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
                    Logger.v("开通城市数据加载成功");

                    break;
            }
        }
    };

    // 获取本地json文件
    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
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
            //先判断版本号
            if (data!=null) {
                //存数据
                Logger.v("更新地址版本");
                getProvince(AppJsonUtil.getString(AppJsonUtil.getString(data, "data"), "areaList"));

            } else {

                if (isSaveFile()) {
                    boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                    String filePath = null;
                    if (hasSDCard) {
                        filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "userData/send_address.json";
                    } else
                        filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "userData/send_address.json";


                    String JsonData = getJson(filePath);

                    ArrayList<Province> jsonBean = (ArrayList<Province>) AppJsonUtil.getMyArrayList(JsonData, Province.class);//用Gson 转成实体


                    Logger.v("size=="+jsonBean.size());
                    if (jsonBean == null || jsonBean.size() == 0) {
                        //重新设置版本号加载数据
                      UserManger.setSendAddressVersion(-1);
                        return;
                    }
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


                } else {
                    //存数据
                    //重新设置版本号加载数据
                    UserManger.setSendAddressVersion(-1);

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            UserManger.setSendAddressVersion(-1);
        }


    }

    /**
     * 读写json数据
     *
     * @param data
     * @return
     */
    public List<Province> getProvince(final String data) {

        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    long time = System.currentTimeMillis();
                    List<Province> list01 = AppJsonUtil.getMyArrayList(data, Province.class);
                    List<City> list02 = AppJsonUtil.getMyArrayList(data, City.class);
                    List<District> list03 = AppJsonUtil.getMyArrayList(data, District.class);
                    List<Street> list04 = AppJsonUtil.getMyArrayList(data, Street.class);

                    // 取出所有的省
                    ArrayList<Province> provinces = new ArrayList<>();
                    //取出所有的城市
                    List<City> cities = new ArrayList<>();
                    //取出所有的区
                    List<District> districts = new ArrayList<>();
                    //取出所有的街道
                    List<Street> streets = new ArrayList<>();


                    for (Province province : list01) {
                        if (province.getAreaType() == 2) {
                            provinces.add(province);
                        }
                    }
                    for (City province : list02) {
                        if (province.getAreaType() == 3) {
                            cities.add(province);
                        }
                    }
                    for (District province : list03) {
                        if (province.getAreaType() == 4) {
                            districts.add(province);
                        }
                    }
                    for (Street province : list04) {
                        if (province.getAreaType() == 5) {
                            streets.add(province);
                        }
                    }

                    //把街道设置给区
                    for (District district : districts) {
                        ArrayList<Street> temp = new ArrayList<>();

                        for (Street street : streets) {
                            if (district.getAreaCode() == street.getAreaParentCode()) {
                                temp.add(street);
                            }
                        }
                        district.setStreets(temp);
                    }

                    //把区设置给市

                    for (City city : cities) {
                        List<District> temp = new ArrayList<>();

                        for (District district : districts) {
                            if (city.getAreaCode() == district.getAreaParentCode()) {
                                temp.add(district);
                            }
                        }
                        city.setDistricts(temp);
                    }
                    //把市设置给省

                    for (Province province : provinces) {
                        List<City> temp = new ArrayList<>();
                        for (City city : cities) {
                            if (province.getAreaCode() == city.getAreaParentCode()) {
                                temp.add(city);
                            }
                        }
                        province.setCityList(temp);
                    }


                    /**
                     * 添加省份数据
                     *
                     * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
                     * PickerView会通过getPickerViewText方法获取字符串显示出来。
                     */
                    options1Items = provinces;

                    for (int i = 0; i < options1Items.size(); i++) {//遍历省份
                        ArrayList<City> CityList = new ArrayList<>();//该省的城市列表（第二级）
                        ArrayList<ArrayList<District>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                        for (int c = 0; c < options1Items.get(i).getCityList().size(); c++) {//遍历该省份的所有城市

                            CityList.add(options1Items.get(i).getCityList().get(c));//添加城市

                            ArrayList<District> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (options1Items.get(i).getCityList().get(c).getAreaName() == null
                                    || options1Items.get(i).getCityList().get(c).getDistricts().size() == 0) {
                                City_AreaList.add(new District());
                            } else {

                                for (int d = 0; d < options1Items.get(i).getCityList().get(c).getDistricts().size(); d++) {//该城市对应地区所有数据
                                    City_AreaList.add(options1Items.get(i).getCityList().get(c).getDistricts().get(d));//添加该城市所有地区数据
                                    for (Street street : options1Items.get(i).getCityList().get(c).getDistricts().get(d).getStreets()) {
                                        mMap.put(street.getAreaParentCode(), options1Items.get(i).getCityList().get(c).getDistricts().get(d).getStreets());
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


                    Log.v("result", "str==" + JSON.toJSONString(provinces));

                    saveFile(JSON.toJSONString(provinces));

                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            UserManger.setSendAddressVersion(-1);
        }

        return null;
    }


    public void saveFile(String str) {

        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "userData/send_address.json";
        } else
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "userData/send_address.json";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserManger.setSendAddressVersion(version);
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
}
