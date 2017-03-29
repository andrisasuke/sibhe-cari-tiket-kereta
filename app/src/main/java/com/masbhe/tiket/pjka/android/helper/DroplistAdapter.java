package com.masbhe.tiket.pjka.android.helper;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class DroplistAdapter implements SpinnerAdapter {
    private Context context;
    private ArrayList<DroplistItem> list;
    private int mode;

    public DroplistAdapter(Context context, ArrayList<DroplistItem> list) {
        this.mode = 17367048;
        this.context = context;
        this.list = list;
    }

    public DroplistAdapter(Context context, ArrayList<DroplistItem> list, int mode) {
        this.mode = 17367048;
        this.context = context;
        this.list = list;
        this.mode = mode;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textview = (TextView) ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.mode, null);
        textview.setText(((DroplistItem) this.list.get(position)).getLabel());
        return textview;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    public int getCount() {
        if (this.list != null) {
            return this.list.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if (this.list == null || this.list.size() <= 0) {
            return null;
        }
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textview = (TextView) ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(this.mode, null);
        textview.setText(((DroplistItem) this.list.get(position)).getLabel());
        return textview;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        if (this.list == null || this.list.size() == 0) {
            return true;
        }
        return false;
    }

    public String getSelectedKey(int position) {
        return ((DroplistItem) this.list.get(position)).getKey();
    }
}