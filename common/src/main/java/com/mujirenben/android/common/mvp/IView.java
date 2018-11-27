/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mujirenben.android.common.mvp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * ================================================
 * 框架要求框架中的每个 View 都需要实现此类,以满足规范
 * ================================================
 */
public interface IView {

    /**
     * 显示加载,下拉刷新
     */
    void showLoading();

    /**
     * 隐藏加载,刷新完获取
     */
    void hideLoading();




    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void showMessage(@NonNull String message);



    /**
     * 跳转 {@link Activity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    void launchActivity(@NonNull Intent intent);




    /**
     * 杀死自己
     */
    void killMyself();
}
