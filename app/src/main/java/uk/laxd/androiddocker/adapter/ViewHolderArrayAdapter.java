package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by lawrence on 13/01/17.
 */

public abstract class ViewHolderArrayAdapter<T, H extends AbstractViewHolder> extends ArrayAdapter<T> {
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


    public H createOrRestoreViewHolder(View view, int layoutId) {
        H holder;
        if(view == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            view = inf.inflate(layoutId, null);
            holder = createNewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (H) view.getTag();
        }

        return holder;
    }

    public abstract H createNewHolder(View view);
}
