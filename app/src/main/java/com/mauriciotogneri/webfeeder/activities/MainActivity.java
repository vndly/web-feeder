package com.mauriciotogneri.webfeeder.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.mauriciotogneri.webfeeder.R;
import com.mauriciotogneri.webfeeder.adapters.EntryAdapter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.ByteArrayInputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_main);

        load();

        DrawerLayout drawer = findViewById(R.id.drawer);

        findViewById(R.id.toolbar_menu).setOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            }
            else
            {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawer.closeDrawers();

            return true;
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void load()
    {
        new AsyncTask<Void, Void, SyndFeed>()
        {
            @Override
            protected SyndFeed doInBackground(Void... voids)
            {
                try
                {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://feeds.weblogssl.com/xataka2")
                            .build();

                    Response response = client.newCall(request).execute();

                    String body = response.body().string();

                    return new SyndFeedInput().build(new XmlReader(new ByteArrayInputStream(body.getBytes())));
                }
                catch (Exception e)
                {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(SyndFeed feed)
            {
                createPages(feed.getEntries());
            }
        }.execute();
    }

    private void createPages(List<SyndEntry> entries)
    {
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new EntryAdapter(getSupportFragmentManager(), entries));
    }
}