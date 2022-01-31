package com.honor.volleyfullproject.Controller.CallBack;

import com.honor.volleyfullproject.Model.DataMain;
import com.honor.volleyfullproject.Model.UserListData;

import java.util.List;

public interface CallBackUserListData {

    void onResponce(List<UserListData> dataMains);
    void onError(String error);
}
