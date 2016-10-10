package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.MyBaseAdapter;
import com.example.administrator.myfirstapp.model.SubType;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class NewTypeAdapter extends MyBaseAdapter<SubType> {

    public NewTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_newtype_item, null);
            vh = new ViewHolder();
            vh.tv_news_item = (TextView) view.findViewById(R.id.tv_news_item);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_news_item.setText(getItem(i).getSubgroup());
        return view;
}

    public class ViewHolder {
        TextView tv_news_item;
    }
}
