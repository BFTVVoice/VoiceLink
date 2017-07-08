package com.bftv.fui.clienta;

import android.app.Application;
import android.content.Context;

/**
 * @author less
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2017/7/7 0007 21:26
 * @changeRecord [修改记录] <br/>
 */
public class App extends Application{

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}