package ru.pap.newsapi.screen.main;

import android.os.Bundle;

import ru.pap.newsapi.screen.common.ContentActivity;

public class SourcesActivity extends ContentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState !=null){
            return;
        }
        setContentFragment(SourcesFragment.TAG, new SourcesFragment());
    }
}
