package com.example.recipebook.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;
import com.example.recipebook.interfaces.AdapterItemClickListener;
import com.example.recipebook.model.Recipe;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class CookBookListAdapter extends RecyclerView.Adapter<CookBookListAdapter.ViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private AdapterItemClickListener listener;

    public CookBookListAdapter(AdapterItemClickListener listener){
        this.listener = listener;
    }

    public void setItems(Collection<Recipe> tweets) {
        recipes.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItems() {
        recipes.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cookbook,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindToView(recipes.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(recipes.get(position) );
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.itemTitle);
            imageView = itemView.findViewById(R.id.itemImage);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bindToView(Recipe recipe){
            title.setText(recipe.getTitle());

            if (recipe.getImage() != null) {
                byte[] bytesImage = Base64.getDecoder().decode(recipe.getImage());
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                Bitmap resized = Bitmap.createScaledBitmap(bmp, 177, 140, true);

                imageView.setImageBitmap(resized);
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();

        }
    }
}
