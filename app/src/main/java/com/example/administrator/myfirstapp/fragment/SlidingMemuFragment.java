package com.example.administrator.myfirstapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.BaiduActivity;
import com.example.administrator.myfirstapp.activity.MainActivity;

public class SlidingMemuFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout relayout_news, relayout_favorite, relayout_local, relayout_comment, relayout_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_memu, container, false);
        relayout_news = (RelativeLayout) view.findViewById(R.id.relayout_news);
        relayout_favorite = (RelativeLayout) view.findViewById(R.id.relayout_favorite);
        relayout_local = (RelativeLayout) view.findViewById(R.id.relayout_local);
        relayout_comment = (RelativeLayout) view.findViewById(R.id.relayout_comment);
        relayout_image = (RelativeLayout) view.findViewById(R.id.relayout_image);
        relayout_news.setOnClickListener(this);
        relayout_favorite.setOnClickListener(this);
        relayout_local.setOnClickListener(this);
        relayout_comment.setOnClickListener(this);
        relayout_image.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relayout_news:
                ((MainActivity) getActivity()).showHomeFragment();
                relayout_news.setBackgroundColor(0x33C85555);
                relayout_favorite.setBackgroundColor(0x00ffffff);
                relayout_local.setBackgroundColor(0x00ffffff);
                relayout_comment.setBackgroundColor(0x00ffffff);
                relayout_image.setBackgroundColor(0x00ffffff);
                break;
            case R.id.relayout_favorite:
                ((MainActivity) getActivity()).showFavoriteFragment();
                relayout_news.setBackgroundColor(0x00ffffff);
                relayout_favorite.setBackgroundColor(0x33C85555);
                relayout_local.setBackgroundColor(0x00ffffff);
                relayout_comment.setBackgroundColor(0x00ffffff);
                relayout_image.setBackgroundColor(0x00ffffff);
                break;
            case R.id.relayout_local:
                relayout_news.setBackgroundColor(0x00ffffff);
                relayout_favorite.setBackgroundColor(0x00ffffff);
                relayout_local.setBackgroundColor(0x33C85555);
                relayout_comment.setBackgroundColor(0x00ffffff);
                relayout_image.setBackgroundColor(0x00ffffff);
                ((MainActivity) getActivity()).startActivity(BaiduActivity.class);
                break;
            case R.id.relayout_comment:
                relayout_news.setBackgroundColor(0x00ffffff);
                relayout_favorite.setBackgroundColor(0x00ffffff);
                relayout_local.setBackgroundColor(0x00ffffff);
                relayout_comment.setBackgroundColor(0x33C85555);
                relayout_image.setBackgroundColor(0x00ffffff);
                break;
            case R.id.relayout_image:
                ((MainActivity) getActivity()).showPhotoFragment();
                relayout_news.setBackgroundColor(0x00ffffff);
                relayout_favorite.setBackgroundColor(0x00ffffff);
                relayout_local.setBackgroundColor(0x00ffffff);
                relayout_comment.setBackgroundColor(0x00ffffff);
                relayout_image.setBackgroundColor(0x33C85555);
                break;
        }
    }
}
