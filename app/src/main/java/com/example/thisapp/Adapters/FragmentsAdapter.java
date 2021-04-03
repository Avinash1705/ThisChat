package com.example.thisapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.thisapp.Fragments.CallsFragments;
import com.example.thisapp.Fragments.ChatsFragments;
import com.example.thisapp.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChatsFragments();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallsFragments();
            default:
                return new ChatsFragments();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="CHATS";
        }
        if(position==1){
            title="STATUS";
        }
        if(position==2){
            title="CALLS";
        }
//        switch (position){
//            case 0:
//                title="CHATS";
//            case 1:
//                title="STATUS";
//            case 2:
//                title="CALLS";
//            default:
//                return title;
//        }
        return title;
    }
}
