package com.agus.transversalmoviles.ui.pago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agus.transversalmoviles.databinding.FragmentPagoBinding;

public class PagoFragment extends Fragment {

    private PagoViewModel vm;
    private FragmentPagoBinding binding;

    public static PagoFragment newInstance() {
        return new PagoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagoViewModel.class);


        vm.getListaPagos().observe(getViewLifecycleOwner(), pagos -> {
            PagoAdapter adapter = new PagoAdapter(pagos, getLayoutInflater());
            binding.rvPagos.setAdapter(adapter);


            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            binding.rvPagos.setLayoutManager(llm);
        });


        vm.obtenerListaPagos(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}