package com.mlsdev.serhiy.mycookbook.presenter;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IPressurePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IPressureView;

/**
 * Created by android on 08.04.15.
 */
public class PressurePresenter implements IPressurePresenter {
    private IPressureView mView;
    public static final int FROM_GRAM_TO_MILLIGRAM = 0;
    public static final int FROM_MILLIGRAM_TO_GRAM = 1;
    private int mCaonvertType;
    private int mCurrentValue = 1;
    private String mCurrentResult = null;

    public PressurePresenter(IPressureView aView) {
        this.mView = aView;
        mCaonvertType = FROM_GRAM_TO_MILLIGRAM;
    }

    @Override
    public void calculate(int value) {
        Double result;

        if (mCaonvertType == FROM_GRAM_TO_MILLIGRAM){
            result = value / 0.001;
        } else {
            result = value / 1000.0;
        }

        mView.setResult(result.toString());

        mCurrentValue = value;
        mCurrentResult = result.toString();

        return;
    }

    @Override
    public void changeConverType() {
        if (mCaonvertType == FROM_GRAM_TO_MILLIGRAM){
            mCaonvertType = FROM_MILLIGRAM_TO_GRAM;
        } else {
            mCaonvertType = FROM_GRAM_TO_MILLIGRAM;
        }

        calculate(mCurrentValue);
        mView.changeConvertType(mCaonvertType);
    }

    @Override
    public String getCurrentResult() {
        return mCurrentResult;
    }

    @Override
    public int getCurrentValue() {
        return mCurrentValue;
    }

    @Override
    public void setCurrentData() {
        calculate(mCurrentValue);
        mView.setPickerValue(mCurrentValue);
    }
}
