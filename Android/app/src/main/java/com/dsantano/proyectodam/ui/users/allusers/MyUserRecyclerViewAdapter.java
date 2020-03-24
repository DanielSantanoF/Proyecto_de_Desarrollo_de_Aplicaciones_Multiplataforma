package com.dsantano.proyectodam.ui.users.allusers;

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
import com.dsantano.proyectodam.listeners.IAllUsersListener;
import com.dsantano.proyectodam.models.users.User;

import org.joda.time.LocalDate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private final Context ctx;
    private List<User> mValues;
    private final IAllUsersListener mListener;

    public MyUserRecyclerViewAdapter(Context ctx, List<User> mValues, IAllUsersListener mListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);
            holder.txtName.setText(holder.mItem.getName());
            if(holder.mItem.getTypeUser().equals("BUSCA_COMPANIA")){
                holder.txtType.setText(ctx.getResources().getString(R.string.searching_compani));
            } else if(holder.mItem.getTypeUser().equals("OFRECE_ALOJAMIENTO")){
                holder.txtType.setText(ctx.getResources().getString(R.string.offer_occupation));
            } else if(holder.mItem.getTypeUser().equals("JOVEN")){
                holder.txtType.setText(ctx.getResources().getString(R.string.offer_compani));
            } else if(holder.mItem.getTypeUser().equals("OFRECE_SERVICIO")) {
                holder.txtType.setText(ctx.getResources().getString(R.string.offer_service));
            } else {
                holder.txtType.setText("NaN");
            }
            LocalDate today = new LocalDate();
            int actualYear = today.getYear();
            int birthYear = Integer.parseInt(holder.mItem.getDateOfBirth().split("-")[0]);
            int age = actualYear - birthYear;
            holder.txtAge.setText(ctx.getResources().getString(R.string.age) + " " + age);

            if(holder.mItem.getAvatar() != null){
                //decode base64 string to image
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] imageBytes = baos.toByteArray();
                imageBytes = Base64.decode(holder.mItem.getAvatar().getData(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Glide
                        .with(ctx)
                        .load(decodedImage)
                        .transform(new CircleCrop())
                        .into(holder.ivAvatar);
            } else {
                Glide
                        .with(ctx)
                        .load(ctx.getDrawable(R.drawable.default_user))
                        .transform(new CircleCrop())
                        .into(holder.ivAvatar);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onAllUsersItemClick(holder.mItem);
                    }
                }
            });
        }
    }

    public void setData(List<User> list){
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
        public final ImageView ivAvatar;
        public final TextView txtName;
        public final TextView txtAge;
        public final TextView txtType;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivAvatar = view.findViewById(R.id.imageViewUserUserList);
            txtName = view.findViewById(R.id.textViewNameUserLIst);
            txtAge = view.findViewById(R.id.textViewAgeUserList);
            txtType = view.findViewById(R.id.textViewTypeUserList);
        }

    }
}
