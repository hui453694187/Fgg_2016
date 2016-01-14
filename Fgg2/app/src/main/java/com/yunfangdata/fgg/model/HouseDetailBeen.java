package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 房子详情的业务类
 *
 * Created by zjt on 2015-12-11.
 */
public class HouseDetailBeen {



    public HouseDetailBeen(){

    }

    /**
     * ResidentialAreaName : 海淀南路31号院
     * RatioByLastMonthForPrice : -0.0021
     * RentPrice : 9500
     * CityName : null
     * ResidentialAreaID : 12944
     * FloorAreaRatio : 0.3
     * UnitPrice : 64200
     * RatioByLastYearForPrice : 0.15
     * RatioByLastMonthForRent : 0.0207
     * RoomType : 三居室
     * Address : 海淀南路31号
     * DistrictName : 海淀区
     * ImagesList : [{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd48b2ba1532f9d3cb7efdb46d252aa106b","Type":"原图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4945f11111af74887d31b739001042a9ae17c98035ac51c21","Type":"大图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd472cf1eb0b053311028bc56fa4bfd19a5c4b7952e953a44ee","Type":"中图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd488d9b13218c6c958d79e9d2c7519afe86727a52af4112edd","Type":"小图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd43d7b6cf719310aa7d6ce68d82d1af942","Type":"原图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd41698a6adc04670373674ae4553dedc37eacd4045232b51e3","Type":"大图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4147482c4cd1eed09874857b5c9da5ad638f542d6e4b941d8","Type":"中图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd44c1e74b6efda5e7f6780ece1a4e57af8aff4ce30322d1cd0","Type":"小图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4acb2e645694e88b5635d94574194fa04","Type":"原图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4a83e75c0181cbfb87ca132af05b5fb84a0e045fca08e441e","Type":"大图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4f056c32c0762285ebe34bee63a94215a39cf7bb4697857bd","Type":"中图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4603d5ab070a1298cc68800f9e4cc261b5cbc3a3e3d383873","Type":"小图","Title":"小区外环境"}]
     * Y : 39.981834
     * X : 116.31031
     * BuildingAreaInt : 135
     * RatioByLastYearForRent : 0.057
     * GreeningRate : 0.3
     */

    private ResultDataEntity resultData;
    /**
     * resultData : {"ResidentialAreaName":"海淀南路31号院","RatioByLastMonthForPrice":-0.0021,"RentPrice":9500,"CityName":null,"ResidentialAreaID":12944,"FloorAreaRatio":0.3,"UnitPrice":64200,"RatioByLastYearForPrice":0.15,"RatioByLastMonthForRent":0.0207,"RoomType":"三居室","Address":"海淀南路31号","DistrictName":"海淀区","ImagesList":[{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd48b2ba1532f9d3cb7efdb46d252aa106b","Type":"原图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4945f11111af74887d31b739001042a9ae17c98035ac51c21","Type":"大图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd472cf1eb0b053311028bc56fa4bfd19a5c4b7952e953a44ee","Type":"中图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd488d9b13218c6c958d79e9d2c7519afe86727a52af4112edd","Type":"小图","Title":"小区入口"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd43d7b6cf719310aa7d6ce68d82d1af942","Type":"原图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd41698a6adc04670373674ae4553dedc37eacd4045232b51e3","Type":"大图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4147482c4cd1eed09874857b5c9da5ad638f542d6e4b941d8","Type":"中图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd44c1e74b6efda5e7f6780ece1a4e57af8aff4ce30322d1cd0","Type":"小图","Title":"小区内环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4acb2e645694e88b5635d94574194fa04","Type":"原图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4a83e75c0181cbfb87ca132af05b5fb84a0e045fca08e441e","Type":"大图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4f056c32c0762285ebe34bee63a94215a39cf7bb4697857bd","Type":"中图","Title":"小区外环境"},{"Url":"http://res.yunfangdata.com/yf/file/b8b8002631fdfdd4603d5ab070a1298cc68800f9e4cc261b5cbc3a3e3d383873","Type":"小图","Title":"小区外环境"}],"Y":39.981834,"X":116.31031,"BuildingAreaInt":135,"RatioByLastYearForRent":0.057,"GreeningRate":0.3}
     * startTime : 2015-19-11 09:19:02
     * message : request success
     * endTime : 2015-19-11 09:19:02
     * success : true
     */

    private String startTime;
    private String message;
    private String endTime;
    private String success;

    @Override
    public String toString() {
        return "HouseDetailBeen{" +
                "resultData=" + resultData +
                ", startTime='" + startTime + '\'' +
                ", message='" + message + '\'' +
                ", endTime='" + endTime + '\'' +
                ", success='" + success + '\'' +
                '}';
    }

    public void setResultData(ResultDataEntity resultData) {
        this.resultData = resultData;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResultDataEntity getResultData() {
        return resultData;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getMessage() {
        return message;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSuccess() {
        return success;
    }

    public static class ResultDataEntity {
        private String ResidentialAreaName;
        private double RatioByLastMonthForPrice;
        private int RentPrice;
        private String CityName;
        private int ResidentialAreaID;
        private double FloorAreaRatio;
        private int UnitPrice;
        private double RatioByLastYearForPrice;
        private double RatioByLastMonthForRent;
        private String RoomType;
        private String Address;
        private String DistrictName;
        private double Y;
        private double X;
        private int BuildingAreaInt;
        private double RatioByLastYearForRent;
        private double GreeningRate;

        @Override
        public String toString() {
            return "ResultDataEntity{" +
                    "ResidentialAreaName='" + ResidentialAreaName + '\'' +
                    ", RatioByLastMonthForPrice=" + RatioByLastMonthForPrice +
                    ", RentPrice=" + RentPrice +
                    ", CityName=" + CityName +
                    ", ResidentialAreaID=" + ResidentialAreaID +
                    ", FloorAreaRatio=" + FloorAreaRatio +
                    ", UnitPrice=" + UnitPrice +
                    ", RatioByLastYearForPrice=" + RatioByLastYearForPrice +
                    ", RatioByLastMonthForRent=" + RatioByLastMonthForRent +
                    ", RoomType='" + RoomType + '\'' +
                    ", Address='" + Address + '\'' +
                    ", DistrictName='" + DistrictName + '\'' +
                    ", Y=" + Y +
                    ", X=" + X +
                    ", BuildingAreaInt=" + BuildingAreaInt +
                    ", RatioByLastYearForRent=" + RatioByLastYearForRent +
                    ", GreeningRate=" + GreeningRate +
                    ", ImagesList=" + ImagesList +
                    '}';
        }

        /**
         * Url : http://res.yunfangdata.com/yf/file/b8b8002631fdfdd48b2ba1532f9d3cb7efdb46d252aa106b
         * Type : 原图
         * Title : 小区入口
         */

        private List<ImagesListEntity> ImagesList;

        public void setResidentialAreaName(String ResidentialAreaName) {
            this.ResidentialAreaName = ResidentialAreaName;
        }

        public void setRatioByLastMonthForPrice(double RatioByLastMonthForPrice) {
            this.RatioByLastMonthForPrice = RatioByLastMonthForPrice;
        }

        public void setRentPrice(int RentPrice) {
            this.RentPrice = RentPrice;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public void setResidentialAreaID(int ResidentialAreaID) {
            this.ResidentialAreaID = ResidentialAreaID;
        }

        public void setFloorAreaRatio(double FloorAreaRatio) {
            this.FloorAreaRatio = FloorAreaRatio;
        }

        public void setUnitPrice(int UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public void setRatioByLastYearForPrice(double RatioByLastYearForPrice) {
            this.RatioByLastYearForPrice = RatioByLastYearForPrice;
        }

        public void setRatioByLastMonthForRent(double RatioByLastMonthForRent) {
            this.RatioByLastMonthForRent = RatioByLastMonthForRent;
        }

        public void setRoomType(String RoomType) {
            this.RoomType = RoomType;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public void setDistrictName(String DistrictName) {
            this.DistrictName = DistrictName;
        }

        public void setY(double Y) {
            this.Y = Y;
        }

        public void setX(double X) {
            this.X = X;
        }

        public void setBuildingAreaInt(int BuildingAreaInt) {
            this.BuildingAreaInt = BuildingAreaInt;
        }

        public void setRatioByLastYearForRent(double RatioByLastYearForRent) {
            this.RatioByLastYearForRent = RatioByLastYearForRent;
        }

        public void setGreeningRate(double GreeningRate) {
            this.GreeningRate = GreeningRate;
        }

        public void setImagesList(List<ImagesListEntity> ImagesList) {
            this.ImagesList = ImagesList;
        }

        public String getResidentialAreaName() {
            return ResidentialAreaName;
        }

        public double getRatioByLastMonthForPrice() {
            return RatioByLastMonthForPrice;
        }

        public int getRentPrice() {
            return RentPrice;
        }

        public String getCityName() {
            return CityName;
        }

        public int getResidentialAreaID() {
            return ResidentialAreaID;
        }

        public double getFloorAreaRatio() {
            return FloorAreaRatio;
        }

        public int getUnitPrice() {
            return UnitPrice;
        }

        public double getRatioByLastYearForPrice() {
            return RatioByLastYearForPrice;
        }

        public double getRatioByLastMonthForRent() {
            return RatioByLastMonthForRent;
        }

        public String getRoomType() {
            return RoomType;
        }

        public String getAddress() {
            return Address;
        }

        public String getDistrictName() {
            return DistrictName;
        }

        public double getY() {
            return Y;
        }

        public double getX() {
            return X;
        }

        public int getBuildingAreaInt() {
            return BuildingAreaInt;
        }

        public double getRatioByLastYearForRent() {
            return RatioByLastYearForRent;
        }

        public double getGreeningRate() {
            return GreeningRate;
        }

        public List<ImagesListEntity> getImagesList() {
            return ImagesList;
        }

        public static class ImagesListEntity {
            private String Url;
            private String Type;
            private String Title;

            @Override
            public String toString() {
                return "ImagesListEntity{" +
                        "Url='" + Url + '\'' +
                        ", Type='" + Type + '\'' +
                        ", Title='" + Title + '\'' +
                        '}';
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getUrl() {
                return Url;
            }

            public String getType() {
                return Type;
            }

            public String getTitle() {
                return Title;
            }
        }
    }
}
