package com.ground0.firebasechatdemo.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.ground0.firebasechatdemo.R;
import com.ground0.firebasechatdemo.core.BaseActivity;
import com.ground0.firebasechatdemo.core.viewModel.ChatListActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatListActivity extends BaseActivity {

  @BindView(R.id.a_chat_list_chat_id)
  EditText editText;

  ChatListActivityViewModel viewModel = new ChatListActivityViewModel();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_list);
    ButterKnife.bind(this);
  }

  @Override
  protected void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @OnClick(R.id.a_chat_list_send)
  void onStartButtonClick() {
    Long threadId = Long.parseLong(editText.getText().toString());
    viewModel.startChat(threadId);
  }
}
