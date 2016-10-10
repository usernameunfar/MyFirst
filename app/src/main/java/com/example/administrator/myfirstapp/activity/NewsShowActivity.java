package com.example.administrator.myfirstapp.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.adapter.NewsCommentNumber;
import com.example.administrator.myfirstapp.base.BaseActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.CommentNumber;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.model.parser.ParserNews;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.DBTools;

import org.json.JSONObject;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsShowActivity extends BaseActivity {
    private static final String TAG = "NewsShowActivity";
    private WebView webView;
    private ProgressBar progressBar;
    private PopupWindow popupWindow;
    private ImageView imageView, iv_show_left;
    private TextView tv_favorite, tv_show_comment, tv_share;
    private DBTools dbTools;
    private HomeNews homeNews;
    private NewsCommentNumber newsCommentNumber;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        ShareSDK.initSDK(this, "1735f1addd857");

        if (!CommonUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.no_network);
        } else {
            setContentView(R.layout.activity_news_show);
            init();
        }
    }

    private void init() {

        tv_show_comment = (TextView) findViewById(R.id.tv_show_comment);
        webView = (WebView) findViewById(R.id.myWebView);
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        imageView = (ImageView) findViewById(R.id.iv_show_right);
        iv_show_left = (ImageView) findViewById(R.id.iv_show_left);
        imageView.setOnClickListener(onClickListener);
        iv_show_left.setOnClickListener(onClickListener);
        tv_show_comment.setOnClickListener(onClickListener);

        dbTools = new DBTools(this);
        Bundle bundle = getIntent().getExtras();
        homeNews = (HomeNews) bundle.getSerializable("homeNews");

        newsCommentNumber = new NewsCommentNumber(this);
        String url = API.USER_COMMENT + "ver=" + Contacts.VER + "&nid=" + homeNews.getNid();
        Log.i(TAG, "onCreate: `````````````````" + url);
        sendRequesNumber(url);

        // User settings

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放


        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebChromeClient client = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };
        webView.setWebChromeClient(client);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.loadUrl(homeNews.getLink());
        showPopupWindow();
    }


    private void sendRequesNumber(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        CommentNumber co = ParserNews.getCommentNumber(jsonObject.toString());
                        Log.i(TAG, "onResponse: ````````````" + co);
                        if (co.getData() <= 0) {
                            tv_show_comment.setVisibility(View.INVISIBLE);
                        } else {
                            tv_show_comment.setVisibility(View.VISIBLE);
                            tv_show_comment.setText("跟帖: " + co.getData());
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

    public void showPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_favorite, null);
        tv_favorite = (TextView) view.findViewById(R.id.tv_favorite);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                boolean isSave = dbTools.saveLoadFavorite(homeNews);
                if (isSave) {
                    showToast("收藏成功");
                } else {
                    showToast("已在收藏列表");
                }
                List<HomeNews> homeNews1 = dbTools.getLocalFavorite();
                Log.i(TAG, "onClick: ````````````````" + homeNews1);
            }
        });
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showShare();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_show_right:
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else if (popupWindow != null) {
                        popupWindow.showAsDropDown(imageView, 0, 2);
                    }
                    break;
                case R.id.iv_show_left:
                    popupWindow.dismiss();
                    finish();
                    break;
                case R.id.tv_show_comment:
                    Bundle bu = new Bundle();
                    bu.putSerializable("homeNews", homeNews);
                    startActivity(CommentActivity.class, bu, null);
                    break;

            }

        }


    };

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(homeNews.getTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(homeNews.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(homeNews.getSummary());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(homeNews.getIcon());//网络路径
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(homeNews.getLink());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(homeNews.getLink());

        // 启动分享GUI
        oks.show(this);
    }
}
