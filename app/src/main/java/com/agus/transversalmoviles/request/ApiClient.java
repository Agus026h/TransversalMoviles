package com.agus.transversalmoviles.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.agus.transversalmoviles.modelo.Inmueble;
import com.agus.transversalmoviles.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {
    public final static String BASE_URL = "https://capacitacion.alwaysdata.net/";

    public static MiServicio getMiServicio(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(MiServicio.class);


    }

    public interface MiServicio{
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> changePassword(@Header("Authorization") String token,
                                    @Field("currentPassword") String claveActual,
                                    @Field("newPassword") String claveNueva);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> putPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @GET("api/Propietarios")
        Call<Propietario> getPropietarios(@Header("Authorization")String token);

        @GET("api/Inmuebles")
        Call<List<Inmueble>>getInmuebles(@Header("Authorization") String token);

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> putInmuebleDisponible(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);

    }
    //metodo para obtener/guardar token
    public static void recuperarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer "+ token);
        editor.apply();
        // como es asincrono no uso commit
    }

    //metodo para leer el token
    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

}
