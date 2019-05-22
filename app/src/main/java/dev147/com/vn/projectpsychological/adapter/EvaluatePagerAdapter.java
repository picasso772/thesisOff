package dev147.com.vn.projectpsychological.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import dev147.com.vn.projectpsychological.ui.evaluate.charts.ChartsFragment;
import dev147.com.vn.projectpsychological.ui.evaluate.detail.DetailFragment;

public class EvaluatePagerAdapter extends FragmentStatePagerAdapter {

    public EvaluatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new ChartsFragment();
                break;
            case 1:
                frag = new DetailFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
