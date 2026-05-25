package com.agus.transversalmoviles.ui.inmueble;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agus.transversalmoviles.R;
import com.agus.transversalmoviles.modelo.Inmueble;
import com.agus.transversalmoviles.request.ApiClient;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble>{


    private List<Inmueble> listaInmuebles;

    private LayoutInflater inflater;
    // Usamos interfaz funcional para manejar el click
    private java.util.function.Consumer<Inmueble> onItemClickListener;
    public InmuebleAdapter(List<Inmueble> listaInmuebles, LayoutInflater inflater, java.util.function.Consumer<Inmueble> onItemClickListener) {
        this.listaInmuebles = listaInmuebles;
        this.inflater = inflater;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }
    // recorrer la lista
    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolderInmueble holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.direccion.setText(inmuebleActual.getDireccion());

        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
        String valorFormateado = nf.format(inmuebleActual.getValor());
        holder.valor.setText("$ "+valorFormateado);
        String superficieConcatenada = "Superficie: "+inmuebleActual.getSuperficie() + " m²";
        holder.superficie.setText(superficieConcatenada);

        Glide.with(holder.itemView.getContext())
                .load(ApiClient.BASE_URL + inmuebleActual.getImagen())
                .placeholder(R.drawable.loading)
                .error(R.drawable.house)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.accept(inmuebleActual);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaInmuebles !=null ? listaInmuebles.size() : 0;
    }
    public static class ViewHolderInmueble extends RecyclerView.ViewHolder{
        TextView direccion, superficie, valor;
        ImageView imagen;
        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            valor = itemView.findViewById(R.id.tvValor);
            superficie = itemView.findViewById(R.id.tvSuperficie);
            imagen = itemView.findViewById(R.id.imageView2);
        }
    }

}
