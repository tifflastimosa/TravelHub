package edu.neu.madcourse.pettin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;

public class DogPlayDateAdapter extends RecyclerView.Adapter<DogPlayDateAdapter.ViewHolder> implements View.OnClickListener {
    private LayoutInflater layoutInflater;
    private List<Dogs> dogs;
    private OnDogListener onDogListener;
    private Context context;
    FirebaseFirestore db;
    final String TAG = "Dog Adapater";
    private SingleDogActivity activity;

    DogPlayDateAdapter(Context context, List<Dogs> dogs, OnDogListener onDogListener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dogs = dogs;
        this.onDogListener = onDogListener;
    }

    DogPlayDateAdapter(Context context, List<Dogs> posts, OnDogListener onDogListener, SingleDogActivity activity) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dogs = posts;
        this.onDogListener = onDogListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.dog_view, viewGroup, false);
        return new ViewHolder(view, onDogListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Dogs dog = dogs.get(i);
        viewHolder.name.setText(dog.getName());
        viewHolder.age.setText(String.valueOf(dog.getAge()));
        viewHolder.gender.setText(" "+dog.getGender());

        viewHolder.energyLevel.setText(String.valueOf(dog.getEnergyLevel()));
        viewHolder.weight.setText(Double.toString(dog.getWeight()) + " lbs");
        viewHolder.spayed.setText("Spayed: " + dog.getSpayed());
        viewHolder.breed.setText(dog.getBreed());
        viewHolder.city.setText(dog.getLocation());
        viewHolder.setPhoto(dog.getImg());

    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    @Override
    public void onClick(View view) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, age, gender, energyLevel, weight, spayed, breed, city;
        ImageView photo;
        OnDogListener onDogListener;

        public ViewHolder(@NonNull View itemView, OnDogListener onDogListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_name);
            age = itemView.findViewById(R.id.textView_age);
            gender = itemView.findViewById(R.id.textView_gender);

            energyLevel = itemView.findViewById(R.id.textView_energyLevel);
            weight = itemView.findViewById(R.id.textView_weight);
            spayed = itemView.findViewById(R.id.textView_spayed);
            breed = itemView.findViewById(R.id.textView_breed);
            city = itemView.findViewById(R.id.textView_loc);


            this.onDogListener = onDogListener;
            itemView.setOnClickListener(this);
        }


        public void setPhoto(String url) {
            System.out.println("url " + url);
            photo = itemView.findViewById(R.id.imageView_dog);
            Glide.with(context).load(url).apply(new RequestOptions().override(150, 150)).centerCrop().into(photo);
        }

        @Override
        public void onClick(View view) {
            onDogListener.onDogClick(getAdapterPosition());
        }
    }
    public interface OnDogListener {
        void onDogClick(int position);
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position) {
        Dogs dog = dogs.get(position);
        db = FirebaseFirestore.getInstance();
        db.collection("dogs").document(dog.getDog_id()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "failed to delete");
                    }
                });
        dogs.remove(position);
        notifyItemRemoved(position);
    }
}