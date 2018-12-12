package com.prashanthgurunath.F22LabsApp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
 import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdView;
import com.prashanthgurunath.F22LabsApp.R;

public class FoodActivity extends AppCompatActivity {

    private TextView foodNameTextView,foodRatingTextView,foodPriceTextView,foodQuantityTextView,foodCostTextView;
    private ImageView foodImageView;
    private RatingBar ratingBar;
    private Button addFoodItemBtn, removeFoodItemBtn;
    private AdView mAdView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        String food_name=getIntent().getExtras().getString("food_name");
        String food_image_url=getIntent().getExtras().getString("food_image_url");

        final Double food_price=getIntent().getExtras().getDouble("food_price");
        Double food_rating=getIntent().getExtras().getDouble("food_rating");
        Double food_cost=getIntent().getExtras().getDouble("food_cost");


          final int food_quantity=getIntent().getExtras().getInt("food_qty");


          foodNameTextView=findViewById(R.id.af_item_name_text_view_id);
          foodRatingTextView=findViewById(R.id.af_avg_rating_text_view_id);
          foodPriceTextView=findViewById(R.id.af_item_price_text_view_id);
          foodImageView=findViewById(R.id.af_food_icon_image_view_id);
          foodQuantityTextView=findViewById(R.id.af_item_count_text_view_id);
          foodCostTextView=findViewById(R.id.af_current_item_cost_text_view_id);
          addFoodItemBtn=findViewById(R.id.af_add_btn_id);
          removeFoodItemBtn=findViewById(R.id.af_remove_btn_id);
          ratingBar=findViewById(R.id.af_rating_bar_id);


        foodNameTextView.setText(food_name);
        foodPriceTextView.setText(String.valueOf(food_price));
        foodRatingTextView.setText(String.valueOf(food_rating));
        foodQuantityTextView.setText(String.valueOf(food_quantity));
        foodCostTextView.setText(String.valueOf(food_cost));

         float ratingValue1 = 0.1f;
         ratingBar.setStepSize(ratingValue1);
         ratingBar.setRating(food_rating.floatValue());

         Glide.with(this).load(food_image_url)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foodImageView);

         if(food_quantity>=0)
        {

            food_cost=  (food_quantity*food_price);
            foodCostTextView.setText(String.valueOf(food_cost));

        }


        removeFoodItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_onclick_anim));

                int food_quantity1= Integer.parseInt(foodQuantityTextView.getText().toString());


                if (food_quantity1>0)
                {


                    --food_quantity1;
                    foodQuantityTextView.setText(String.valueOf(food_quantity1));

                   double food_cost =  (food_quantity1*food_price);


                    foodCostTextView.setText(String.valueOf(food_cost));


                }

            }
        });

        addFoodItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_onclick_anim));

                int food_quantity2= Integer.parseInt(foodQuantityTextView.getText().toString());


                if (food_quantity2>=0)
                {

                    ++food_quantity2;
                    foodQuantityTextView.setText(String.valueOf(food_quantity2));

                    double food_cost =  (food_quantity2*food_price);

                foodCostTextView.setText(String.valueOf(food_cost));


                }

            }
        });




        loadWebView(food_name);  // load web view to search the google meaning of the item name


    }

    public void loadWebView(String url)     // to search the google description of the food item
    {
        webView=findViewById(R.id.web_view_id);

        webView.loadUrl("http://www.google.com/search?q=" + url);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
    }


}
