package com.dfire.danggui.testapp.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dfire.danggui.testapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/3/13.
 */

public class TestMapActivity extends AppCompatActivity {
    @Bind(R.id.image)
    ImageView mImage;

    private static final String IMAGE_HTTPS = "https";
    private static final String IMAGE_HTTP = "http";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_glide);
        ButterKnife.bind(this);
        //https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX
        //http://wx.qlogo.cn/mmopen/DcXCHdWgDnukXGxohSwEN59CgU1LU0ayxoP3Qn9uibj707icZQcjobFlhsq3hic7S5eRT81CdFib0weoz1DBmqPhpwgELwAWVcKX/0
        Glide.with(this).load("http://wx.qlogo.cn/mmopen/DcXCHdWgDnukXGxohSwEN59CgU1LU0ayxoP3Qn9uibj707icZQcjobFlhsq3hic7S5eRT81CdFib0weoz1DBmqPhpwgELwAWVcKX/0")
                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImage);
        String aliUrl = "https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX";

//        loadImage(aliUrl, mAliImage);
//        Glide.with(this).load("https://tfs.alipayobjects.com/images/partner/T1gE4yXf0XXXXXXXXX")
//                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mAliImage);

    }
}
