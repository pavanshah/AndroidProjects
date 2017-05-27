package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amipa on 5/20/2017.
 */

public class ProfileCustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    String [] imageId;
    String[] femail;
    private static LayoutInflater inflater=null;
    private static int i=0;
    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
    SessionManager sessionManager;

    public ProfileCustomAdapter(Context context, String[] prgmNameList, String[] prgmImages, String[] prgmEmail) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        femail=prgmEmail;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void updateResults(String fe) {

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
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

    public class Holder2{
        TextView text;
        ImageView img;
        Button button;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ProfileCustomAdapter.Holder2 holder2=new ProfileCustomAdapter.Holder2();
        final View rowView;
        Log.d("timeline","timeline");
        rowView = inflater.inflate(R.layout.profile_list, null);
        holder2.img=(ImageView) rowView.findViewById(R.id.userprofilepic);
        Log.d("PAVAN","..name "+result[position]+"image id position...."+imageId[position]);
        holder2.img.setImageDrawable(this.LoadImageFromWebOperations(imageId[position]));
        holder2.text=(TextView) rowView.findViewById(R.id.profilename);
        holder2.text.setText(result[position]);
        holder2.button=(Button)rowView.findViewById(R.id.followbutton);
        holder2.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PAVAN","button called");
                Log.d("PAVAN",""+result[position]);
                sessionManager = new SessionManager(HTTPRequestHandler.getMyContext());
                String email = sessionManager.getEmail();
                String fn=sessionManager.getFirstname();
                String ln=sessionManager.getLastname();
                final JSONObject dataJSON = new JSONObject();
                JSONObject paramObject = new JSONObject();
                final JSONObject dataJSON2 = new JSONObject();
                JSONObject paramObject2 = new JSONObject();
                try {
                    paramObject.put("email", email);
                    paramObject.put("firstName", fn);
                    paramObject.put("lastName", ln);
                    paramObject.put("friendEmail",femail[position] );
                    dataJSON.put("data", paramObject);
                    Log.d("PAVAN", "params " + dataJSON.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                httpRequestHandler.sendHTTPRequest("/addFriendByEmail", dataJSON, new HTTPRequestHandler.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        Log.d("PAVAN", "invite start hereeeeeee " + result.toString());
                        String status = result.get("status").toString();
                        String res = result.get("result").toString();
                        if (status.equals("200")) {
                            rowView.setVisibility(View.INVISIBLE);
                            Log.d("PAVAN","FOLLOwed");
                        } else if (status.equals("400")) {
                            Log.d("", "wrong credentials");
                        }
                    }
                });
                try {
                    paramObject2.put("email", email);
                    dataJSON2.put("data",paramObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
