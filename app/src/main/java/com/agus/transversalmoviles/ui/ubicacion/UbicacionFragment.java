package com.agus.transversalmoviles.ui.ubicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentUbicacionBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class UbicacionFragment extends Fragment {

    private FragmentUbicacionBinding binding;
    private UbicacionViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(UbicacionViewModel.class);
        binding = FragmentUbicacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm.getMapaActual().observe(getViewLifecycleOwner(), new Observer<UbicacionViewModel.MapaActual>() {
            @Override
            public void onChanged(UbicacionViewModel.MapaActual mapaActual) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

                if(mapFragment != null){
                    mapFragment.getMapAsync(mapaActual);
                }
            }

        });

        vm.cargarMapaActual();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}