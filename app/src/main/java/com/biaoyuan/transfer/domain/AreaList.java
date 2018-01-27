package com.biaoyuan.transfer.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AreaList implements Serializable {

    private ArrayList<AreaBean> AreaBeans;

    public ArrayList<AreaBean> getAreaBeans() {
        return AreaBeans;
    }

    public void setAreaBeans(ArrayList<AreaBean> areaBeans) {
        AreaBeans = areaBeans;
    }

    public static class AreaBean implements Serializable {
        private String Area_Code;
        private String Area_Name;
        private String Area_ParentCode;
        private String Area_Type;
        private String Area_City;
        private String Area_Order;
        private String Area_Status;

        public String getArea_Code() {
            return Area_Code;
        }

        public void setArea_Code(String area_Code) {
            Area_Code = area_Code;
        }

        public String getArea_Name() {
            return Area_Name;
        }

        public void setArea_Name(String area_Name) {
            Area_Name = area_Name;
        }

        public String getArea_ParentCode() {
            return Area_ParentCode;
        }

        public void setArea_ParentCode(String area_ParentCode) {
            Area_ParentCode = area_ParentCode;
        }

        public String getArea_Type() {
            return Area_Type;
        }

        public void setArea_Type(String area_Type) {
            Area_Type = area_Type;
        }

        public String getArea_City() {
            return Area_City;
        }

        public void setArea_City(String area_City) {
            Area_City = area_City;
        }

        public String getArea_Order() {
            return Area_Order;
        }

        public void setArea_Order(String area_Order) {
            Area_Order = area_Order;
        }

        public String getArea_Status() {
            return Area_Status;
        }

        public void setArea_Status(String area_Status) {
            Area_Status = area_Status;
        }

        @Override
        public String toString() {
            return "CityBean [Area_Code=" + Area_Code + ", Area_Name=" + Area_Name + ", Area_ParentCode="
                    + Area_ParentCode + ", Area_Type=" + Area_Type + ", Area_City=" + Area_City + ", Area_Order="
                    + Area_Order + ", Area_Status=" + Area_Status + "]";
        }

    }

    @Override
    public String toString() {
        return "AreaList [AreaBeans=" + AreaBeans + "]";
    }

}
