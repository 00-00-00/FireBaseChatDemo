package com.ground0.firebasechatdemo.core.viewModel;

import com.ground0.firebasechatdemo.core.BaseActivity;
import java.lang.ref.WeakReference;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zer0 on 5/10/16.
 */
public abstract class BaseActivityViewModel<T> {

  WeakReference<T> activity;

  public void registerActivity(T activity) {
    this.activity = new WeakReference<T>(activity);
    afterRegister();
  }

  public T getActivity() {
    return activity.get();
  }

  public void afterRegister() {

  }

  public CompositeSubscription getCompositeSubscription() {
    return ((BaseActivity)activity.get()).getCompositeSubscription();
  }
}
