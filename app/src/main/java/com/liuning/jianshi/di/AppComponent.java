/*
 * Created by wingjay on 11/16/16 3:31 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.liuning.jianshi.di;

import com.liuning.jianshi.ui.DiaryListActivity;
import com.liuning.jianshi.ui.EditActivity;
import com.liuning.jianshi.ui.MainActivity;
import com.liuning.jianshi.ui.SettingActivity;
import com.liuning.jianshi.ui.SignupActivity;
import com.liuning.jianshi.ui.ViewActivity;
import com.liuning.jianshi.ui.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends MiscComponent{

  void inject(BaseActivity obj);

  void inject(MainActivity obj);
//
  void inject(SignupActivity obj);

  void inject(DiaryListActivity obj);

  void inject(EditActivity obj);

  void inject(ViewActivity obj);

  void inject(SettingActivity obj);
}
