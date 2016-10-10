package com.example.administrator.myfirstapp.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private ImageView iv_login;
    private RelativeLayout rel_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iv_login = (ImageView) findViewById(R.id.iv_login);
        rel_login = (RelativeLayout) findViewById(R.id.rel_login);
        AlphaAnimation alp = new AlphaAnimation(0, 1);
        AlphaAnimation alp1 = new AlphaAnimation(0.2f, 1);
        alp.setDuration(2 * 1000);
        alp1.setDuration(4 * 1000);
        iv_login.setAnimation(alp);
        rel_login.setAnimation(alp1);
        alp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            startActivity(MainActivity.class);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
