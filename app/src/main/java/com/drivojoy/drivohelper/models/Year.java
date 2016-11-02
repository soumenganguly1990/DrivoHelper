package com.drivojoy.drivohelper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by sam on 10/30/2016.
 */
public class Year {

    @Element(name = "d2p1:int", required = false)
    int yearNum;

    public Year() {
    }

    public int getYearNum() {
        return yearNum;
    }

    public void setYearNum(int yearNum) {
        this.yearNum = yearNum;
    }
}
