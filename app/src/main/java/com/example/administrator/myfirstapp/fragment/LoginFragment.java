package com.example.administrator.myfirstapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.activity.UserActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.Register;
import com.example.administrator.myfirstapp.model.parser.ParserUser;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.ShareUtil;

import org.json.JSONObject;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btn_login_regist, btn_login_login;
    private static final String TAG = "LoginFragment";
    private EditText et_loginName, et_loginPsw;
    private RequestQueue requestQueue;
    private TextView tv_login_psw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login_regist = (Button) view.findViewById(R.id.btn_login_regist);
        btn_login_login = (Button) view.findViewById(R.id.btn_login_login);
        et_loginName = (EditText) view.findViewById(R.id.et_loginName);
        et_loginPsw = (EditText) view.findViewById(R.id.et_loginPsw);
        tv_login_psw = (TextView) view.findViewById(R.id.tv_login_psw);
        requestQueue = Volley.newRequestQueue(getContext());
        btn_login_regist.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        et_loginName.setOnClickListener(this);
        et_loginPsw.setOnClickListener(this);
        tv_login_psw.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_regist:
                ((MainActivity) getActivity()).showRegistFragment();
                break;
            case R.id.btn_login_login:
                String name = et_loginName.getText().toString();
                String pwd = et_loginPsw.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonUtil.verifyPassword(pwd)) {
                    Toast.makeText(getActivity(), "请输入6-16位字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = API.USER_LOGIN + "ver=" + Contacts.VER + "&uid=" + name + "&pwd=" + pwd + "&device=" + "0";
                requestLogin(url);
                break;
            case R.id.tv_login_psw:
                ((MainActivity) getActivity()).showFindPSWFragment();
                break;
        }

    }

    private void requestLogin(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> register = ParserUser.getRegisterInfo(jsonObject.toString());
                        int status = register.getStatus();
                        if (status == 0) {
                            String name = et_loginName.getText().toString();
                            startActivity(new Intent(getActivity(), UserActivity.class));
                            ShareUtil.putString(getActivity(), "name", name);
                            ShareUtil.saveRegisterInfo(register, getActivity());
                        }
                        if (status == -1) {
                            Toast.makeText(getActivity(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                            return;
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

}
