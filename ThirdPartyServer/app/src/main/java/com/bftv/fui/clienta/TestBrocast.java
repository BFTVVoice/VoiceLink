package com.bftv.fui.clienta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bftv.fui.accessibility.VoiceAccessibility;
import com.bftv.fui.thirdparty.BindAidlManager;

/**
 * Author by Less on 17/7/10.
 */

public class TestBrocast extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我收到用户发的广播了 哈哈",Toast.LENGTH_SHORT).show();
        BindAidlManager.getInstance().dealMessage(App.sContext, VoiceAccessibility.sPackageName,"播放");
    }

}
