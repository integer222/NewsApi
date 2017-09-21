package ru.pap.newsapi.screen.common.holder;

import android.view.View;

/**
 * Created by alex on 13.08.2017.
 */

public abstract class ActionBaseHolder<M> extends BaseHolder<M> {

    private IItemAction mAction;

    public ActionBaseHolder(View itemView, IItemAction action) {
        super(itemView);
        mAction = action;
    }

    public IItemAction getAction() {
        return mAction;
    }
}
