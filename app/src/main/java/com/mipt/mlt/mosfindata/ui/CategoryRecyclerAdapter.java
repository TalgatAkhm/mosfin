package com.mipt.mlt.mosfindata.ui;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mipt.mlt.mosfindata.R;
import com.mipt.mlt.mosfindata.model.Category;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    private List<Category> categories;

    public interface ItemClickListener {
        void onClick(int position);
    }

    private ItemClickListener listener;

    public CategoryRecyclerAdapter(List<Category> categories, ItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_view, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.titleTV.setText(categories.get(position).getTitle());
        holder.desTV.setText(categories.get(position).getDes());
        holder.photoIM.setImageResource(categories.get(position).getPhoto());
        holder.cardView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView photoIM;
        TextView titleTV, desTV;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            photoIM = itemView.findViewById(R.id.photoIM);
            titleTV = itemView.findViewById(R.id.titleTV);
            desTV = itemView.findViewById(R.id.desTV);
        }
    }

}
