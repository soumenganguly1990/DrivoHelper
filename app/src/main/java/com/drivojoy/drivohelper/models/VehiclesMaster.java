package com.drivojoy.drivohelper.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.ArrayList;

/**
 * Created by sam on 10/30/2016.
 */
@Root(name = "VehiclesMaster", strict = false)
public class VehiclesMaster {

    @Element(name = "CreatedTime", required = false)
    String createdTime = null;
    @Element(name = "EntityID", required = false)
    String entityID = null;
    @Element(name = "SoftDelete", required = false)
    String softDelete = null;
    @Element(name = "UpdatedTime", required = false)
    String updatedTime = null;
    @Element(name = "DisassemblingQty", required = false)
    String disassemblingQty = null;
    @Element(name = "DrainingQty", required = false)
    String drainingQty = null;
    @Element(name = "Grade", required = false)
    String grade = null;
    @ElementList(entry = "OilBrandPrice", inline = false, name = "OilBrandPrice")
    ArrayList<BrandPricePair> brandPricePair = null;
    @Element(name = "Quantity", required = false)
    Integer quantity = null;
    @Element(name = "VehicleBrand", required = false)
    String vehicleBrand = null;
    @Element(name = "VehicleCategory", required = false)
    String vehicleCategory = null;
    @Element(name = "VehicleModel", required = false)
    String vehicleModel = null;
    @Element(name = "VehicleModelBrand", required = false)
    String vehicleModelBrand = null;
    @Element(name = "VehicleType", required = false)
    String vehicleType = null;
    @ElementList(entry = "VehicleYears", name = "VehicleYears", inline = false)
    ArrayList<Year> years = null;

    /**
     * @constructor
     */
    public VehiclesMaster() {
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(String softDelete) {
        this.softDelete = softDelete;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDisassemblingQty() {
        return disassemblingQty;
    }

    public void setDisassemblingQty(String disassemblingQty) {
        this.disassemblingQty = disassemblingQty;
    }

    public String getDrainingQty() {
        return drainingQty;
    }

    public void setDrainingQty(String drainingQty) {
        this.drainingQty = drainingQty;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ArrayList<BrandPricePair> getBrandPricePair() {
        return brandPricePair;
    }

    public void setBrandPricePair(ArrayList<BrandPricePair> brandPricePair) {
        this.brandPricePair = brandPricePair;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModelBrand() {
        return vehicleModelBrand;
    }

    public void setVehicleModelBrand(String vehicleModelBrand) {
        this.vehicleModelBrand = vehicleModelBrand;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ArrayList<Year> getYears() {
        return years;
    }

    public void setYears(ArrayList<Year> years) {
        this.years = years;
    }
}
