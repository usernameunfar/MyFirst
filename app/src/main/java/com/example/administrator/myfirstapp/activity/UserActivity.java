package com.example.administrator.myfirstapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.adapter.LoginAdapter;
import com.example.administrator.myfirstapp.base.BaseActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.ImageLoader;
import com.example.administrator.myfirstapp.model.User;
import com.example.administrator.myfirstapp.model.parser.ParserUser;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.ShareUtil;

import org.json.JSONObject;

public class UserActivity extends BaseActivity {
    private static final String TAG = "UserActivity";
    private RequestQueue requestQueue;
    private TextView tv_user_loginName, tv_user_integral, tv_user_number;
    private LoginAdapter adapter;
    private ListView listView;
    private ImageView iv_user_left, iv_user_head;
    private Button btn_user_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        requestQueue = Volley.newRequestQueue(this);

        tv_user_loginName = (TextView) findViewById(R.id.tv_user_loginName);
        tv_user_integral = (TextView) findViewById(R.id.tv_user_integral);
        tv_user_number = (TextView) findViewById(R.id.tv_user_number);
        iv_user_left = (ImageView) findViewById(R.id.iv_user_left);
        btn_user_exit = (Button) findViewById(R.id.btn_user_exit);
        iv_user_head = (ImageView) findViewById(R.id.iv_user_head);

        iv_user_left.setOnClickListener(onClickListener);
        btn_user_exit.setOnClickListener(onClickListener);
        listView = (ListView) findViewById(R.id.lv_user_listView);
        adapter = new LoginAdapter(this);
        listView.setAdapter(adapter);
        if (ShareUtil.getIsOther(this, "isOther", false)) {
            tv_user_loginName.setText(ShareUtil.getString(this, "otherName"));
            new ImageLoader(this).display(ShareUtil.getString(this, "otherIcon"), iv_user_head);
        } else {
            String token = ShareUtil.getTokey(this, "token");
            String url = API.USER_CENTER_DATA + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(this) + "&token=" + token;
            Log.i(TAG, "onCreate: `````````````````````````" + url);
            requestUserData(url);
        }

    }

    public void requestUserData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<User> userBaseEntry = ParserUser.getLoginInfo(jsonObject.toString());
                        int status = userBaseEntry.getStatus();
                        if (status != 0) {
                            showToast("用户数据请求错误");
                        } else {
                            User user = userBaseEntry.getData();
                            tv_user_loginName.setText(user.getUid());
                            tv_user_integral.setText("用户积分:  " + user.getIntegration());
                            tv_user_number.setText(user.getComnum() + "");
                            adapter.appendDataed(user.getLoginlog(), true);
                            adapter.updateAdapter();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_user_left:
                    startActivity(MainActivity.class);
                    break;
                case R.id.btn_user_exit:
                    dialog_exit();
                    break;
            }

        }
    };

    private void dialog_exit() {
        DialogInterface.OnClickListener dialogOnclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        Toast.makeText(UserActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        startActivity(MainActivity.class);
                        ShareUtil.clearData(UserActivity.this);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(UserActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //设置dialog的参数
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//得到构造器
        builder.setTitle("提示信息");
        builder.setMessage("是否确认退出?");
        builder.setIcon(R.drawable.a4);
        builder.setPositiveButton("确认", dialogOnclick);
        builder.setNegativeButton("取消", dialogOnclick);
        builder.create().show();
    }

}
