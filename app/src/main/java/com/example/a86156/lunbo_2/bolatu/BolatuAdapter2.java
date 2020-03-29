package com.example.a86156.lunbo_2.bolatu;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.a86156.lunbo_2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BolatuAdapter2 extends BaseAdapter {
    private List<Entity> entityList;
    private Context context;

    View view;
    private ViewHolder viewHolder;



    public BolatuAdapter2(List<Entity> entityList, Context context) {
        this.entityList = entityList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return entityList.size();
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.balatu_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.scrq = view.findViewById(R.id.scrq);
            viewHolder.ydg = view.findViewById(R.id.ydg);
            viewHolder.sdg = view.findViewById(R.id.sdg);
            viewHolder.bzgs = view.findViewById(R.id.bzgs);
            viewHolder.zcsc = view.findViewById(R.id.zcsc);
            viewHolder.jdl = view.findViewById(R.id.jdl);
            viewHolder.mbjdl = view.findViewById(R.id.mbjdl);
            viewHolder.jhsl = view.findViewById(R.id.jhsl);
            viewHolder.jhdcl = view.findViewById(R.id.jhdcl);
            viewHolder.mbdcl = view.findViewById(R.id.mbdcl);
            viewHolder.color = view.findViewById(R.id.color);
            viewHolder.sjsl = view.findViewById(R.id.sjsl);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        try {

            if (entityList.get(position).getColor().equals("0")) {
                viewHolder.scrq.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.ydg.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.sdg.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.bzgs.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.zcsc.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.jdl.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.mbjdl.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.jhsl.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.jhdcl.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.mbdcl.setTextColor(Color.parseColor("#ADFF2F"));
                viewHolder.sjsl.setTextColor(Color.parseColor("#ADFF2F"));
            }



            viewHolder.scrq.setText(entityList.get(position).getData());
            viewHolder.ydg.setText(entityList.get(position).getYdg());
            viewHolder.sdg.setText(entityList.get(position).getSdg());
            viewHolder.bzgs.setText(entityList.get(position).getBzgs());
            viewHolder.zcsc.setText(entityList.get(position).getZcsc());
            viewHolder.jdl.setText(entityList.get(position).getJdl());
            viewHolder.mbjdl.setText(entityList.get(position).getMbjdl());
            viewHolder.jhsl.setText(entityList.get(position).getJhsl());
            viewHolder.jhdcl.setText(entityList.get(position).getJhdcl());
            viewHolder.mbdcl.setText(entityList.get(position).getMbdcl());
            viewHolder.sjsl.setText(entityList.get(position).getSjsl());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public class ViewHolder {
        TextView scrq;
        TextView ydg;
        TextView sdg;
        TextView bzgs;
        TextView zcsc;
        TextView jdl;
        TextView mbjdl;
        TextView jhsl;
        TextView sjsl;
        TextView jhdcl;
        TextView mbdcl;
        TableRow color;

    }
}
