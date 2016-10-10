package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.base.MyBaseAdapter;
import com.example.administrator.myfirstapp.model.Loginlog;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class LoginAdapter extends MyBaseAdapter<Loginlog> {

    public LoginAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_login_item, null);
            vh = new ViewHolder();
            vh.tv_login_address = (TextView) view.findViewById(R.id.tv_login_address);
            vh.tv_login_time = (TextView) view.findViewById(R.id.tv_login_time);
            vh.tv_login_device = (TextView) view.findViewById(R.id.tv_login_device);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_login_address.setText(getItem(i).getAddress());
        vh.tv_login_time.setText(getItem(i).getTime());
        vh.tv_login_device.setText(getItem(i).getDevice() + "");
        return view;
    }

    public class ViewHolder {

        TextView tv_login_time, tv_login_address, tv_login_device;
    }
}
