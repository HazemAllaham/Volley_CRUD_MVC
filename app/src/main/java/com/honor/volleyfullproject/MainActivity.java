package com.honor.volleyfullproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.honor.volleyfullproject.Controller.CallBack.CallnackFromMainActivity;
import com.honor.volleyfullproject.Controller.GetDataToMainActivity;
import com.honor.volleyfullproject.Controller.MainActivityAdapter;
import com.honor.volleyfullproject.Controller.SessionManager;
import com.honor.volleyfullproject.Model.DataMain;
import com.honor.volleyfullproject.UI.ListUserActivity;
import com.honor.volleyfullproject.UI.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    public static String token ;
    private Button btn_Logout , btn_list_user;
    private RecyclerView myRecycler;
    private static MainActivityAdapter mainActivityAdapter ;
    private List<DataMain> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Logout = findViewById(R.id.btn_logout);
        btn_list_user = findViewById(R.id.btn_list_user);

        btn_list_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , ListUserActivity.class));
            }
        });


        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.getInstance(getBaseContext()).userLogout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });


        /*
        if the User Logeddin in our App  , we Will direct View MainActivity , if Not Will Return the
        user into LoginActivity
        */

        queue = Volley.newRequestQueue(getBaseContext());
        if (SessionManager.getInstance(getBaseContext()).isLoggedin()){ // check if the User Data Saved From SharedPref
             if (SessionManager.getInstance(getBaseContext()).getToken() != null){
                 token = SessionManager.getInstance(getBaseContext()).getToken().getToken();
             }
        }else {
            finish();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            return; // to Stop
        }




        myRecycler = findViewById(R.id.myRecycler);
        myRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myList = new ArrayList<>();

        GetDataToMainActivity.getDataFromApi(MainActivity.this , new CallnackFromMainActivity() {
            @Override
            public void onResponce(List<DataMain> dataMains) {

                try {
                    for (int i= 0 ; i < dataMains.size()  ; i++){
                        myList.add(dataMains.get(i));
                    }
                }catch (Exception e){
                    Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(String error) {
                Toast.makeText(getBaseContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });

        mainActivityAdapter = new MainActivityAdapter(MainActivity.this, myList);
        myRecycler.setAdapter(mainActivityAdapter);


    }

    public static void notifyAdapter(){ // for Notify the Adapter When Make Any Change
        mainActivityAdapter.notifyDataSetChanged();
    }
}