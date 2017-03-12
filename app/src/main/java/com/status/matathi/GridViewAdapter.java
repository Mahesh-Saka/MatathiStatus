package com.status.matathi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.status.matathi.marathistatus.R;

/**
 * Created by Mahesh on 1/27/2017.
 * @version 1.0.0
 */

public class GridViewAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    int drawable[] = {
            R.drawable.ic_all_status,
            R.drawable.ic_maitri,
            R.drawable.ic_prem,
            R.drawable.ic_vinod,
            R.drawable.ic_mhani,
            R.drawable.ic_kavita,
            R.drawable.ic_prerana,
            R.drawable.ic_janta_rajai,
            R.drawable.ic_aatvani,
            R.drawable.ic_desh,
            R.drawable.ic_shayarii,
            R.drawable.ic_suvichar,
            R.drawable.ic_dharma,
            R.drawable.ic_tomane,
            R.drawable.ic_paus,
            R.drawable.ic_breakup,
            R.drawable.ic_drushtikon,
            R.drawable.ic_san,
            R.drawable.ic_vaddivas,
            R.drawable.ic_mule,
            R.drawable.ic_congratesi

    };

    public GridViewAdapter(Activity activity) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 21;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 21;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.listview_layout, null);
        int draw = position > drawable.length - 1 ? 0 : position;
        CustomeImageView imageView = (CustomeImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(drawable[draw]);
        return view;
    }
}
