package ru.pap.newsapi.screen.common;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by alex on 03.08.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private boolean mHomeNecessarily;

    public boolean isHomeNecessarily() {
        return mHomeNecessarily;
    }

    public void setHomeNecessarily(boolean homeNecessarily) {
        mHomeNecessarily = homeNecessarily;
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeNecessarily);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
