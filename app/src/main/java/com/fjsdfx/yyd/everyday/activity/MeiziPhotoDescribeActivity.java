package com.fjsdfx.yyd.everyday.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fjsdfx.yyd.everyday.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MeiziPhotoDescribeActivity extends AppCompatActivity {

    String mImageUrl;
    PhotoViewAttacher mPhotoViewAttacher;
    private boolean mIsHidden = false;

    private ImageView mShot;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_photo_describe);
        mImageUrl = getIntent().getStringExtra("image");
        initalView();
        getData();
        setupPhotoAttacher();
        mToolbar.setAlpha(0.7f);
    }

    private void initalView(){
        mShot =(ImageView)findViewById(R.id.shot);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("每日看看");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.background_light));
        setSupportActionBar(mToolbar);
    }

    private void getData() {
        Glide.with(this)
                .load(mImageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mShot);

        mPhotoViewAttacher = new PhotoViewAttacher(mShot);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setupPhotoAttacher() {
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });

        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(MeiziPhotoDescribeActivity.this)
                        .setMessage(getString(R.string.save_meizi))
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {

                                anInterface.dismiss();
                                saveImage();
                            }
                        }).show();
                return true;
            }
        });

    }

    private void saveImage() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File directory = new File(externalStorageDirectory,"LookLook");
        if (!directory.exists())
            directory.mkdir();
        Bitmap drawingCache = mPhotoViewAttacher.getImageView().getDrawingCache();
        try {
            File file = new File(directory, new Date().getTime() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.JPEG,100,fos);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getApplicationContext().sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Snackbar.make(getCurrentFocus(),"阿偶出错了呢",Snackbar.LENGTH_SHORT).show();
        }
    }

    protected void hideOrShowToolbar() {
        mToolbar.animate()
                .translationY(mIsHidden ? 0 : -mToolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }

}
