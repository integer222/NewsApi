package ru.pap.newsapi.screen.common.adapter;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ru.pap.newsapi.screen.common.holder.BaseHolder;

/**
 * Created by alex on 13.08.2017.
 */

public abstract class BaseAdapter<M> extends RecyclerView.Adapter<BaseHolder<M>> {

    private List<M> mData = new ArrayList<>();

    public M getItem(int position) {
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    public void setItems(List<M> items, boolean notifyChanged) {
        clear(false);
        addItems(items, notifyChanged);
    }

    public void addItem(M item, boolean notifyChanged) {
        mData.add(item);
        if (notifyChanged) {
            notifyItemInserted(mData.size() - 1);
        }
    }

    public void addItems(List<M> items, boolean notifyChanged) {
        mData.addAll(items);
        if (notifyChanged) {
            notifyDataSetChanged();
        }
    }

    public void clear(boolean notifyChanged) {
        mData.clear();
        if (notifyChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
