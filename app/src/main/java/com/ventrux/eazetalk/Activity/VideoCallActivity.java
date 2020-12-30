package com.ventrux.eazetalk.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.Firebase.Config;
import com.ventrux.eazetalk.MainActivity;
import com.ventrux.eazetalk.R;
import android.util.Log;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;

import android.view.View;
import android.widget.FrameLayout;

import android.opengl.GLSurfaceView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class VideoCallActivity extends AppCompatActivity  implements  Session.SessionListener,PublisherKit.PublisherListener{
    private static String API_KEY = "46899144";
    private static String SESSION_ID = "1_MX40Njg5OTE0NH5-MTU5ODUzNzU0NTI5MH5KaHpqV2t1ZFBJdTVTV3RxOWF1NVF1NGV-fg";
    //private static String SESSION_ID = "1_MX40Njg5OTE0NH4yMDIuMTQyLjEwNC44fjE1OTk4OTQ1Mzk4ODZ-MU4zczhiSFJIbkhKWC9jOEl5RFZtMVNIfn4";
     private static String TOKEN = "T1==cGFydG5lcl9pZD00Njg5OTE0NCZzaWc9N2RhMWQ5N2E1OTM4Njg5NTRmOTIzYmZjN2E3ODdmZmU3MjMwZTUyNDpzZXNzaW9uX2lkPTFfTVg0ME5qZzVPVEUwTkg1LU1UVTVPRFV6TnpVME5USTVNSDVLYUhwcVYydDFaRkJKZFRWVFYzUnhPV0YxTlZGMU5HVi1mZyZjcmVhdGVfdGltZT0xNTk4NTM3NjE1Jm5vbmNlPTAuNTEyNTg2MzM0MDk4ODE4MiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjAxMTI5NjE2JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
   // private static String TOKEN1 ="T1==cGFydG5lcl9pZD00Njg5OTE0NCZzaWc9ZmY2ZmVjNjAwMmU2ZjcxMjRlNzkxMDdkNGUyNjE2NDcxMzZlNTY0MzpzZXNzaW9uX2lkPTFfTVg0ME5qZzVPVEUwTkg0eU1ESXVNVFF5TGpFd05DNDRmakUxT1RrNE9UUTFNems0T0RaLU1VNHpjemhpU0ZKSWJraEtXQzlqT0VsNVJGWnRNVk5JZm40JmNyZWF0ZV90aW1lPTE1OTk4OTQ1NDAmcm9sZT1tb2RlcmF0b3Imbm9uY2U9MTU5OTg5NDU0MC4wMjk4MTUxOTQ5MDg5";
    private static final String LOG_TAG = VideoCallActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private static final String TAG = VideoCallActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Session mSession;
    private Publisher mPublisher;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Subscriber mSubscriber;

    ToggleButton togglemic;
    ImageButton endcall,acceptcall,rejectcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        requestPermissions();
        endcall=findViewById(R.id.button_hangup_call);
        togglemic=findViewById(R.id.toggle_mic);
        acceptcall=findViewById(R.id.image_button_accept_call);
        rejectcall=findViewById(R.id.image_button_reject_call);
        togglemic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubscriber != null) {
                    if (togglemic.isChecked()) {
                        mSubscriber.setSubscribeToAudio(true);
                    }
                    mSubscriber.setSubscribeToAudio(false);
                }
            }
        });
        if (StaticData.callerstatus==0){
            String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
            if (EasyPermissions.hasPermissions(VideoCallActivity.this, perms)) {
                // initialize view objects from your layout
                findViewById(R.id.calllayout).setVisibility(View.GONE);
                findViewById(R.id.framevideocall).setVisibility(View.VISIBLE);
                mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
                mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
                // initialize and connect to the session
                mSession = new Session.Builder(VideoCallActivity.this, API_KEY, SESSION_ID).build();
                mSession.setSessionListener(VideoCallActivity.this);
                mSession.connect(TOKEN);
            }
        }

        acceptcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };

                if (EasyPermissions.hasPermissions(VideoCallActivity.this, perms)) {
                    // initialize view objects from your layout
                    findViewById(R.id.calllayout).setVisibility(View.GONE);
                    findViewById(R.id.framevideocall).setVisibility(View.VISIBLE);
                    mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
                    mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
                    // initialize and connect to the session
                    mSession = new Session.Builder(VideoCallActivity.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(VideoCallActivity.this);
                    mSession.connect(TOKEN);
                }
            }
        });
        rejectcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.callerstatus=0;
                mSession.disconnect();
                Toast.makeText(VideoCallActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        endcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.disconnect();
                StaticData.callerstatus=0;
                Toast.makeText(VideoCallActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    //displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "OyeMeet" + message, Toast.LENGTH_LONG).show();

                }
            }
        };

    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        if (mSession != null) {
            mSession.onPause();
        }
    }

    @Override
    protected void onResume() {

        Log.d(LOG_TAG, "onResume");

        super.onResume();

        if (mSession != null) {
            mSession.onResume();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
//            // initialize view objects from your layout
//            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
//            mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);
//
//
//            // initialize and connect to the session
//            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
//            mSession.setSessionListener(this);
//            mSession.connect(TOKEN);


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }


    // SessionListener methods

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }


    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
        session.disconnect();
        Toast.makeText(VideoCallActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
        session.disconnect();
        Toast.makeText(VideoCallActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }

}
