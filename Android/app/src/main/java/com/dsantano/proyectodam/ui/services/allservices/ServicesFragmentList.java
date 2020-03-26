package com.dsantano.proyectodam.ui.services.allservices;

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
import com.dsantano.proyectodam.data.viewmodel.AllServicesViewModel;
import com.dsantano.proyectodam.listeners.IAllServicesListener;
import com.dsantano.proyectodam.models.services.Services;
import com.dsantano.proyectodam.ui.services.newservice.NewServiceDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ServicesFragmentList extends Fragment {

    private int mColumnCount = 1;
    private IAllServicesListener mListener;
    Context context;
    RecyclerView recyclerView;
    MyServicesRecyclerViewAdapter adapter;
    AllServicesViewModel allServicesViewModel;
    List<Services> servicesList = new ArrayList<>();
    List<Services> servicesListLoaded = new ArrayList<>();
    private MenuItem busqueda;
    String typeUser, userRole, userId;

    public ServicesFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allServicesViewModel =new ViewModelProvider(getActivity()).get(AllServicesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyServicesRecyclerViewAdapter(getActivity(), servicesList, mListener);
            recyclerView.setAdapter(adapter);
            loadAllServices();
        }
        return view;
    }

    public void loadAllServices(){
        allServicesViewModel.getAllServices().observe(getActivity(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> list) {
                servicesList = list;
                adapter.setData(servicesList);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAllServicesListener) {
            mListener = (IAllServicesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IAllServicesListener");
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
        loadAllServices();
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
        if(typeUser.equals("OFRECE_SERVICIO") || userRole.equals("ADMIN")){
            getActivity().getMenuInflater().inflate(R.menu.offering_service_all_services_menu, menu);
        } else {
            getActivity().getMenuInflater().inflate(R.menu.not_offering_service_all_services_menu, menu);
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
                List<Services> lista = busqueda(newText);
                cargarBusqueda(lista);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public List<Services> busqueda(String palabraClave){
        List<Services> result = new ArrayList<>();
        for (Services services : servicesList ){
            for (String palabraClaveList : services.getPalabrasClave()){
                if(palabraClaveList.equalsIgnoreCase(palabraClave) || palabraClaveList.toLowerCase().contains(palabraClave.toLowerCase())){
                    if (!result.contains(services)){
                        result.add(services);
                    }
                }
            }
        }
        adapter.setData(result);
        return result;
    }

    public void cargarBusqueda (List<Services> busqueda){
        adapter.setData(busqueda);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_service:
                NewServiceDialogFragment dialog = new NewServiceDialogFragment(getActivity(), getResources().getString(R.string.new_service_title), getResources().getString(R.string.new_service_body), false, null, null, null);
                dialog.show(getActivity().getSupportFragmentManager(), "NewServiceDialogFragment");
                break;
            case R.id.action_my_services:
                servicesListLoaded.clear();
                userId = getActivity().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_PERSONAL_ID, null);
                for (int i = 0; i <servicesList.size() ; i++) {
                    if(servicesList.get(i).getUserOfferingService().equals(userId)){
                        servicesListLoaded.add(servicesList.get(i));
                    }
                }
                adapter.setData(servicesListLoaded);
                break;
            case R.id.action_all_services:
                adapter.setData(servicesList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
