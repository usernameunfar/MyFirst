package com.example.administrator.myfirstapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.activity.NewsShowActivity;
import com.example.administrator.myfirstapp.adapter.HomeListViewAdapter;
import com.example.administrator.myfirstapp.adapter.NewTypeAdapter;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.model.SubType;
import com.example.administrator.myfirstapp.model.parser.ParserNews;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.DBTools;
import com.example.administrator.myfirstapp.view.HorizontalListView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RequestQueue requestQueue;
    private NewTypeAdapter newTypeAdapter;
    private HomeListViewAdapter homeAdapter;
    private DBTools dbTools;
    private Unbinder mUnbinder;
    @BindView(R.id.listView_homecontent)
    ListView listView_homecontent;
    @BindView(R.id.horizontalView)
    HorizontalListView horizontalListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder=  ButterKnife.bind(this, view);
        newTypeAdapter = new NewTypeAdapter(getContext());
        homeAdapter = new HomeListViewAdapter(getContext());
        dbTools = new DBTools(getContext());
        horizontalListView.setAdapter(newTypeAdapter);
        listView_homecontent.setAdapter(homeAdapter);
        requestQueue = Volley.newRequestQueue(getContext());
//        String url = API.NEWS_SORT + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(getContext());
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=864394101849794";
        String urlHome = API.NEWS_LIST;
        loadTitleData(url);
        loadHomeData(urlHome);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void loadTitleData(String url) {
        if (CommonUtil.isNetworkAvailable(getActivity())) {
            sendRequesData(url);
        } else {
            List<SubType> list = dbTools.getLocalSubType();
            if (list != null & list.size() > 0) {
                newTypeAdapter.appendDataed(list, true);
                newTypeAdapter.updateAdapter();
            } else {
                ((MainActivity) getActivity()).showToast("请检查您的网络！");
            }
        }
    }

    private void loadHomeData(String urlHome) {
        if (CommonUtil.isNetworkAvailable(getActivity())) {
            sendRequesHomeData(urlHome);
        } else {
            List<HomeNews> list = dbTools.getLocalHomeNews();
            if (list != null & list.size() > 0) {
                homeAdapter.appendDataed(list, true);
                homeAdapter.updateAdapter();
            } else {
                ((MainActivity) getActivity()).showToast("请检查您的网络！");
            }
        }

    }

    private void sendRequesData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG, "onResponse: `````````````````````" + jsonObject);
                        List<SubType> list = ParserNews.getNewsType(jsonObject.toString());
                        for (int i = 0; i < list.size(); i++) {
                            dbTools.saveLocalSubType(list.get(i));
                        }
                        Log.i(TAG, "onResponse: --------------" + list.size());
                        newTypeAdapter.appendDataed(list, true);
                        newTypeAdapter.updateAdapter();
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

    private void sendRequesHomeData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<HomeNews> list = ParserNews.getHomeNews(jsonObject.toString());
                        for (int i = 0; i < list.size(); i++) {
                            dbTools.saveLocalHomeNews(list.get(i));
                        }
                        Log.i(TAG, "onResponse: --------------" + list);
                        homeAdapter.appendDataed(list, true);
                        homeAdapter.updateAdapter();
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


    @OnItemClick(R.id.listView_homecontent)
    void clickIt(int position) {
        Bundle bundle = new Bundle();
        HomeNews homeNews = homeAdapter.getItem(position);
        bundle.putSerializable("homeNews", homeNews);
        ((MainActivity) getActivity()).startActivity(NewsShowActivity.class, bundle, null);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
       mUnbinder.unbind();
    }

}
