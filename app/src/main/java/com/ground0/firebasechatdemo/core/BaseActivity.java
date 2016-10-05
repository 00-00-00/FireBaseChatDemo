package com.ground0.firebasechatdemo.core;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zer0 on 5/10/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

  protected Snackbar mSnackBar;
  protected CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    registerActivityWithViewModel();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }

  protected abstract void registerActivityWithViewModel();

  protected void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  protected void showSnackBar(String message) {
    View view = getWindow().getDecorView().getRootView().findViewById(android.R.id.content);
    mSnackBar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
    mSnackBar.setText(message);
    mSnackBar.show();
  }

  public BaseApplication getBaseApplication() {
    return ((BaseApplication)getApplication());
  }

  public CompositeSubscription getCompositeSubscription() {
    return compositeSubscription;
  }
}
