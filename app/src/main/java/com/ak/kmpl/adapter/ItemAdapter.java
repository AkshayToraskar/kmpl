package com.ak.kmpl.adapter;

/**
 * Created by WDIPL27 on 8/26/2016.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ak.kmpl.R;
import com.ak.kmpl.model.Item;

import java.util.List;

public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItems;
    private ItemListener mListener;
    private Context mContext;

    Typeface tf_regular;


    public ItemAdapter(List<Item> items, ItemListener listener) {
        mItems = items;
        mListener = listener;
    }

    public void setListener(ItemListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         tf_regular = Typeface.createFromAsset(parent.getContext().getAssets(),"CircularAirPro-Light.ttf");

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false));



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;
        public TextView tvRating,tvVicinity;
        public Item item;

        public ViewHolder(View itemView) {


            super(itemView);

            itemView.setOnClickListener(this);



            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvVicinity = (TextView) itemView.findViewById(R.id.tvVicinity);
        }

        public void setData(Item item) {
            this.item = item;

            imageView.setImageResource(item.getDrawableResource());

            textView.setText(item.getTitle());
            textView.setTypeface(tf_regular);

            tvRating.setText(item.getRating());
            tvRating.setTypeface(tf_regular);

            tvVicinity.setText(item.getVicinity());
            tvVicinity.setTypeface(tf_regular);



        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(Item item);
    }
}