package projects.tiji.com.famtracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    static int countPowerOff = 0;
    private Activity activity = null;

    public MyReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("onReceive", "Power button is pressed.");

        Toast.makeText(context, "power button clicked", Toast.LENGTH_LONG)
                .show();

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countPowerOff++;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (countPowerOff == 5) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_TEXT, "Iam in trouble");
                context.startActivity(myIntent);
            }
        }
    }
}