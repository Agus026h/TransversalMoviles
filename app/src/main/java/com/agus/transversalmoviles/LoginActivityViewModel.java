package com.agus.transversalmoviles;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensaje;
    private Context context;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void recuperarDatos(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            mensaje.setValue("Por favor, complete todos los campos");
        }else{         //implementar la interface
            ApiClient.MiServicio servicio = ApiClient.getMiServicio();
            Call<String> call = servicio.loginForm(email, password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful()) {
                        String token = response.body();
                        ApiClient.recuperarToken(context, token);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Log.d("Error", response.message());
                        Log.d("Error", response.code()+"");
                        Log.d("Error", response.errorBody().toString()+"");
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("mensaje", t.getMessage());
                }
            });
        }
    }




}
