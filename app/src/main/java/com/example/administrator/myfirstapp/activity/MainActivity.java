package com.example.administrator.myfirstapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.BaseActivity;
import com.example.administrator.myfirstapp.fragment.FavoriteFragment;
import com.example.administrator.myfirstapp.fragment.FindpswFragment;
import com.example.administrator.myfirstapp.fragment.HomeFragment;
import com.example.administrator.myfirstapp.fragment.LoginFragment;
import com.example.administrator.myfirstapp.fragment.PhotoFragment;
import com.example.administrator.myfirstapp.fragment.RegistFragment;
import com.example.administrator.myfirstapp.fragment.SlidingMemuFragment;
import com.example.administrator.myfirstapp.fragment.SlidingRightFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "这是想要看的内容：";
    private RequestQueue mRequestQueue;
    private Fragment homeFragment, slidingFragment, slidingRightFragment, loginFragment, registFragment, favoriteFragment, photoFragment, findpswFragment;
    public static SlidingMenu slidingMenu;
    private ImageView iv_home_left, iv_home_right;
    private TextView tv_home_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_home_left = (ImageView) findViewById(R.id.iv_home_left);
        iv_home_right = (ImageView) findViewById(R.id.iv_home_right);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        iv_home_left.setOnClickListener(this);
        iv_home_right.setOnClickListener(this);
        loadHomeFragment();
        initSlidingMemu();

    }

    private void initSlidingMemu() {
        slidingRightFragment = new SlidingRightFragment();
        slidingFragment = new SlidingMemuFragment();
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置slidingMemu左滑
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置可滑动区域为全屏
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_offset);//slidingmemu滑出时主页面显示的宽度
        slidingMenu.setFadeDegree(0.35f);//slidingMemu滑动时的渐变程度
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使slidingMemu附加在activity上
        slidingMenu.setMenu(R.layout.layout_memu);//设置slidingMemu的布局文件
        slidingMenu.setSecondaryMenu(R.layout.layout_right);//设置右侧布局文件
        getSupportFragmentManager().beginTransaction().add(R.id.sliding_contianer, slidingFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_right_contianer, slidingRightFragment).commit();
    }

    private void loadHomeFragment() {
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_home, homeFragment).commit();
    }

    /**
     * 显示主页面的fragment
     */
    public void showHomeFragment() {
        setTitle("资讯");
        slidingMenu.showContent();
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, homeFragment).commit();
    }


    public void showLoginFragmen() {
        setTitle("用户登录");
        slidingMenu.showContent();
        if (loginFragment == null)
            loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, loginFragment).commit();
    }

    public void showRegistFragment() {
        setTitle("用户注册");
        if (registFragment == null)
            registFragment = new RegistFragment();
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, registFragment).commit();
    }

    public void showFavoriteFragment() {
        setTitle("用户收藏");
        if (favoriteFragment == null)
            favoriteFragment = new FavoriteFragment();
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, favoriteFragment).commit();
    }

    public void showPhotoFragment() {
        setTitle("图片");
        if (photoFragment == null)
            photoFragment = new PhotoFragment();
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, photoFragment).commit();
    }

    public void showFindPSWFragment() {
        setTitle("密码找回");
        if (findpswFragment == null)
            findpswFragment = new FindpswFragment();
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_home, findpswFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_left:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.iv_home_right:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showSecondaryMenu();
                }
                break;
        }
    }

    public void changeFragmentStatus() {
        ((SlidingRightFragment) slidingRightFragment).changeView();
    }

    /**
     * 设置标题栏的内容
     *
     * @param name
     */
    public void setTitle(String name) {
        tv_home_title.setText(name);
    }
}
