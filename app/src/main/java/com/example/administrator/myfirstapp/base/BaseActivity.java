package com.example.administrator.myfirstapp.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myfirstapp.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class BaseActivity extends FragmentActivity {

    private static int screenW, screenH;
    private static Toast toast;
    private Dialog dialog;//界面弹出框

    /**
     * 当前集合用来装所有的activity
     */
    private static ArrayList<BaseActivity> onlineActiArraylist = new ArrayList<BaseActivity>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenH = getWindowManager().getDefaultDisplay().getHeight();
        screenW = getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 点击，弹框
     */
    public void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 一键销毁所有的activity
     */
    public void finishAllActivity() {
        Iterator<BaseActivity> iterator = onlineActiArraylist.iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
        }
    }

//    protected void initActionBar(String title, int leftId, int rightId, View.OnClickListener clickListener){
//
//    }


    public void startActivity(Class<?> targetClass) {
        startActivity(targetClass, null, null);
    }

    public void startActivity(Class<?> targetClass, Bundle bundle, Uri uri) {
        Intent intent = new Intent(this, targetClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_tight_in, R.anim.anim_activity_bottom_out);
    }

    public void showLoadingDialog(Context context, String msg, boolean cancle) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.llt_loading);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_loading_dialog);
        TextView tv = (TextView) view.findViewById(R.id.tv_loading_dialog);
        Animation roatAnim = AnimationUtils.loadAnimation(context, R.anim.loading_rotate);//设置一个动画
        iv.setAnimation(roatAnim);
        if (null != msg) {
            tv.setText(msg);
        }
        dialog = new Dialog(context, R.style.loading_dialog);//设置dialog的样式
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(cancle);
        dialog.show();

    }
    /**
     * 取消显示dialog
     */
    public void cancleDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }


}
