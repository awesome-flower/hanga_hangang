package com.example.bbarroo.awesome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MFOF_map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int sel;
    int hangang_num;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfof_map);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        TextView call_text = (TextView) findViewById(R.id.call_text);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        sel = intent.getExtras().getInt("name");
        String[] hangang_list = {"강서", "광나루","난지", "뚝섬", "반포", "망원", "양화", "여의도", "이촌", "잠실", "잠원"};
        String name="";

        for(int i =0; i<11; i++) {
            if (sel == (i+1)) {
                hangang_num = i;
                name = hangang_list[i];
                toolbar_title.setText(name);
            }
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        switch((--sel)){
            case 0:
                LatLng gangseo = new LatLng(37.588156, 126.815230);
                mMap.addMarker(new MarkerOptions().position(gangseo).title("강서 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gangseo, 16));
                break;

            case 1:
                LatLng gwangnaru = new LatLng(37.550256, 127.121644);
                mMap.addMarker(new MarkerOptions().position(gwangnaru).title("광나루 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gwangnaru, 16));
                break;

            case 2:
                LatLng nanji = new LatLng(37.566944, 126.876690);
                mMap.addMarker(new MarkerOptions().position(nanji).title("난지 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nanji, 16));
                break;

            case 3:
                LatLng dduksum = new LatLng(37.529326, 127.070063);
                mMap.addMarker(new MarkerOptions().position(dduksum).title("뚝섬 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dduksum, 16));
                break;

            case 4:
                LatLng banpo = new LatLng(37.511188, 126.998350);
                mMap.addMarker(new MarkerOptions().position(banpo).title("반포 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(banpo, 16));
                break;

            case 5:
                LatLng mangwon = new LatLng(37.555964, 126.894602);
                mMap.addMarker(new MarkerOptions().position(mangwon).title("망원 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mangwon, 16));
                break;

            case 6:
                LatLng yanghwa = new LatLng(37.538471, 126.902276);
                mMap.addMarker(new MarkerOptions().position(yanghwa).title("양화 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yanghwa, 16));
                break;

            case 7:
                LatLng yeouido = new LatLng(37.528552, 126.934313);
                mMap.addMarker(new MarkerOptions().position(yeouido).title("여의도 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yeouido, 16));
                break;

            case 8:
                LatLng ichon = new LatLng(37.516094, 126.975827);
                mMap.addMarker(new MarkerOptions().position(ichon).title("이촌 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ichon, 16));
                break;

            case 9:
                LatLng jamsil = new LatLng(37.518095, 127.081922);
                mMap.addMarker(new MarkerOptions().position(jamsil).title("잠실 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jamsil, 16));
                break;

            case 10:
                LatLng jamwon = new LatLng(37.519550, 127.009926);
                mMap.addMarker(new MarkerOptions().position(jamwon).title("잠원 한강공원"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jamwon, 16));
                break;

        }

    }
}