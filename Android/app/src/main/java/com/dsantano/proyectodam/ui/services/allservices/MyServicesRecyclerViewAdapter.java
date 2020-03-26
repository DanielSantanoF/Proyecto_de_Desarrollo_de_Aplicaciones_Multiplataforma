package com.dsantano.proyectodam.ui.services.allservices;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.listeners.IAllServicesListener;
import com.dsantano.proyectodam.models.services.Services;

import java.util.ArrayList;
import java.util.List;

public class MyServicesRecyclerViewAdapter extends RecyclerView.Adapter<MyServicesRecyclerViewAdapter.ViewHolder> {

    private final Context ctx;
    private List<Services> mValues;
    private final IAllServicesListener mListener;

    public MyServicesRecyclerViewAdapter(Context ctx, List<Services> mValues, IAllServicesListener mListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_services_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);
            holder.txtTitle.setText(holder.mItem.getTitle());
            if(holder.mItem.getDescription().length() > 60){
                String description = holder.mItem.getDescription().substring(0, 50);
                holder.txtDescription.setText(description + "...");
            } else {
                holder.txtDescription.setText(holder.mItem.getDescription());
            }
            if(holder.mItem.getTypeService().equals("HACER_COMPRA")){
                Glide.with(ctx).load(R.drawable.hacer_compra).transform(new CircleCrop()).into(holder.ivTypeService);
            } else if(holder.mItem.getTypeService().equals("TRANSPORTE")){
                Glide.with(ctx).load(R.drawable.transport).transform(new CircleCrop()).into(holder.ivTypeService);
            } else if(holder.mItem.getTypeService().equals("COCINAR")){
                Glide.with(ctx).load(R.drawable.cooking).transform(new CircleCrop()).into(holder.ivTypeService);
            } else if(holder.mItem.getTypeService().equals("REPARACIONES")){
                Glide.with(ctx).load(R.drawable.repair).transform(new CircleCrop()).into(holder.ivTypeService);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onAllServicesItemClick(holder.mItem);
                    }
                }
            });
        }
    }

    public void setData(List<Services> list){
        if(this.mValues != null) {
            this.mValues.clear();
        } else {
            this.mValues =  new ArrayList<>();
        }
        this.mValues.addAll(list);
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
        public final ImageView ivTypeService;
        public final TextView txtTitle;
        public final TextView txtDescription;
        public Services mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivTypeService = view.findViewById(R.id.imageViewTypeServiceServicesList);
            txtTitle = view.findViewById(R.id.textViewTitleServicesList);
            txtDescription = view.findViewById(R.id.textViewDescriptionServicesList);
        }
    }
}
