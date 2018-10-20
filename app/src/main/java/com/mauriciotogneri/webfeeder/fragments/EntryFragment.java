package com.mauriciotogneri.webfeeder.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView content = view.findViewById(R.id.content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            content.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
        }
        else
        {
            content.setText(Html.fromHtml(html));
        }

        return view;
    }
}