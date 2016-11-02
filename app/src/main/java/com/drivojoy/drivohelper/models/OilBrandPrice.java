package com.drivojoy.drivohelper.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.ArrayList;

/**
 * Created by sam on 10/30/2016.
 * **** Not used, please ignore ****
 */
@Root(name = "OilBrandPrice")
public class OilBrandPrice {

    @ElementList(name = "BrandPricePair", required = false, inline = true)
    ArrayList<BrandPricePair> brandPricePairList = null;

    public OilBrandPrice(){
    }

    public ArrayList<BrandPricePair> getBrandPricePairList() {
        return brandPricePairList;
    }

    public void setBrandPricePairList(ArrayList<BrandPricePair> brandPricePairList) {
        this.brandPricePairList = brandPricePairList;
    }
}
