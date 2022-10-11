package edu.neu.madcourse.pettin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.pettin.Classes.RestaurantModel;

public class Adapter_for_Restaurants extends RecyclerView.Adapter<ViewHolder_for_Restaurants> {

    private final List<RestaurantModel> people;
    private final Context context;

    /**
     * Creates a WebsiteAdapter with the provided arraylist of Website objects.
     *
     * @param people    arraylist of Website object.
     * @param context   context of the activity used for inflating layout of the viewholder.
     */
    public Adapter_for_Restaurants (List<RestaurantModel> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder_for_Restaurants onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new ViewHolder_for_Restaurants(LayoutInflater.from(context).inflate(R.layout.restaurant_cardview, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_for_Restaurants holder, int position) {
        holder.bindThisData(people.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return people.size();
    }

}
