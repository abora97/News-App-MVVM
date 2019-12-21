package com.example.newappmvvm.activity.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newappmvvm.ApiCall.APIInterface;
import com.example.newappmvvm.ApiCall.ApiClient;
import com.example.newappmvvm.model.headLines.Article;
import com.example.newappmvvm.model.headLines.ResponseHeadLines;
import com.example.newappmvvm.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    MutableLiveData<ResponseHeadLines> articleMutableLiveData = new MutableLiveData<>();


    private boolean isCountry;

    void getNews(String country) {
        isCountry = true;
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNews(country, Constants.KEY);
        callEnqueue(call);
    }

    void getNewsWithSource(String sources) {
        isCountry = false;
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNewsWithSource(sources, Constants.KEY);
        callEnqueue(call);
    }

    private void callEnqueue(Call<ResponseHeadLines> call) {
        call.enqueue(new Callback<ResponseHeadLines>() {
            @Override
            public void onResponse(Call<ResponseHeadLines> call, Response<ResponseHeadLines> response) {
                Log.e("Network", "WTF " + response);
                Log.e("Network", "body " + response.body());
                Log.e("Network", "message " + response.message());
                // Log.e("Network", "getStatus  " + response.body().getStatus());
                if (response.message().equals("OK")) {
                    //     articleList = response.body().getArticles();
                    articleMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseHeadLines> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });
    }
}
