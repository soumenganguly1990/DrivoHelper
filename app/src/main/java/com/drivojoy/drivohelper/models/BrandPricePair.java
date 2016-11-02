package com.drivojoy.drivohelper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by sam on 10/30/2016.
 */
@Root(name = "BrandPricePair")
public class BrandPricePair {

    @Element(name = "OilBrandName", required = false)
    String oilBrandName = null;
    @Element(name = "OilPrice", required = false)
    String oilPrice = null;

    public BrandPricePair(){
    }

    public String getOilBrandName() {
        return oilBrandName;
    }

    public void setOilBrandName(String oilBrandName) {
        this.oilBrandName = oilBrandName;
    }

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }
}