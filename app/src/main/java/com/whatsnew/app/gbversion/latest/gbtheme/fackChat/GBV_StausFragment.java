package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_StausFragment extends Fragment {
    View view;
    GBV_MainFackChat mainFactChat;

    public GBV_StausFragment(GBV_MainFackChat mainFackChat) {
        this.mainFactChat = mainFackChat;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.gbv_fragment_status, container, false);
        return this.view;
    }
}
