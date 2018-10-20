package com.mauriciotogneri.webfeeder.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mauriciotogneri.webfeeder.fragments.EntryFragment;
import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;

public class EntryAdapter extends FragmentStatePagerAdapter
{
    private final List<SyndEntry> entries;

    public EntryAdapter(FragmentManager fragmentManager, List<SyndEntry> entries)
    {
        super(fragmentManager);
        this.entries = entries;
    }

    @Override
    public Fragment getItem(int position)
    {
        SyndEntry entry = entries.get(position);

        return EntryFragment.newInstance(entry.getDescription().getValue());
    }

    @Override
    public int getCount()
    {
        return entries.size();
    }
}