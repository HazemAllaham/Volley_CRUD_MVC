package com.honor.volleyfullproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.honor.volleyfullproject.Controller.SessionManager;
import com.honor.volleyfullproject.Controller.VolleySingleton;
import com.honor.volleyfullproject.MainActivity;
import com.honor.volleyfullproject.Model.User;
import com.honor.volleyfullproject.R;
import com.honor.volleyfullproject.Server.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText ed_email , edt_password ;
    private Button btn_Login , btn_register ;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ed_email = findViewById(R.id.ed_email_login);
        edt_password = findViewById(R.id.edt_password_login);
        btn_Login = findViewById(R.id.btn_Login);
        btn_register = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progressBar_Login);


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                     loginUser();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext() , RegisterActivity.class));
                finish();
            }
        });

    }

    private void loginUser(){

        final String email = ed_email.getText().toString().trim();
        final String password = edt_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            ed_email.setError("Enter Your Email Please...");
            ed_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            edt_password.setError("Enter Your Email Please...");
            edt_password.requestFocus();
            return;
        }




        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLS.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.VISIBLE);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("token") != null){   // just For Check if the Server Responce Any think ?

                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getBaseContext(), "Success to Register an your token is : " + jsonObject.getString("token"), Toast.LENGTH_SHORT).show();

                                // for get token From Server
                                String token = jsonObject.getString("token");
                                User user = new User(token);
                                SessionManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                startActivity(new Intent(getBaseContext() , MainActivity.class));

                            }else {
                                Toast.makeText(getBaseContext(), "Error to Register User", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException jsonException){

                            Log.d("ERROR","JSON REQUEST ERROR IS : " + jsonException.getMessage());
                            Toast.makeText(getBaseContext(), jsonException.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })

        { // send the data into Server :) Just User name and Password

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("email",email);
                params.put("password",password);

                return params;
            }

        };

        // Send the Request to the Server
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}