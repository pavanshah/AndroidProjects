package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by amipa on 5/25/2017.
 */

public class ChatProfileCustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    String[] femail;
    private static LayoutInflater inflater=null;
    private static int i=0;
    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
    SessionManager sessionManager;

    public ChatProfileCustomAdapter(Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ChatProfileCustomAdapter.Holder2 holder2=new ChatProfileCustomAdapter.Holder2();
        View rowView;
        Log.d("timeline","timeline");
        rowView = inflater.inflate(R.layout.messageprofile_list, null);
        holder2.img=(ImageView) rowView.findViewById(R.id.receiverimage);
        Log.d("PAVAN","..name "+result[position]+"image id position...."+imageId[position]);
        holder2.img.setImageResource(imageId[position]);
        holder2.text=(TextView) rowView.findViewById(R.id.receivename);
        holder2.text.setText(result[position]);
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

