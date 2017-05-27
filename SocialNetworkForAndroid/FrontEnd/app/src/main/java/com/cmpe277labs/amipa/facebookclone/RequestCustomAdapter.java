package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amipa on 5/20/2017.
 */

public class RequestCustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    String[] femail;
    String[] images;
    private static LayoutInflater inflater=null;
    private static int i=0;
    SessionManager sessionManager;
    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();


    public RequestCustomAdapter(Context context, String[] prgmNameList, String[] prgmEmailList, String[] prgmImages) {
        // TODO Auto-generated constructor stub

        this.context=context;
        result=prgmNameList;
        femail=prgmEmailList;
        images=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void updateResults(String[] prgmNameList) {
        Log.d("pavan1", "inside update");
        result = prgmNameList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView text;
        ImageView imageView;
        ImageButton accept;
        ImageButton reject;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final RequestCustomAdapter.Holder holder=new RequestCustomAdapter.Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.request_list, null);
        holder.text =(TextView)rowView.findViewById(R.id.requestname);
        holder.text.setText(result[position]);
        holder.imageView=(ImageView)rowView.findViewById(R.id.ruserprofilepic);
        Log.d("PAVAN","..name "+result[position]+"image id position...."+images[position]);

        holder.imageView.setImageDrawable(this.LoadImageFromWebOperations(images[position]));
        holder.accept =(ImageButton) rowView.findViewById(R.id.tick);
        holder.reject=(ImageButton)rowView.findViewById(R.id.cross);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PAVAN","button called");
                Log.d("PAVAN",""+result[position]);

                sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());
                String email = sessionManager.getEmail();
                final JSONObject dataJSON = new JSONObject();
                JSONObject paramObject = new JSONObject();
                try {
                    paramObject.put("email", email);
                    paramObject.put("friendEmail",femail[position]);
                    dataJSON.put("data", paramObject);
                    Log.d("PREQ", "params " + dataJSON.toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                httpRequestHandler.sendHTTPRequest("/acceptInvitation", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        ArrayList<String> prgmNameList33 = new ArrayList<String>();
                        Log.d("PREQ", "invite start hereeeeeee " + result.toString());
                        String status = result.get("status").toString();
                        String res = result.get("result").toString();
                        Log.d("PREQ","Error is"+res);
                        if (status.equals("200")) {
                            rowView.setVisibility(View.INVISIBLE);

                            Log.d("PREQ","Accepted");
                        } else if (status.equals("400")) {
                            Log.d("PREQ", "wrong credentials");
                        }
                    }
                });


            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PAVAN","button called");
                Log.d("PAVAN",""+result[position]);

                sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());
                final String email = sessionManager.getEmail();
                final JSONObject dataJSON = new JSONObject();
                JSONObject paramObject = new JSONObject();
                try {
                    paramObject.put("email", email);
                    paramObject.put("rejectEmail",femail[position]);
                    dataJSON.put("data", paramObject);
                    Log.d("PREQ", "params " + dataJSON.toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                httpRequestHandler.sendHTTPRequest("/rejectFriendRequest", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        Log.d("PREQ", "rejection start hereeeeeee " + result.toString());
                        String status = result.get("status").toString();
                        String res = result.get("result").toString();
                        Log.d("PREQ","Error is"+res);
                        if (status.equals("200")) {
                            rowView.setVisibility(View.INVISIBLE);
                            Log.d("PREQ","Rejected");
                            result.remove(femail[position]);
                        } else if (status.equals("400")) {
                            Log.d("PREQ", "wrong credentials");
                        }

                    }
                });
            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        return rowView;
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            Log.d("PAVAN","**** inside drawable");
            InputStream is = (InputStream) new URL(url).getContent();
            Log.d("PAVAN", " input stream ^^^^^^^^"+is.toString());
            Drawable d = Drawable.createFromStream(is, "src");
            Log.d("PAVAN","**** -->"+d.toString());
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}

