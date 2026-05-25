package com.agus.transversalmoviles.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentPerfilBinding;
import com.agus.transversalmoviles.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);

        vm.getPropietarioMutable().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etEmail.setText(propietario.getEmail());
                binding.etNombre.setText(propietario.getNombre());
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });
        vm.cargarPerfil();
        vm.getEsEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean edit) {
                binding.etNombre.setEnabled(edit);
                binding.etApellido.setEnabled(edit);
                binding.etDni.setEnabled(edit);
                binding.etTelefono.setEnabled(edit);
                binding.etEmail.setEnabled(edit);

                if(edit){
                    binding.btnEditar.setText("Guardar");
                }else{
                    binding.btnEditar.setText("Editar");
                }
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.btnEditar.getText().toString().toLowerCase().equals("guardar")) {
                    vm.actualizarPropietario(binding.etNombre.getText().toString(),
                            binding.etApellido.getText().toString(),
                            binding.etDni.getText().toString(),
                            binding.etTelefono.getText().toString(),
                            binding.etEmail.getText().toString());
                    //binding.btnEditar.setText("Editar");
                }else{
                    vm.setEsEditable(true);
                }
            }
        });



        //navegar a fragment cambiar clave
        binding.btnClave.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_perfil_to_perfilCambiarClaveFragment);
        });

        return binding.getRoot();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}