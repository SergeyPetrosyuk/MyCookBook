package com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor;

/**
 * Created by android on 10.03.15.
 */
public interface ICategoriesInteractor {

    void deleteCategory(long id);
    void editCategory(long id);
    void loadCategories();

}
