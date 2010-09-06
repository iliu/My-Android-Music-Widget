package com.sacliv.simpmusicwidget;

import java.util.List;

import com.android.music.IMediaPlaybackService;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigureSMW extends Activity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.configure);
        String output = "";

        Intent im = new Intent();
        im.setClassName("com.android.music", "com.android.music.MediaPlaybackService");
        MediaPlayerServiceConnection mpsc = new MediaPlayerServiceConnection();
        this.bindService(im, mpsc, 0);
        
        ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
        for (int i=0; i<rs.size(); i++) {
        ActivityManager.RunningServiceInfo rsi = rs.get(i);
        output += "Process " + rsi.process + " with component " + rsi.service.getClassName();
        output += "\n";
        }
        TextView tv = (TextView) findViewById(R.id.TextNikeId);
        tv.setText(output);
        
        // don't call 'this', use 'getApplicationContext()', the activity-object
        // is bigger than just the
        // context because the activity also stores the UI elemtents
        Toast.makeText(getApplicationContext(), "We are in Configure",
                Toast.LENGTH_SHORT).show();
    }
    
    private class MediaPlayerServiceConnection implements ServiceConnection {

        public IMediaPlaybackService mService;

        public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("MediaPlayerServiceConnection", "Connected! Name: " + name.getClassName());

                // This is the important line
                mService = IMediaPlaybackService.Stub.asInterface(service);

                // If all went well, now we can use the interface
                try {
                        Log.i("MediaPlayerServiceConnection", "Playing track: " + mService.getTrackName());
                        Log.i("MediaPlayerServiceConnection", "By artist: " + mService.getArtistName());
                        if (mService.isPlaying()) {
                                Log.i("MediaPlayerServiceConnection", "Music player is playing.");
                        } else {
                                Log.i("MediaPlayerServiceConnection", "Music player is not playing.");
                        }
                } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
                }
        }

        public void onServiceDisconnected(ComponentName name) {
                Log.i("MediaPlayerServiceConnection", "Disconnected!");
        }
    }

}
