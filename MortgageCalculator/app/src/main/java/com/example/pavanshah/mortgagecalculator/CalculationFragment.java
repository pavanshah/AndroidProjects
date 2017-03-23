package com.example.pavanshah.mortgagecalculator;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Pavan Shah on 3/16/2017.
 */

public class CalculationFragment extends Fragment
{

    String globalAddress = new String();
    double lat, lng;
    int globalResult = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_calculation, container, false);

        final DatabaseHelper db = new DatabaseHelper(getContext(), null, null,0);
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        Bundle arg = getArguments();
        final String ID;
        if(arg != null)
        {
            ID = getArguments().getString("ID");
            //Toast.makeText(getContext(),"aavyo ne madyo"+ID, Toast.LENGTH_LONG).show();
            PropertyDetails pd = new PropertyDetails();
            ArrayList<PropertyDetails> pdd = db.readData();

            for(int i=0;i<pdd.size();i++)
            {
                Log.d("PAVAN", "madya oachiihudgsgdysa"+pdd.get(i).getID()+pdd.get(i).getCity());
                if(pdd.get(i).getID().equals(ID))
                {
                    pd=pdd.get(i);
                }
            }

            Log.d("PAVAN" , "pdpdpdpdpdpdd"+pd.getCity());

            ((EditText) rootView.findViewById(R.id.editTextCity)).setText(pd.getCity());
            ((EditText) rootView.findViewById(R.id.editTextZipcode)).setText(pd.getZip());
            ((EditText) rootView.findViewById(R.id.editTextPropertyPrice)).setText(pd.getPrice());
            ((EditText) rootView.findViewById(R.id.editTextDownPayment)).setText(pd.getDownPayment());
            ((EditText) rootView.findViewById(R.id.editTextAPR)).setText(pd.getAPR().toString());
            ((Spinner) rootView.findViewById(R.id.spinnerStates)).setSelection(0);
            ((Spinner) rootView.findViewById(R.id.spinnerYears)).setSelection(0);
            //int radio = ((RadioGroup)rootView.findViewById(R.id.radioGroupPropertyType)).getCheckedRadioButtonId();
            autocompleteFragment.setText(pd.getAddress());
            globalAddress = pd.getAddress();
            lat=pd.getLatitude().doubleValue();
            lng=pd.getLongitude().doubleValue();

            /*Spinner stateSpinner = (Spinner) rootView.findViewById(R.id.spinnerStates);

            for(int i=0;i<stateSpinner.getCount();i++)
            {
                if(stateSpinner.getItemAtPosition(i).toString().equals(pd.ge))
                {
                    stateSpinner.setSelection(i);
                    //return;
                }
            }*/





        }
        else
        {
            ID = null;
            //Toast.makeText(getContext(),"aavyo ne madyo else ma", Toast.LENGTH_LONG).show();
        }

        //Intent intent = getIntent();




        //final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //final String globalAdd = new String();
        final EditText price = (EditText)rootView.findViewById(R.id.editTextPropertyPrice);
        final EditText downPayment = (EditText)rootView.findViewById(R.id.editTextDownPayment);
        final EditText apr = (EditText)rootView.findViewById(R.id.editTextAPR);
        final Spinner years = (Spinner)rootView.findViewById(R.id.spinnerYears);


        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                autocompleteFragment.setText("");
                EditText cityEditText = (EditText)rootView.findViewById(R.id.editTextCity);
                cityEditText.setText("");
                EditText zipEditText = (EditText)rootView.findViewById(R.id.editTextZipcode);
                zipEditText.setText("");
                Spinner stateSpinner = (Spinner) rootView.findViewById(R.id.spinnerStates);
                stateSpinner.setSelection(0);
                globalAddress="";
            }
        });


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Vansh", "Place: " + place.getName());
                Log.i("Vansh", "Place: " + place.getAddress().toString());
                //CharSequence addressCS = place.getAddress();
                Geocoder geocoder = new Geocoder(getContext());
                //String[] address = place.getAddress().toString().split(",");
                try{

                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);
                    //List<Integer> addresses = place.getPlaceTypes();
                    String city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();
                    String address = addresses.get(0).getAddressLine(0);
                    globalAddress = address;
                    Double latitude = addresses.get(0).getLatitude();
                    Double longitude = addresses.get(0).getLongitude();
                    lat = latitude;
                    lng = longitude;
                    String zip = addresses.get(0).getPostalCode();
                    String state = addresses.get(0).getAdminArea();
                    Log.i("Vansh", "City: " + city);
                    Log.i("Vansh", "State: " + state);
                    Log.i("Vansh", "Country: " + country);
                    Log.i("Vansh", "Address: " + address);
                    Log.i("Vansh", "Lat: " + latitude);
                    Log.i("Vansh", "Long: " + longitude);
                    Log.i("Vansh", "Zip: " + zip);
                    //Log.i("Vansh", "Place: " + address[2]);

                    Spinner stateSpinner = (Spinner) rootView.findViewById(R.id.spinnerStates);

                    for(int i=0;i<stateSpinner.getCount();i++)
                    {
                        if(stateSpinner.getItemAtPosition(i).toString().equals(state))
                        {
                            stateSpinner.setSelection(i);
                            //return;
                        }
                    }

                    EditText cityEditText = (EditText)rootView.findViewById(R.id.editTextCity);
                    cityEditText.setText(city);
                    EditText zipEditText = (EditText)rootView.findViewById(R.id.editTextZipcode);
                    zipEditText.setText(zip);
                    //Log.i("Vansh", "thay che ahiya sudhi execute" + zipEditText.getText());



                }catch (Exception e)
                {
                    Log.i("Vansh", "Exception: " + e.getMessage());
                }



            }




            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
            }
        });

        Button calculate = (Button) rootView.findViewById(R.id.buttonCalculate);
        Button newCalculation = (Button) rootView.findViewById(R.id.newCalculation);
        Button saveCalculation = (Button) rootView.findViewById(R.id.saveCalculation);

        calculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(price.getText().toString().isEmpty())
                {
                    price.setError("Plese enter Payment Price!");
                    return;
                }
/*                else
                {
                    String temp = price.getText().toString();
                    *//*if((Double.parseDouble(temp).)
                    {
                        price.setError("Plese enter a valid price!");
                        return;
                    }*//*
                }*/
                if(downPayment.getText().toString().isEmpty())
                {
                    downPayment.setError("Plese enter down payment!");
                    return;
                }
                if(apr.getText().toString().isEmpty())
                {
                    apr.setError("Plese enter annual percentage rate!");
                    return;
                }




                //Log.i("Vansh", "Thayooooooooooooo" + price.toString());
                Double priceDbl = Double.parseDouble(price.getText().toString());
                //Log.i("Vansh", "Thayooooooooooooo1"+years.getSelectedItem().toString());
                Double downPaymentDbl = Double.parseDouble(downPayment.getText().toString());
                //Log.i("Vansh", "Thayooooooooooooo2");
                Double aprDbl = Double.parseDouble(apr.getText().toString());
                //Log.i("Vansh", "Thayooooooooooooo3");

                //Log.i("Vansh", "price " + priceDbl);
                ///Log.i("Vansh", "downpayment " + downPaymentDbl);
                ///Log.i("Vansh", "apr " + aprDbl);
                Double rate = Math.pow(1+((Double.parseDouble(apr.getText().toString()))/(100*12)),12*Double.parseDouble(years.getSelectedItem().toString()));
                //Log.i("Vansh", "rate: " + rate);
                Double result = (priceDbl-downPaymentDbl)*((rate*(aprDbl/(100*12)))/(rate-1));
                int output = (int)Math.round(result);
                TextView resultText = (TextView)rootView.findViewById(R.id.textViewResult);
                globalResult = output;
                resultText.setText("Monthly Installment: $"+Integer.toString(output));


            }
        });

        newCalculation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            ((EditText) rootView.findViewById(R.id.editTextAPR)).getText().clear();
            ((EditText) rootView.findViewById(R.id.editTextCity)).getText().clear();
            ((EditText) rootView.findViewById(R.id.editTextZipcode)).getText().clear();
            ((EditText) rootView.findViewById(R.id.editTextPropertyPrice)).getText().clear();
            ((EditText) rootView.findViewById(R.id.editTextDownPayment)).getText().clear();
            ((Spinner) rootView.findViewById(R.id.spinnerStates)).setSelection(0);
            ((Spinner) rootView.findViewById(R.id.spinnerYears)).setSelection(0);
            //int radio = ((RadioGroup)rootView.findViewById(R.id.radioGroupPropertyType)).getCheckedRadioButtonId();
            autocompleteFragment.setText("");
        }});

        saveCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(globalAddress.equals(""))
                {
                    autocompleteFragment.setText("Select address!");

                    return;
                }
                if(price.getText().toString().isEmpty())
                {
                    price.setError("Plese enter Payment Price!");
                    return;
                }
                if(downPayment.getText().toString().isEmpty())
                {
                    downPayment.setError("Plese enter down payment!");
                    return;
                }
                if(apr.getText().toString().isEmpty())
                {
                    apr.setError("Plese enter annual percentage rate!");
                    return;
                }

                if(globalResult==0)
                {
                    Toast.makeText(getContext(),"Please calculate first!", Toast.LENGTH_LONG).show();
                    return;
                }




                Log.i("Vansh", "savecalc 1");

                Log.i("Vansh", "savecalc 2");
                //String PropertyType, String Address, String City, Double LoanAmount, Double APR, Double MonthlyPayment, Double Latitude, Double Longitude
                RadioGroup rg = (RadioGroup)rootView.findViewById(R.id.radioGroupPropertyType);
                Log.i("Vansh", "savecalc 3");
                String propertyType = ((RadioButton)rootView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                Log.i("Vansh", "savecalc 4");
                //String address = autocompleteFragment.getText(0).toString();
                String add = autocompleteFragment.toString();
                Log.i("Vansh", "savecalc 5");
                String city = ((EditText) rootView.findViewById(R.id.editTextCity)).getText().toString();
                String zip = ((EditText) rootView.findViewById(R.id.editTextZipcode)).getText().toString();
                //String price = ((EditText) rootView.findViewById(R.id.editTextPropertyPrice)).getText().toString();
                //String downPayment = ((EditText) rootView.findViewById(R.id.editTextDownPayment)).getText().toString();
                Double priceDbl = Double.parseDouble(price.getText().toString());
                Log.i("Vansh", "savecalc 6");

                Double downPaymentDbl = Double.parseDouble(downPayment.getText().toString());
                Log.i("Vansh", "savecalc 7");
                double loanAmount = priceDbl-downPaymentDbl;
                Log.i("Vansh", "savecalc 8");
                double apr = Double.parseDouble(((EditText) rootView.findViewById(R.id.editTextAPR)).getText().toString());
                Log.i("Vansh", "savecalc 9");
                //String[] tempp = (((TextView)rootView.findViewById(R.id.textViewResult)).getText().toString()).split("$");
                //Log.i("Vansh", "tempppppppp"+tempp[1]);
                //double result = Double.parseDouble(tempp[1]);
                double result = globalResult;
                Log.i("Vansh", "savecalc 10");

                Log.d("PAVAN", "Save calculation");

                String tempID = UUID.randomUUID().toString();
                Log.d("PAVAN", "iddddddddddddd"+tempID);
                if(ID == null)
                {
                    db.addPropertyInfo(tempID, propertyType, globalAddress, city, loanAmount, apr, result, lat, lng, zip, priceDbl.toString(), downPaymentDbl.toString());
                    Toast.makeText(getContext(),"Property Details saved!", Toast.LENGTH_LONG).show();
                    Log.i("Vansh", "savecalc 10"+ID);
                }
                else
                {
                    db.editPropertyInfo(ID, propertyType, globalAddress, city, loanAmount, apr, result, lat, lng, zip, priceDbl.toString(), downPaymentDbl.toString());
                    Toast.makeText(getContext(),"Property Details edited!", Toast.LENGTH_LONG).show();
                    Log.i("Vansh", "savecalc 11"+ID);
                }
                /*db.addPropertyInfo(tempID, propertyType, globalAddress, city, loanAmount, apr, result, lat, lng);
                Toast.makeText(getContext(),"Property Details saved!", Toast.LENGTH_LONG).show();
                Log.i("Vansh", "savecalc 10"+ID);*/

            }
        });


        return rootView;
    }

}
