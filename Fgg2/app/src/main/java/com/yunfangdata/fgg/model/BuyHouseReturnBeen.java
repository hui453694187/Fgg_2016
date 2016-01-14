package com.yunfangdata.fgg.model;

/**
 *
 * 买房估值返回
 *
 * Created by zjt on 2015-12-13.
 */
public class BuyHouseReturnBeen {


    /**
     * guid : 52adabd2-0b4d-4eba-848f-098c6c472f15
     * rent : 9500
     * price : 70419
     * totalprice : 704
     * roomType : 9
     * mortgageUnitPrice : {"minPrice":63377.1,"maxPrice":66898.05}
     * mortgageTotalPrice : {"minPrice":633.6,"maxPrice":668.8}
     */

    private DataEntity Data;
    /**
     * Data : {"guid":"52adabd2-0b4d-4eba-848f-098c6c472f15","rent":9500,"price":70419,"totalprice":704,"roomType":9,"mortgageUnitPrice":{"minPrice":63377.1,"maxPrice":66898.05},"mortgageTotalPrice":{"minPrice":633.6,"maxPrice":668.8}}
     * Success : true
     */

    private boolean Success;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public DataEntity getData() {
        return Data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public static class DataEntity {
        private String guid;
        private int rent;
        private int price;
        private int totalprice;
        private int roomType;




        /**
         * minPrice : 63377.1
         * maxPrice : 66898.05
         */

        private MortgageUnitPriceEntity mortgageUnitPrice;
        /**
         * minPrice : 633.6
         * maxPrice : 668.8
         */

        private MortgageTotalPriceEntity mortgageTotalPrice;

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public void setRent(int rent) {
            this.rent = rent;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setTotalprice(int totalprice) {
            this.totalprice = totalprice;
        }

        public void setRoomType(int roomType) {
            this.roomType = roomType;
        }

        public void setMortgageUnitPrice(MortgageUnitPriceEntity mortgageUnitPrice) {
            this.mortgageUnitPrice = mortgageUnitPrice;
        }

        public void setMortgageTotalPrice(MortgageTotalPriceEntity mortgageTotalPrice) {
            this.mortgageTotalPrice = mortgageTotalPrice;
        }

        public String getGuid() {
            return guid;
        }

        public int getRent() {
            return rent;
        }

        public int getPrice() {
            return price;
        }

        public int getTotalprice() {
            return totalprice;
        }

        public int getRoomType() {
            return roomType;
        }

        public MortgageUnitPriceEntity getMortgageUnitPrice() {
            return mortgageUnitPrice;
        }

        public MortgageTotalPriceEntity getMortgageTotalPrice() {
            return mortgageTotalPrice;
        }

        public static class MortgageUnitPriceEntity {
            private double minPrice;
            private double maxPrice;

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public double getMaxPrice() {
                return maxPrice;
            }
        }

        public static class MortgageTotalPriceEntity {
            private double minPrice;
            private double maxPrice;

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public double getMaxPrice() {
                return maxPrice;
            }
        }
    }
}
