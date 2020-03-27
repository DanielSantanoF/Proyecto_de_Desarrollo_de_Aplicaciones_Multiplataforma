package com.dsantano.proyectodam.ui.locations.alllocations;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.listeners.IAllLocationsListener;
import com.dsantano.proyectodam.models.Locations.LocationUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MyLocationRecyclerViewAdapter extends RecyclerView.Adapter<MyLocationRecyclerViewAdapter.ViewHolder> {

    private final Context ctx;
    private List<LocationUser> mValues;
    private final IAllLocationsListener mListener;

    public MyLocationRecyclerViewAdapter(Context ctx, List<LocationUser> mValues, IAllLocationsListener mListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_location_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);
            holder.txtCountry.setText(holder.mItem.getLocationOffered().getCountry());
            holder.txtCity.setText(holder.mItem.getLocationOffered().getCity());
            holder.txtStreet.setText(holder.mItem.getLocationOffered().getStreet());
            if(holder.mItem.getAvatar() != null){
                //decode base64 string to image
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] imageBytes = baos.toByteArray();
                imageBytes = Base64.decode(holder.mItem.getAvatar().getData(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Glide
                        .with(ctx)
                        .load(decodedImage)
                        .thumbnail(Glide.with(ctx).load(R.drawable.loading_gif))
                        .transform(new CircleCrop())
                        .into(holder.ivUserOffering);
            } else {
                Glide
                        .with(ctx)
                        .load(ctx.getDrawable(R.drawable.default_user))
                        .thumbnail(Glide.with(ctx).load(R.drawable.loading_gif))
                        .transform(new CircleCrop())
                        .into(holder.ivUserOffering);
            }
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onAllLocationsItemClick(holder.mItem);
                    }
                }
            });
        }
    }

    public void setData(List<LocationUser> list){
        if(this.mValues != null) {
            this.mValues.clear();
            for (int i = 0; i <list.size() ; i++) {
                if(list.get(i).getLocationOffered() != null){
                    this.mValues.add(list.get(i));
                }
            }
        } else {
            this.mValues =  new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivUserOffering;
        public final TextView txtCountry;
        public final TextView txtCity;
        public final TextView txtStreet;
        public LocationUser mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivUserOffering = view.findViewById(R.id.imageViewUserLocationList);
            txtCountry = view.findViewById(R.id.textViewCountryLocationList);
            txtCity = view.findViewById(R.id.textViewCityLocationList);
            txtStreet = view.findViewById(R.id.textViewStreetLocationList);
        }

    }
}
