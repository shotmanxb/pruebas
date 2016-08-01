package test.doubango.org.pruebas;

import android.app.Activity;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;

public class CallScreen extends Activity {
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    //IncomingCallReceiver callReceiver;
    String domain = "myserver.net";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_call_screen);

        initManager();
        Log.d("Z:", "Done initManger()");
        Thread waiter = new Thread(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    sleep(10000);
                    Log.d("Z:","Done waiting");
                    initCall();
                    Log.d("Z:","Done initCall");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        waiter.start();

        //initCall();

    }
    public void initManager()
    {
        manager = SipManager.newInstance(this);
        initLocalProfile();
    }
    public void initLocalProfile()
    {
        String username = "user";
        String password = "12345";
        String domain = "myserver.net";
        try {
            SipProfile.Builder builder = new SipProfile.Builder(username,domain);
            builder.setPassword(password);
            me = builder.build();
            //Intent intent = new Intent();
            //PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
            //manager.open(me,pi,null);
            manager.open(me);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void initCall()
    {
        SipAudioCall.Listener listener = new SipAudioCall.Listener(){
            @Override
            public void onCallEstablished(SipAudioCall call) {
                // TODO Auto-generated method stub
                //super.onCallEstablished(call);
                call.startAudio();
                call.setSpeakerMode(true);
                call.toggleMute();
            }
            @Override
            public void onCallEnded(SipAudioCall call) {
                // TODO Auto-generated method stub
                super.onCallEnded(call);
            }
        };
        try {
            call = manager.makeAudioCall(me.getUriString(), "sip:12345678910@myserver.net", listener, 30);
        } catch (SipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
