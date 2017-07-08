package com.bftv.fui.clienta;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.VoiceContast;
import com.bftv.fui.thirdparty.VoiceFeedback;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private IRemoteVoice mIRemoteVoice;

    private Queue<String> mQueue = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMessage("com.bftv.fui.test", "加载A客户端");
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMessage("com.bftv.fui.testb", "加载B客户端");
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(VoiceContast.ACTION_COMMON_MESSAGE);
                sendOrderedBroadcast(intent, null, new DefaultBroadcastReceiver(), null, Activity.RESULT_OK, "用户说了一句话", null);
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Toast.makeText(MainActivity.this, "onServiceConnected-className:" + className + "service:" + service, Toast.LENGTH_SHORT).show();
            mIRemoteVoice = IRemoteVoice.Stub.asInterface(service);
            String msg = mQueue.poll();
            if (!TextUtils.isEmpty(msg)) {
                send(msg);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Toast.makeText(MainActivity.this, "onServiceDisconnected:", Toast.LENGTH_SHORT).show();
            mIRemoteVoice = null;
        }
    };

    private void dealMessage(String pck, String userTxt) {
        Intent intent = new Intent("android.intent.action.remotevoice");
        intent.setPackage(pck);
        getApplicationContext().bindService(intent, mConnection, BIND_AUTO_CREATE);
        if (mIRemoteVoice == null) {
            mQueue.add(userTxt);
        } else {
            send(userTxt);
        }
    }

    private void send(String userTxt) {
        try {
            VoiceFeedback result = mIRemoteVoice.sendMessage(userTxt, null);
            Toast.makeText(MainActivity.this, "send:"+result.feedback, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
