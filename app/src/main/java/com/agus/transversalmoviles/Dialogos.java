package com.agus.transversalmoviles;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.agus.transversalmoviles.request.ApiClient;

public class Dialogos {


    public static void mostrarDialogoCerrarSesion(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Cerrar Sesion")
                .setMessage("¿Realmente desea salir de la aplicacion?")
                .setCancelable(false)
                .setPositiveButton("Si, salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiClient.recuperarToken(activity.getApplicationContext(), "");

                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void mostrarDialogoTipoInmueble(Context context, final EditText etTipo) {

        final String[] tipos = {"Local", "Deposito", "Casa", "Departamento", "Quinta"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Seleccione Tipo de Inmueble");

        builder.setItems(tipos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String seleccion = tipos[which];

                etTipo.setText(seleccion);
            }
        });

        builder.show();
    }
}