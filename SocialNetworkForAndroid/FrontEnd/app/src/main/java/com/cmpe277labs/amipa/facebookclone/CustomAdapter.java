package com.cmpe277labs.amipa.facebookclone;

/**
 * Created by amipa on 5/16/2017.
 */

import android.content.Context;

import android.graphics.BitmapFactory;

import android.net.Uri;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater=null;
    private ArrayList<TimelineInfo> resultList;

    public void updateResults(ArrayList<TimelineInfo> arrayList) {
        Log.d("pavan1", "inside update");
        resultList = arrayList;
        notifyDataSetChanged();
    }



    public CustomAdapter(Context context, ArrayList<TimelineInfo> timelineInfo) {
        Log.d("pavan1", "inside arraylist "+timelineInfo.size());
        resultList = timelineInfo;
        this.context=context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder2{
        TextView postText;
        LinearLayout imageLayout;
        TextView screenname;
        TextView relationship;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder2 holder2=new Holder2();

        View rowView = inflater.inflate(R.layout.main_list, null);
        holder2.postText=(TextView) rowView.findViewById(R.id.postText);
        holder2.screenname = (TextView) rowView.findViewById(R.id.screenname);
        holder2.imageLayout = (LinearLayout) rowView.findViewById(R.id.imagelist);

        TimelineInfo timelineInfo = resultList.get(position);
        Log.d("pavan1", "user "+resultList.get(position).getScreenname()+" images "+timelineInfo.getPostImages().length);
        holder2.postText.setText(timelineInfo.getPostText());
        holder2.screenname.setText(timelineInfo.getScreenname());
       // holder2.relationship.setText(timelineInfo.getRelationship());

        for(int i = 0 ; i < timelineInfo.getPostImages().length ; i++)
        {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);
            imageView.setMaxWidth(100);
            imageView.setMaxHeight(100);
            imageView.setPadding(0, 0, 0, 0);
            Picasso.with(context).load(timelineInfo.getPostImages()[i]).into(imageView);
           // imageView.setImageResource(R.drawable.pavan);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder2.imageLayout.addView(imageView);
        }

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "touched", Toast.LENGTH_LONG).show();
            }
        });

        return rowView;

    }
}
