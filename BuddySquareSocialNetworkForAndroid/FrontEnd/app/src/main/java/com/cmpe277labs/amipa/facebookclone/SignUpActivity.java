package com.cmpe277labs.amipa.facebookclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import layout.SettingsFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("su","su");
        Log.e("su","su");
        setContentView(R.layout.activity_sign_up);
        final EditText emailValidate = (EditText)findViewById(R.id.input_email);
        final EditText password=(EditText)findViewById(R.id.input_password);
        final EditText screenname=(EditText)findViewById(R.id.screennamereg);
        final EditText firstname=(EditText)findViewById(R.id.firstname);
        final EditText lastname=(EditText)findViewById(R.id.lastname);
        final Button b=(Button) findViewById(R.id.register);
        final Button b2=(Button)findViewById(R.id.verifycode);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailValidate.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (email.matches(emailPattern)) {
                    //Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();

                    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();

                    JSONObject dataJSON = new JSONObject();
                    JSONObject paramObject = new JSONObject();
                    final String ev = emailValidate.getText().toString();
                    final String pw = password.getText().toString();
                    final String sn = screenname.getText().toString();
                    final String fn = firstname.getText().toString();
                    final String ln = lastname.getText().toString();
                    Log.d("values", "" + ev + " " + pw + " " + sn + " " + fn + " " + ln);

                    if (ev == "" || pw == "" || sn == "" || fn == "" || ln == "") {
                        Toast.makeText(getApplicationContext(), "one of the fields is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            paramObject.put("email", ev);
                            paramObject.put("screenName", sn);
                            paramObject.put("firstName", fn);
                            paramObject.put("lastName", ln);
                            paramObject.put("password", pw);
                            dataJSON.put("data", paramObject);
                            Log.d("PAVAN", "params " + dataJSON.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        httpRequestHandler.sendHTTPRequest("/signUp", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) throws JSONException {

                                Log.d("PAVAN", "result here " + result.toString());

                                String status = result.get("status").toString();
                                String res = result.get("result").toString();
                                Log.d("Error is ", "erorrr        " + res);
                                if (status.equals("200")) {
                                    Toast.makeText(getApplicationContext(), "Click verify button", Toast.LENGTH_SHORT).show();
                                } else if (status.equals("400")) {
                                    Log.d("", "wrong credentials");
                                    Toast.makeText(getApplicationContext(), "Error " + res, Toast.LENGTH_SHORT).show();

                                }else if (status.equals("401")) {
                                    //Log.d("", "wrong credentials");
                                    Toast.makeText(getApplicationContext(), "User already registered!", Toast.LENGTH_SHORT).show();

                                }else if (status.equals("402")) {
                                    //Log.d("", "wrong credentials");
                                    Toast.makeText(getApplicationContext(), "Screen Name already in use, please enter different Screen Name!", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }
                }
                else{
                    Log.d("MAKAI","***********");
                    Toast.makeText(getApplicationContext(),"Invalid Emai Format",Toast.LENGTH_LONG);
                }
            }

        });


        final LayoutInflater factory = LayoutInflater.from(this);
       b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Verification Code");

                    final View vvc = factory.inflate(R.layout.verify_layout,null);
                    builder.setView(vvc);
                    final EditText emailvc=(EditText)vvc.findViewById(R.id.emaivc);
                    final EditText code=(EditText)vvc.findViewById(R.id.vcode);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email=emailvc.getText().toString();
                            String vc=code.getText().toString();
                            HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();

                            JSONObject dataJSON = new JSONObject();
                            JSONObject paramObject = new JSONObject();

                            if(vc == "")
                            {
                                Toast.makeText(getApplicationContext(),"invalid code",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                try {
                                    paramObject.put("email", email);
                                    paramObject.put("verificationCode", vc);
                                    dataJSON.put("data", paramObject);
                                    Log.d("PAVAN", "params "+dataJSON.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                httpRequestHandler.sendHTTPRequest("/verifyCode", dataJSON, new HTTPRequestHandler.VolleyCallback(){
                                    @Override
                                    public void onSuccess(JSONObject result) throws JSONException {

                                        Log.d("PAVAN", "result here "+result.toString());

                                        String status = result.get("status").toString();
                                        String res = result.get("result").toString();

                                        Log.d("******verfiy code","result is "+res);
                                        if(status.equals("200"))
                                        {
                                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(i);
                                        }
                                        else if(status.equals("400"))
                                        {
                                            Log.d("", "wrong credentials");
                                        }
                                        else if(status.equals("401"))
                                        {
                                            Log.d("", "wrong credentials");
                                            Toast.makeText(getApplicationContext(), "User doesn't exist!", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(status.equals("402"))
                                        {
                                            Toast.makeText(getApplicationContext(), "User already verified! Please Login!", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(status.equals("403"))
                                        {
                                            Toast.makeText(getApplicationContext(), "Verification Code mismatched!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });

    }
}
