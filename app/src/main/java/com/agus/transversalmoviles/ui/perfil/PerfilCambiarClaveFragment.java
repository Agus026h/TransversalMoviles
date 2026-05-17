package com.agus.transversalmoviles.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentPerfilCambiarClaveBinding;

public class PerfilCambiarClaveFragment extends Fragment {

    private PerfilCambiarClaveViewModel vm;
    private FragmentPerfilCambiarClaveBinding binding;

    public static PerfilCambiarClaveFragment newInstance() {
        return new PerfilCambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPerfilCambiarClaveBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilCambiarClaveViewModel.class);

        binding.btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cambiarClave(binding.etClaveActual.getText().toString(),binding.etClaveNueva.getText().toString());
            }
        });
        return binding.getRoot();
    }



}