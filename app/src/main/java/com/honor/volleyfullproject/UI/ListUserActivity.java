package com.honor.volleyfullproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.honor.volleyfullproject.Controller.CallBack.CallBackUserListData;
import com.honor.volleyfullproject.Controller.GetDataToMainActivity;
import com.honor.volleyfullproject.Controller.ListUserAdapter;
import com.honor.volleyfullproject.Model.UserListData;
import com.honor.volleyfullproject.R;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

  private   RecyclerView list_user_rv ;
  private   List<UserListData> list = new ArrayList<>();
  private    ListUserAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        list_user_rv = findViewById(R.id.list_user_rv);
        list_user_rv.setLayoutManager(new LinearLayoutManager(ListUserActivity.this));


        GetDataToMainActivity.getUserDataFromApi(ListUserActivity.this, new CallBackUserListData() {
            @Override
            public void onResponce(List<UserListData> dataMains) {

            try {
                for (int i =0 ; i < dataMains.size() ; i++){
                    list.add(dataMains.get(i));
                }
                adapter = new ListUserAdapter(list , ListUserActivity.this);
                list_user_rv.setAdapter(adapter);

            }catch (Exception e){
                Toast.makeText(ListUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            } }
            @Override
            public void onError(String error) {
                Toast.makeText(ListUserActivity.this, " "+ error, Toast.LENGTH_SHORT).show();
            }
        });





    }
}