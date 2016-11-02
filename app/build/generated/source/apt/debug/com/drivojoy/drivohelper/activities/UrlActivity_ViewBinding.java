// Generated code from Butter Knife. Do not modify!
package com.drivojoy.drivohelper.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.drivojoy.drivohelper.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class UrlActivity_ViewBinding<T extends UrlActivity> implements Unbinder {
  protected T target;

  private View view2131492996;

  public UrlActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    target.edtSearchBike = finder.findRequiredViewAsType(source, R.id.edtSearchBike, "field 'edtSearchBike'", EditText.class);
    view = finder.findRequiredView(source, R.id.btnSearch, "field 'btnSearch' and method 'onSearchButtonClicked'");
    target.btnSearch = finder.castView(view, R.id.btnSearch, "field 'btnSearch'", Button.class);
    view2131492996 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchButtonClicked();
      }
    });
    target.scrDetailContainer = finder.findRequiredViewAsType(source, R.id.scrDetailContainer, "field 'scrDetailContainer'", ScrollView.class);
    target.txtVehicleBrand = finder.findRequiredViewAsType(source, R.id.txtVehicleBrand, "field 'txtVehicleBrand'", TextView.class);
    target.txtVehicleCategory = finder.findRequiredViewAsType(source, R.id.txtVehicleCategory, "field 'txtVehicleCategory'", TextView.class);
    target.txtVehicleModel = finder.findRequiredViewAsType(source, R.id.txtVehicleModel, "field 'txtVehicleModel'", TextView.class);
    target.txtVehicleType = finder.findRequiredViewAsType(source, R.id.txtVehicleType, "field 'txtVehicleType'", TextView.class);
    target.txtOilbrandprice = finder.findRequiredViewAsType(source, R.id.txtOilbrandprice, "field 'txtOilbrandprice'", TextView.class);
    target.spnOilBrands = finder.findRequiredViewAsType(source, R.id.spnOilBrands, "field 'spnOilBrands'", Spinner.class);
    target.txtOilPrice = finder.findRequiredViewAsType(source, R.id.txtOilPrice, "field 'txtOilPrice'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.edtSearchBike = null;
    target.btnSearch = null;
    target.scrDetailContainer = null;
    target.txtVehicleBrand = null;
    target.txtVehicleCategory = null;
    target.txtVehicleModel = null;
    target.txtVehicleType = null;
    target.txtOilbrandprice = null;
    target.spnOilBrands = null;
    target.txtOilPrice = null;

    view2131492996.setOnClickListener(null);
    view2131492996 = null;

    this.target = null;
  }
}
