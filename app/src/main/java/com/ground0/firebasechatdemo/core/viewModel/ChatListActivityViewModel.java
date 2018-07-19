package com.ground0.firebasechatdemo.core.viewModel;

import android.content.Intent;

import com.firebase.client.Firebase;
import com.ground0.firebasechatdemo.activity.ChatActivity;
import com.ground0.firebasechatdemo.activity.ChatListActivity;
import com.ground0.firebasechatdemo.core.event.StartChatEvent;
import com.ground0.model.MessageThread;

public class ChatListActivityViewModel extends BaseActivityViewModel<ChatListActivity> {

  private Firebase firebase;

  public void startChat(Long threadId) {
    Intent intent = new Intent(activity.get(), ChatActivity.class);
    activity.get().startActivity(intent);

    getActivity().getBaseApplication()
        .getAppBehaviourBus()
        .onNext(new StartChatEvent(new MessageThread(threadId)))  ;
  }


    /*private boolean initFirebase() {
        if (firebase == null) {
            firebase = new Firebase(BuildConfig.FIREBASE_URL);
        }
        return (firebase != null);
    }*/
}
