package com.example.newappmvvm.activity.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newappmvvm.R;
import com.example.newappmvvm.activity.details.DetailsActivity;
import com.example.newappmvvm.model.headLines.Article;
import com.example.newappmvvm.utils.Constants;
import com.example.newappmvvm.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> ArticleList;
    private Context context;
    private DateConverter dateConverter = new DateConverter();



    public NewsAdapter(List<Article> ArticleList, Context context) {
        this.ArticleList = ArticleList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = ArticleList.get(position);
        holder.tvHeadLine.setText(article.getTitle());
        holder.tvDescription.setText(article.getDescription());
        Date publishedDate = dateConverter.getDateFromDepartureOrArrivalInquiryString(article.getPublishedAt());
        String publishedDateString = dateConverter.getDateFromDate(publishedDate);
        holder.tvDate.setText(publishedDateString);
        String publishedTimeString = dateConverter.getTimeFromDate(publishedDate);
        holder.tvTime.setText(publishedTimeString);

        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.icon_news)
                .error(R.drawable.icon_news)
                .into(holder.ivNews);

        holder.linearLayout.setOnClickListener(v -> {
            // start detailsActivity and move data using bundle
            Intent intent = new Intent(context, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.DETAILS_DATA, article);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return ArticleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_news)
        ImageView ivNews;
        @BindView(R.id.tv_head_line)
        TextView tvHeadLine;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.lay_news)
        LinearLayout linearLayout;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
