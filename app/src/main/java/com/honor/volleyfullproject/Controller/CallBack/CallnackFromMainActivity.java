package com.honor.volleyfullproject.Controller.CallBack;

import com.honor.volleyfullproject.Model.DataMain;

import java.util.List;

public interface CallnackFromMainActivity {
    void onResponce(List<DataMain> dataMains);
    void onError(String error);
}
