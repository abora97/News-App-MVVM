
package com.example.newappmvvm.model.headLines;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseHeadLines implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    private final static long serialVersionUID = 3437750808074654178L;

    protected ResponseHeadLines(Parcel in) {
        status = in.readString();
        totalResults = in.readInt();
    }

    public static final Creator<ResponseHeadLines> CREATOR = new Creator<ResponseHeadLines>() {
        @Override
        public ResponseHeadLines createFromParcel(Parcel in) {
            return new ResponseHeadLines(in);
        }

        @Override
        public ResponseHeadLines[] newArray(int size) {
            return new ResponseHeadLines[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(totalResults);
    }
}
