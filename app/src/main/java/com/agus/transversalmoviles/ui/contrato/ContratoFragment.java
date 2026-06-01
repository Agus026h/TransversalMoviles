package com.agus.transversalmoviles.ui.contrato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.databinding.FragmentContratoBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);

        vm.getContratoMutable().observe(getViewLifecycleOwner(), contrato -> {
            // Datos del Contrato
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
            binding.tvFechaInicio.setText(sdf.format(contrato.getFechaInicio()));
            binding.tvFechaFin.setText(sdf.format(contrato.getFechaFinalizacion()));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
            binding.tvMontoAlquiler.setText(nf.format(contrato.getMontoAlquiler()));

            vm.getTextoEstadoBoton().observe(getViewLifecycleOwner(), texto -> {
                binding.tvEstadoContrato.setText(texto);
            });

            vm.getColorEstadoBoton().observe(getViewLifecycleOwner(), color -> {

                binding.tvEstadoContrato.setTextColor(color);
            });
            binding.tvNombreInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            binding.tvDniInquilino.setText(contrato.getInquilino().getDni());
            binding.tvTelefonoInquilino.setText(contrato.getInquilino().getTelefono());

            binding.tvDireccionInmueble.setText(contrato.getInmueble().getDireccion());
            binding.tvUsoInmueble.setText(contrato.getInmueble().getUso());
            binding.tvTipoInmueble.setText(contrato.getInmueble().getTipo());

        });

        binding.btnPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("idContrato", vm.getContratoMutable().getValue().getIdContrato());
                    Navigation.findNavController(v).navigate(R.id.action_nav_contrato_to_nav_pago, bundle);

            }
        });

        vm.cargarContrato(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
