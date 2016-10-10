package com.example.administrator.myfirstapp.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.activity.UserActivity;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.ImageLoader;
import com.example.administrator.myfirstapp.model.Update;
import com.example.administrator.myfirstapp.model.parser.ParserUser;
import com.example.administrator.myfirstapp.util.ShareUtil;
import com.example.administrator.myfirstapp.util.UpdateManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class SlidingRightFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SlidingRightFragment";
    private ImageView iv_loginImageView, iv_loginedImageView, iv_qq;
    private TextView tv_loginTextView, tv_loginedTextView, tv_update;
    private RelativeLayout layout_unlogin, layout_logined;
    private boolean isLogin;
    private UpdateManager mUpdateManager;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_right, container, false);
        iv_loginImageView = (ImageView) view.findViewById(R.id.iv_unloginImageView);
        tv_loginTextView = (TextView) view.findViewById(R.id.tv_unloginTextView);
        layout_unlogin = (RelativeLayout) view.findViewById(R.id.layout_unlogin);
        layout_logined = (RelativeLayout) view.findViewById(R.id.layout_logined);
        tv_loginedTextView = (TextView) view.findViewById(R.id.tv_loginedTextView);
        iv_loginedImageView = (ImageView) view.findViewById(R.id.iv_loginedImageView);
        iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
        tv_update = (TextView) view.findViewById(R.id.tv_update);
        requestQueue = Volley.newRequestQueue(getContext());

        iv_loginImageView.setOnClickListener(this);
        tv_loginTextView.setOnClickListener(this);
        iv_loginedImageView.setOnClickListener(this);
        tv_loginedTextView.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        layout_unlogin.setVisibility(View.VISIBLE);
        layout_logined.setVisibility(View.GONE);
        changeView();
        return view;
    }

    public void changeView() {
        isLogin = ShareUtil.getIsLogined(getActivity(), "isLogin", false);
        if (ShareUtil.getIsOther(getContext(), "isOther", false)) {
            layout_unlogin.setVisibility(View.GONE);
            layout_logined.setVisibility(View.VISIBLE);
            tv_loginedTextView.setText(ShareUtil.getString(getActivity(), "otherName"));
            new ImageLoader(getContext()).display(ShareUtil.getString(getContext(), "otherIcon"), iv_loginedImageView);
        } else {
            if (!isLogin) {
                layout_unlogin.setVisibility(View.VISIBLE);
                layout_logined.setVisibility(View.GONE);
            } else {
                layout_unlogin.setVisibility(View.GONE);
                layout_logined.setVisibility(View.VISIBLE);
                tv_loginedTextView.setText(ShareUtil.getString(getActivity(), "name"));
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_unloginImageView || v.getId() == R.id.tv_unloginTextView) {
            ((MainActivity) getActivity()).showLoginFragmen();
        }
        if (v.getId() == R.id.iv_loginedImageView || v.getId() == R.id.tv_loginedTextView) {
            startActivity(new Intent(getActivity(), UserActivity.class));
        }
        if (v.getId() == R.id.tv_update) {
            try {
                sendRequesData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.iv_qq) {
            initAuthour();
        }
    }

    private void initAuthour() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(paListener);
        qq.authorize();//单独授权
        qq.showUser(null);//授权并获取用户信息
        //authorize与showUser单独调用一个即可
        //移除授权
        //qq.removeAccount(true);
    }

    public PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //用户资源都保存到res
            //通过打印res数据看看有哪些数据是你想要的
//            if (i == Platform.ACTION_USER_INFOR) {

            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            //通过DB获取各种数据
//                String token = platDB.getToken();
//                String gender = platDB.getUserGender();
            String icon = platDB.getUserIcon();
//                String id = platDB.getUserId();
            String name = platDB.getUserName();
            ShareUtil.putString(getContext(), "otherName", name);
            ShareUtil.putString(getContext(), "otherIcon", icon);
            ShareUtil.putBooleanOther(getContext(), "isOther", true);
            startActivity(new Intent(getActivity(), UserActivity.class));

//            }
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    };


    private void updateApk(String url) {
        UpdateManager updateManager = new UpdateManager(getContext(), url);
        updateManager.checkUpdateInfo();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                BaseEntry<Update> baseEntity = ParserUser.
                        getUpdate(msg.obj.toString());
                if (baseEntity.getStatus() == 0) {
                    Update update = baseEntity.getData();
                    if (Contacts.VER == update.getVersion()) {
                        ((MainActivity) getActivity()).showToast("版本已经是最新的了！");
                    } else {
                        String updateUrl = update.getLink();
                        updateApk(updateUrl);
                    }
                } else {
                    ((MainActivity) getActivity()).showToast("获取更新信息失败，请检查网络连接");
                }
            }

        }
    };

    private String getVersionName() throws Exception {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getContext().getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getContext().getPackageName(),
                0);
        return packInfo.versionName;
    }

    private void sendRequesData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.3.80:8080/test/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = stringBuffer.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
