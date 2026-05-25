package com.agus.transversalmoviles.ui.inmueble;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentInmuebleBinding;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel vm;
    private FragmentInmuebleBinding binding;

    public static InmuebleFragment newInstance() {
        return new InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmuebleViewModel.class);

        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {

            InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getLayoutInflater(), inmueble -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_nav_inmueble_to_inmuebleDetalleFragment, bundle);
            });
            binding.rvInmueble.setAdapter(adapter);

            GridLayoutManager glm = new GridLayoutManager(getContext(), 2,
                    GridLayoutManager.VERTICAL, false);
            binding.rvInmueble.setLayoutManager(glm);
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_nav_inmueble_to_nav_inmueble_agregar);
            }
        });
        vm.obtenerListaInmuebles();




        return binding.getRoot();
    }


}