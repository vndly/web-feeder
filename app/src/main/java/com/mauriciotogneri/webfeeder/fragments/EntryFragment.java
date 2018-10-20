package com.mauriciotogneri.webfeeder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mauriciotogneri.webfeeder.R;

public class EntryFragment extends Fragment
{
    public static EntryFragment newInstance(String content)
    {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String html = getArguments().getString("content");

        View view = inflater.inflate(R.layout.view_entry, container, false);
        WebView webview = view.findViewById(R.id.content);

        loadEntry(webview, html);

        return view;
    }

    private void loadEntry(WebView webview, String content)
    {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebViewClient(new WebViewClient()
        {
            private boolean isFinished = false;

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                isFinished = true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (isFinished)
                {
                    System.out.println(url);
                }

                return isFinished;
            }
        });

        String html = "<link rel='stylesheet' type='text/css' href='style.css' />" + content;
        webview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
    }
}