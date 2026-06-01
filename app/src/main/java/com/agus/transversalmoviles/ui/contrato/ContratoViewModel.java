package com.agus.transversalmoviles.ui.contrato;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agus.transversalmoviles.modelo.Contrato;
import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contratoMutable;

    private  MutableLiveData<String> textoEstado = new MutableLiveData<>();
    private  MutableLiveData<Integer> colorEstado = new MutableLiveData<>();

    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContratoMutable() {
        if (contratoMutable == null) {
            contratoMutable = new MutableLiveData<>();
        }
        return contratoMutable;
    }

    public LiveData<String> getTextoEstadoBoton() {
        return textoEstado;
    }

    public LiveData<Integer> getColorEstadoBoton() {
        return colorEstado;
    }
    public void cargarContrato(Bundle bundle) {
        int idInmueble = bundle.getInt("idInmueble");
        if (idInmueble != -1) {
            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicio servicio = ApiClient.getMiServicio();
            Call<Contrato> call = servicio.getContrato(token, idInmueble);

            call.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if (response.isSuccessful()) {
                        contratoMutable.postValue(response.body());
                        calcularEstadoContrato(response.body());
                    } else {
                        Toast.makeText(getApplication(), "No se encontro un contrato vigente para este inmueble", Toast.LENGTH_SHORT).show();
                        Log.d("API_ERROR", "Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    Log.d("API_FAILURE", t.getMessage());
                }
            });
        }
    }

    public void calcularEstadoContrato(Contrato contrato) {
        if (contrato == null) {
            textoEstado.setValue("Sin Contrato");
            colorEstado.setValue(Color.parseColor("#757575"));
            return;
        }


        if (contrato.isEstado()) {
            textoEstado.setValue("Vigente");
            colorEstado.setValue(Color.parseColor("#2E7D32")); // Verde oscuro
        } else {
            textoEstado.setValue("Inactivo / Vencido");
            colorEstado.setValue(Color.parseColor("#C62828")); // Rojo oscuro
        }
    }

}
