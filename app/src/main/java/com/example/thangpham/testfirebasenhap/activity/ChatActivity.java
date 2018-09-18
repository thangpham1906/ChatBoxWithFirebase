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
import android.widget.Toast;
import com.example.thangpham.testfirebasenhap.R;
import com.example.thangpham.testfirebasenhap.adapter.ChatAdapter;
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
import java.util.List;

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
  boolean check;
  ValueEventListener valueEventListener;
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
    check = true;
  }

  private void loadData() {

     valueEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        // chỉ load lần đầu tiên
        if(check==true){
          list.clear();
          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            ChatMessage chatMessage = dataSnapshot1.getValue(ChatMessage.class);
            list.add(chatMessage);
          }
          Collections.sort(list, new MessageComparator());
          chatAdapter = new ChatAdapter(ChatActivity.this, list);
          rvListChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
          rvListChat.setAdapter(chatAdapter);
          goToEndListChat();
        }else{ // những lần sau chỉ append
          ChatMessage chatMessage = null;
          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            chatMessage = dataSnapshot1.getValue(ChatMessage.class);
          }
          chatAdapter.addNewMessage(chatMessage);
          goToEndListChat();
        }


      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    };
    databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId()).addValueEventListener(valueEventListener);


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
        databaseReference.child("typing").child(userFrom.userId).child(userTo.userId)
            .child("chatting").setValue("123");
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.toString().equals("")) {
          databaseReference.child("typing").child(userFrom.userId).child(userTo.userId)
              .child("chatting").removeValue();

          Log.e("ThangPham", "remove chating");
        }

      }
    });

    databaseReference.child("typing").child("63uVTBdQlXg1ZfZH61d2zK2z2842")
        .child("VayN0RR4AWPTI4tdQtuQqPncgN12").child("chatting").addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if ( dataSnapshot.getValue() != null && dataSnapshot.getValue().toString().equals("123")) {
              Toast.makeText(ChatActivity.this, "Chatting..........", Toast.LENGTH_SHORT).show();
            }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });


  }

  private void sendContent(String content) {
    databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId()).removeEventListener(valueEventListener);
    long time = System.currentTimeMillis();
    final ChatMessage myMessage = new ChatMessage(content, time);
    myMessage.setOwn(true);
    final ChatMessage yourMessage = new ChatMessage(content, time);
    yourMessage.setOwn(false);
    databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId())
        .child(String.valueOf(time)).setValue(myMessage);
    databaseReference.child("chat").child(userTo.getUserId()).child(userFrom.getUserId())
        .child(String.valueOf(time)).setValue(yourMessage);
    // append messege cuối
    check=false;
    databaseReference.child("chat").child(userFrom.getUserId()).child(userTo.getUserId()).addValueEventListener(valueEventListener);
  }
  public void goToEndListChat(){
    rvListChat.smoothScrollToPosition(list.size() - 1);
  }
  private void setupUI() {
    list = new ArrayList<>();
    firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabase.getReference();
    rvListChat = findViewById(R.id.rv_list_chat);
    btnSend = findViewById(R.id.btnSend);
    etText = findViewById(R.id.et_text);
  }

}
