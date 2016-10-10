package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.MyBaseAdapter;
import com.example.administrator.myfirstapp.model.CommentNumber;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class NewsCommentNumber extends MyBaseAdapter<CommentNumber> {

    public NewsCommentNumber(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.activity_news_show, null);
            vh = new ViewHolder();
            vh.tv_show_comment = (TextView) view.findViewById(R.id.tv_show_comment);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_show_comment.setText("跟帖: " + getItem(i).getData());

        return view;
    }


    private class ViewHolder {
        TextView tv_show_comment;
    }
}
