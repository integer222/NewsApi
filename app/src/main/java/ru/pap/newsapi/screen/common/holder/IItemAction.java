package ru.pap.newsapi.screen.common.holder;

import android.support.annotation.IntDef;

/**
 * Created by alex on 13.08.2017.
 */

public interface IItemAction {

    @IntDef({ON_CLICK})
    public @interface Action{};

    public static final int ON_CLICK = 0;

    public void onItemAction(@Action int action, int position);

}
