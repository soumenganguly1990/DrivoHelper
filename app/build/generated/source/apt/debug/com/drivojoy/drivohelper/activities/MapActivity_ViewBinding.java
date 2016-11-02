// Generated code from Butter Knife. Do not modify!
package com.drivojoy.drivohelper.activities;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.drivojoy.drivohelper.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MapActivity_ViewBinding<T extends MapActivity> implements Unbinder {
  protected T target;

  public MapActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.txtLat = finder.findRequiredViewAsType(source, R.id.txtLat, "field 'txtLat'", TextView.class);
    target.txtLong = finder.findRequiredViewAsType(source, R.id.txtLong, "field 'txtLong'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.txtLat = null;
    target.txtLong = null;

    this.target = null;
  }
}
