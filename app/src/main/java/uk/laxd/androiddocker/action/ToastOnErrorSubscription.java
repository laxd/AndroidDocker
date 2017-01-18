package uk.laxd.androiddocker.action;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by lawrence on 18/01/17.
 */

public abstract class ToastOnErrorSubscription<T> extends Subscriber<T> {

    private Context context;

    private ProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;


    private String errorMessage = "Failed to connect to docker service!";

    public ToastOnErrorSubscription(Context context) {
        this.context = context;
    }

    public ToastOnErrorSubscription<T> withProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        return this;
    }

    public ToastOnErrorSubscription withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ToastOnErrorSubscription withRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        return this;
    }

    @Override
    public void onCompleted() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }

        if(refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(progressDialog != null) {
            progressDialog.cancel();
        }

        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
