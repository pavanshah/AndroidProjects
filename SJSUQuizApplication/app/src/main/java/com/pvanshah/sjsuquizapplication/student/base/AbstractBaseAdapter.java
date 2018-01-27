package com.pvanshah.sjsuquizapplication.student.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avinash
 */
public abstract class AbstractBaseAdapter<T, V> extends BaseAdapter {

    List<T> items = new ArrayList<>();

    protected final Context context;

    /**
     * Constructor to get hold to context
     *
     * @param context
     */
    public AbstractBaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * Add items to the adapter
     * this is similar to sending list to adapter in constructor
     * and refresh the list
     *
     * @param items
     * @return
     */
    public AbstractBaseAdapter addItems(List<T> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
        return this;
    }

    /**
     * Add items to the existing list
     * and refresh the list
     *
     * @param items
     */
    public AbstractBaseAdapter addAllItems(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V holder;
        View view = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
            holder = newViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (V) view.getTag();
        }
        bindView(holder, getItem(position));
        return view;
    }

    /**
     * abstract method to be implemented to
     * get layout id from the subclass
     *
     * @return layout id as int
     */
    public abstract int getLayoutId();

    /**
     * abstract method to be implemented to
     * get view holder from the sub class
     *
     * @param view
     * @return V which is second param of the abstracted class
     */
    public abstract V newViewHolder(View view);

    /**
     * abstract method to be implemented to
     * implement the getView method
     * logic of getview i.e., set data to view is done
     * in this method
     *
     * @param holder
     * @param item
     */
    public abstract void bindView(V holder, T item);
}
