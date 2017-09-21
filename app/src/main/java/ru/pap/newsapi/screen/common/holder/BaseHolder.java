package ru.pap.newsapi.screen.common.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alex on 13.08.2017.
 */

public abstract class BaseHolder<M> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    protected Context getContext() {
        return itemView != null ? itemView.getContext() : null;
    }

    public abstract void bind(M model);


}
