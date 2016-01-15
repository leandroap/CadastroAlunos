package com.example.caelum.cadastroalunos.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by android5519 on 15/01/16.
 */
public class Localizador {

    private Geocoder geocoder;

    public Localizador(Context context) {
        geocoder = new Geocoder(context);
    }

    public LatLng getCoordenada(String endereco){
        List<Address> enderecos = null;
        try {
            enderecos = geocoder.getFromLocationName(endereco, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!enderecos.isEmpty()){
            Address address = enderecos.get(0);
            return new LatLng(address.getLatitude(),
                    address.getLongitude());
        } else {
            return null;
        }
    }
}
