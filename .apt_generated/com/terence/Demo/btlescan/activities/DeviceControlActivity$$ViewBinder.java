// Generated code from Butter Knife. Do not modify!
package com.terence.Demo.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DeviceControlActivity$$ViewBinder<T extends com.terence.Demo.btlescan.activities.DeviceControlActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296341, "field 'mDataAsArray'");
    target.mDataAsArray = finder.castView(view, 2131296341, "field 'mDataAsArray'");
    view = finder.findRequiredView(source, 2131296339, "field 'mGattUUIDDesc'");
    target.mGattUUIDDesc = finder.castView(view, 2131296339, "field 'mGattUUIDDesc'");
    view = finder.findRequiredView(source, 2131296343, "field 'mGattServicesList'");
    target.mGattServicesList = finder.castView(view, 2131296343, "field 'mGattServicesList'");
    view = finder.findRequiredView(source, 2131296338, "field 'mGattUUID'");
    target.mGattUUID = finder.castView(view, 2131296338, "field 'mGattUUID'");
    view = finder.findRequiredView(source, 2131296340, "field 'mDataAsString'");
    target.mDataAsString = finder.castView(view, 2131296340, "field 'mDataAsString'");
    view = finder.findRequiredView(source, 2131296335, "field 'mConnectionState'");
    target.mConnectionState = finder.castView(view, 2131296335, "field 'mConnectionState'");
  }

  @Override public void unbind(T target) {
    target.mDataAsArray = null;
    target.mGattUUIDDesc = null;
    target.mGattServicesList = null;
    target.mGattUUID = null;
    target.mDataAsString = null;
    target.mConnectionState = null;
  }
}
