package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.MyBaseAdapter;
import com.example.administrator.myfirstapp.model.CommentContent;
import com.example.administrator.myfirstapp.model.ImageLoader;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class CommentListAdapter extends MyBaseAdapter<CommentContent> {


    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_comment_item, null);
            vh = new ViewHolder();
            vh.iv_comment_image = (ImageView) view.findViewById(R.id.iv_comment_image);
            vh.tv_comment_cotent = (TextView) view.findViewById(R.id.tv_comment_cotent);
            vh.tv_comment_name = (TextView) view.findViewById(R.id.tv_comment_name);
            vh.tv_comment_date = (TextView) view.findViewById(R.id.tv_comment_date);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_comment_cotent.setText(getItem(i).getContent());
        vh.tv_comment_date.setText(getItem(i).getStamp() + "");
        vh.tv_comment_name.setText(getItem(i).getUid());
        new ImageLoader(context).display(getItem(i).getPortrait(), vh.iv_comment_image);
        return view;
    }

    class ViewHolder {
        private ImageView iv_comment_image;
        private TextView tv_comment_name, tv_comment_date, tv_comment_cotent;
    }
}
