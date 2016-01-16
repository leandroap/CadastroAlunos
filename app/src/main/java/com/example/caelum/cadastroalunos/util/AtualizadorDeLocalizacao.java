package com.example.caelum.cadastroalunos.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.example.caelum.cadastroalunos.fragment.MapaFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by android5519 on 15/01/16.
 */
public class AtualizadorDeLocalizacao implements LocationListener {

    private GoogleApiClient client;

    public AtualizadorDeLocalizacao(Context context, MapaFragment mapaFragment) {

        Configurador config = new Configurador(this);
        this.client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(config)
                .build();
        this.client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        LatLng local = new LatLng(latitude, longitude);
    }

    public void inicia(LocationRequest request) {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        this.client.disconnect();
    }
}
