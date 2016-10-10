package com.example.administrator.myfirstapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.adapter.CommentListAdapter;
import com.example.administrator.myfirstapp.base.BaseActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.Comit;
import com.example.administrator.myfirstapp.model.CommentContent;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.model.parser.ParserNews;
import com.example.administrator.myfirstapp.model.parser.ParserUser;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.ShareUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class CommentActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CommentActivity";
    private CommentListAdapter commentListAdapter;
    private ListView lv_comment_listview;
    private RequestQueue requestQueue;
    private HomeNews homeNews;
    private Button btn_comment;
    private EditText et_comment;
    private ImageView iv_comment_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
    }

    private void init() {
        Bundle bu = getIntent().getExtras();
        homeNews = (HomeNews) bu.getSerializable("homeNews");
        lv_comment_listview = (ListView) findViewById(R.id.lv_comment_listview);
        btn_comment = (Button) findViewById(R.id.btn_comment);
        et_comment = (EditText) findViewById(R.id.et_comment);
        iv_comment_left = (ImageView) findViewById(R.id.iv_comment_left);
        requestQueue = Volley.newRequestQueue(this);
        commentListAdapter = new CommentListAdapter(this);
        lv_comment_listview.setAdapter(commentListAdapter);
        btn_comment.setOnClickListener(this);
        iv_comment_left.setOnClickListener(this);

        String url = API.USER_COMMENT_LIST + "ver=" + Contacts.VER + "&nid=" + homeNews.getNid() + "&type=1&stamp=20150505" + "&cid=20&dir=1&cnt=20 ";

        Log.i(TAG, "init: `````````````````" + url);
        sendRequesComment(url);
    }

    private void sendRequesComment(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<CommentContent> list = ParserNews.getCommentContent(jsonObject.toString());
                        Log.i(TAG, "onResponse: `````````````comment" + list);
                        commentListAdapter.appendDataed(list, true);
                        commentListAdapter.updateAdapter();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment:
                String comment = et_comment.getText().toString();
                String token = ShareUtil.getTokey(this, "token");
                try {
                    String ss = URLEncoder.encode(comment, "utf-8");
                    String url1 = API.USER_COMMENT_CONTENT + "ver=" + Contacts.VER + "&nid=" + homeNews.getNid() + "&token=" + token + "&imei=" + CommonUtil.getIMEI(this) + "&ctx=" + ss;
                    Log.i(TAG, "init: `````````````````" + url1);
                    sendRequesContent(url1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_comment_left:
                finish();
                break;
        }
    }

    private void sendRequesContent(String url1) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Comit> ba = ParserUser.getComit(jsonObject.toString());
                        if (ba.getStatus() == -3) {
                            showToast("请登录！");
                        }
                        if (ba.getStatus() == 0) {
                            Comit c = ba.getData();
                            if (c.getResult() == 0) {
                                showToast("评论发表成功");
                                et_comment.setText(null);
                                init();
                            } else if (c.getResult() == -1) {
                                showToast("非法字符");
                            }
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
}
