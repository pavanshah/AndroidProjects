package com.cmpe277labs.amipa.facebookclone;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HTTPRequestHandler extends Application {

    String appPath ="http://ec2-13-58-101-250.us-east-2.compute.amazonaws.com:8080";


    private static Context mContext;
    private static HTTPRequestHandler instance;
    private RequestQueue mRequestQueue;
    JSONObject finalResult = new JSONObject();

    public static HTTPRequestHandler getInstance() {
        return instance;
    }

    public static Context getMyContext() {
        return mContext;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getMyContext());
        }

        return mRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }


    public void sendHTTPRequest(String customurl, JSONObject body,final VolleyCallback callback){

        String url = appPath+customurl;
        RequestQueue queue = getRequestQueue();

        Log.d("HTTP", "url "+url);
        Log.d("HTTP", "params "+body);

        Request request = new JsonObjectRequest(url, body,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {

                        finalResult = response;
                        Log.d("HTTP", "result "+finalResult);
                        try {
                            callback.onSuccess(finalResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP", "Inside error2"+error);
                VolleyLog.e("Error: ", error.getMessage());

                NetworkResponse response = error.networkResponse;

                if(response != null){
                    switch(response.statusCode){
                        case 400:

                            try {
                                finalResult.put("status", "400");
                                callback.onSuccess(finalResult);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            break;
                    }
                    //Additional cases
                }
            }
        }

        ).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(request);
    }


    public interface VolleyCallback{
        void onSuccess(JSONObject result) throws JSONException;
    }

}
