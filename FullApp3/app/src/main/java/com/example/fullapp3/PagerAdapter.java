package com.example.fullapp3;

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
        titles.add("Messages");
        titles.add("Calls");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(titles.get(position)){
            case "Messages":
                return new MessageFragment();
            case "Calls":
                return new CallFragment();
        }
        return new MessageFragment();
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
