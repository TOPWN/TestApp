package com.dfire.danggui.testapp.glide;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dfire.danggui.testapp.R;
import com.orhanobut.logger.Logger;

import javax.net.ssl.SSLHandshakeException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/3/13.
 */

public class TestGlideActivity extends AppCompatActivity {
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.aliimage)
    ImageView mAliImage;

    private static final String IMAGE_HTTPS = "https";
    private static final String IMAGE_HTTP = "http";

    private CustomDialogFragment customDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        setContentView(R.layout.activity_test_glide);
        ButterKnife.bind(this);
        //https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX
        //http://wx.qlogo.cn/mmopen/DcXCHdWgDnukXGxohSwEN59CgU1LU0ayxoP3Qn9uibj707icZQcjobFlhsq3hic7S5eRT81CdFib0weoz1DBmqPhpwgELwAWVcKX/0
        Glide.with(this).load("http://wx.qlogo.cn/mmopen/DcXCHdWgDnukXGxohSwEN59CgU1LU0ayxoP3Qn9uibj707icZQcjobFlhsq3hic7S5eRT81CdFib0weoz1DBmqPhpwgELwAWVcKX/0")
                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImage);
        String aliUrl = "https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX";

        loadImage(aliUrl, mAliImage);
//        Glide.with(this).load("https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX")
//                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mAliImage);

        mAliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == customDialogFragment) {
                    customDialogFragment = new CustomDialogFragment();
                }
//                customDialogFragment.show(getSupportFragmentManager(), "tag");
                showDialogFragment(customDialogFragment);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("onSaveInstanceState");
        mAliImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null == customDialogFragment) {
                    customDialogFragment = new CustomDialogFragment();
                }
//                Logger.d("isFinishing-->" + isFinishing());
                if(!getSupportFragmentManager().isDestroyed()){
                    customDialogFragment.show(getSupportFragmentManager(), "tag");
                }
//                showDialogFragment(customDialogFragment);
            }
        }, 2000);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d("onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Logger.d("onSaveInstanceState PersistableBundle");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.d("onConfigurationChanged");
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        Logger.d("showDialogFragment---"+isFinishing());
        if(!getSupportFragmentManager().isDestroyed()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(dialogFragment, this.getClass().getSimpleName());
            ft.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
        }
    }

    public void loadImage(final String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        Logger.e("onLoadFailed---" + e.toString());
                        if (e instanceof SSLHandshakeException) {
                            String httpUrl = url.replaceFirst(IMAGE_HTTPS, IMAGE_HTTP);
//                            loadImage(httpUrl, imageView);
                        } else {
                            super.onLoadFailed(e, errorDrawable);
                        }
//                        if (!TextUtils.isEmpty(url) && url.contains(IMAGE_HTTPS)) {
//                            String httpUrl = url.replaceFirst(IMAGE_HTTPS, IMAGE_HTTP);
//                            loadImage(httpUrl, imageView);
//                        } else {
//                            super.onLoadFailed(e, errorDrawable);
//                        }
                    }
                });
    }
}
