package com.agus.transversalmoviles.ui.inmueble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agus.transversalmoviles.Dialogos;
import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentInmuebleAgregarBinding;

public class InmuebleAgregarFragment extends Fragment {

    private InmuebleAgregarViewModel vm;
    private FragmentInmuebleAgregarBinding b;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;
    public static InmuebleAgregarFragment newInstance() {
        return new InmuebleAgregarFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentInmuebleAgregarBinding.inflate(inflater,container,false);
        vm = new ViewModelProvider(this).get(InmuebleAgregarViewModel.class);
        vm.getmUri().observe(getViewLifecycleOwner(),uri -> {
            b.ivFoto.setImageURI(uri);
        });
        b.btnCargarImagen.setOnClickListener(view->{
            selector.launch(intent);
        });
        b.etTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialogos.mostrarDialogoTipoInmueble(getContext(), b.etTipo);
            }
        });
        b.btnGuardarInmueble.setOnClickListener(view->{
            vm.cargarInmueble(
                    b.etDireccion.getText().toString(),
                    b.etTipo.getText().toString(),
                    b.etTipo.getText().toString(),
                    b.etAmbiente.getText().toString(),
                    b.etSuperficie.getText().toString(),
                    b.etValor.getText().toString()
            );
        });
        abrirGaleria();
        return b.getRoot();
    }
    private void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult resultado) {
                vm.recibirFoto(resultado);
                Log.d("galeria", "onActivityResult: "+resultado.toString());
            }
        });
    }



}