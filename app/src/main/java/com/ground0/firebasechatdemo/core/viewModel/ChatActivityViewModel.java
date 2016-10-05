package com.ground0.firebasechatdemo.core.viewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.firebase.client.Firebase;
import com.ground0.firebasechatdemo.BuildConfig;
import com.ground0.firebasechatdemo.activity.ChatActivity;
import com.ground0.firebasechatdemo.adapter.ChatRecyclerAdapter;
import com.ground0.firebasechatdemo.core.event.StartChatEvent;
import com.ground0.model.ChatMessage;
import com.ground0.model.MessageThread;

/**
 * Created by zer0 on 5/10/16.
 */
public class ChatActivityViewModel extends BaseActivityViewModel<ChatActivity> {

  ChatRecyclerAdapter chatRecyclerAdapter;
  LinearLayoutManager linearLayoutManager;
  Firebase firebase;
  MessageThread messageThread;

  @Override public void afterRegister() {
    super.afterRegister();
  }

  public void initSubscriptions() {
    getCompositeSubscription().add(getActivity().getBaseApplication()
        .getAppBehaviourBus()
        .filter(event -> event instanceof StartChatEvent)
        .subscribe(event -> {
          setMessageThread((MessageThread) event.data());
          initFirebase();
        }));
  }

  public ChatRecyclerAdapter getChatRecyclerAdapter() {
    if (chatRecyclerAdapter == null) {
      if (initFirebase() && messageThread != null) {
        chatRecyclerAdapter = new ChatRecyclerAdapter(getActivity().getBaseApplication().getUserId(), firebase);
      }
    } return chatRecyclerAdapter;
  }

  public LinearLayoutManager getLinearLayoutManager() {
    if (linearLayoutManager == null) {
      linearLayoutManager =
          new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
    return linearLayoutManager;
  }

  public void registerDataSetObserver() {
    if (chatRecyclerAdapter != null) {
      chatRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        @Override public void onChanged() {
          super.onChanged();
          linearLayoutManager.scrollToPosition(chatRecyclerAdapter.getItemCount() - 1);
        }
      });
    }
  }

  private boolean initFirebase() {
    if (firebase == null && messageThread != null) {
      firebase = new Firebase(BuildConfig.FIREBASE_URL).child(messageThread.getId().toString());
    }
    return (firebase != null);
  }

  public void pushMessage(String message) {
    if (initFirebase()) {
      ChatMessage chatMessage = new ChatMessage(message, messageThread.getId());
      firebase.push().setValue(chatMessage);
    }
  }

  public void setMessageThread(MessageThread messageThread) {
    this.messageThread = messageThread;
  }
}
