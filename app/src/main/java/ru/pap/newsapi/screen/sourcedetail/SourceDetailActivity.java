package ru.pap.newsapi.screen.sourcedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import ru.pap.newsapi.R;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.screen.common.BaseActivity;

/**
 * Created by alex on 13.08.2017.
 */

public class SourceDetailActivity extends BaseActivity {

    public static Intent newIntent(Context context, String sourceId) {
        if (TextUtils.isEmpty(sourceId)) {
            throw new UnsupportedOperationException("source id == null");
        }
        Intent intent = new Intent(context, SourceDetailActivity.class);
        intent.putExtra(SourceDetailFragment.EXTRA_SOURCE_ID, sourceId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_detail_activity);
        if (savedInstanceState != null) {
            return;
        }
        SourceDetailFragment fragment = SourceDetailFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentContainer, fragment)
                .commit();
    }
}
