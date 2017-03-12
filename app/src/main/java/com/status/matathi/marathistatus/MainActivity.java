package com.status.matathi.marathistatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.status.matathi.ListAdapter;
import com.status.matathi.service.StatusService;

import java.util.ArrayList;

/**
 * ^
 * (*)
 * (*)
 * (*)
 * (*****)
 * (********)
 * (************)
 * (****************)
 * \			      /
 * \   <*>  <*>  /
 * \			/
 * \	  _    /
 * \   -   /
 * \  .  /
 * /\   /\ \
 * / /|   | \ \
 * \/ |   | \ \
 * |   |  |/
 * \   \__
 * \___/
 * <p>
 * Created by Anonymous are LEGEND on 1/27/2017.
 *
 * @author /\
 *         /  \
 *         /____\
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    private ArrayList<String> arrayList;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Switch aSwitch;
    int count;
    private AdView adView;
    private InterstitialAd interstitialAd;
    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        // toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        arrayList = new StatusService(this).getAllStatusByCatagories(getIntent().getIntExtra("catId", 0));
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        listView = (ListView) findViewById(R.id.listview);
        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(listAdapter);
        textView = (TextView) findViewById(R.id.txtViewMessageCount);
        aSwitch = (Switch) findViewById(R.id.switchBtn);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                count = ListAdapter.pos;
                setCount(count);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                count = position + 1;
                setCount(count);
                if (position % 5 == 0)
                    showInterstitial();
            }

            @Override
            public void onPageSelected(int position) {
                count = position + 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.lblInterstitial));
        adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        requestLoadNewInterstitialAd();// Load full iterstail ads on mobile.......
        interstitialAd.setAdListener(adListener);
        handler = new Handler();
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mViewPager.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                setCount(count);
            } else {
                mViewPager.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                count = mSectionsPagerAdapter.count_pos;
                setCount(count);
            }
            showInterstitial();
        }
    };

    public void setCount(int pos) {
        textView.setText((pos) + "/" + arrayList.size());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnAbout) {
            new AboutAlertDialog(MainActivity.this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public int count_pos = 0;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            count_pos = position + 1;
            return PlaceHolderFragment.getInstance(arrayList.get(position));
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    public void requestLoadNewInterstitialAd() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    AdListener adListener = new AdListener() {
        @Override
        public void onAdClosed() {
            requestLoadNewInterstitialAd();
        }
    };

    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}
