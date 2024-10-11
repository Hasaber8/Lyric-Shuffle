package edu.northeastern.a6_group10.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.northeastern.a6_group10.R;

public class RviewAdapter extends RecyclerView.Adapter<RviewHolder> {

    private final ArrayList<ItemCard> itemList;

    //Constructor
    public RviewAdapter(ArrayList<ItemCard> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new RviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RviewHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);

        holder.resultRating.setText(currentItem.getRating());
        holder.resultType.setText(currentItem.getType());
        holder.resultTitle.setText(currentItem.getTitle());

        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageUrl())
                .into(holder.resultIcon);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
