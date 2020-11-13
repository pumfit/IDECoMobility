package com.teamide.idecomobility;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.teamide.idecomobility.fragments.Fragment1;
import com.teamide.idecomobility.fragments.Fragment2;
import com.teamide.idecomobility.fragments.Fragment3;
import com.teamide.idecomobility.fragments.Fragment4;

import java.util.ArrayList;

public class SubwayTapAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;

    public SubwayTapAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new Fragment2());
        items.add(new Fragment3());
        items.add(new Fragment4());
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
