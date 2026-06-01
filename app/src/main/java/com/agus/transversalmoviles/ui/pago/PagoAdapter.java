package com.agus.transversalmoviles.ui.pago;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.modelo.Pago;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolderPago> {

    private List<Pago> listaPagos;
    private LayoutInflater inflater;

    public PagoAdapter(List<Pago> listaPagos, LayoutInflater inflater) {
        this.listaPagos = listaPagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_pago, parent, false);
        return new ViewHolderPago(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoAdapter.ViewHolderPago holder, int position) {
        Pago pagoActual = listaPagos.get(position);


        holder.tvNumeroPago.setText("Codigo del pago " + pagoActual.getIdPago());


        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        holder.tvMonto.setText(nf.format(pagoActual.getMonto()));

        if (pagoActual.getFechaPago() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.tvFechaPago.setText("Fecha: " + sdf.format(pagoActual.getFechaPago()));
        } else {
            holder.tvFechaPago.setText("Fecha: -");
        }

        if (pagoActual.getDetalle() != null && !pagoActual.getDetalle().isEmpty()) {
            holder.tvDetalle.setText("Detalle: " + pagoActual.getDetalle());
        } else {
            holder.tvDetalle.setText("Detalle: Sin especificar");
        }
    }

    @Override
    public int getItemCount() {
        return listaPagos != null ? listaPagos.size() : 0;
    }

    public static class ViewHolderPago extends RecyclerView.ViewHolder {
        TextView tvNumeroPago, tvMonto, tvFechaPago, tvDetalle;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);
            tvNumeroPago = itemView.findViewById(R.id.tvNumeroPago);
            tvMonto = itemView.findViewById(R.id.tvMonto);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
        }
    }
}