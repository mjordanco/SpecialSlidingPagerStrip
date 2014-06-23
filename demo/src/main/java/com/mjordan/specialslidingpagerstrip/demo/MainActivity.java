package com.mjordan.specialslidingpagerstrip.demo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private SlidingPagerStrip mPagerStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerStrip = (SlidingPagerStrip) findViewById(R.id.sliding_pager_strip);

        mViewPager.setAdapter(new BogusPagerAdapter(getFragmentManager()));
        mPagerStrip.setViewPager(mViewPager);
    }

    private static class BogusPagerAdapter extends FragmentPagerAdapter {

        public BogusPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BogusFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Titles!";
        }
    }

    public static class BogusFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_bogus, container, false);
            Random rand = new Random();
            view.setBackgroundColor(Color.rgb(rand.nextInt(365), rand.nextInt(365), rand.nextInt(365)));

            return view;
        }
    }
}
