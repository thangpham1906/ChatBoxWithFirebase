package com.example.thangpham.testfirebasenhap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import com.example.thangpham.testfirebasenhap.R;
import com.example.thangpham.testfirebasenhap.custom.EmojiTextView;
import com.example.thangpham.testfirebasenhap.model.ChatMessage;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter  extends Adapter<ChatAdapter.ViewHolder>{
  private Context context;
  private List<com.example.thangpham.testfirebasenhap.model.ChatMessage> listChatMessage;
  public ChatAdapter(Context context,
      List<ChatMessage> listChatMessage) {
    this.context = context;
    this.listChatMessage = listChatMessage;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    int resId = R.layout.item_list_chat_receiver_mess;
//
//    for(int i=0;i<listChatMessage.size();i++){
//      if(listChatMessage.get(i).isOwn){
//        resId = R.layout.item_list_chat_send_message;
//      }else
//      {
//        resId = R.layout.item_list_chat_receiver_mess;
//      }
//    }

    View convertView = LayoutInflater.from(context).inflate(resId,parent,false);
    return new ViewHolder(convertView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(listChatMessage.get(position));
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
    public void setData(ChatMessage data){
      if(data.isOwn)
      {
        emojiTextView.setBack
      }else {

      }
      emojiTextView.setEmojiText(data.content);
    }

  }
  public void clearAllMessage(){
    if(listChatMessage!=null){
      listChatMessage.clear();
    }
  }
  public void addNewMessage(ChatMessage newChatMessage){
    listChatMessage.add(newChatMessage);

  }
}
