package yummylau.account;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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

import yummylau.common.activity.BaseActivityOld;
import yummylau.account.databinding.AccountActivityMainLayoutBinding;


/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Route(path = Constants.ROUTER_LOGIN)
public class LoginActivity extends BaseActivityOld {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private AccountActivityMainLayoutBinding mBinding;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.account_activity_main_layout);
        mSsoHandler = new SsoHandler(this);
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        initView();
    }

    private void initView() {
        mBinding.toolbar.setTitle(R.string.third_weibo_login);
        mBinding.toolbar.setTitleTextColor(Color.WHITE);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mBinding.toolbar.inflateMenu(R.menu.account_toolbar_menu);//设置右上角的填充菜单

        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSsoHandler.authorize(new WbAuthListener() {
                    @Override
                    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        mAccessToken = oauth2AccessToken;
                        if (mAccessToken.isSessionValid()) {
                            AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                        }
                    }

                    @Override
                    public void cancel() {
                        Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        mBinding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mAccessToken.getRefreshToken())) {
                    AccessTokenKeeper.refreshToken(Constants.APP_KEY, LoginActivity.this, new RequestListener() {
                        @Override
                        public void onComplete(String response) {
                            Log.d(TAG, response + "");
                        }

                        @Override
                        public void onWeiboException(WeiboException e) {
                            e.getMessage();
                        }
                    });
                }
            }
        });

        mBinding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAccessToken = AccessTokenKeeper.readAccessToken(LoginActivity.this);
                if (mAccessToken != null) {
                    Log.d(TAG, mAccessToken.toString());
                }
            }
        });

        mBinding.out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessTokenKeeper.clear(getApplicationContext());
                mAccessToken = new Oauth2AccessToken();
            }
        });
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
}
