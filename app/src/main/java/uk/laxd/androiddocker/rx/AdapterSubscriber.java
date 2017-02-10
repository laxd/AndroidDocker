package uk.laxd.androiddocker.rx;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Collection;

import rx.Subscriber;

/**
 * Created by lawrence on 19/01/17.
 */

public class AdapterSubscriber<T> extends Subscriber<Collection<T>> {

    private Context context;
    private ArrayAdapter<T> adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    public AdapterSubscriber(Context context, ArrayAdapter<T> adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public AdapterSubscriber(Context context, ArrayAdapter<T> adapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public AdapterSubscriber(Context context, ArrayAdapter<T> adapter, ProgressDialog progressDialog) {
        this.context = context;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    public AdapterSubscriber(Context context, ArrayAdapter<T> adapter, SwipeRefreshLayout swipeRefreshLayout, ProgressDialog progressDialog) {
        this.context = context;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.progressDialog = progressDialog;
    }

    @Override
    public void onCompleted() {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if(progressDialog != null) {
            progressDialog.dismiss();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(context,
                "Failed to connect to docker address",
                Toast.LENGTH_LONG)
                .show();

        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNext(Collection<T> ts) {
        adapter.clear();
        adapter.addAll(ts);
    }
}
