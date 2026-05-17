package com.agus.transversalmoviles.ui.ubicacion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionViewModel extends AndroidViewModel {

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng sanLuis = new LatLng(-33.20576, -66.332482);
        LatLng ulp = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(sanLuis).title("San Luis"));
            googleMap.addMarker(new MarkerOptions().position(ulp).title("ULP"));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ulp)      // Sets the center of the map to Mountain View
                    .zoom(18)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition( cameraPosition);
            googleMap.animateCamera(cameraUpdate);

        }
    }
    private MutableLiveData<MapaActual> mapaActual= new MutableLiveData<>();

    public LiveData<MapaActual> getMapaActual(){
        if(mapaActual == null){
            mapaActual = new MutableLiveData<>();
        }
        return mapaActual;
    }

    public void cargarMapaActual(){

        MapaActual mapaActualNuevo = new MapaActual();
        mapaActual.setValue(mapaActualNuevo);
    }


}