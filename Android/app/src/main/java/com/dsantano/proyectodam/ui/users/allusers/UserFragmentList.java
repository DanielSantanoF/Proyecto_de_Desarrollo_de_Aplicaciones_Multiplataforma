package com.dsantano.proyectodam.ui.users.allusers;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.data.viewmodel.AllUserViewModel;
import com.dsantano.proyectodam.listeners.IAllUsersListener;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.ui.users.allusers.MyUserRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class UserFragmentList extends Fragment {

    private int mColumnCount = 1;
    private IAllUsersListener mListener;
    Context context;
    RecyclerView recyclerView;
    MyUserRecyclerViewAdapter adapter;
    AllUserViewModel allUserViewModel;
    List<User> userList = new ArrayList<>();

    public UserFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allUserViewModel =new ViewModelProvider(getActivity()).get(AllUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyUserRecyclerViewAdapter(getActivity(), userList, mListener);
            recyclerView.setAdapter(adapter);
            loadAllUsers();
        }
        return view;
    }

    public void loadAllUsers(){
        allUserViewModel.getAllUsers().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> list) {
                userList = list;
                adapter.setData(userList);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAllUsersListener) {
            mListener = (IAllUsersListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IAllUsersListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.GONE);
        loadAllUsers();
        recyclerView.setVisibility(View.VISIBLE);
    }
}
