package edu.neu.madcourse.pettin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;

public class DogToMatchAdapter extends RecyclerView.Adapter<DogToMatchAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<String> dogs;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser curUser;
    final String TAG = "DogToMatchAdapter";


    DogToMatchAdapter(Context context, List<String> dogs) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dogs = dogs;
    }



    @NonNull
    @Override
    public DogToMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.match_dialog_dog_view, parent, false);
        return new DogToMatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogToMatchAdapter.ViewHolder viewHolder, int i) {
        String dogId = dogs.get(i);
        viewHolder.name.setText(dogId);

        viewHolder.position = viewHolder.getAdapterPosition();


    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        int position;
        CheckBox matchSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.matchDialog_dogName);
            matchSelect = itemView.findViewById(R.id.matchDialog_checkbox);
        }


    }}
