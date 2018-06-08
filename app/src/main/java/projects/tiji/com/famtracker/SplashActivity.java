package projects.tiji.com.famtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.spalshid);
        iv = (ImageView) findViewById(R.id.splashimage);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim) ;
        final Intent i=new Intent(this,MainActivity.class);
        Thread timer=new Thread(){
            public void run (){
            try {


                sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                startActivity(i);
                finish();
            }
        }

    };
    timer.start();

    }


}

