package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.model.ImageLoader;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PhotoAdapter extends HomeListViewAdapter {

    public PhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_photo_item, null);
            vh = new ViewHolder();
            vh.iv_photo_item = (ImageView) view.findViewById(R.id.iv_photo_item);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        new ImageLoader(context).display(getItem(i).getIcon(), vh.iv_photo_item);
        return view;
    }

    class ViewHolder {
        private ImageView iv_photo_item;
    }
}
