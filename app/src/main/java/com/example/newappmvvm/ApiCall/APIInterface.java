package com.example.newappmvvm.ApiCall;


import com.example.newappmvvm.model.headLines.ResponseHeadLines;
import com.example.newappmvvm.utils.Constants;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    //this interface is using special retrofit annotations to encode the parameters and request method

    // https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=72a09f3a08aa416c8f394e4c75ef1435
    @GET(Constants.TOP_HEADLINES)
    Call<ResponseHeadLines> getNewsWithSource(@Query(Constants.SOURCES) String sources, @Query(Constants.API_KEY) String apiKey);


    // https://newsapi.org/v2/top-headlines?country=us&apiKey=72a09f3a08aa416c8f394e4c75ef1435
    @GET(Constants.TOP_HEADLINES)
    Call<ResponseHeadLines> getNews(@Query(Constants.COUNTRY) String country, @Query(Constants.API_KEY) String apiKey);


}
