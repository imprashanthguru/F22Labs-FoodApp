package com.prashanthgurunath.F22LabsApp.Adapters;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.prashanthgurunath.F22LabsApp.Activity.FoodActivity;

import com.prashanthgurunath.F22LabsApp.Activity.MainActivity;
import com.prashanthgurunath.F22LabsApp.Model.Food;
import com.prashanthgurunath.F22LabsApp.R;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private Context context;
    private List<Food> listFoodData;

    private int curFoodItemCount;
    private Double currItemCost;

//     private Double total=0.0;
    private Double totalFinal=0.0;

     private Boolean qtyChanged=false;

    private MainActivity mainActivity;

    public RecyclerViewAdapter(Context context, List<Food> listFoodData) {
        this.context = context;
        this.listFoodData = listFoodData;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.list_item,parent,false);

        final MyHolder myHolder= new MyHolder(view);

        mainActivity=new MainActivity();


        myHolder.viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_onclick_anim));

                Intent intent=new Intent(context, FoodActivity.class);
                intent.putExtra("food_name",listFoodData.get(myHolder.getAdapterPosition()).getItemName());
                intent.putExtra("food_price",listFoodData.get(myHolder.getAdapterPosition()).getItemPrice());
                intent.putExtra("food_image_url",listFoodData.get(myHolder.getAdapterPosition()).getImageUrl());
                intent.putExtra("food_rating",listFoodData.get(myHolder.getAdapterPosition()).getAverageRating());
                intent.putExtra("food_qty", curFoodItemCount);
                intent.putExtra("food_cost",currItemCost);

                context.startActivity(intent);

            }
        });


       // return new MyHolder(view);
                            return myHolder;

    }



    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {


        holder.itemNameTextView.setText(listFoodData.get(position).getItemName());

        holder.itemRatingTextView.setText(Double.toString(listFoodData.get(position).getAverageRating()));
        holder.itemPriceTextView.setText(Double.toString(listFoodData.get(position).getItemPrice()));

        // we use Glide to Load the image
        Glide.with(context).load(listFoodData.get(position).getImageUrl())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImageView);


        Double ratingValue=listFoodData.get(position).getAverageRating();
        float ratingValue1 = 0.1f;
         holder.ratingBar.setStepSize(ratingValue1);
        holder.ratingBar.setRating(ratingValue.floatValue());



        // Logic calc


        currItemCost=0.0;
        curFoodItemCount= Integer.parseInt(holder.itemCountTextView.getText().toString());

        if(curFoodItemCount>=0)
        {
            currItemCost =  (curFoodItemCount*listFoodData.get(position).getItemPrice());
            holder.currItemCostTextView.setText(String.valueOf(currItemCost));

        }

        holder.removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_onclick_anim));



                curFoodItemCount= Integer.parseInt(holder.itemCountTextView.getText().toString());

                if (curFoodItemCount>0)
                {

                    --curFoodItemCount;
                    holder.itemCountTextView.setText(String.valueOf(curFoodItemCount));

                    currItemCost =  (curFoodItemCount*listFoodData.get(position).getItemPrice());

                    holder.currItemCostTextView.setText(String.valueOf(currItemCost));



                    qtyChanged=true;
                    if(qtyChanged==true)
                    {
                         Double total=listFoodData.get(position).getItemPrice();

                         totalFinal=totalFinal-total;
                        Log.d("tot_final_-","total final: "+totalFinal);
                        mainActivity.getTotalVal(totalFinal);
                        Toast.makeText(context,"Total: "+totalFinal,Toast.LENGTH_SHORT).show();



                    }

                }


            }
        });


        holder.addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_onclick_anim));


                curFoodItemCount= Integer.parseInt(holder.itemCountTextView.getText().toString());
                if (curFoodItemCount>=0)
                {
                    ++curFoodItemCount;
                    holder.itemCountTextView.setText(String.valueOf(curFoodItemCount));


                    currItemCost =  (curFoodItemCount*listFoodData.get(position).getItemPrice());

                    holder.currItemCostTextView.setText(String.valueOf(currItemCost));
                    qtyChanged=true;


                    if(qtyChanged==true)
                    {
                           Double total=listFoodData.get(position).getItemPrice();

                           totalFinal=totalFinal+total;
                        Log.d("tot_final_+","total final: "+totalFinal);

//                        setTotalVal(totalFinal);
//                        getTotalVal();
                        mainActivity.getTotalVal(totalFinal);

                        Toast.makeText(context,"Total: "+totalFinal,Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });




    }

    public void setTotalVal(Double t)
    {
        totalFinal=t;
    }

    public double getTotalVal()
    {
        return totalFinal;
    }




    @Override
    public int getItemCount() {
        return listFoodData.size();
    }




    public static class MyHolder extends RecyclerView.ViewHolder
    {

          TextView itemNameTextView;
          TextView itemPriceTextView;
          TextView itemRatingTextView;
          ImageView itemImageView;

          RatingBar ratingBar;
          Button removeItemBtn,addItemBtn;

          TextView itemCountTextView;
          TextView currItemCostTextView;

          Button viewMoreButton;



        public MyHolder(View itemView) {
            super(itemView);

            itemNameTextView=itemView.findViewById(R.id.item_name_text_view_id);
            itemPriceTextView=itemView.findViewById(R.id.item_price_text_view_id);
            itemRatingTextView=itemView.findViewById(R.id.avg_rating_text_view_id);
            itemImageView=itemView.findViewById(R.id.food_icon_image_view_id);

            ratingBar=itemView.findViewById(R.id.rating_bar_id);
            removeItemBtn=itemView.findViewById(R.id.remove_btn_id);
            addItemBtn=itemView.findViewById(R.id.add_btn_id);

            itemCountTextView=itemView.findViewById(R.id.item_count_text_view_id);
            currItemCostTextView=itemView.findViewById(R.id.current_item_cost_text_view_id);

            viewMoreButton=itemView.findViewById(R.id.view_more_button_id);




        }
    }

}
