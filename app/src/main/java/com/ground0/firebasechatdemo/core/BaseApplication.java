package com.ground0.firebasechatdemo.core;

import android.app.Application;
import com.firebase.client.Firebase;
import com.ground0.firebasechatdemo.BuildConfig;
import java.util.Random;
import rx.subjects.BehaviorSubject;

/**
 * Created by zer0 on 5/10/16.
 */
public class BaseApplication extends Application {

  protected BehaviorSubject<Event> appBehaviourBus = BehaviorSubject.create();
  private Long userId;

  public BehaviorSubject<Event> getAppBehaviourBus() {
    return appBehaviourBus;
  }

  public Long getUserId() {
    return (long) (new Random()).nextInt(1000);
  }

  @Override public void onCreate() {
    super.onCreate();
    Firebase.setAndroidContext(this);
  }
}