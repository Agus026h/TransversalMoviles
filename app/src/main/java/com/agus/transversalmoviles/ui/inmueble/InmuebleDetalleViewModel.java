package com.agus.transversalmoviles.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agus.transversalmoviles.modelo.Inmueble;
import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutable;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getInmuebleMutable() {
        if (inmuebleMutable == null) {
            inmuebleMutable = new MutableLiveData<>();
        }
        return inmuebleMutable;
    }

    public void setInmueble(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble != null) {
            inmuebleMutable.setValue(inmueble);
        }
    }

    public void cambiarDisponibilidad(Boolean disponible){

        Inmueble inmuebleOriginal = inmuebleMutable.getValue();
        inmuebleOriginal.setDuenio(null);
        inmuebleOriginal.setDisponible(disponible);

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicio miServicio = ApiClient.getMiServicio();

        Call<Inmueble> call = miServicio.putInmuebleDisponible(token, inmuebleOriginal);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    inmuebleMutable.postValue(inmuebleOriginal);
                    Toast.makeText(getApplication(), "Se cambio la disponibilidad", Toast.LENGTH_SHORT).show();
                } else{
                    Log.d("Error", response.message());
                    Log.d("Error", response.code() +"");
                    Toast.makeText(getApplication(), "No se pudo cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error al cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
