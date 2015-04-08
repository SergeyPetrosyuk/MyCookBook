package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.presenter.PressurePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IPressurePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IPressureView;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;

/**
 * Created by android on 07.04.15.
 */
public class PressureFragment extends Fragment implements IPressureView {

    private NumberPicker mNumberPicker;
    private IPressurePresenter mPresenter;
    private TextView mResultTextView;
    private ImageButton mChangeConvertTypeButton;
    private TextView mLeftTextView;
    private TextView mRightTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new PressurePresenter(this);
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
        mNumberPicker = (NumberPicker) view.findViewById(R.id.np_select_value);
        mNumberPicker.setMaxValue(1000);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setFocusable(false);
        mNumberPicker.setOnValueChangedListener(new OnPickerValueChangedListener(mPresenter));

        mResultTextView = (TextView) view.findViewById(R.id.tv_pressure_result);

        mChangeConvertTypeButton = (ImageButton) view.findViewById(R.id.ib_change_convert_type);
        mChangeConvertTypeButton.setOnClickListener(new OnChangeTypeClickListener());

        mLeftTextView = (TextView) view.findViewById(R.id.tv_left);
        mRightTextView = (TextView) view.findViewById(R.id.tv_right);

        mPresenter.setCurrentData();

        return view;
    }

    @Override
    public void setResult(String result) {
        mResultTextView.setText(result);
    }

    @Override
    public void setPickerValue(int mCurrentValue) {
        mNumberPicker.setValue(mCurrentValue);
    }

    @Override
    public void changeConvertType(int type) {
        if (type == PressurePresenter.FROM_GRAM_TO_MILLIGRAM){
            mLeftTextView.setText(getString(R.string.pressure_g));
            mRightTextView.setText(getString(R.string.pressure_mg));
        } else {
            mLeftTextView.setText(getString(R.string.pressure_mg));
            mRightTextView.setText(getString(R.string.pressure_g));
        }
    }

    private class OnPickerValueChangedListener implements NumberPicker.OnValueChangeListener{
        IPressurePresenter mPressurePresenter;

        private OnPickerValueChangedListener(IPressurePresenter mPressurePresenter) {
            this.mPressurePresenter = mPressurePresenter;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mPressurePresenter.calculate(newVal);
        }

    }

    private class OnChangeTypeClickListener implements View.OnClickListener {

        private Animation animation;

        private OnChangeTypeClickListener() {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.change_convert_type);
        }

        @Override
        public void onClick(View v) {
            mPresenter.changeConverType();
            mChangeConvertTypeButton.startAnimation(animation);
        }

    }
}
