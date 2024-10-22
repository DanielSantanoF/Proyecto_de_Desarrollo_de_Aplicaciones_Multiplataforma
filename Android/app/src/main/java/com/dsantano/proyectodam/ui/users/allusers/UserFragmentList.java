package com.dsantano.proyectodam.ui.users.allusers;

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
import com.dsantano.proyectodam.data.viewmodel.AllUserViewModel;
import com.dsantano.proyectodam.listeners.IAllUsersListener;
import com.dsantano.proyectodam.models.users.User;

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
    List<User> userListToLoad = new ArrayList<>();
    private MenuItem busqueda;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        getActivity().getMenuInflater().inflate(R.menu.all_users_menu, menu);
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
            case R.id.action_searching_compani_users:
                userListToLoad.clear();
                for (int i = 0; i <userList.size() ; i++) {
                    if(userList.get(i).getTypeUser().equals("BUSCA_COMPANIA")){
                        userListToLoad.add(userList.get(i));
                    }
                }
                adapter.setData(userListToLoad);
                break;
            case R.id.action_ofers_accomodation_users:
                userListToLoad.clear();
                for (int i = 0; i <userList.size() ; i++) {
                    if(userList.get(i).getTypeUser().equals("OFRECE_ALOJAMIENTO")){
                        userListToLoad.add(userList.get(i));
                    }
                }
                adapter.setData(userListToLoad);
                break;
            case R.id.action_young_users:
                userListToLoad.clear();
                for (int i = 0; i <userList.size() ; i++) {
                    if(userList.get(i).getTypeUser().equals("JOVEN")){
                        userListToLoad.add(userList.get(i));
                    }
                }
                adapter.setData(userListToLoad);
                break;
            case R.id.action_offers_service_users:
                userListToLoad.clear();
                for (int i = 0; i <userList.size() ; i++) {
                    if(userList.get(i).getTypeUser().equals("OFRECE_SERVICIO")){
                        userListToLoad.add(userList.get(i));
                    }
                }
                adapter.setData(userListToLoad);
                break;
            case R.id.action_all_users:
                adapter.setData(userList);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
