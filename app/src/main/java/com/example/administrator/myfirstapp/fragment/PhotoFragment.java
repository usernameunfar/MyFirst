package com.example.administrator.myfirstapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.adapter.PhotoAdapter;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.util.DBTools;

import java.util.List;


public class PhotoFragment extends Fragment {
    private PhotoAdapter adapter;
    private GridView gv_photo;
    private DBTools dbTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        gv_photo = (GridView) v.findViewById(R.id.gv_photo);
        adapter = new PhotoAdapter(getContext());
        gv_photo.setAdapter(adapter);
        dbTools = new DBTools(getContext());
        initData();
        return v;
    }

    private void initData() {
        List<HomeNews> list = dbTools.getLocalHomeNews();
        adapter.appendDataed(list, true);
        adapter.updateAdapter();
    }


}
