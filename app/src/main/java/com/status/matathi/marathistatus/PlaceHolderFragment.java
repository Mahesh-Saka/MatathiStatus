package com.status.matathi.marathistatus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.status.matathi.util.Util;

/**
 * Created by Mahesh on 1/27/2017.
 */

public class PlaceHolderFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_MESSAGE = "message";
    String message = null;

    public static PlaceHolderFragment getInstance(String message) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.txtView);
        message = getArguments().getString(ARG_MESSAGE);
        textView.setText(message);
        rootView.findViewById(R.id.btnCopyText).setOnClickListener(this);
        rootView.findViewById(R.id.btnWhatApp).setOnClickListener(this);
        rootView.findViewById(R.id.btnShare).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCopyText:
                Util.getClipboardManager(getActivity(), message);
                break;
            case R.id.btnWhatApp:
                Util.shareMessageWhatApp(getActivity(), message);
                break;
            case R.id.btnShare:
                Util.shareMarathiStatus(getActivity(), message, false);
                break;
        }
    }

}
