package com.example.newappmvvm.activity.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newappmvvm.R;
import com.example.newappmvvm.model.headLines.Article;
import com.example.newappmvvm.model.headLines.ResponseHeadLines;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NewsViewModel newsViewModel;

    @BindView(R.id.rec_news)
    RecyclerView recNews;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.progress_news)
    ProgressBar progressNews;

    private RecyclerView.LayoutManager newsLayoutManager;
    private NewsAdapter newsAdapter;

    boolean isCountry = true;
    String country = "us";
    String sources = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        swipeLayout.setOnRefreshListener(this);
        progressNews.setVisibility(View.VISIBLE);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        getDataFromApi();
    }

    private void getDataFromApi() {
        newsViewModel.getNews(country);
        newsViewModel.articleMutableLiveData.observe(this, new Observer<ResponseHeadLines>() {
            @Override
            public void onChanged(ResponseHeadLines responseHeadLines) {
                // Toast.makeText(NewsActivity.this, i + " " + responseHeadLines.getArticles().get(0).getTitle(), Toast.LENGTH_LONG).show();
                progressNews.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                initRecycle(responseHeadLines.getArticles());
            }
        });
    }


    private void initRecycle(List<Article> article) {
        newsAdapter = new NewsAdapter(article, this);
        recNews.setLayoutManager(getLayoutManager());
        recNews.setAdapter(newsAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (newsLayoutManager == null) {
            newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        return newsLayoutManager;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_filter:
                showFilterDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // this method to show filterDialog and handle all action
    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> dialog.dismiss());
        Button buCancel = dialog.findViewById(R.id.bu_cancel);
        //btn
        buCancel.setOnClickListener(v -> dialog.dismiss());
        Spinner spinnerCountry = dialog.findViewById(R.id.spinner_country);
        Spinner spinnerSources = dialog.findViewById(R.id.spinner_sources);
        Button buFilter = dialog.findViewById(R.id.bu_filter);
        buFilter.setOnClickListener(v -> {

            country = spinnerCountry.getSelectedItem().toString();
            sources = spinnerSources.getSelectedItem().toString();
            if (!country.equals("Select Country")) {
                newsViewModel.getNews(country);
                isCountry = true;
                progressNews.setVisibility(View.VISIBLE);
                dialog.dismiss();
            } else if (!sources.equals("Select News Sources")) {
                newsViewModel.getNewsWithSource(sources);
                progressNews.setVisibility(View.VISIBLE);
                isCountry = false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onRefresh() {
        progressNews.setVisibility(View.VISIBLE);
        if (isCountry) {
            newsViewModel.getNews(country);
        } else {
            newsViewModel.getNewsWithSource(sources);
        }
    }

}
