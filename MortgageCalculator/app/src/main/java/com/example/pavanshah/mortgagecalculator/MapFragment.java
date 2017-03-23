package com.example.pavanshah.mortgagecalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Pavan Shah on 3/16/2017.
 */

public class MapFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    TextView propertyType, address;
    LatLng latLng;
    String PropertyType, Address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("PAVAN", "Inside map create");

        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View v = inflater.inflate(R.layout.custom_window_adapter, null);

                Log.d("PAVAN", "inside getInfoWindow");

                propertyType = (TextView) v.findViewById(R.id.PropertyType);
                address = (TextView) v.findViewById(R.id.Address);
                TextView loanAmount = (TextView) v.findViewById(R.id.loanAmount);
                TextView APR = (TextView) v.findViewById(R.id.APR);
                TextView monthlyPayment = (TextView) v.findViewById(R.id.monthlyPayment);
                Button edit = (Button) v.findViewById(R.id.Edit);
                Button delete = (Button) v.findViewById(R.id.Delete);


                String separateTitle = marker.getTitle();
                Log.d("CLICK", "Separate Title"+separateTitle);
                String[] titleParts;

                titleParts = separateTitle.split(":");

                propertyType.setText(titleParts[0]);
                address.setText(titleParts[1]);
                loanAmount.setText(titleParts[2]);
                APR.setText(titleParts[3]);
                monthlyPayment.setText(titleParts[4]);

                latLng = marker.getPosition();

                return v;
            }
        });


        updateMap();

        return v;
    }


    public void updateMap()
    {
        Log.d("TEST", "Updating map after delete");
        final DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), null, null, 0);
        ArrayList<PropertyDetails> arrayList = databaseHelper.readData();

        if(arrayList.size() != 0)
        {
            Log.i("TEST", "IF part");
            Log.i("TEST", "Arraylist size "+arrayList.size());
            for(int i = 0 ; i < arrayList.size() ; i++)
            {
                Log.i("TEST", "Arraylist "+arrayList.get(i).getCity());
                PropertyType = arrayList.get(i).PropertyType;
                Address = arrayList.get(i).Address;
                String City = arrayList.get(i).City;
                Double LoanAmount = arrayList.get(i).LoanAmount;
                Double APR = arrayList.get(i).APR;
                Double MonthlyPayment = arrayList.get(i).MonthlyPayment;
                Double Latitude = arrayList.get(i).getLatitude();
                Double Longitude = arrayList.get(i).getLongitude();
                final String ID = arrayList.get(i).getID();

                LatLng latlang = new LatLng(Latitude, Longitude);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latlang)
                        .title(PropertyType+":"+Address+":"+LoanAmount+":"+APR+":"+MonthlyPayment+":"+ID)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                Log.i("PAVAN", "calling show info window");

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                        return true;
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(final Marker marker) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button

                                String separateTitle = marker.getTitle();
                                String[] titleParts;
                                titleParts = separateTitle.split(":");
                                String uniqueID = titleParts[5];

                                /*Bundle bundle=new Bundle();
                                bundle.putString("ID", uniqueID);
                                //set Fragmentclass Arguments
                                CalculationFragment fragobj=new CalculationFragment();
                                fragobj.setArguments(bundle);*/

                                /*final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.layout.fragment_calculation, new NewFragmentToReplace(), "NewFragmentTag");
                                ft.commit();*/


                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                intent.putExtra("ID", uniqueID);
                                intent.putExtra("FROM_MAP", "YES");
                                startActivity(intent);

                                updateMap();

                                //Toast.makeText(getContext(), "edit Clicked "+uniqueID, Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                //
                                // Toast.makeText(getContext(), "delete Clicked ", Toast.LENGTH_LONG).show();
                                String separateTitle = marker.getTitle();
                                String[] titleParts;
                                titleParts = separateTitle.split(":");
                                String uniqueID = titleParts[5];

                                String query = "DELETE FROM PropertyTable WHERE ID = \""+uniqueID+"\"";
                                SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                                sqLiteDatabase.execSQL(query);
                                Toast.makeText(getContext(), "Property Deleted!", Toast.LENGTH_LONG).show();
                                Log.i("TEST", "Property deleted");

                                updateMap();


                            }
                        });
                        builder.setTitle("Which action do you need to perform?");
                        builder.show();
                        AlertDialog dialog = builder.create();

                        //Toast.makeText(getContext(), "Clicked ", Toast.LENGTH_LONG).show();



                    }
                });


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(39.827591, -98.579598)).zoom(3).build();
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
        }
        else {

            Log.i("TEST", "ELSE part");
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(39.827591, -98.579598)).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
