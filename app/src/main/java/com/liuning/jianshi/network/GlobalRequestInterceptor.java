/*
 * Created by wingjay on 11/16/16 3:32 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.liuning.jianshi.network;

import android.content.Context;
import android.text.TextUtils;

import com.liuning.jianshi.BuildConfig;
import com.liuning.jianshi.di.ForApplication;
import com.liuning.jianshi.events.InvalidUserTokenEvent;
import com.liuning.jianshi.prefs.UserPrefs;
import com.liuning.jianshi.util.DeviceUtil;
import com.liuning.jianshi.util.RequestUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jay on 8/27/16.
 */
public class GlobalRequestInterceptor implements Interceptor {

  Context applicationContext;

  @Inject
  UserPrefs userPrefs;

  @Inject
  GlobalRequestInterceptor(@ForApplication Context applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    HttpUrl.Builder urlBuilder = request.url().newBuilder();
    urlBuilder.addQueryParameter("device_id", DeviceUtil.getAndroidId(applicationContext))
        .addQueryParameter("version_name", BuildConfig.VERSION_NAME)
        .addQueryParameter("locale", Locale.getDefault().toString())
        .addQueryParameter("random", String.valueOf(System.nanoTime()))
        .addQueryParameter("ts", String.valueOf(System.currentTimeMillis()));

    Request.Builder newRequestBuilder = request.newBuilder()
        .addHeader("Request-Id", RequestUtils.generateRequestId());

    if (!TextUtils.isEmpty(userPrefs.getAuthToken())) {
      newRequestBuilder.addHeader("Authorization", userPrefs.getAuthToken());
    }
    Response response = chain.proceed(newRequestBuilder.url(urlBuilder.build()).build());

    if (response.code() == 401) {
      EventBus.getDefault().post(new InvalidUserTokenEvent());
    }
    return response;
  }
}
