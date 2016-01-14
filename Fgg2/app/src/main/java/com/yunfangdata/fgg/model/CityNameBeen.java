package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 城市列表
 * <p/>
 * Created by zjt on 2015-12-18.
 */
public class CityNameBeen {

    public List<provinceEntity> provinceEntity;

    public List<CityNameBeen.provinceEntity> getProvinceEntity() {
        return provinceEntity;
    }

    public void setProvinceEntity(List<CityNameBeen.provinceEntity> provinceEntity) {
        this.provinceEntity = provinceEntity;
    }

    public static class provinceEntity {
        public String provinceName;
        public List<cityEntity> cityEntity;

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public List<CityNameBeen.provinceEntity.cityEntity> getCityEntity() {
            return cityEntity;
        }

        public void setCityEntity(List<CityNameBeen.provinceEntity.cityEntity> cityEntity) {
            this.cityEntity = cityEntity;
        }

        public static class cityEntity {
            public String cityName;
            public countyEntity countyEntity;

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public CityNameBeen.provinceEntity.cityEntity.countyEntity getCountyEntity() {
                return countyEntity;
            }

            public void setCountyEntity(CityNameBeen.provinceEntity.cityEntity.countyEntity countyEntity) {
                this.countyEntity = countyEntity;
            }

            public static class countyEntity {
                public List<String> countyName;

                public List<String> getCountyName() {
                    return countyName;
                }

                public void setCountyName(List<String> countyName) {
                    this.countyName = countyName;
                }
            }
        }
    }

}
