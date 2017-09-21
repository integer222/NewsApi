package ru.pap.newsapi.screen.common.adapter;

import android.view.ViewGroup;

import ru.pap.newsapi.screen.common.holder.BaseHolder;
import ru.pap.newsapi.screen.common.holder.IItemAction;

/**
 * Created by alex on 13.08.2017.
 */

public abstract class ActionBaseAdapter<M> extends BaseAdapter<M> implements IItemAction {

    private IItemAction mAction;

    public ActionBaseAdapter(IItemAction action) {
        mAction = action;
    }

    @Override
    public BaseHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType, this);
    }

    public abstract BaseHolder<M> onCreateViewHolder(ViewGroup parent, int viewType, IItemAction action);

    @Override
    public void onItemAction(int action, int position) {
        if (mAction == null) {
            return;
        }
        mAction.onItemAction(action, position);
    }
}
