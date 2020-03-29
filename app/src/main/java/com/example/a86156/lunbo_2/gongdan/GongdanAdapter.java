package com.example.a86156.lunbo_2.gongdan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a86156.lunbo_2.R;

import java.util.ArrayList;
import java.util.List;

public class GongdanAdapter extends BaseAdapter {
    View view;
    ViewHolder viewHolder;
    List<GongdanEntity> dataList;
    Context context;

    public GongdanAdapter(List<GongdanEntity> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gongdan_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.jhjhrq = view.findViewById(R.id.jhjhrq);
            viewHolder.gdbh = view.findViewById(R.id.gdbh);
            viewHolder.cpbh = view.findViewById(R.id.cpbh);
            viewHolder.cpmc = view.findViewById(R.id.cpmc);
            viewHolder.jhsl = view.findViewById(R.id.jhsl);
            viewHolder.yzj = view.findViewById(R.id.yzj);
            viewHolder.cpf = view.findViewById(R.id.cpf);
            viewHolder.jj = view.findViewById(R.id.jj);
            viewHolder.pg = view.findViewById(R.id.pg);
            viewHolder.dd = view.findViewById(R.id.dd);
            viewHolder.bz = view.findViewById(R.id.bz);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        try {
            viewHolder.jhjhrq.setText(dataList.get(position).getJhjhrq());
            viewHolder.gdbh.setText(dataList.get(position).getGdbh());
            viewHolder.cpbh.setText(dataList.get(position).getCpbh());
            viewHolder.cpmc.setText(dataList.get(position).getCpmc());
            viewHolder.jhsl.setText(dataList.get(position).getJhsl() + "");
            viewHolder.yzj.setText(dataList.get(position).getYzj() + "");
            viewHolder.cpf.setText(dataList.get(position).getCpf() + "");
            viewHolder.jj.setText(dataList.get(position).getJj() + "");
            viewHolder.pg.setText(dataList.get(position).getPg() + "");
            viewHolder.dd.setText(dataList.get(position).getDd() + "");
            viewHolder.bz.setText(dataList.get(position).getBz() + "");

            if (dataList.get(position).getColor().equals("0")) {
                viewHolder.jhjhrq.setBackgroundColor(Color.WHITE);
                viewHolder.gdbh.setBackgroundColor(Color.WHITE);
                viewHolder.cpbh.setBackgroundColor(Color.WHITE);
                viewHolder.cpmc.setBackgroundColor(Color.WHITE);
                viewHolder.jhsl.setBackgroundColor(Color.WHITE);
                viewHolder.yzj.setBackgroundColor(Color.WHITE);
                viewHolder.cpf.setBackgroundColor(Color.WHITE);
                viewHolder.jj.setBackgroundColor(Color.WHITE);
                viewHolder.pg.setBackgroundColor(Color.WHITE);
                viewHolder.dd.setBackgroundColor(Color.WHITE);
                viewHolder.bz.setBackgroundColor(Color.WHITE);
            }
            if (!dataList.get(position).getYzj().equals("-") ) {
                if (Integer.parseInt(dataList.get(position).getYzj()) >= Integer.parseInt(dataList.get(position).getYzj())) {
                   // viewHolder.yzj.setBackgroundColor(Color.GREEN);
                }
            }
           if (!dataList.get(position).getCpf().equals("-")) {
                if (Integer.parseInt(dataList.get(position).getCpf()) >= Integer.parseInt(dataList.get(position).getCpf())) {
                   // viewHolder.cpf.setBackgroundColor(Color.GREEN);
                }
            }
            if (!dataList.get(position).getJj().equals("-")) {
                if (Integer.parseInt(dataList.get(position).getJj()) >= Integer.parseInt(dataList.get(position).getJj())) {
                   // viewHolder.jj.setBackgroundColor(Color.GREEN);
                }
            }
            if (!dataList.get(position).getPg().equals("-")) {
                if (Integer.parseInt(dataList.get(position).getPg()) >= Integer.parseInt(dataList.get(position).getPg())) {
                   // viewHolder.pg.setBackgroundColor(Color.GREEN);
                }
            }
            if (!dataList.get(position).getDd().equals("-")) {
                if (Integer.parseInt(dataList.get(position).getDd()) >= Integer.parseInt(dataList.get(position).getDd())) {
                   // viewHolder.dd.setBackgroundColor(Color.GREEN);
                }
            }
            if (!dataList.get(position).getBz().equals("-")) {
                if (Integer.parseInt(dataList.get(position).getBz()) >= Integer.parseInt(dataList.get(position).getBz())) {
                   // viewHolder.bz.setBackgroundColor(Color.GREEN);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

}
