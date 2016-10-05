package com.ground0.firebasechatdemo.core.event;

import com.ground0.firebasechatdemo.core.Event;
import com.ground0.model.MessageThread;

/**
 * Created by zer0 on 5/10/16.
 */
public class StartChatEvent implements Event<MessageThread> {

  MessageThread messageThread;

  public StartChatEvent(MessageThread messageThread) {
    this.messageThread = messageThread;
  }

  @Override public MessageThread data() {
    return messageThread;
  }

  @Override public int eventType() {
    return START_CHAT;
  }
}
