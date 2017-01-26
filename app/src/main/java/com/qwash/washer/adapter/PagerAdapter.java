package com.qwash.washer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qwash.washer.Sample;
import com.qwash.washer.ui.fragment.ChildWashHistoryFragment;

/**
 * Created by kundan on 10/16/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = ChildWashHistoryFragment.newInstance(Sample.WASH_HISTORY_IN_PROGRESS);
                break;
            case 1:
                frag = ChildWashHistoryFragment.newInstance(Sample.WASH_HISTORY_COMPLETE);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "In Progress";
                break;
            case 1:
                title = "Complete";
                break;
        }

        return title;
    }
}
