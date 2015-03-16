package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.widget.AdapterView;

/**
 * Created by android on 11.03.15.
 */
public interface IChooseCategoryPresenter {

    void loadCategoryList();
    void categoryIsChoisen(AdapterView<?> parent, int position);

}
