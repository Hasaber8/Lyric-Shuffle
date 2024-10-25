package edu.northeastern.a6_group10.userListRecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.a6_group10.R;

public class RviewHolder extends RecyclerView.ViewHolder {
    public TextView resultUsername;

    public RviewHolder(View itemView) {
        super(itemView);
        resultUsername = itemView.findViewById(R.id.resultUser);

    }
}