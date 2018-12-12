package com.prashanthgurunath.F22LabsApp.Activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.prashanthgurunath.F22LabsApp.Adapters.RecyclerViewAdapter;
import com.prashanthgurunath.F22LabsApp.Model.Food;
import com.prashanthgurunath.F22LabsApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.support.design.widget.BottomSheetBehavior;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{


    private final String url="https://android-full-time-task.firebaseio.com/data.json"; // URL Link

    private RecyclerView recyclerView;
    private List<Food> foodList1;
    private List<Food> foodList2;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private TextView totalTextView;
      Double totalVal;

      String textVal;


    private BottomSheetBehavior mBottomSheetBehavior;

    private String TAG="BOTTOM_SHEET";
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        totalTextView = findViewById(R.id.total_text_view_id);
        recyclerView = findViewById(R.id.recycler_view_id);

        foodList1 = new ArrayList<>();
         jsonRequestFunction();     // network req to the URL



        //  attach bottom sheet ID
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);


        // bottom sheet callback behavior
         mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(TAG, "State Collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "State Dragging");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "State Expanded");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(TAG, "State Hidden");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d(TAG, "State Settling");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });



    }


    public void getTotalVal(Double tot)
    {

        totalVal=tot;
        Log.d("inside_main_getTotVal","getTotVal: "+totalVal);


        textVal=totalVal.toString();
    }

    public void sortByRatings(View v)
    {

        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_onclick_anim));

        bottomSheetDialogFragment.dismiss(); // dismiss Btm sheet first
         foodList2=foodList1;
         createRecyclerView(foodList1);
         Collections.sort(foodList1, Food.RATING_DESC);     // use collections to sort content


        Toast.makeText(getApplicationContext(),"Sorted Data by Ratings!",Toast.LENGTH_SHORT).show();
    }


    public void sortByPrice(View v)
    {
        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_onclick_anim));

        bottomSheetDialogFragment.dismiss();
        createRecyclerView(foodList1);
        Collections.sort(foodList1, Food.FOOD_PRICE_DESC);
        Toast.makeText(getApplicationContext(),"Sorted Data by Price!",Toast.LENGTH_SHORT).show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_cart_item_id:


                RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(getApplicationContext(),foodList1);
                Double t=recyclerViewAdapter.getTotalVal();

          totalTextView.setText(t.toString());
          Log.d("app_cart_val","getTotVal: "+t);  // we are displaying the total val in a log




                return true;
            case R.id.app_filter_item_id:   // invoke Bottom sheet fragment for filter options

                 bottomSheetDialogFragment = new BottomSheetFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void jsonRequestFunction() {

        jsonArrayRequest=new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {   // response listener
                 jsonObject = null;

                for (int i = 0; i < response.length(); ++i) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Food foodObj = new Food();
                        foodObj.setItemName(jsonObject.getString("item_name"));
                        foodObj.setAverageRating(jsonObject.getDouble("average_rating"));
                        foodObj.setItemPrice(jsonObject.getDouble("item_price"));
                        foodObj.setImageUrl(jsonObject.getString("image_url"));


                        Log.d(jsonObject.getString("item_name"),"Double val: "+jsonObject.getDouble("item_price"));

                        foodList1.add(foodObj);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                createRecyclerView(foodList1);
            }
        }, new Response.ErrorListener() {           // error listener
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("volley_error","Volley Error");

            }
        });

            requestQueue= Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(jsonArrayRequest);

    }

    private void createRecyclerView(List<Food> foodList1) {

        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(this,foodList1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);  // pass recyclerview adapter

    }

}
