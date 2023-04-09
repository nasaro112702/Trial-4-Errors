package com.example.tablayoutviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> titles = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        titles.add("CHATS");
        titles.add("STATUS");
        titles.add("CALLS");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(titles.get(position)){
            case "CHATS":
                return new ChatFragment();
            case "STATUS":
                return new StatusFragment();
            case "CALLS":
                return new CallsFragment();
        }

        return new ChatFragment();
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
