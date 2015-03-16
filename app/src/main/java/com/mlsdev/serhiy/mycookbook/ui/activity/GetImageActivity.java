package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

public class GetImageActivity extends BaseActivity implements View.OnClickListener {

    private Button mMakePhotoButton;
    private Button mChooseInGalleryButton;
    private Button mCancel;
    private RelativeLayout mContainer;
    private RelativeLayout mButtonsHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mMakePhotoButton = (Button) findViewById(R.id.btn_make_photo);
        mChooseInGalleryButton = (Button) findViewById(R.id.btn_choose_in_gallery);
        mCancel = (Button) findViewById(R.id.btn_take_photo_cancel);
        mContainer = (RelativeLayout) findViewById(R.id.rl_container);
        mButtonsHolder = (RelativeLayout) findViewById(R.id.rl_btns_holder);

        mMakePhotoButton.setOnClickListener(this);
        mChooseInGalleryButton.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mContainer.setOnClickListener(this);
        mButtonsHolder.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_get_image;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_make_photo:
                makePhoto();
                break;
            case R.id.btn_choose_in_gallery:
                takeImageFromGallery();
                break;
            case R.id.btn_take_photo_cancel:
                finish();
                break;
            case R.id.rl_container:
                finish();
                break;
            case R.id.rl_btns_holder:
                break;
            default:
                break;
        }
    }

    private void makePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    private void takeImageFromGallery(){
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setResult(RESULT_OK, data);
        finish();
    }

}
