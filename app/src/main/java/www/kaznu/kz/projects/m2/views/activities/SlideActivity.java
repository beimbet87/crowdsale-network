package www.kaznu.kz.projects.m2.views.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import www.kaznu.kz.projects.m2.R;

public class SlideActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

    }
}