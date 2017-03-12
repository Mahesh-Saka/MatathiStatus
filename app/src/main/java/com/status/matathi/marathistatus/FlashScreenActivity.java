package com.status.matathi.marathistatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.status.matathi.GridViewAdapter;
import com.status.matathi.util.Util;

/**
 * Created by Mahesh on 1/27/2017.
 *
 * @author Fuck your w......r (anonoymous@gamail.com)
 * @version 1.0
 */

public class FlashScreenActivity extends AppCompatActivity {

    GridView gridView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("catId", position);
                startActivity(intent);
            }
        });
        gridView.setAdapter(new GridViewAdapter(this));
        adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        findViewById(R.id.imageViewAds).setOnClickListener(onClickListenerNewsApp);
      /*View view = ((LayoutInflater) getApplication().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.grid_view_footer, null, false);
        gridView.addView(view);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnAbout) {
            new AboutAlertDialog(FlashScreenActivity.this).show();
        } else if (id == R.id.btnShare) {
            Util.shareApp(FlashScreenActivity.this);
        }
        return super.onOptionsItemSelected(item);
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

    View.OnClickListener onClickListenerNewsApp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sharingIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mahesh.hindi.news.paper"));
            startActivity(sharingIntent);
        }
    };
}
