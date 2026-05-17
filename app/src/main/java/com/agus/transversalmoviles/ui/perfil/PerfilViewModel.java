package com.agus.transversalmoviles.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.agus.transversalmoviles.modelo.Propietario;
import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietarioMutable;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Propietario> getPropietarioMutable() {
        if (propietarioMutable == null) {
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }

    public void cargarPerfil(){

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicio servicio = ApiClient.getMiServicio();
        Call<Propietario> call = servicio.getPropietarios(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    // si es exitoso
                    Propietario p = response.body();
                    propietarioMutable.postValue(p);
                }else{
                    Log.d("error", "efe nomas");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("error", t.getMessage());
            }
        });

    }

    public void actualizarPropietario(String nombre, String apellido, String dni, String telefono, String email){
        if(nombre != null && !nombre.trim().isEmpty()
                && apellido != null && !apellido.trim().isEmpty()
                && dni != null && !dni.trim().isEmpty()
                && telefono != null && !telefono.trim().isEmpty()
                && email != null && !email.trim().isEmpty()) {
            Propietario p = propietarioMutable.getValue();
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setDni(dni);
            p.setTelefono(telefono);
            p.setEmail(email);
            p.setClave(null);

            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicio servicio = ApiClient.getMiServicio();
            Call<Propietario> call = servicio.putPropietario(token, p);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Propietario prop = response.body();
                        propietarioMutable.postValue(prop);
                        Toast.makeText(getApplication(), "Propietario Actualizado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(), "No se pudo actualizar el propietario", Toast.LENGTH_SHORT).show();
                        Log.d("error", response.message());//agrgar mensajes de error
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "onFailure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}