package com.dsh.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;


import com.dsh.latte.delegates.LatteDelegate;
import com.dsh.latte.ec.R;
import com.dsh.latte.ec.R2;
import com.dsh.latte.ec.base.Constants;
import com.dsh.latte.net.RestClient;
import com.dsh.latte.net.callback.ISuccess;
import com.dsh.latte.util.log.LatteLogger;
import com.dsh.latte.wechat.LatteWeChat;
import com.dsh.latte.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            //String url = Constants.BASE_IP + Constants.Action_Login;
            //RestClient.bulider()
            //        .url(url)
            //        .params("userphone", mEmail.getText().toString())
            //        .params("userpassword", mPassword.getText().toString())
            //        .success(new ISuccess() {
            //            @Override
            //            public void onSuccess(String response) {
            //                LatteLogger.json("USER_PROFILE", response);
            //                SignHandler.onSignIn(response, mISignListener);
            //            }
            //        })
            //        .build()
            //        .post();
            if (mEmail.getText().toString().equals("18510430345")&&mPassword.getText().toString().equals("123456")){
                LatteLogger.json("USER_PROFILE", "response");
                SignHandler.onSignIn("response", mISignListener);
            }
        }

    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
                    }
                })
                .signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

//        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            mEmail.setError("错误的邮箱格式");
//            isPass = false;
//        } else {
//            mEmail.setError(null);
//        }

        if (email.isEmpty() || email.length()<11) {
            mEmail.setError("错误的手机号码");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }
}
