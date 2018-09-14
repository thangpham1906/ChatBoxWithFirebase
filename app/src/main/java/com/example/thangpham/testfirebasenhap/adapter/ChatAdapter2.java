package com.example.thangpham.testfirebasenhap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.example.thangpham.testfirebasenhap.R;
import com.example.thangpham.testfirebasenhap.custom.EmojiTextView;
import com.example.thangpham.testfirebasenhap.model.ChatMessage;
import java.util.List;

public class ChatAdapter2 extends BaseAdapter {
  private Context context;
  private List<ChatMessage> listChatMessage;

  public ChatAdapter2(Context context,
      List<ChatMessage> listChatMessage) {
    this.context = context;
    this.listChatMessage = listChatMessage;
  }

  @Override
  public int getCount() {
    return listChatMessage.size();
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {

    MyViewHolder mViewHolder;
    if(view == null) {
     if(listChatMessage.get(i).isOwn){
       view = LayoutInflater.from(context).inflate(R.layout.item_list_chat_send_message, viewGroup,false);
     }else  {
       view = LayoutInflater.from(context).inflate(R.layout.item_list_chat_receiver_mess, viewGroup,false);
     }
      mViewHolder = new MyViewHolder();
      mViewHolder.emojiTextView = view.findViewById(R.id.message);
      view.setTag(mViewHolder);
    } else {
      mViewHolder = (MyViewHolder) view.getTag();
    }
    Log.e("ThangPham1","content: "+listChatMessage.get(i).isOwn);
    mViewHolder.emojiTextView.setEmojiText(listChatMessage.get(i).content);
    return view;
  }
  public class MyViewHolder{
    public EmojiTextView emojiTextView;
  }
}
