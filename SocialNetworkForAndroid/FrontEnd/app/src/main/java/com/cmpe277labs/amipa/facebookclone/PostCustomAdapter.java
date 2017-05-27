package com.cmpe277labs.amipa.facebookclone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amipa on 5/20/2017.
 */

public class PostCustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    ArrayList<PostInfo> resultList = new ArrayList<>();

    public PostCustomAdapter(Context context,  int[] prgmImages) {
        // TODO Auto-generated constructor stub

        this.context=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void updateResults(ArrayList<PostInfo> arrayList) {
        Log.d("pavan2", "inside update");
        resultList = arrayList;
        notifyDataSetChanged();
    }


    public PostCustomAdapter(Context context,  ArrayList<PostInfo> arrayList) {
        this.context=context;
        resultList=arrayList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return resultList.size();
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
        TextView postText;
        LinearLayout imageLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
            PostCustomAdapter.Holder2 holder2=new PostCustomAdapter.Holder2();
            View rowView;

            rowView = inflater.inflate(R.layout.posts_list, null);
            PostInfo postInfo = resultList.get(position);

             holder2.postText =(TextView) rowView.findViewById(R.id.postText);
             holder2.postText.setText(postInfo.getPostText());

            holder2.imageLayout = (LinearLayout) rowView.findViewById(R.id.imagelist);
            for(int i = 0 ; i < postInfo.getPostImages().length ; i++)
            {
                ImageView imageView = new ImageView(context);
                imageView.setId(i);
                imageView.setMaxWidth(100);
                imageView.setMaxHeight(200);
                imageView.setPadding(5, 5, 5, 5);
                Picasso.with(context).load(postInfo.getPostImages()[i]).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                holder2.imageLayout.addView(imageView);
            }

            //holder2.img=(ImageView) rowView.findViewById(R.id.postedimage);
            //holder2.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        return rowView;
    }
}
