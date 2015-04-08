package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

/**
 * Created by android on 08.04.15.
 */
public interface IPressureView {

    void setResult(String result);
    void setPickerValue(int mCurrentValue);
    void changeConvertType(int type);
}
