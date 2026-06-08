package com.agus.transversalmoviles;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.agus.transversalmoviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivityViewModel extends AndroidViewModel implements SensorEventListener {
    private MutableLiveData<String> mensaje;
    private Context context;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration = 0.00f;
    private float currentAcceleration = 0.00f;
    private float lastAcceleration = 0.00f;
    private static final int SHAKE_THRESHOLD = 5;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
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

    public void iniciarSensor() {
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void detenerSensor() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        lastAcceleration = currentAcceleration;
        currentAcceleration = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = currentAcceleration - lastAcceleration;
        acceleration = acceleration * 0.9f + delta;

        // Si se detecta la sacudida, disparamos la llamada
        if (acceleration > SHAKE_THRESHOLD) {
            llamarNumeroDirecto("2664275436");
        }
    }


    private void llamarNumeroDirecto(String numero) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numero));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Log.d("SensorLlamada", "No se puede realizar la llamada directa porque falta el permiso.");
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }
    }
}
