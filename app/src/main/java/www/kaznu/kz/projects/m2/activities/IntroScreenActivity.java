package www.kaznu.kz.projects.m2.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.fragments.IntroFragment01;

public class IntroScreenActivity extends FragmentActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        IntroFragment01 introFragment01 = new IntroFragment01();
        ft.add(R.id.intro_fragment, introFragment01);
        ft.commit();
    }
}