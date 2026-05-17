package com.agus.transversalmoviles.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilCambiarClaveViewModel extends AndroidViewModel {
    public PerfilCambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }
    public void cambiarClave(String claveActual, String claveNueva){
        if(claveActual != null && !claveActual.trim().isEmpty()){
            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicio miServicio = ApiClient.getMiServicio();
            Call<Void> call = miServicio.changePassword(token, claveActual, claveNueva);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Clave cambiada con exito", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(), "No se pudo cambiar la clave", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplication(), "algo salio mal", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}