package com.example.thangpham.testfirebasenhap.model;

import android.content.SharedPreferences;

public class UserPreferenes {
  public static final String SHARE_PREFERENCE ="SHARE_PREFERENCE";
  public static final String KEY_USER_ID ="key_user_id";
  public static UserPreferenes userPreferenes;
  public static UserPreferenes getInstance(){
    if(userPreferenes==null){
      userPreferenes = new UserPreferenes();
    }
    return userPreferenes;
  }
}
