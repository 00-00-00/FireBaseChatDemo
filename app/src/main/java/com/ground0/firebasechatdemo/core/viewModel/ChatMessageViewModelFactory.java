package com.ground0.firebasechatdemo.core.viewModel;

import com.ground0.model.ChatMessage;

/**
 * Created by zer0 on 5/10/16.
 */

public class ChatMessageViewModelFactory {

  public ChatMessageItemViewModel createMessageItemViewModel(ChatMessage chatMessage) {
    return new ChatMessageItemViewModel(chatMessage);
  }

  public class ChatMessageItemViewModel {

    ChatMessage chatMessage;

    public ChatMessageItemViewModel(ChatMessage chatMessage) {
      this.chatMessage = chatMessage;
    }

    public String getMessage() {
      return chatMessage.getMessage();
    }
  }
}