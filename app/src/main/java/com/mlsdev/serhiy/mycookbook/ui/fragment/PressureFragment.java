package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;

/**
 * Created by android on 07.04.15.
 */
public class PressureFragment extends Fragment {

    private NumberPicker mLeftNumberPicker;
    private NumberPicker mRightNumberPicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity) getActivity()).setActionBarTitle(getString(R.string.g_to_mg));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pressure, container, false);
        mLeftNumberPicker = (NumberPicker) view.findViewById(R.id.np_left);
        mRightNumberPicker = (NumberPicker) view.findViewById(R.id.np_right);

        mLeftNumberPicker.setMaxValue(1000);
        mLeftNumberPicker.setMinValue(1);
        mRightNumberPicker.setMaxValue(1000);
        mRightNumberPicker.setMinValue(1);
        return view;
    }
}
