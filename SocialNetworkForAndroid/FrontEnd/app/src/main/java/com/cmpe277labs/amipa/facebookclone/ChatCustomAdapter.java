package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by amipa on 5/25/2017.
 */

public class ChatCustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    String[] femail;
    private static LayoutInflater inflater=null;
    private static int i=0;
    HTTPRequestHandler httpRequestHandler = HTTPRequestHandler.getInstance();
    SessionManager sessionManager;

    public ChatCustomAdapter(Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateResults(String[] MessageNames) {
        Log.d("pavan3", "inside update");
        result = MessageNames;
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

    public class Holder2{
        TextView text;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("pavan3", "update view now");
        final ChatCustomAdapter.Holder2 holder2=new ChatCustomAdapter.Holder2();
        View rowView;
        Log.d("timeline","timeline");
        rowView = inflater.inflate(R.layout.chat_list, null);
        holder2.text=(TextView) rowView.findViewById(R.id.sendername);
        holder2.text.setText(result[position]);
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinearLayout b = (LinearLayout) v;
                TextView tv = (TextView) b.findViewById(R.id.sendername);
                String to = tv.getText().toString();
                Log.d("pavan3", "buttontext"+tv.getText().toString());

                Intent intent = new Intent(HTTPRequestHandler.getMyContext(),MessageBodyFragment.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("to", to);
                HTTPRequestHandler.getMyContext().startActivity(intent);

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
