package com.ground0.firebasechatdemo.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.firebase.client.Query;
import com.ground0.firebasechatdemo.R;
import com.ground0.firebasechatdemo.core.adapter.FireBaseRecyclerAdapter;
import com.ground0.firebasechatdemo.core.viewModel.ChatMessageViewModelFactory;
import com.ground0.firebasechatdemo.databinding.ItemMessageReceivedBinding;
import com.ground0.firebasechatdemo.databinding.ItemMessageSentBinding;
import com.ground0.model.ChatMessage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * Created by zer0 on 5/10/16.
 */

public class ChatRecyclerAdapter
    extends FireBaseRecyclerAdapter<ChatMessage, ChatRecyclerAdapter.ViewHolder> {

  ChatMessageViewModelFactory chatMessageViewModelFactory;
  Long sender;
  LayoutInflater layoutInflater;

  @Retention(RetentionPolicy.SOURCE) @IntDef({ MESSAGE_SENT, MESSAGE_RECEIVED })
  public @interface ViewType {
  }

  public static final int MESSAGE_SENT = 0;
  public static final int MESSAGE_RECEIVED = 1;

  public ChatRecyclerAdapter(Long sender, Query query) {
    super(query, ChatMessage.class);
    this.sender = sender;
    chatMessageViewModelFactory = new ChatMessageViewModelFactory();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
    switch (viewType) {
      case MESSAGE_RECEIVED:
        View view1 = layoutInflater.inflate(R.layout.item_message_received, parent, false);
        return new ViewHolder(view1, viewType);
      case MESSAGE_SENT:
        View view2 = layoutInflater.inflate(R.layout.item_message_sent, parent, false);
        return new ViewHolder(view2, viewType);
    }
    return null;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    ChatMessageViewModelFactory.ChatMessageItemViewModel chatMessageItemViewModel =
        chatMessageViewModelFactory.createMessageItemViewModel(getItem(position));
    switch (getItemViewType(position)) {
      case MESSAGE_RECEIVED:
        holder.messageReceivedBinding = DataBindingUtil.bind(holder.itemView);
        holder.messageReceivedBinding.setViewModel(chatMessageItemViewModel);
        break;
      case MESSAGE_SENT:
        holder.messageSentBinding = DataBindingUtil.bind(holder.itemView);
        holder.messageSentBinding.setViewModel(chatMessageItemViewModel);
        break;
    }
  }

  @ViewType @Override public int getItemViewType(int position) {
    ChatMessage chatMessage = (ChatMessage) getItem(position);
    if (chatMessage != null) {
      if ((sender.equals(chatMessage.getAuthor()))) {
        return MESSAGE_SENT;
      } else {
        return MESSAGE_RECEIVED;
      }
    }
    return MESSAGE_SENT;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.i_message_text_view) TextView messageView;
    public ItemMessageReceivedBinding messageReceivedBinding;
    public ItemMessageSentBinding messageSentBinding;
    @ViewType int viewType;

    public ViewHolder(View itemView, @ViewType int viewType) {
      super(itemView);
      this.viewType = viewType;
      ButterKnife.bind(itemView);
    }

    public void setText(String text) {
      messageView.setText(text);
    }

    public String getText() {
      return messageView.getText().toString();
    }

    public ViewDataBinding getDataBinding() {
      switch (viewType) {
        case MESSAGE_SENT:
          return messageSentBinding;
        case MESSAGE_RECEIVED:
          return messageReceivedBinding;
      }
      return null;
    }
  }
}
