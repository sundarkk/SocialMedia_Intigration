package com.sundar.task.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sundar.task.R;

public class TittleAdapter extends RecyclerView.Adapter<TittleAdapter.ViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;

    public TittleAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public TittleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TittleAdapter.ViewHolder(inflater.inflate(R.layout.tittle_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TittleAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.img.setImageDrawable(activity.getDrawable(R.drawable.tenant));
            holder.txt_tittle.setText(activity.getString(R.string.rent));
          //  holder.cardview.setCardBackgroundColor(activity.getColor(R.color.medium_blue));


        } else if (position == 1) {
            holder.img.setImageDrawable(activity.getDrawable(R.drawable.sales));
            holder.txt_tittle.setText(activity.getString(R.string.sale));
        } else if (position == 2) {
            holder.img.setImageDrawable(activity.getDrawable(R.drawable.lease));
            holder.txt_tittle.setText(activity.getString(R.string.lease));
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_tittle;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txt_tittle = itemView.findViewById(R.id.txt_tittle);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
