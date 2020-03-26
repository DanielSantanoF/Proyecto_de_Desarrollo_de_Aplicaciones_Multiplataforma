package com.dsantano.proyectodam.ui.users.allfavoriteusers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.listeners.IFavoriteUserListener;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import org.joda.time.LocalDate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFavoriteUserRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteUserRecyclerViewAdapter.ViewHolder> {

    private final Context ctx;
    private List<User> mValues;
    private final IFavoriteUserListener mListener;
    Service service;

    public MyFavoriteUserRecyclerViewAdapter(Context ctx, List<User> mValues, IFavoriteUserListener mListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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
            int actualDay = today.getDayOfMonth();
            int actualMonth = today.getMonthOfYear();
            String birthDate = holder.mItem.getDateOfBirth().split("T")[0];
            int birthYear = Integer.parseInt(birthDate.split("-")[0]);
            int birthDay = Integer.parseInt(birthDate.split("-")[2]);
            int birthMonth = Integer.parseInt(birthDate.split("-")[1]);
            int age = actualYear - birthYear;
            age = age -1;
            if(actualMonth >= birthMonth){
                if(actualDay < birthDay){
                    age = age + 1;
                }
            }
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
                        .thumbnail(Glide.with(ctx).load(R.drawable.loading_gif))
                        .transform(new CircleCrop())
                        .into(holder.ivAvatar);
            } else {
                Glide
                        .with(ctx)
                        .load(ctx.getDrawable(R.drawable.default_user))
                        .thumbnail(Glide.with(ctx).load(R.drawable.loading_gif))
                        .transform(new CircleCrop())
                        .into(holder.ivAvatar);
            }
            holder.btnDeleteFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service = TokenServiceGenerator.createService(Service.class);
                    Call<User> call = service.deleteFavoriteById(holder.mItem.getId());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                mValues.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(MyApp.getContext(), ctx.getResources().getString(R.string.favorite_deleted), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MyApp.getContext(), ctx.getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                                Log.i("deleteFav", "Error onResponse");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MyApp.getContext(), ctx.getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                            Log.d("deleteFav", "onFailure: " + t.getMessage());
                        }
                    });
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onFavoriteUsersItemClick(holder.mItem);
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
        public final ImageButton btnDeleteFav;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivAvatar = view.findViewById(R.id.imageViewUserUserList);
            txtName = view.findViewById(R.id.textViewNameUserLIst);
            txtAge = view.findViewById(R.id.textViewAgeUserList);
            txtType = view.findViewById(R.id.textViewTypeUserList);
            btnDeleteFav = view.findViewById(R.id.imageButtonDeleteFavorite);
        }

    }
}
