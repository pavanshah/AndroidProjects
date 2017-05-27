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
 * Created by amipa on 5/20/2017.
 */

public class InvitationCustomAdapter extends BaseAdapter{
    String [] result;
    String[] images;
    Context context;

    private static LayoutInflater inflater=null;
    private static int i=0;

    public InvitationCustomAdapter(Context context, String[] prgmNameList, String images[]) {
        // TODO Auto-generated constructor stub

        this.context=context;
        result=prgmNameList;
        this.images=images;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        InvitationCustomAdapter.Holder holder=new InvitationCustomAdapter.Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.invitation_list, null);
        holder.text =(TextView)rowView.findViewById(R.id.invitationname);
        holder.text.setText(result[position]);
        holder.img=(ImageView)rowView.findViewById(R.id.iuserprofilepic);
        holder.img.setImageDrawable(this.LoadImageFromWebOperations(images[position]));
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
