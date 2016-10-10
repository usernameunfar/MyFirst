package com.example.administrator.myfirstapp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.adapter.MyPagerAdapter;
import com.example.administrator.myfirstapp.base.BaseActivity;
import com.example.administrator.myfirstapp.util.ShareUtil;
import com.igexin.sdk.PushManager;

public class LeadActivity extends BaseActivity {
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private ImageView[] image = new ImageView[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        PushManager.getInstance().initialize(this.getApplicationContext());
        boolean isFirst = ShareUtil.getBoolean(LeadActivity.this, "isFirstRun", true);
        if (!isFirst) {
            startActivity(LoginActivity.class);
            finish();
            return;
        }
        init();
        initData();
    }


    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        image[0] = (ImageView) findViewById(R.id.iv_pic1);
        image[1] = (ImageView) findViewById(R.id.iv_pic2);
        image[2] = (ImageView) findViewById(R.id.iv_pic3);
        image[3] = (ImageView) findViewById(R.id.iv_pic4);
        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);

        image[0].setAlpha(1f);
        image[1].setAlpha(0.5f);
        image[2].setAlpha(0.5f);
        image[3].setAlpha(0.5f);
        viewPager.setOnPageChangeListener(listener);
    }

    private void initData() {
        ImageView imageView = null;
        imageView = (ImageView) getLayoutInflater().inflate(R.layout.layout_mypager, null);
        imageView.setBackgroundResource(R.drawable.bd);
        adapter.addToMyadapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.layout_mypager, null);
        imageView.setBackgroundResource(R.drawable.small);
        adapter.addToMyadapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.layout_mypager, null);
        imageView.setBackgroundResource(R.drawable.wy);
        adapter.addToMyadapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.layout_mypager, null);
        imageView.setBackgroundResource(R.drawable.welcome);
        adapter.addToMyadapterView(imageView);
        adapter.notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //滑动完成后
        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < image.length; i++) {
                image[i].setAlpha(0.5f);
            }
            image[position].setAlpha(1f);
            if (position == 3) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            startActivity(LoginActivity.class);
                            finish();
                            ShareUtil.putBoolean(LeadActivity.this, "isFirstRun", false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
