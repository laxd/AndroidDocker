package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lawrence on 13/01/17.
 */

public abstract class ViewHolder<T> {

    private int variableId;
    protected LayoutInflater layoutInflater;
    private ViewDataBinding binding;

    public ViewHolder(Context context, int layout, ViewGroup parent, int variableId) {
        this.layoutInflater = LayoutInflater.from(context);

        binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false);

        this.variableId = variableId;
    }

    public View bindView(T t) {
        binding.setVariable(variableId, t);
        binding.executePendingBindings();

        return binding.getRoot();
    }
}
