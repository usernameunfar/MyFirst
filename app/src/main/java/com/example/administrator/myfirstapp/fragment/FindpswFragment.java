package com.example.administrator.myfirstapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.FindPSW;
import com.example.administrator.myfirstapp.model.parser.ParserUser;

import org.json.JSONObject;

public class FindpswFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "FindpswFragment";
    private EditText et_find_email;
    private Button btn_find;
    private RequestQueue requestQueue;
    private TextView tv_find_regist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findpsw, container, false);
        et_find_email = (EditText) view.findViewById(R.id.et_find_email);
        btn_find = (Button) view.findViewById(R.id.btn_find);
        tv_find_regist = (TextView) view.findViewById(R.id.tv_find_regist);
        btn_find.setOnClickListener(this);
        tv_find_regist.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(getContext());
        return view;
    }

    private void requestFind(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<FindPSW> findPSW = ParserUser.getFind(jsonObject.toString());
                        String s = findPSW.getData().getExplain();
                        int i = findPSW.getData().getResult();
                        if (i == 0) {
                            ((MainActivity) getActivity()).showToast(s);
                            ((MainActivity) getActivity()).showHomeFragment();
                        } else if (i == -1 || i == -2) {
                            ((MainActivity) getActivity()).showToast(s);
                        }
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find:
                String emailString = et_find_email.getText().toString();
                String url = API.ServerIP + "user_forgetpass?ver=" + Contacts.VER + "&email=" + emailString;
                Log.i(TAG, "onCreateView: ```````````````````````" + url);
                requestFind(url);
                break;
            case R.id.tv_find_regist:
                ((MainActivity) (getActivity())).showRegistFragment();
                break;
        }
    }
}
