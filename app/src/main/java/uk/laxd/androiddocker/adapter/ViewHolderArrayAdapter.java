package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by lawrence on 13/01/17.
 */

public abstract class ViewHolderArrayAdapter<T> extends ArrayAdapter<T> {
    public ViewHolderArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ViewHolderArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ViewHolderArrayAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
    }

    public ViewHolderArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ViewHolderArrayAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    public ViewHolderArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ViewHolder<T> createOrRestoreViewHolder(ViewGroup parent, View view) {
        ViewHolder<T> holder;
        if(view == null) {
            holder = createNewHolder(parent);
        }
        else {
            holder = (ViewHolder<T>) view.getTag();
        }

        return holder;
    }

    public abstract ViewHolder<T> createNewHolder(ViewGroup parent);

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder<T> holder = createOrRestoreViewHolder(parent, view);

        View boundView = holder.bindView(getItem(position));

        boundView.setTag(holder);

        return boundView;
    }

    public class GenericViewHolder<T> extends ViewHolder<T> {
        public GenericViewHolder(Context context, int layout, ViewGroup parent, int variableId) {
            super(context, layout, parent, variableId);
        }
    }
}
