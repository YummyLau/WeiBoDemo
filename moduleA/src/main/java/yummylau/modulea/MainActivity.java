package yummylau.modulea;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.text.SimpleDateFormat;

import yummylau.common.activity.BaseActivity;
import yummylau.modulea.databinding.ModuleaActivityMainLayoutBinding;


/**
 * Created by g8931 on 2017/11/14.
 */
@Route(path = "/modulea/MainActivity")
public class MainActivity extends BaseActivity {

    private ModuleaActivityMainLayoutBinding mBinding;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.modulea_activity_main_layout);
        initView();
    }

    private void initView() {
        mSsoHandler = new SsoHandler(this);
        mBinding.weibologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSsoHandler.authorize(new WbAuthListener() {
                    @Override
                    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        mAccessToken = oauth2AccessToken;
                        if (mAccessToken.isSessionValid()) {
                            updateTokenView();
                            AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                        }
                    }

                    @Override
                    public void cancel() {
                        Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mBinding.weiboout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessTokenKeeper.clear(getApplicationContext());
                mAccessToken = new Oauth2AccessToken();
                updateTokenView();
            }
        });
        mBinding.weiboupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mAccessToken.getRefreshToken())) {
                    AccessTokenKeeper.refreshToken(Constants.APP_KEY, MainActivity.this, new RequestListener() {
                        @Override
                        public void onComplete(String response) {
                            response.toString();
                        }

                        @Override
                        public void onWeiboException(WeiboException e) {
                            e.getMessage();
                        }
                    });
                }
            }
        });

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView();
        }
    }

    private void updateTokenView() {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        mBinding.textview.setText("token: " + mAccessToken.getToken() + "\n"
                + "到期时间： " + date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果发起sso授权回调
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected boolean supportHandlerStatusbar() {
        return true;
    }

    @Override
    public int getStatusbarColor() {
        return Color.RED;
    }
}
