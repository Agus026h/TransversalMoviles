package com.agus.transversalmoviles.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentInmuebleDetalleBinding;
import com.agus.transversalmoviles.request.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel vm;
    private FragmentInmuebleDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);

        vm.getInmuebleMutable().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvIdInmueble.setText(String.valueOf(inmueble.getIdInmueble()));
            binding.tvDireccion.setText(inmueble.getDireccion());
            binding.tvUso.setText(inmueble.getUso());
            binding.tvTipo.setText(inmueble.getTipo());
            binding.tvAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
            binding.tvSuperficie.setText(String.valueOf(inmueble.getSuperficie()));
            binding.tvPrecio.setText(String.valueOf(inmueble.getValor()));
            binding.cbDisponible.setChecked(inmueble.isDisponible());


            Glide.with(getContext())
                    .load(ApiClient.BASE_URL + inmueble.getImagen())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.house)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivInmuebleDetalle);
        });

        vm.setInmueble(getArguments());

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cambiarDisponibilidad(binding.cbDisponible.isChecked());
            }
        });

        return binding.getRoot();
    }
}
