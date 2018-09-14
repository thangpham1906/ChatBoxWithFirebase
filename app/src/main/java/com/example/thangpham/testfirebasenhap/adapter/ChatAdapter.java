package com.example.thangpham.testfirebasenhap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thangpham.testfirebasenhap.R;
import com.example.thangpham.testfirebasenhap.custom.EmojiTextView;
import com.example.thangpham.testfirebasenhap.model.ChatMessage;
import java.util.List;

public class ChatAdapter extends Adapter<ChatAdapter.ViewHolder> {

  private Context context;
  private List<ChatMessage> listChatMessage;
  boolean a = true;
  public ChatAdapter(Context context,
      List<ChatMessage> listChatMessage) {
    this.context = context;
    this.listChatMessage = listChatMessage;
  }


  @Override
  public int getItemViewType(int position) {
    if (listChatMessage.get(position).isOwn)
      return 0;
    else
      return 1;
    }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    int resId=0;
    if(viewType==0){
       resId = R.layout.item_list_chat_send_message;
    }else{
       resId = R.layout.item_list_chat_receiver_mess;
    }

    View convertView = LayoutInflater.from(context).inflate(resId, null, false);


    return new ViewHolder(convertView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int i) {
    holder.setData(listChatMessage.get(i));
  }

  @Override
  public int getItemCount() {
    return listChatMessage.size();

  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    EmojiTextView emojiTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      emojiTextView = itemView.findViewById(R.id.message);

    }

    public void setData(ChatMessage data) {
      emojiTextView.setEmojiText(data.content);
    }

  }

  public void clearAllMessage() {
    if (listChatMessage != null) {
      listChatMessage.clear();
    }
  }

  public void addNewMessage(ChatMessage newChatMessage) {
    listChatMessage.add(newChatMessage);

  }
}
