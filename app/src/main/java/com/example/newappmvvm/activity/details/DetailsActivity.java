package com.example.newappmvvm.activity.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newappmvvm.R;
import com.example.newappmvvm.databinding.ActivityDetailsBinding;
import com.example.newappmvvm.model.headLines.Article;
import com.example.newappmvvm.utils.Constants;
import com.example.newappmvvm.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    Article article;

    private DateConverter dateConverter = new DateConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        // get data using bundle
        Bundle bundle = getIntent().getExtras();
        // check bundle
        if (bundle != null) {
            article = bundle.getParcelable(Constants.DETAILS_DATA);
            setTitle(article.getSource().getName());
            init();
        }
    }

    private void init() {
        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.icon_news)
                .error(R.drawable.icon_news)
                .into(binding.ivDetails);
        binding.tvDetailsHeadLine.setText(article.getTitle());
        Date publishedDate = dateConverter.getDateFromDepartureOrArrivalInquiryString(article.getPublishedAt());
        String publishedDateString = dateConverter.getDateFromDate(publishedDate);
        binding.tvDetailsDate.setText(publishedDateString);
        binding.tvDetailsDescription.setText(article.getDescription());
        if (article.getContent() != null)
            binding.tvDetailsContent.setText(Html.fromHtml(article.getContent()));
        binding.tvAuthorName.setText(article.getAuthor());

        binding.tvUrl.setText(article.getUrl());
        binding.tvUrl.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            startActivity(intent);
        });

    }

}
