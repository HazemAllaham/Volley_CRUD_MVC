package com.honor.volleyfullproject.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.honor.volleyfullproject.Controller.CallBack.CallBackPostDataIntoServer;
import com.honor.volleyfullproject.Controller.CallBack.CallBackUserListData;
import com.honor.volleyfullproject.Controller.CallBack.CallnackFromMainActivity;
import com.honor.volleyfullproject.MainActivity;
import com.honor.volleyfullproject.Model.DataMain;
import com.honor.volleyfullproject.Model.UserListData;
import com.honor.volleyfullproject.Server.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetDataToMainActivity {


    private  Context context ;

    public GetDataToMainActivity(Context context){this.context = context;}


    // Here get the Data From the Api to MainActivity

    public static void getDataFromApi(Context context , CallnackFromMainActivity callnackFromMainActivity){
        ArrayList<DataMain> myList = new ArrayList<>();


        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLS.ALL_DATA_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0 ;i< jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                DataMain dataMain = new DataMain();

                                dataMain.setName(jsonObject.getString("name"));
                                dataMain.setColor(jsonObject.getString("color"));
                                dataMain.setYear(jsonObject.getInt("year"));
                                dataMain.setPantone_value(jsonObject.getString("pantone_value"));
                                myList.clear();
                                myList.add(dataMain);
                                callnackFromMainActivity.onResponce(myList);
                                MainActivity.notifyAdapter();
                                progressDialog.dismiss();

                            }

                        }catch (JSONException e){

                            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            callnackFromMainActivity.onError(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                callnackFromMainActivity.onError(error.getMessage());

            }
        })

                // if our Api Have Token or Anythink need to Send Here We Can Send into Request
        {
            public Map<String , String> getHeaders(){
                Map<String,String> map = new HashMap<>();
                map.put("Content-Type","application/json");
                map.put("token","Bearer "+ MainActivity.token);
                return map;
            }

        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUserDataFromApi(Context context , CallBackUserListData callBackUserListData){

        ArrayList<UserListData> list = new ArrayList<>();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLS.ALL_USER_DATA,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("data");


                            for (int i=0 ; i < jsonArray.length() ; i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                UserListData userListData = new UserListData();
                                userListData.setId(jsonObject.getInt("id"));
                                userListData.setEmail(jsonObject.getString("email"));
                                userListData.setFirst_name(jsonObject.getString("first_name"));
                                userListData.setLast_name(jsonObject.getString("last_name"));
                                userListData.setAvatar(jsonObject.getString("avatar"));
                                list.clear();
                                list.add(userListData);
                                callBackUserListData.onResponce(list);
                                progressDialog.dismiss();
                                Log.d("Hazem","the data : " + userListData.getEmail());
                                MainActivity.notifyAdapter();
                            }

                        }catch (JSONException e){
                            callBackUserListData.onError(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackUserListData.onError(error.getMessage());
            }
        })
        {
           public  Map<String , String > getHeaders(){
                HashMap<String , String > hashMap = new HashMap<>();
               hashMap.put("Content-Type","application/json");
               hashMap.put("token","Bearer "+ MainActivity.token);
                return hashMap;
            }
        };

         VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }



    public void postDataIntoApi(Context context , CallBackPostDataIntoServer callBackPostDataIntoServer , String ed_name , String ed_jop){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Add Data into Server ...");
        progressDialog.setTitle("Upload Data");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

      try {
          jsonObject.put("name",ed_name);
          jsonObject.put("job",ed_jop);
      }catch (JSONException e){
          e.getMessage();
      }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URLS.ALL_USER_DATA,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                       try {

                           if (response.getString("createdAt") != null){
                               callBackPostDataIntoServer.onResponce(response.getString("createdAt"));
                               progressDialog.dismiss();
                           }

                       }catch (JSONException e){
                           callBackPostDataIntoServer.onError(e.getMessage());
                       }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 callBackPostDataIntoServer.onError(error.getMessage());
            }
        })

        {

             public Map<String, String > getHolders(){
                 Map<String , String > map = new HashMap<>();
                 map.put("Content-Type","application/json");
                 map.put("token","Bearer "+ MainActivity.token);
                 return  map;
             }
        };
    }


    /* Edit Function Same Of the PostFunction the deferant is :
    1- The Method is PUT .
    2- Send the Id Of View  With The Server in the Url by get the id From the Adapter ;
    */


    /* Delete Function get (int position , int id)
      => position for Delete Directory From ArrayList in the Adapter by => myList.removeat(position);
      => id for delete the Specific view , Need to Sent the Id with the Url to the Server .
      => you Should Use the NotifyDataChange() , in the MainActivity to Notify the Adapter With the Change.
      => the Method Delete Placed into Adapter Calss to get the id directory and Position elemnt Selected and Delete .
      => You Can Use GET Method For Delete , and You Can Use the DELETE Method
      Thx
     */


}
