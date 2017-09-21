package ru.pap.newsapi.ui.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.pap.newsapi.screen.sourcedetail.article.ArticlesFragment;

/**
 * Created by alex on 23.08.2017.
 */

public class NewsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<PageData> mPagesData = new ArrayList<>();

    public NewsFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPagesData(List<PageData> pagesData) {
        mPagesData = pagesData;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        PageData pageData = mPagesData.get(position);
        return ArticlesFragment.newInstance(pageData.sourceId, pageData.sortBy);
    }

    @Override
    public int getCount() {
        return mPagesData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagesData.get(position).title;
    }

    public static class PageData{
        public final String title;
        public final String sourceId;
        public final String sortBy;

        public PageData(String title, String sourceId, String sortBy) {
            this.title = title;
            this.sourceId = sourceId;
            this.sortBy = sortBy;
        }
    }
}
