package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 获取小区周边详情的Been
 *
 * Created by zjt on 2015-12-11.
 */
public class AroundResidentialBeen {


    /**
     * startTime : 2015-45-11 11:45:40
     * resultData : {"busStationMap":[{"Name":"稻香园","Distance":375.77,"Type":"公交","Y":39.978508,"X":116.30954},{"Name":"北京市地震局","Distance":470.07,"Type":"公交","Y":39.98572,"X":116.31248},{"Name":"人民大学西门","Distance":525.45,"Type":"公交","Y":39.97768,"X":116.31325},{"Name":"巴沟村","Distance":537.1,"Type":"公交","Y":39.980503,"X":116.304245},{"Name":"万泉河路","Distance":570.54,"Type":"公交","Y":39.976906,"X":116.31217},{"Name":"海淀南路西口","Distance":76.89,"Type":"公交","Y":39.981365,"X":116.30965},{"Name":"海淀南路","Distance":432.64,"Type":"公交","Y":39.981724,"X":116.315384},{"Name":"万泉河桥南","Distance":490.03,"Type":"公交","Y":39.98562,"X":116.30736},{"Name":"万柳中路北口","Distance":697.09,"Type":"公交","Y":39.979298,"X":116.302826},{"Name":"海淀中街","Distance":652.63,"Type":"公交","Y":39.983585,"X":116.31762},{"Name":"巴沟村公交场站","Distance":650.06,"Type":"公交","Y":39.98077,"X":116.3028}],"bankMap":[{"Name":"中国农业银行(海淀大街支行)","Distance":266.08,"Type":"银行","Y":116.31232,"X":39.983665},{"Name":"中国民生银行(苏州街支行)","Distance":263.17,"Type":"银行","Y":116.31336,"X":39.98218},{"Name":"中国建设银行(万泉储蓄所)","Distance":711.52,"Type":"银行","Y":116.30747,"X":39.975815},{"Name":"中国建设银行(硅谷支行)","Distance":717.57,"Type":"银行","Y":116.31186,"X":39.98818},{"Name":"中国建设银行(工商大厦支行)","Distance":449.02,"Type":"银行","Y":116.31402,"X":39.978966},{"Name":"中国光大银行(苏州街支行电费代收点)","Distance":325.7,"Type":"银行","Y":116.31309,"X":39.983845},{"Name":"中国光大银行(苏州街支行)","Distance":312.18,"Type":"银行","Y":116.31297,"X":39.98376},{"Name":"中国工商银行(人民大学支行)","Distance":682.36,"Type":"银行","Y":116.313835,"X":39.976326},{"Name":"中国工商银行(北京苏州街支行)","Distance":359.07,"Type":"银行","Y":116.31285,"X":39.984413},{"Name":"兴业银行(海淀支行)","Distance":768.24,"Type":"银行","Y":116.31788,"X":39.985588},{"Name":"天津银行(北京中关村支行)","Distance":636.04,"Type":"银行","Y":116.31739,"X":39.983646},{"Name":"天津银行","Distance":609.77,"Type":"银行","Y":116.31737,"X":39.982727},{"Name":"上海浦东发展银行(中关村支行电费代收点)","Distance":609.98,"Type":"银行","Y":116.31745,"X":39.982174},{"Name":"上海浦东发展银行(中关村支行)","Distance":612.56,"Type":"银行","Y":116.31747,"X":39.982353},{"Name":"锦州银行(中关村支行)","Distance":721.62,"Type":"银行","Y":116.31545,"X":39.98699},{"Name":"交通银行(海淀支行)","Distance":480.54,"Type":"银行","Y":116.31286,"X":39.98569},{"Name":"江苏银行(北京中关村西区支行)","Distance":783.46,"Type":"银行","Y":116.31556,"X":39.987617},{"Name":"恒生银行(中关村支行)","Distance":779,"Type":"银行","Y":116.31777,"X":39.985886},{"Name":"东亚银行(中关村支行)","Distance":782.09,"Type":"银行","Y":116.31536,"X":39.98771},{"Name":"北京银行(航天支行)","Distance":168.36,"Type":"银行","Y":116.31221,"X":39.981426},{"Name":"北京农商银行（中关村支行）","Distance":611.49,"Type":"银行","Y":116.315674,"X":39.98549},{"Name":"包商银行(中关村支行)","Distance":772.86,"Type":"银行","Y":116.31538,"X":39.9876}],"subwayStationMap":[{"Name":"苏州街","Distance":308,"Type":"地铁","Y":39.981705,"X":116.31277},{"Name":"苏州街","Distance":221,"Type":"地铁","Y":39.981705,"X":116.31277},{"Name":"巴沟","Distance":799,"Type":"地铁","Y":39.98039,"X":116.30051},{"Name":"苏州街","Distance":234,"Type":"地铁","Y":39.981705,"X":116.31277},{"Name":"苏州街","Distance":171,"Type":"地铁","Y":39.981705,"X":116.31277}],"marketMap":[{"Name":"艾瑟顿商业广场","Distance":142.97,"Type":"商场","Y":116.30974,"X":39.980625}],"hospitalMap":[{"Name":"科发源植发医院","Distance":256.28,"Type":"医院","Y":39.98227,"X":116.31326},{"Name":"海淀区妇幼保健院（东南院区）","Distance":434.81,"Type":"医院","Y":39.978447,"X":116.31286},{"Name":"海淀区妇幼保健院","Distance":438.04,"Type":"医院","Y":39.97838,"X":116.312775},{"Name":"海淀妇幼保健院","Distance":438.04,"Type":"医院","Y":39.97838,"X":116.312775},{"Name":"海淀妇产医院","Distance":166.61,"Type":"医院","Y":39.98108,"X":116.30862},{"Name":"北京海淀妇产医院","Distance":157.11,"Type":"医院","Y":39.981377,"X":116.30856}],"supermarketMap":[{"Name":"华联超市（万柳店）","Distance":790.24,"Type":"超市","Y":116.3017,"X":39.979195},{"Name":"超市发超市(苏州街店)","Distance":672.7,"Type":"超市","Y":116.31205,"X":39.987736}]}
     * endTime : 2015-45-11 11:45:40
     * msg : request success
     * success : true
     */

    private String startTime;
    private ResultDataEntity resultData;
    private String endTime;
    private String msg;
    private String success;

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setResultData(ResultDataEntity resultData) {
        this.resultData = resultData;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStartTime() {
        return startTime;
    }

    public ResultDataEntity getResultData() {
        return resultData;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }

    public static class ResultDataEntity {
        /**
         * Name : 稻香园
         * Distance : 375.77
         * Type : 公交
         * Y : 39.978508
         * X : 116.30954
         */

        private List<BusStationMapEntity> busStationMap;
        /**
         * Name : 中国农业银行(海淀大街支行)
         * Distance : 266.08
         * Type : 银行
         * Y : 116.31232
         * X : 39.983665
         */

        private List<BankMapEntity> bankMap;
        /**
         * Name : 苏州街
         * Distance : 308
         * Type : 地铁
         * Y : 39.981705
         * X : 116.31277
         */

        private List<SubwayStationMapEntity> subwayStationMap;
        /**
         * Name : 艾瑟顿商业广场
         * Distance : 142.97
         * Type : 商场
         * Y : 116.30974
         * X : 39.980625
         */

        private List<MarketMapEntity> marketMap;
        /**
         * Name : 科发源植发医院
         * Distance : 256.28
         * Type : 医院
         * Y : 39.98227
         * X : 116.31326
         */

        private List<HospitalMapEntity> hospitalMap;
        /**
         * Name : 华联超市（万柳店）
         * Distance : 790.24
         * Type : 超市
         * Y : 116.3017
         * X : 39.979195
         */

        private List<SupermarketMapEntity> supermarketMap;

        public void setBusStationMap(List<BusStationMapEntity> busStationMap) {
            this.busStationMap = busStationMap;
        }

        public void setBankMap(List<BankMapEntity> bankMap) {
            this.bankMap = bankMap;
        }

        public void setSubwayStationMap(List<SubwayStationMapEntity> subwayStationMap) {
            this.subwayStationMap = subwayStationMap;
        }

        public void setMarketMap(List<MarketMapEntity> marketMap) {
            this.marketMap = marketMap;
        }

        public void setHospitalMap(List<HospitalMapEntity> hospitalMap) {
            this.hospitalMap = hospitalMap;
        }

        public void setSupermarketMap(List<SupermarketMapEntity> supermarketMap) {
            this.supermarketMap = supermarketMap;
        }

        public List<BusStationMapEntity> getBusStationMap() {
            return busStationMap;
        }

        public List<BankMapEntity> getBankMap() {
            return bankMap;
        }

        public List<SubwayStationMapEntity> getSubwayStationMap() {
            return subwayStationMap;
        }

        public List<MarketMapEntity> getMarketMap() {
            return marketMap;
        }

        public List<HospitalMapEntity> getHospitalMap() {
            return hospitalMap;
        }

        public List<SupermarketMapEntity> getSupermarketMap() {
            return supermarketMap;
        }

        public static class BusStationMapEntity {
            private String Name;
            private double Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(double Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public double getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }

        public static class BankMapEntity {
            private String Name;
            private double Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(double Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public double getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }

        public static class SubwayStationMapEntity {
            private String Name;
            private int Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(int Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public int getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }

        public static class MarketMapEntity {
            private String Name;
            private double Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(double Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public double getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }

        public static class HospitalMapEntity {
            private String Name;
            private double Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(double Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public double getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }

        public static class SupermarketMapEntity {
            private String Name;
            private double Distance;
            private String Type;
            private double Y;
            private double X;

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setDistance(double Distance) {
                this.Distance = Distance;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setY(double Y) {
                this.Y = Y;
            }

            public void setX(double X) {
                this.X = X;
            }

            public String getName() {
                return Name;
            }

            public double getDistance() {
                return Distance;
            }

            public String getType() {
                return Type;
            }

            public double getY() {
                return Y;
            }

            public double getX() {
                return X;
            }
        }
    }
}
