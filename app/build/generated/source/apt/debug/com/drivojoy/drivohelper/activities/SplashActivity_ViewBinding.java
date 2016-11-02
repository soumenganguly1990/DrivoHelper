// Generated code from Butter Knife. Do not modify!
package com.drivojoy.drivohelper.activities;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.drivojoy.drivohelper.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SplashActivity_ViewBinding<T extends SplashActivity> implements Unbinder {
  protected T target;

  public SplashActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.imgDrivoLogo = finder.findRequiredViewAsType(source, R.id.imgDrivoLogo, "field 'imgDrivoLogo'", ImageView.class);
    target.txtBanner = finder.findRequiredViewAsType(source, R.id.txtBanner, "field 'txtBanner'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.imgDrivoLogo = null;
    target.txtBanner = null;

    this.target = null;
  }
}
