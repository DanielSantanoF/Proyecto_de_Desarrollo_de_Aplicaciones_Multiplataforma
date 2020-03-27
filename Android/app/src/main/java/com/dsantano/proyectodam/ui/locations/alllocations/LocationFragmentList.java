package com.dsantano.proyectodam.ui.locations.alllocations;

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
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.data.viewmodel.AllLocationsViewModel;
import com.dsantano.proyectodam.listeners.IAllLocationsListener;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.ui.locations.newlocation.NewLocationDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class LocationFragmentList extends Fragment {

    private int mColumnCount = 1;
    private IAllLocationsListener mListener;
    Context context;
    RecyclerView recyclerView;
    MyLocationRecyclerViewAdapter adapter;
    AllLocationsViewModel allLocationsViewModel;
    List<LocationUser> locationsList = new ArrayList<>();
    List<LocationUser> locationsListLoaded = new ArrayList<>();
    private MenuItem busqueda;
    String typeUser, userRole, userId;

    public LocationFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allLocationsViewModel =new ViewModelProvider(getActivity()).get(AllLocationsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list_list, container, false);

        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyLocationRecyclerViewAdapter(getActivity(), locationsList, mListener);
            recyclerView.setAdapter(adapter);
            loadAllLocations();
        }
        return view;
    }

    public void loadAllLocations(){
        allLocationsViewModel.getAllServices().observe(getActivity(), new Observer<List<LocationUser>>() {
            @Override
            public void onChanged(List<LocationUser> list) {
                locationsList = list;
                adapter.setData(locationsList);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAllLocationsListener) {
            mListener = (IAllLocationsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IAllLocationsListener");
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
        loadAllLocations();
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
        typeUser = getActivity().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_TYPE, null);
        userRole = getActivity().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_ROLE, null);
        if(typeUser.equals("OFRECE_ALOJAMIENTO") || userRole.equals("ADMIN")){
            getActivity().getMenuInflater().inflate(R.menu.offering_accomodation_all_locations_menu, menu);
        } else {
            getActivity().getMenuInflater().inflate(R.menu.not_offering_accomodation_all_locations_menu, menu);
        }
        busqueda = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) busqueda.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<LocationUser> lista = busqueda(newText);
                cargarBusqueda(lista);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public List<LocationUser> busqueda(String palabraClave){
        List<LocationUser> result = new ArrayList<>();
        for (LocationUser locationUser : locationsList ){
            for (String palabraClaveList : locationUser.getPalabrasClave()){
                if(palabraClaveList.equalsIgnoreCase(palabraClave) || palabraClaveList.toLowerCase().contains(palabraClave.toLowerCase())){
                    if (!result.contains(locationUser)){
                        result.add(locationUser);
                    }
                }
            }
        }
        adapter.setData(result);
        return result;
    }

    public void cargarBusqueda (List<LocationUser> busqueda){
        adapter.setData(busqueda);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_my_location:
                locationsListLoaded.clear();
                userId = getActivity().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_PERSONAL_ID, null);
                for (int i = 0; i <locationsList.size() ; i++) {
                    if(locationsList.get(i).getId().equals(userId)){
                        locationsListLoaded.add(locationsList.get(i));
                    }
                }
                adapter.setData(locationsListLoaded);
                break;
            case R.id.action_view_all_locations:
                adapter.setData(locationsList);
                break;
            case R.id.action_add_location:
                NewLocationDialogFragment dialog = new NewLocationDialogFragment(getActivity(), getResources().getString(R.string.new_location_title), getResources().getString(R.string.new_location_body), null, null ,null, null, null, null, false);
                dialog.show(getActivity().getSupportFragmentManager(), "NewLocationDialogFragment");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
