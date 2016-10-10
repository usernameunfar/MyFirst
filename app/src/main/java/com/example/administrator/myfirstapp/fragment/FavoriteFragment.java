package com.example.administrator.myfirstapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.activity.NewsShowActivity;
import com.example.administrator.myfirstapp.adapter.HomeListViewAdapter;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.util.DBTools;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private ListView iv_favorite;
    private HomeListViewAdapter adapter;
    private DBTools dbTools;
    private RelativeLayout rel_favorite;
    private TextView tv_favorite_now;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        iv_favorite = (ListView) view.findViewById(R.id.iv_favorite);
        iv_favorite.setOnItemClickListener(onItemClickListener);
        rel_favorite = (RelativeLayout) view.findViewById(R.id.rel_favorite);
        tv_favorite_now = (TextView) view.findViewById(R.id.tv_favorite_now);
        adapter = new HomeListViewAdapter(getActivity());
        iv_favorite.setAdapter(adapter);
        dbTools = new DBTools(getContext());
        getData();
        return view;
    }


    public void getData() {
        List<HomeNews> list = dbTools.getLocalFavorite();
        if (list.size() <= 0) {
            ((MainActivity) getActivity()).showToast("抱歉！您还没有任何收藏!");
            iv_favorite.setVisibility(View.GONE);
            rel_favorite.setVisibility(View.VISIBLE);
        } else {
            iv_favorite.setVisibility(View.VISIBLE);
            rel_favorite.setVisibility(View.GONE);
            adapter.appendDataed(list, true);
            adapter.updateAdapter();
        }

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            HomeNews homeNews = adapter.getItem(position);
            bundle.putSerializable("homeNews", homeNews);
            ((MainActivity) getActivity()).startActivity(NewsShowActivity.class, bundle, null);
        }
    };
}
