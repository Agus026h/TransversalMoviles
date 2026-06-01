package com.agus.transversalmoviles.ui.pago;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agus.transversalmoviles.modelo.Pago;
import com.agus.transversalmoviles.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> listaPagos = new MutableLiveData<>();

    public PagoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getListaPagos() {
        return listaPagos;
    }

    public void obtenerListaPagos(Bundle bundle) {
        if (bundle == null) return;

        int idContrato = bundle.getInt("idContrato", -1);

        if (idContrato != -1) {
            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicio miServicio = ApiClient.getMiServicio();
            Call<List<Pago>> call = miServicio.getPagosContrato(token, idContrato);

            call.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        listaPagos.setValue(response.body());
                    } else {
                        if (response.code() == 401 || response.code() == 403) {
                            Toast.makeText(getApplication(), "Sesion expirada", Toast.LENGTH_SHORT).show();
                            ApiClient.recuperarToken(getApplication(), "");
                            System.exit(0);
                        } else {
                            Toast.makeText(getApplication(), "No se encontraron pagos para este contrato", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error al obtener historial de pagos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}