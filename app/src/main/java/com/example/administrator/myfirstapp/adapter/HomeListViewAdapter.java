package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.MyBaseAdapter;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 16-9-7.
 */
public class HomeListViewAdapter extends MyBaseAdapter<HomeNews> {


    public HomeListViewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_homecontent, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_content_title.setText(getItem(i).getTitle());
        vh.tv_content_summary.setText(getItem(i).getSummary());
        vh.tv_content_stamp.setText(getItem(i).getStamp());
        Picasso.with(context).load(getItem(i).getIcon()).fit()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(vh.iv_homecontent);
//        new ImageLoader(context).display(getItem(i).getIcon(), vh.iv_homecontent);
        return view;
    }

    class ViewHolder {
        @BindView(R.id.iv_homecontent)
        ImageView iv_homecontent;
        @BindView(R.id.tv_content_title)
        TextView tv_content_title;
        @BindView(R.id.tv_content_summary)
        TextView tv_content_summary;
        @BindView(R.id.tv_content_stamp)
        TextView tv_content_stamp;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
