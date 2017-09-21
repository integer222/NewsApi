package ru.pap.newsapi.screen.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import ru.pap.newsapi.R;

/**
 * Created by alex on 03.08.2017.
 */

public abstract class ContentActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setContentFragment(String tag, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer, fragment, tag)
                .commit();
    }

    public Fragment getCurrentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.contentContainer);
    }
}
