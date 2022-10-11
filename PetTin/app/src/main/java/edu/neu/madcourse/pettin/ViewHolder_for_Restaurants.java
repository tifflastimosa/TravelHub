package edu.neu.madcourse.pettin;

import static java.lang.Math.max;
import static java.lang.Math.round;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import edu.neu.madcourse.pettin.Classes.RestaurantModel;

public class ViewHolder_for_Restaurants extends RecyclerView.ViewHolder {
    //debug tool
    private static final String TAG = ViewHolder_for_Restaurants.class.getSimpleName();

    public TextView nameTV;
    public TextView ratingTV;
    public TextView addressTV;
    public TextView distanceTV;
    public TextView typeTV;
    public ImageView picIV;

    public ViewHolder_for_Restaurants(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.idTVRestaurantName);
        this.ratingTV = itemView.findViewById(R.id.idTVRestaurantRating);
        this.addressTV =  itemView.findViewById(R.id.idTVRestaurantAddress);
        this.distanceTV = itemView.findViewById(R.id.idTVDistance);
        this.typeTV = itemView.findViewById(R.id.idTVRestaurantType);
        this.picIV =  itemView.findViewById(R.id.idIVRestaurantImage);
    }


    public void bindThisData(RestaurantModel thePersonToBind) {
        // sets the name of the person to the name textview of the viewholder.
        nameTV.setText(thePersonToBind.getCourse_name());
        // sets the age of the person to the websiteItem textview of the viewholder.
        ratingTV.setText(String.valueOf(thePersonToBind.getCourse_rating()));
        addressTV.setText(thePersonToBind.getCourse_address());
        double distance_to_2_decimal_places = round(Double.parseDouble(thePersonToBind.getDistance())*100.0)/100.0;
        distanceTV.setText(distance_to_2_decimal_places+" meters away");
        typeTV.setText(thePersonToBind.getCategory());
        if(!thePersonToBind.getCourse_image().isEmpty()) {
            Picasso
                    .get()
                    .load(thePersonToBind.getCourse_image())
                    .into(picIV);

        }
        }
}
