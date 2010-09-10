package com.sacliv.simpmusicwidget;

import java.util.List;

import com.android.music.IMediaPlaybackService;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigureSMW extends Activity {
    private MediaPlayerServiceConnection mpsc = new MediaPlayerServiceConnection();
    private IMediaPlaybackService mService;
    private boolean bound;
    private TextView nikeid;
    private TextView musicstuff;
    private Button refreshbutton;

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (this.bound) {
            this.bound = false;
            unbindService(this.mpsc);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.bound) {
            this.bound = false;
            unbindService(this.mpsc);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (!this.bound) {
            Intent im = new Intent();
            im.setClassName("com.android.music",
                    "com.android.music.MediaPlaybackService");
            bindService(im, mpsc, Context.BIND_AUTO_CREATE);
            this.bound = true;
        }
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        bound = false;
        setContentView(R.layout.configure);
        
        String output = "";
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
        for (int i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            output += "Process " + rsi.process + " with component "
                    + rsi.service.getClassName();
            output += "\n";
            nikeid = (TextView) findViewById(R.id.TextNikeId);
            nikeid.setText(output);
        }
        musicstuff = (TextView) findViewById(R.id.musicstuff);
        musicstuff.setText("music service connected");
        if (mService != null) {
            try {
                Toast.makeText(ConfigureSMW.this, mService.getTrackName(),
                        Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else
            Toast.makeText(ConfigureSMW.this, "mService is null",
                    Toast.LENGTH_SHORT).show();
        
        refreshbutton = (Button) findViewById(R.id.refresh_button);
        refreshbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    Toast.makeText(ConfigureSMW.this, mService.getTrackName(),
                            Toast.LENGTH_SHORT).show();
                } catch (DeadObjectException e) {
                    Log.e("ActivityExample", "error", e);
                } catch (RemoteException e) {
                    Log.e("ActivityExample", "error", e);
                }
            }
        });
        
        // try {
        // output += "MediaPlayerServiceConnection" + " Playing track: "
        // + mService.getTrackName();
        // output += "\n";
        // output += "MediaPlayerServiceConnection" + "By artist: "
        // + mService.getArtistName();
        // output += "\n";
        // if (mService.isPlaying()) {
        // output += "MediaPlayerServiceConnection"
        // + "Music player is playing.";
        // } else {
        // output += "MediaPlayerServiceConnection"
        // + "Music player is not playing.";
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // throw new RuntimeException(e);
        // }

        // don't call 'this', use 'getApplicationContext()', the activity-object
        // is bigger than just the
        // context because the activity also stores the UI elemtents
        Toast.makeText(getApplicationContext(), "We are in Configure",
                Toast.LENGTH_SHORT).show();
    }

    private class MediaPlayerServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder service) {
           

            bound = true;
            
            

            Log.i("MediaPlayerServiceConnection", "Connected! Name: "
                    + name.getClassName());

            // This is the important line
            mService = IMediaPlaybackService.Stub.asInterface(service);
            // If all went well, now we can use the interface
            try {
                Log.i("MediaPlayerServiceConnection", "Playing track: "
                        + mService.getTrackName());
                Log.i("MediaPlayerServiceConnection", "By artist: "
                        + mService.getArtistName());
                if (mService.isPlaying()) {
                    Log.i("MediaPlayerServiceConnection",
                            "Music player is playing.");
                } else {
                    Log.i("MediaPlayerServiceConnection",
                            "Music player is not playing.");

                }
                Toast.makeText(ConfigureSMW.this,
                        "connected to mplayer Service", Toast.LENGTH_SHORT)
                        .show();
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
