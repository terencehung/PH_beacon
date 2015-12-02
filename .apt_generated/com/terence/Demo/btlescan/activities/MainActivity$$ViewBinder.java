// Generated code from Butter Knife. Do not modify!
package com.terence.Demo.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.terence.Demo.btlescan.activities.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296351, "field 'mList'");
    target.mList = finder.castView(view, 2131296351, "field 'mList'");
    view = finder.findRequiredView(source, 2131296349, "field 'mTvItemCount'");
    target.mTvItemCount = finder.castView(view, 2131296349, "field 'mTvItemCount'");
    view = finder.findRequiredView(source, 2131296345, "field 'mTvBluetoothLeStatus'");
    target.mTvBluetoothLeStatus = finder.castView(view, 2131296345, "field 'mTvBluetoothLeStatus'");
    view = finder.findRequiredView(source, 16908292, "field 'mEmpty'");
    target.mEmpty = view;
    view = finder.findRequiredView(source, 2131296346, "field 'mTvBluetoothStatus'");
    target.mTvBluetoothStatus = finder.castView(view, 2131296346, "field 'mTvBluetoothStatus'");
  }

  @Override public void unbind(T target) {
    target.mList = null;
    target.mTvItemCount = null;
    target.mTvBluetoothLeStatus = null;
    target.mEmpty = null;
    target.mTvBluetoothStatus = null;
  }
}
