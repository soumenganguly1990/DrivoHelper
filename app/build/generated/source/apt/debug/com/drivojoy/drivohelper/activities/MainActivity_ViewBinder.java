// Generated code from Butter Knife. Do not modify!
package com.drivojoy.drivohelper.activities;

import android.content.Context;
import android.content.res.Resources;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.Object;
import java.lang.Override;

public final class MainActivity_ViewBinder implements ViewBinder<MainActivity> {
  @Override
  public Unbinder bind(Finder finder, MainActivity target, Object source) {
    Context context = finder.getContext(source);
    Resources res = context.getResources();
    Resources.Theme theme = context.getTheme();
    return new MainActivity_ViewBinding<>(target, finder, source, res, theme);
  }
}
