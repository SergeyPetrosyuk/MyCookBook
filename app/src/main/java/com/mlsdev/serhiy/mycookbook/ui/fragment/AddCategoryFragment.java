package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.presenter.AddCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddCategoryView;
import com.mlsdev.serhiy.mycookbook.ui.activity.CategoryActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

/**
 * Created by android on 10.03.15.
 */
public class AddCategoryFragment extends Fragment implements IAddCategoryView, View.OnClickListener {

    private IAddCategoryPresenter presenter;

    private EditText mCategoryNameEditText;
    private Button mAddCategoryButton;
    private ProgressBar mProgressBar;
    private RelativeLayout mContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddCategoryPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        mCategoryNameEditText = (EditText) view.findViewById(R.id.et_category_title);
        mAddCategoryButton = (Button) view.findViewById(R.id.btn_add_category);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_add_category);
        mContainer = (RelativeLayout) view.findViewById(R.id.rl_add_category_container);

        mAddCategoryButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void addCategory() {
        String categoryName = mCategoryNameEditText.getText().toString();
        presenter.addCategory(categoryName);
    }

    @Override
    public void openCreatedCategory(int categoryId, String categoryName) {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(DBContract.RecipeEntry.COLUMN_CATEGORY_ID, categoryId);
        intent.putExtra(DBContract.CategoryEntry.COLUMN_NAME, categoryName);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showError(String errorText) {
        Toast.makeText(getActivity(), errorText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        mContainer.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void backToCategoriesList() {
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_category){
            addCategory();
        }
    }
}
