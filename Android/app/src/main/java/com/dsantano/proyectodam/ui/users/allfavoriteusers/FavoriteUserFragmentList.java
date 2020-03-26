package com.dsantano.proyectodam.ui.users.allfavoriteusers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.data.viewmodel.FavoriteUsersViewModel;
import com.dsantano.proyectodam.listeners.IFavoriteUserListener;
import com.dsantano.proyectodam.models.users.FavoriteUser;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.ui.users.allusers.MyUserRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteUserFragmentList extends Fragment {

    private int mColumnCount = 1;
    private IFavoriteUserListener mListener;
    Context context;
    RecyclerView recyclerView;
    MyFavoriteUserRecyclerViewAdapter adapter;
    FavoriteUsersViewModel favoriteUsersViewModel;
    List<User> userList = new ArrayList<>();
    private MenuItem busqueda;
    FavoriteUser favoriteUser;

    public FavoriteUserFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteUsersViewModel =new ViewModelProvider(getActivity()).get(FavoriteUsersViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_user_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyFavoriteUserRecyclerViewAdapter(getActivity(), userList, mListener);
            recyclerView.setAdapter(adapter);
            loadAllFavUsers();
        }
        return view;
    }

    public void loadAllFavUsers(){
        favoriteUsersViewModel.getAllFavUsers().observe(getActivity(), new Observer<FavoriteUser>() {
            @Override
            public void onChanged(FavoriteUser fav) {
                favoriteUser = fav;
                adapter.setData(favoriteUser.getFavoriteUsers());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFavoriteUserListener) {
            mListener = (IFavoriteUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IFavoriteUserListener");
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
        loadAllFavUsers();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        getActivity().getMenuInflater().inflate(R.menu.favorite_users_menu, menu);
        busqueda = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) busqueda.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<User> lista = busqueda(newText);
                cargarBusqueda(lista);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public List<User> busqueda(String palabraClave){
        List<User> result = new ArrayList<>();
        for (User user : userList ){
            for (String palabraClaveList : user.getPalabrasClave()){
                if(palabraClaveList.equalsIgnoreCase(palabraClave) || palabraClaveList.toLowerCase().contains(palabraClave.toLowerCase())){
                    if (!result.contains(user)){
                        result.add(user);
                    }
                }
            }
        }
        adapter.setData(result);
        return result;
    }

    public void cargarBusqueda (List<User> busqueda){
        adapter.setData(busqueda);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //OPT OF MENU
        }
        return super.onOptionsItemSelected(item);
    }

}
