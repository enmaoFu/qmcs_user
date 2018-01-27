package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineTripInfo {


    /**
     * pathDepartureTime : 1499130000000
     * pathDestinationId : 500232000
     * pathDeparture : {"entProvince":"重庆市","entArea":"江北区","entCity":"重庆"}
     * pathDepartureId : 500105000
     * pathDestination : {"entProvince":"重庆市","entArea":"武隆县","entCity":"重庆"}
     * pathId : 125
     */

    private long pathDepartureTime;

    private PathDepartureBean pathDeparture;

    private PathDestinationBean pathDestination;
    private long pathId;
    /**
     * parentPathDepartureId : 500103000
     * parentPathDestinationId : 500106000
     * pathDestinationId : 500106003
     * pathDeparture : {"entProvince":"重庆市","entArea":"渝中区","street":"大坪街道","entCity":"重庆"}
     * pathDepartureId : 500103012
     * pathDestination : {"entProvince":"重庆市","entArea":"沙坪坝区","street":"渝碚路街道","entCity":"重庆"}
     * pathId : 126
     */

    private int parentPathDepartureId;
    private int parentPathDestinationId;
    private int pathDestinationId;
    private int pathDepartureId;

    public int getParentPathDepartureId() {
        return parentPathDepartureId;
    }

    public void setParentPathDepartureId(int parentPathDepartureId) {
        this.parentPathDepartureId = parentPathDepartureId;
    }

    public int getParentPathDestinationId() {
        return parentPathDestinationId;
    }

    public void setParentPathDestinationId(int parentPathDestinationId) {
        this.parentPathDestinationId = parentPathDestinationId;
    }

    public int getPathDestinationId() {
        return pathDestinationId;
    }

    public void setPathDestinationId(int pathDestinationId) {
        this.pathDestinationId = pathDestinationId;
    }

    public long getPathDepartureTime() {
        return pathDepartureTime;
    }

    public void setPathDepartureTime(long pathDepartureTime) {
        this.pathDepartureTime = pathDepartureTime;
    }



    public PathDepartureBean getPathDeparture() {
        return pathDeparture;
    }

    public void setPathDeparture(PathDepartureBean pathDeparture) {
        this.pathDeparture = pathDeparture;
    }

    public int getPathDepartureId() {
        return pathDepartureId;
    }

    public void setPathDepartureId(int pathDepartureId) {
        this.pathDepartureId = pathDepartureId;
    }

    public PathDestinationBean getPathDestination() {
        return pathDestination;
    }

    public void setPathDestination(PathDestinationBean pathDestination) {
        this.pathDestination = pathDestination;
    }

    public long getPathId() {
        return pathId;
    }

    public void setPathId(long pathId) {
        this.pathId = pathId;
    }

    public static class PathDepartureBean {
        /**
         * entProvince : 重庆市
         * entArea : 江北区
         * entCity : 重庆
         */

        private String entProvince;
        private String entArea;
        private String entCity;
        private String street;

        public String getStreet() {
            if (street==null){
                return "";
            }
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getEntProvince() {
            return entProvince;
        }

        public void setEntProvince(String entProvince) {
            this.entProvince = entProvince;
        }

        public String getEntArea() {
            return entArea;
        }

        public void setEntArea(String entArea) {
            this.entArea = entArea;
        }

        public String getEntCity() {
            return entCity;
        }

        public void setEntCity(String entCity) {
            this.entCity = entCity;
        }
    }

    public static class PathDestinationBean {
        /**
         * entProvince : 重庆市
         * entArea : 武隆县
         * entCity : 重庆
         */

        private String entProvince;
        private String entArea;
        private String entCity;
        private String street;

        public String getStreet() {
            if (street==null){
                return "";
            }
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getEntProvince() {
            return entProvince;
        }

        public void setEntProvince(String entProvince) {
            this.entProvince = entProvince;
        }

        public String getEntArea() {
            return entArea;
        }

        public void setEntArea(String entArea) {
            this.entArea = entArea;
        }

        public String getEntCity() {
            return entCity;
        }

        public void setEntCity(String entCity) {
            this.entCity = entCity;
        }
    }
}
