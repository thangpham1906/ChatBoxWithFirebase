package com.example.thangpham.testfirebasenhap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thangpham.testfirebasenhap.R;
import com.example.thangpham.testfirebasenhap.adapter.ChatAdapter;
import com.example.thangpham.testfirebasenhap.adapter.ChatAdapter2;
import com.example.thangpham.testfirebasenhap.custom.MessageComparator;
import com.example.thangpham.testfirebasenhap.model.ChatMessage;
import com.example.thangpham.testfirebasenhap.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
      RecyclerView rvListChat;
//    ListView lvList;
    EditText etText;
    Button btnSend;
    com.example.thangpham.testfirebasenhap.model.UserModel userFrom;
    UserModel userTo;
    ChatAdapter chatAdapter;
    List<com.example.thangpham.testfirebasenhap.model.ChatMessage> list;
    boolean isFistTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupUI();
        Intent intent = getIntent();
        userFrom = (UserModel) intent.getSerializableExtra(MainActivity.USER_FROM);
        userTo = (UserModel) intent.getSerializableExtra(MainActivity.USER_TO);
        loadData();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFistTime = true;
    }

    private void loadData() {
        databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = dataSnapshot1.getValue(ChatMessage.class);
                    list.add(chatMessage);
                }
                Collections.sort(list,new MessageComparator());
                chatAdapter = new ChatAdapter(ChatActivity.this, list);
                rvListChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                rvListChat.setAdapter(chatAdapter);
                rvListChat.smoothScrollToPosition(list.size()-1 );

//                databaseReference.child(userTo.getUserId()).child(userFrom.userId).addValueEventListener(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    ChatMessage chatMessage = dataSnapshot1.getValue(ChatMessage.class);
//                                    chatMessage.isOwn = false;
//                                    list.add(chatMessage);
//                                }
//                                Collections.sort(list,new MessageComparator());
//                                chatAdapter = new ChatAdapter2(ChatActivity.this, list);
//                                //rvListChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
//                                lvList.setAdapter(chatAdapter);
//                                lvList.setSelection(list.size() - 1);
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addListener() {
        btnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etText.getText().toString();
                if (!content.equals("")) {
                    sendContent(content);
                    etText.setText("");
                } else {
                    Toast.makeText(ChatActivity.this, "Not Empty~~~~~", Toast.LENGTH_SHORT).show();
                }

            }
        });
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                databaseReference.child("typing").child(userFrom.userId).child(userTo.userId).child("chatting").setValue("chatting1");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")){
                    databaseReference.child("typing").child(userFrom.userId).child(userTo.userId).child("chatting").removeValue();
                }

            }
        });
        Log.e("ThangPham","aaaa: ");
        databaseReference.child("63uVTBdQlXg1ZfZH61d2zK2z2842").child("VayN0RR4AWPTI4tdQtuQqPncgN12").child("chatting").addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("ThangPham","a: "+dataSnapshot.getChildrenCount());
//                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                        Log.e("ThangPham","aaa: "+dataSnapshot1.getValue().toString());
//                        Log.e("ThangPham","aaa: "+dataSnapshot1.getValue().toString());
//                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        if(databaseReference.child(userTo.userId).child(userFrom.userId).child("chatting").child("chatting1").getKey().equals("chatting1")){
//            Toast.makeText(this, userTo.getName()+" is typing", Toast.LENGTH_SHORT).show();
            Log.e("ThangPham", userTo.getName()+" is typing");
        }

    }

    private void sendContent(String content) {
        long time = System.currentTimeMillis();
        final ChatMessage myMessage = new ChatMessage(content, time);
        myMessage.setOwn(true);
        final ChatMessage yourMessage = new ChatMessage(content, time);
        yourMessage.setOwn(false);
        databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId()).child(String.valueOf(time)).setValue(myMessage);
        databaseReference.child("chat").child(userTo.getUserId()).child(userFrom.getUserId()).child(String.valueOf(time)).setValue(yourMessage);
//    Map<String,Object> map = new HashMap<String, Object>();
//    map.put(String.valueOf(time),chatMessage);
        //  databaseReference.child(userFrom.getUserId()).child(userTo.getUserId()).updateChildren(map);
        //chatAdapter.notifyDataSetChanged();
    }

    private void setupUI() {
        list = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    rvListChat = findViewById(R.id.rv_list_chat);
//        lvList = findViewById(R.id.rv_list_chat);
        btnSend = findViewById(R.id.btnSend);
        etText = findViewById(R.id.et_text);
    }

    public void nhap() {
        // lần đầu tiên
//        if(isFistTime){
//          for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//            ChatMessage chatMessage = dataSnapshot1.getValue(ChatMessage.class);
//            list.add(chatMessage);
//          }
//          isFistTime=false;
//          chatAdapter = new ChatAdapter(ChatActivity.this,list);
//          rvListChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
//          rvListChat.setAdapter(chatAdapter);
//
//        }else{   // chỉ append
//          ChatMessage chatMessage=null;
//          for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//            chatMessage = dataSnapshot1.getValue(ChatMessage.class);
//          }
//          list.add(chatMessage);
//          chatAdapter.notifyDataSetChanged();
//        }
    }
}
