package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.laxd.androiddocker.R;

/**
 * Created by lawrence on 13/01/17.
 */

public abstract class AbstractViewHolder<T> {

    protected LayoutInflater layoutInflater;
    private ViewDataBinding binding;

    public AbstractViewHolder(Context context, int layout, ViewGroup parent) {
        this.layoutInflater = LayoutInflater.from(context);

        binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false);
    }

    public View bindView(int variableId, T t) {
        binding.setVariable(variableId, t);

        return binding.getRoot();
    }
}
