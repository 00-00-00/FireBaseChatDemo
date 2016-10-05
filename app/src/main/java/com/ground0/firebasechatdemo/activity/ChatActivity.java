package com.ground0.firebasechatdemo.activity;

/**
 * Created by zer0 on 5/10/16.
 */

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ground0.firebasechatdemo.core.BaseActivity;
import com.ground0.firebasechatdemo.core.viewModel.ChatActivityViewModel;
import com.ground0.firebasechatdemo.databinding.ActivityChatBinding;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zer0 on 4/10/16.
 */

public class ChatActivity extends BaseActivity {

  ChatActivityViewModel viewModel;
  ActivityChatBinding activityChatBinding;


  @Override protected void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
    ButterKnife.bind(this);
    viewModel.initSubscriptions();
    initRecycler();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (viewModel.getChatRecyclerAdapter() != null) viewModel.getChatRecyclerAdapter().cleanup();
  }

  private void initRecycler() {
    if (activityChatBinding != null) {
      activityChatBinding.aChatRecycler.setAdapter(viewModel.getChatRecyclerAdapter());
      activityChatBinding.aChatRecycler.setLayoutManager(viewModel.getLinearLayoutManager());
      viewModel.registerDataSetObserver();
    }
  }

  @OnClick(R.id.a_chat_send) public void sendMessage() {
    String message = "";
    if (activityChatBinding != null) {
      message = activityChatBinding.aChatMessage.getText().toString();
    }
    if (StringUtils.isNotEmpty(message)) {
      viewModel.pushMessage(message);
      activityChatBinding.aChatMessage.setText("");
    }
  }
}
