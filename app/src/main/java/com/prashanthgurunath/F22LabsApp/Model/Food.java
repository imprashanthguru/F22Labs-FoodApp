package com.prashanthgurunath.F22LabsApp.Model;


import java.util.Comparator;

public class Food {

    private Double averageRating;
    private String imageUrl;
    private String itemName;
    private Double itemPrice;



    private Double totalCost;

    public Food() {

    }
    public Food(Double averageRating,String imageUrl, String itemName, Double itemPrice) {

        this.averageRating=averageRating;
        this.imageUrl=imageUrl;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
    }


    public Double getAverageRating() {
        return averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }


    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }





    public static final Comparator<Food> RATING_DESC= new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {

            return o2.getAverageRating().compareTo(o1.getAverageRating());

        }
    };


    public static final Comparator<Food> FOOD_PRICE_DESC= new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {

            return o1.getItemPrice().compareTo(o2.getItemPrice());

        }
    };



}
