package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

/**
 * Created by android on 08.04.15.
 */
public interface IPressurePresenter {

    void calculate(int value);
    void changeConverType();
    String getCurrentResult();
    int getCurrentValue();
    void setCurrentData();

}
