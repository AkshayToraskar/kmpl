package com.ak.kmpl.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ak.kmpl.R;
import com.ak.kmpl.activity.AddRecordsActivity;
import com.ak.kmpl.model.VehicleRecords;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by WDIPL44 on 3/9/2016.
 */
public class VehicleRecordAdapter extends RecyclerView.Adapter<VehicleRecordAdapter.MyViewHolder> {

    private ArrayList<VehicleRecords> mDataset;
    private final static int FADE_DURATION = 1000; // in milliseconds
    private Context mContext;
    public ImageView ivEdit;

    Typeface tf_regular;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvDate, tvLitre, tvAmount, tvReading, tvDistanceCovered, tvAverage;


        public MyViewHolder(View itemView) {
            super(itemView);
            Calligrapher calligrapher = new Calligrapher(mContext);
            calligrapher.setFont((Activity) mContext, "CircularAir-Light.otf", true);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvLitre = (TextView) itemView.findViewById(R.id.tvLitre);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvReading = (TextView) itemView.findViewById(R.id.tvReading);
            tvDistanceCovered = (TextView) itemView.findViewById(R.id.tvDistanceCovered);
            tvAverage = (TextView) itemView.findViewById(R.id.tvAverage);
            //cardView = (CardView) itemView.findViewById(R.id.cvTodaysOrderItem);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);

        }
    }


    public VehicleRecordAdapter(ArrayList<VehicleRecords> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public VehicleRecordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        tf_regular = Typeface.createFromAsset(parent.getContext().getAssets(),"CircularAirPro-Light.ttf");


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle_records, parent, false);
     //   Calligrapher calligrapher = new Calligrapher(mContext);
     //   calligrapher.setFont((Activity) parent.getContext(), "CircularAir-Light.otf", true);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleRecordAdapter.MyViewHolder holder, final int position) {



        holder.tvName.setText("Vehicle Name: " + mDataset.get(position).getvName());
        holder.tvName.setTypeface(tf_regular);

        holder.tvDate.setText("Date: " + mDataset.get(position).getDate());
        holder.tvDate.setTypeface(tf_regular);

        holder.tvLitre.setText("Litre: " + mDataset.get(position).getLitre() + " Ltr");
        holder.tvLitre.setTypeface(tf_regular);

        holder.tvAmount.setText("Amount: " + mDataset.get(position).getAmt() + " \u20B9");
        holder.tvAmount.setTypeface(tf_regular);

        holder.tvReading.setText("Reading: " + mDataset.get(position).getReading() + " Kms");
        holder.tvReading.setTypeface(tf_regular);

        holder.tvDistanceCovered.setText("Distance Covered: " + mDataset.get(position).getDistCover() + " Kms");
        holder.tvDistanceCovered.setTypeface(tf_regular);

        holder.tvAverage.setText("Average: " + mDataset.get(position).getAverage() + " Kmpl");
        holder.tvAverage.setTypeface(tf_regular);










        if (position == 0) {

            ivEdit.setVisibility(View.VISIBLE);
           // Calligrapher calligrapher = new Calligrapher(mContext);
          //  calligrapher.setFont((Activity) mContext, "CircularAir-Light.otf", true);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, AddRecordsActivity.class);
                    i.putExtra("vehicle_record_id", String.valueOf(mDataset.get(position).getId()));

                    mContext.startActivity(i);

                    Log.v("AAA", "position" + String.valueOf(mDataset.get(position).getId()));
                }
            });
        } else {
            ivEdit.setVisibility(View.GONE);
        }

        // setAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void setAnimation(View view) {
        /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);*/

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.REVERSE, 0.5f, Animation.REVERSE, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);

    }


}