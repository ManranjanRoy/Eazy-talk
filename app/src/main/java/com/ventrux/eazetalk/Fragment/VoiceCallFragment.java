package com.ventrux.eazetalk.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ventrux.eazetalk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceCallFragment extends Fragment {

    public VoiceCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_call, container, false);
    }
}
