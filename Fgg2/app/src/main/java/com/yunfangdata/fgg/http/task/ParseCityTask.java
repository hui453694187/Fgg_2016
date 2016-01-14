package com.yunfangdata.fgg.http.task;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfangdata.fgg.model.CityNameBeen;
import com.yunfangdata.fgg.utils.ZLog;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 解析城市列表
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class ParseCityTask implements IRequestTask {

    private static final String TAG = "ParseCityTask";
    private static final int LOGLEVEL = 1;

    private Context context;

    public ParseCityTask(Context context) {
        this.context = context;

    }

    /**
     * 响应数据
     */
    private byte[] mData;

    @Override
    public void setContext(byte[] data) {
        this.mData = data;
    }

    @Override
    public List<String> getResponseData() {
        return null;
    }


    public boolean request(OptionsPickerView CityPickView, ArrayList<String> options1Items,
                           ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        return readCiytXmL(CityPickView, options1Items, options2Items, options3Items);
    }


    /**
     * 读取城市列表
     */
    private boolean readCiytXmL(OptionsPickerView CityPickView, ArrayList<String> options1Items,
                                ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        CityNameBeen cityNameBeen = new CityNameBeen();
        List<CityNameBeen.provinceEntity> provinceEntity = new ArrayList<CityNameBeen.provinceEntity>();
        try {
            InputStream is = context.getAssets().open("area.xml");
            SAXReader sr = new SAXReader();// 获取读取xml的对象。
            Document document = sr.read(is);
            //	1.获取文档的根节点.
            Element root = document.getRootElement();
            //路线名字
            if (root.element("array") != null) {
                Element array = root.element("array");
                //省列表
                List provinceElements = array.elements("dict");

                ZLog.Zlogi("省列表 provinceElements.size()= " + provinceElements.size(), TAG, LOGLEVEL);
                if (provinceElements != null && provinceElements.size() > 0) {
                    for (Iterator iterator = provinceElements.iterator(); iterator.hasNext(); ) {
                        Element provinceItem = (Element) iterator.next();
                        CityNameBeen.provinceEntity pitem = new CityNameBeen.provinceEntity();
                        pitem.setProvinceName(provinceItem.element("string").getText());

                        ZLog.Zlogi("省列表 = " + provinceItem.element("string").getText(), TAG, LOGLEVEL);

                        List<CityNameBeen.provinceEntity.cityEntity> cityEntity = new ArrayList<CityNameBeen.provinceEntity.cityEntity>();
                        //市
                        Element cityarray = provinceItem.element("array");
                        List cityElements = cityarray.elements("dict");
                        if (cityElements != null && cityElements.size() > 0) {
                            for (Iterator iterator2 = cityElements.iterator(); iterator2.hasNext(); ) {
                                Element cityItem = (Element) iterator2.next();

                                CityNameBeen.provinceEntity.cityEntity cityEntityItem = new CityNameBeen.provinceEntity.cityEntity();
                                cityEntityItem.setCityName(cityItem.element("string").getText());
                                ZLog.Zlogi("市列表 = " + cityItem.element("string").getText(), TAG, LOGLEVEL);
                                //县
                                if (cityItem.elements("array") != null) {
                                    Element countItem = cityItem.element("array");
                                    List countyArray = countItem.elements("string");
                                    if (countyArray != null && countyArray.size() > 0) {
                                        CityNameBeen.provinceEntity.cityEntity.countyEntity countyEntity = new CityNameBeen.provinceEntity.cityEntity.countyEntity();
                                        List<String> countyName = new ArrayList<String>();

                                        for (Iterator iterator3 = countyArray.iterator(); iterator3.hasNext(); ) {
                                            Element countyItem = (Element) iterator3.next();
                                            if (countyItem.getText() != null) {
                                                countyName.add(countyItem.getText());
//                                                ZLog.Zlogi("县列表 = " + countyItem.getText(), TAG, 2);
                                            }
                                        }
                                        countyEntity.setCountyName(countyName);

                                        cityEntityItem.setCountyEntity(countyEntity);
                                    }

                                }
                                cityEntity.add(cityEntityItem);
                            }
                        }
                        pitem.setCityEntity(cityEntity);
                        provinceEntity.add(pitem);
                    }
                }
            }

            ZLog.Zlogi("provinceEntity.size(); = " + provinceEntity.size(), TAG, LOGLEVEL);
            cityNameBeen.setProvinceEntity(provinceEntity);
            //城市选择
            return intiCityPick(CityPickView, cityNameBeen, options1Items, options2Items, options3Items);
        } catch (Exception e) {
            // Should never happen!
            e.printStackTrace();

        }

        return false;
    }

    /**
     * 城市选择
     *
     * @param CityPickView
     * @param cityNameBeen
     * @param options1Items
     * @param options2Items
     * @param options3Items
     */
    private boolean intiCityPick(OptionsPickerView CityPickView, CityNameBeen cityNameBeen, ArrayList<String> options1Items, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        if (cityNameBeen != null) {
            List<CityNameBeen.provinceEntity> provinceEntity = cityNameBeen.getProvinceEntity();
            //选项1
            for (int i = 0; i < provinceEntity.size(); i++) {
                CityNameBeen.provinceEntity provinceEntityItem = provinceEntity.get(i);
                options1Items.add(provinceEntityItem.getProvinceName());
            }
            //选项2
            for (int i = 0; i < provinceEntity.size(); i++) {
                CityNameBeen.provinceEntity provinceEntityItem = provinceEntity.get(i);
                List<CityNameBeen.provinceEntity.cityEntity> cityEntity = provinceEntityItem.getCityEntity();

                ArrayList<String> options2Items_01 = new ArrayList<String>();
                if (cityEntity != null && cityEntity.size() > 0) {
                    for (int j = 0; j < cityEntity.size(); j++) {
                        options2Items_01.add(cityEntity.get(j).getCityName());
                    }
                }
                options2Items.add(options2Items_01);
            }

            //选项3
            for (int i = 0; i < provinceEntity.size(); i++) {
                CityNameBeen.provinceEntity provinceEntityItem = provinceEntity.get(i);
                List<CityNameBeen.provinceEntity.cityEntity> cityEntity = provinceEntityItem.getCityEntity();

                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                if (cityEntity != null && cityEntity.size() > 0) {
                    for (int j = 0; j < cityEntity.size(); j++) {
                        ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                        if (cityEntity.get(j).getCountyEntity() != null && cityEntity.get(j).getCountyEntity().getCountyName().size() > 0) {
                            List<String> countyName = cityEntity.get(j).getCountyEntity().getCountyName();
                            for (int g = 0; g < countyName.size(); g++) {
                                options3Items_01_01.add(countyName.get(g));
                            }
                            options3Items_01.add(options3Items_01_01);
                        } else {
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                }
                options3Items.add(options3Items_01);
            }

            //三级联动效果
            CityPickView.setPicker(options1Items, options2Items, options3Items, true);
            //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
            CityPickView.setTitle("选择城市");
            CityPickView.setCyclic(false, false, false);
            //设置默认选中的三级项目
            CityPickView.setSelectOptions(0, 0, 0);

            return true;

        }
        return false;
    }
}
