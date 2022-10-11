package edu.neu.madcourse.pettin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.pettin.Classes.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Post> posts;
    private RecyclerViewClickListener listener;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser curUser;

    public PostAdapter(Context context, ArrayList<Post> postList, RecyclerViewClickListener listener) {
        this.context = context;
        this.posts = postList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();
        Post post = posts.get(position);
        String username = post.getUsername();
        String postId = post.getPost_id();
        String title = post.getTitle();
//        String likes = post.getLikes();
        holder.title.setText(title.length() <= 20? title: title.substring(0,20) + "...");
//        holder.likes.setText(Integer.toString(post.getLikes().size()));
        holder.username.setText(username);
//
//        if (post.getLikes().contains(curUser.getUid())) {
//            Glide.with(context).load(R.drawable.ic_baseline_liked).into(holder.heart);
//        } else {
//            Glide.with(context).load(R.drawable.ic_baseline_match).into(holder.heart);
//        }
        Glide.with(context).load(post.getImage()).into(holder.image);
        isLikes(postId, holder.heart);
        nLikes(holder.likes, postId);



//         Click the username, got some bugs here
//        holder.username.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ShowUserDetailActivity.class);
//                intent.putExtra("username", username);
//                context.startActivity(intent);
//            }
//        });


        // Click the Likes
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("postId " + postId);
                System.out.println( "collections" + db.collection("posts"));
                if (holder.heart.getTag().equals("like")) {
                    DocumentReference postRef = db.collection("posts").document(postId);
                    postRef.update("likes", FieldValue.arrayUnion(curUser.getUid()));
//                    Glide.with(context).load(R.drawable.ic_baseline_liked).into(holder.heart);
                } else {
//                    DocumentReference postRef = db.collection("posts").document(postId);
//                    postRef.update("likes", FieldValue.arrayRemove(curUser.getUid()));
//                    Glide.with(context).load(R.drawable.ic_baseline_match).into(holder.heart);
                }



//                postRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        Post curPost = documentSnapshot.toObject(Post.class);
//                        String likesThisPost = String.valueOf(Integer.valueOf(likes)+1);
//                        curPost.setLikes(likesThisPost);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("post adapter", "failed to like");
//                    }
//                });

            }
        });

    }



    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, likes, username;
        ImageView heart, image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            likes = itemView.findViewById(R.id.likes);
            username = itemView.findViewById(R.id.post_username);
            heart = itemView.findViewById(R.id.heart);
            image = itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        // background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Post> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()) {
                filteredList.addAll(posts);
            } else {
                String pattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();
                // Search for title
                for(Post post: posts) {
                    if(post.getTitle().toLowerCase().contains(pattern) || (post.getLocation() != null && post.getLocation().toLowerCase().contains(pattern))) {
                        filteredList.add(post);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            posts.clear();
            posts.addAll((Collection<? extends Post>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    private void isLikes(String postId, final ImageView imageview) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference postRef = FirebaseFirestore.getInstance().collection("posts").document(postId);

        postRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null) {
                    Log.w("nLikes", error);
                }
                if (value != null && value.exists()) {
                    Post post = value.toObject(Post.class);
                    if (post.getLikes().contains(firebaseUser.getUid())) {
                        imageview.setImageResource(R.drawable.ic_baseline_liked);
                        imageview.setTag("liked");
                    } else {
                        imageview.setImageResource(R.drawable.ic_baseline_match);
                        imageview.setTag("like");
                    }
                }
            }
        });
    }

    private void nLikes(TextView likes, String postId) {
        DocumentReference postRef = FirebaseFirestore.getInstance().collection("posts").document(postId);
        postRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null) {
                    Log.w("nLikes", error);
                }
                if (value != null && value.exists()) {
                    Post post = value.toObject(Post.class);
                    likes.setText(Integer.toString(post.getLikes().size()) + "likes");
                }
            }
        });
//        postRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Post post = task.getResult().toObject(Post.class);
//                    likes.setText(Integer.toString(post.getLikes().size()) + "likes");
//                }
//            }
//        });
    }

} 
