package ru.pap.newsapi.screen.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.databinding.ItemSourceBinding;
import ru.pap.newsapi.screen.common.adapter.ActionBaseAdapter;
import ru.pap.newsapi.screen.common.holder.BaseHolder;
import ru.pap.newsapi.screen.common.holder.IItemAction;

/**
 * Created by alex on 13.08.2017.
 */

public class SourcesAdapter extends ActionBaseAdapter<Source> {


    public SourcesAdapter(IItemAction action) {
        super(action);
    }

    @Override
    public BaseHolder<Source> onCreateViewHolder(ViewGroup parent, int viewType, IItemAction action) {
        ItemSourceBinding binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SourcesItemHolder(binding, action);
    }

    @Override
    public void onBindViewHolder(BaseHolder<Source> holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
