package com.drivojoy.drivohelper.interfaces;

import com.drivojoy.drivohelper.models.VehiclesMaster;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sam on 10/30/2016.
 */
public interface ApiInterface {

    @Headers("Content-Type: text/xml")
    @GET("api/VehiclesMaster")
    Call<VehiclesMaster> getBikeDetails(@Query("vehicleModelBrand") String vehicleModel);
}
