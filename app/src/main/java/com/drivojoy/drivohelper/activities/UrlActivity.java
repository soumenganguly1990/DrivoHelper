package com.drivojoy.drivohelper.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.drivojoy.drivohelper.R;
import com.drivojoy.drivohelper.common.AppCommonValues;
import com.drivojoy.drivohelper.interfaces.ApiInterface;
import com.drivojoy.drivohelper.models.VehiclesMaster;
import com.drivojoy.drivohelper.utils.DrivoClient;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sam on 10/29/2016.
 */
public class UrlActivity extends AppCompatActivity {

    @BindView(R.id.edtSearchBike) EditText edtSearchBike;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.scrDetailContainer) ScrollView scrDetailContainer;
    @BindView(R.id.txtVehicleBrand) TextView txtVehicleBrand;
    @BindView(R.id.txtVehicleCategory) TextView txtVehicleCategory;
    @BindView(R.id.txtVehicleModel) TextView txtVehicleModel;
    @BindView(R.id.txtVehicleType) TextView txtVehicleType;
    @BindView(R.id.txtOilbrandprice) TextView txtOilbrandprice;
    @BindView(R.id.spnOilBrands) Spinner spnOilBrands;
    @BindView(R.id.txtOilPrice) TextView txtOilPrice;

    ProgressDialog mProgressDialog;
    ArrayList<String> brands;
    ArrayList<String> price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_url);
        ButterKnife.bind(this);

        brands = new ArrayList<String>();
        price = new ArrayList<String>();
    }

    @OnClick(R.id.btnSearch)
    public void onSearchButtonClicked() {
        if(AppCommonValues.isNetworkAvailable(UrlActivity.this)) {
            String nameToSearch = edtSearchBike.getText().toString().trim();
            scrDetailContainer.setVisibility(View.GONE);
            clearLists();
            if (nameToSearch.equalsIgnoreCase("")) {
                Toast.makeText(UrlActivity.this, "Oopsie, a name is required", Toast.LENGTH_SHORT).show();
            } else {
                spawnProgressDialog();
                callUrlThroughThisMethod(nameToSearch);
            }
        } else {
            AppCommonValues.showCommonErrorDialog(UrlActivity.this, AppCommonValues.NO_NETWORK);
        }
    }

    /**
     * @task clears the arraylists
     */
    private void clearLists(){
        try {
            price.clear();
        } catch (Exception e){}
        try {
            brands.clear();
        } catch (Exception e){}
    }

    /**
     * @task creates a progressdialog
     */
    private void spawnProgressDialog() {
        mProgressDialog = new ProgressDialog(UrlActivity.this);
        mProgressDialog.setTitle(getString(R.string.app_name));
        mProgressDialog.setMessage("Fetching data, wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        try {
            mProgressDialog.dismiss();
        } catch (NullPointerException n){
            n.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @task take required steps to call the required url
     * @param searchString
     *        the value to be searched like bajaj pulsar 180, honda navi etc
     */
    private void callUrlThroughThisMethod(String searchString) {
        ApiInterface apiService = DrivoClient.getClient().create(ApiInterface.class);
        Call<VehiclesMaster> call = apiService.getBikeDetails(searchString);
        Log.e("request", "" + call.request().url().toString());
        call.enqueue(new Callback<VehiclesMaster>() {
            @Override
            public void onResponse(Call<VehiclesMaster> call, Response<VehiclesMaster> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    /* request was successful, make scrollview visible */
                    scrDetailContainer.setVisibility(View.VISIBLE);
                    txtVehicleBrand.setText("Vehicle Brand      " + response.body().getVehicleBrand());
                    txtVehicleCategory.setText("Vehicle Category      " + response.body().getVehicleCategory());
                    txtVehicleModel.setText("Vehicle Model      " + response.body().getVehicleModel());
                    txtVehicleType.setText("Vehicle Type      " + response.body().getVehicleType());
                    txtOilbrandprice.setText(getString(R.string.oilbrandprice));

                    /* adding data to the arraylists */
                    for(int i = 0;i < response.body().getBrandPricePair().size();i ++){
                        brands.add(response.body().getBrandPricePair().get(i).getOilBrandName());
                        price.add(response.body().getBrandPricePair().get(i).getOilPrice());
                    }
                    ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(UrlActivity.this,
                                android.R.layout.simple_list_item_1, brands);
                    spnOilBrands.setAdapter(brandAdapter);
                    spnOilBrands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            txtOilPrice.setText("Oil Price      " + price.get(i));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else {
                    AppCommonValues.showCommonErrorDialog(UrlActivity.this, AppCommonValues.URL_RETRIEVAL_UNSUCCESSFUL);
                }
            }
            @Override
            public void onFailure(Call<VehiclesMaster> call, Throwable t) {
                dismissProgressDialog();
                AppCommonValues.showCommonErrorDialog(UrlActivity.this, AppCommonValues.URL_ERROR);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
