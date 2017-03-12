package com.status.matathi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.status.matathi.marathistatus.R;
import com.status.matathi.util.Util;

import java.util.ArrayList;

/**
 * Created by Mahesh on 2/18/2017.
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener {
    ArrayList<String> arrayList = null;
    Context context;
    LayoutInflater layoutInflater;
    public static int pos = 0;

    public ListAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return arrayList != null ? arrayList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolders viewHolders = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.sms_list_layout, null, false);
            viewHolders = new ViewHolders();
            viewHolders.textView = (TextView) convertView.findViewById(R.id.txtView);
            viewHolders.btnCopyText = (ImageView) convertView.findViewById(R.id.btnCopyText);
            viewHolders.btnWhatApp = (ImageView) convertView.findViewById(R.id.btnWhatApp);
            viewHolders.btnShare = (ImageView) convertView.findViewById(R.id.btnShare);
            convertView.setTag(viewHolders);
        } else {
            viewHolders = (ViewHolders) convertView.getTag();
        }
        String message = (String) arrayList.get(position);
        viewHolders.textView.setText(message);

        viewHolders.btnCopyText.setTag(message);
        viewHolders.btnShare.setTag(message);
        viewHolders.btnWhatApp.setTag(message);

        viewHolders.btnCopyText.setOnClickListener(this);
        viewHolders.btnShare.setOnClickListener(this);
        viewHolders.btnWhatApp.setOnClickListener(this);

        pos = position + 1;
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCopyText:
                Util.getClipboardManager(context, (String) v.getTag());
                break;
            case R.id.btnWhatApp:
                Util.shareMessageWhatApp(context, (String) v.getTag());
                break;
            case R.id.btnShare:
                Util.shareMarathiStatus(context, (String) v.getTag(), false);
                break;
        }
    }

    static class ViewHolders {
        TextView textView;
        ImageView btnCopyText, btnWhatApp, btnShare;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
