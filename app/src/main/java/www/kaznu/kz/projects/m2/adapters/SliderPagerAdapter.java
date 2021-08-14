package www.kaznu.kz.projects.m2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import www.kaznu.kz.projects.m2.views.fragments.MainSliderFragment;

public class SliderPagerAdapter extends FragmentPagerAdapter {
    public SliderPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MainSliderFragment.newInstance(position);
    }

    // size is hardcoded
    @Override
    public int getCount() {
        return 3;
    }
}
