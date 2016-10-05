package com.ground0.firebasechatdemo.core;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zer0 on 5/10/16.
 */

public interface Event<T> {
  T data();
  @Event.EventType int eventType();

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({START_CHAT})
  public @interface EventType {}

  public static final int START_CHAT = 0;
}