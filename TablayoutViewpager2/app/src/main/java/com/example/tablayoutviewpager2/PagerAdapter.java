package com.example.tablayoutviewpager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> titles = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        titles.add("Call");
        titles.add("Text");
        titles.add("Settings");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (titles.get(position)){
            case "Call":
                return new CalFragment();
            case "Text":
                return new TextFragment();
            case "Settings":
                return new SettingsFragment();
        }
        return new CalFragment();
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
