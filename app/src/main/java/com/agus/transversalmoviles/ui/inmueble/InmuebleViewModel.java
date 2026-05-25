package com.agus.transversalmoviles.ui.inmueble;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.agus.transversalmoviles.modelo.Inmueble;
import com.agus.transversalmoviles.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble;
    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Inmueble> getInmueble() {
        if(inmueble == null){
            inmueble = new MutableLiveData<>();
        }
        return inmueble;
    }


    public MutableLiveData<List<Inmueble>> getListaInmuebles() {
        return listaInmuebles;
    }


    public void obtenerListaInmuebles() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicio miServicio = ApiClient.getMiServicio();
        Call<List<Inmueble>> call = miServicio.getInmuebles(token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    listaInmuebles.setValue(response.body());

                }else{
                    if(response.code() == 401 || response.code() == 403){
                    Toast.makeText(getApplication(), "no se obtuvieron inmuebles", Toast.LENGTH_SHORT).show();
                    ApiClient.recuperarToken(getApplication(), "");
                    System.exit(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }

}