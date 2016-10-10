package com.example.administrator.myfirstapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myfirstapp.activity.MainActivity;
import com.example.administrator.myfirstapp.R;
import com.example.administrator.myfirstapp.activity.UserActivity;
import com.example.administrator.myfirstapp.gloable.API;
import com.example.administrator.myfirstapp.gloable.Contacts;
import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.Register;
import com.example.administrator.myfirstapp.model.parser.ParserUser;
import com.example.administrator.myfirstapp.util.CommonUtil;
import com.example.administrator.myfirstapp.util.ShareUtil;

import org.json.JSONObject;


public class RegistFragment extends Fragment {
    private EditText et_regist_address, et_regist_name, et_regist_psw;
    private Button btn_regist_regist;
    private CheckBox cb_regist;
    private RequestQueue requestQueue;
    private static final String TAG = "RegistFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regist, container, false);
        et_regist_address = (EditText) view.findViewById(R.id.et_regist_address);
        et_regist_name = (EditText) view.findViewById(R.id.et_regist_name);
        et_regist_psw = (EditText) view.findViewById(R.id.et_regist_psw);
        btn_regist_regist = (Button) view.findViewById(R.id.btn_regist_regist);
        cb_regist = (CheckBox) view.findViewById(R.id.cb_regist);
        requestQueue = Volley.newRequestQueue(getActivity());
        btn_regist_regist.setOnClickListener(onClickListener);
        return view;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email, name, pwd;
            email = et_regist_address.getText().toString();
            name = et_regist_name.getText().toString();
            pwd = et_regist_psw.getText().toString();

            if (!cb_regist.isChecked()) {
                Toast.makeText(getActivity(), "请仔细阅读条款，并选择同意", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtil.verifyEmail(email)) {
                Toast.makeText(getActivity(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "请输入用户姓名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtil.verifyPassword(pwd)) {
                Toast.makeText(getActivity(), "请输入6-16位字符", Toast.LENGTH_SHORT).show();
                return;
            }
            String url = API.USER_Register + "ver=" + Contacts.VER + "&uid=" + name + "&email=" + email + "&pwd=" + pwd;
            requestRegister(url);
        }
    };

    public void requestRegister(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG, "onResponse: ``````````````````" + jsonObject);
                        BaseEntry<Register> register = ParserUser.getRegisterInfo(jsonObject.toString());
                        int result = register.getData().getResult();
                        if (result == -2) {
                            Toast.makeText(getActivity(), "用户名重复", Toast.LENGTH_SHORT).show();
                        }
                        if (result == -3) {
                            Toast.makeText(getActivity(), "邮箱重复", Toast.LENGTH_SHORT).show();
                        }
                        if (result == 0) {
                            startActivity(new Intent(getActivity(), UserActivity.class));

                            ShareUtil.saveRegisterInfo(register,getContext());
                            ((MainActivity)getContext()).changeFragmentStatus();
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
