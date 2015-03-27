package com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor;

/**
 * Created by android on 27.03.15.
 */
public interface ILoadCategoryNameInteractor {

    /**
     * @param categoryId The category id by which we get its name from DB
     * */
    void loadCatrgoryName(Integer categoryId);
    
}
