// Generated code from Butter Knife. Do not modify!
package com.drivojoy.drivohelper.activities;

import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.Utils;
import com.drivojoy.drivohelper.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  public MainActivity_ViewBinding(T target, Finder finder, Object source, Resources res, Resources.Theme theme) {
    this.target = target;

    target.navMenu = finder.findRequiredViewAsType(source, R.id.navMenu, "field 'navMenu'", NavigationView.class);
    target.drivoDrawer = finder.findRequiredViewAsType(source, R.id.drivoDrawer, "field 'drivoDrawer'", DrawerLayout.class);
    target.drivoToolbar = finder.findRequiredViewAsType(source, R.id.drivoToolbar, "field 'drivoToolbar'", Toolbar.class);

    target.drivoLogo = Utils.getDrawable(res, theme, R.drawable.drivologo);
    target.appName = res.getString(R.string.app_name);
    target.mailContact = res.getString(R.string.drivocontact);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.navMenu = null;
    target.drivoDrawer = null;
    target.drivoToolbar = null;

    this.target = null;
  }
}
