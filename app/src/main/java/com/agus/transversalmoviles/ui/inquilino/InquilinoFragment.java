package com.agus.transversalmoviles.ui.inquilino;

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

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentInmuebleBinding;
import com.agus.transversalmoviles.databinding.FragmentInquilinoBinding;
import com.agus.transversalmoviles.ui.inmueble.InmuebleAdapter;
import com.agus.transversalmoviles.ui.inmueble.InmuebleViewModel;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel vm;
    private FragmentInquilinoBinding binding;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinoViewModel.class);

        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {

            InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getLayoutInflater(), inmueble -> {


                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", inmueble.getIdInmueble());


                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_nav_inquilino_to_nav_contrato, bundle);
            });
            binding.rvInquilino.setAdapter(adapter);

            GridLayoutManager glm = new GridLayoutManager(getContext(), 2,
                    GridLayoutManager.VERTICAL, false);
            binding.rvInquilino.setLayoutManager(glm);
        });

        vm.obtenerListaInmueblesAlquilados();

        return binding.getRoot();
    }


}