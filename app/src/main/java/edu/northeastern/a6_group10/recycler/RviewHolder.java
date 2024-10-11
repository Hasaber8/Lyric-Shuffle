package edu.northeastern.a6_group10.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.a6_group10.R;

public class RviewHolder extends RecyclerView.ViewHolder {
    public TextView resultTitle;
    public TextView resultType;
    public TextView resultRating;
    public ImageView resultIcon;


    public RviewHolder(View itemView) {
        super(itemView);
        resultTitle = itemView.findViewById(R.id.resultTitle);
        resultType = itemView.findViewById(R.id.resultType);
        resultIcon = itemView.findViewById(R.id.resultIcon);
        resultRating = itemView.findViewById(R.id.resultRating);
    }
}