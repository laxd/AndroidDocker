package uk.laxd.androiddocker.adapter;

import android.view.View;

/**
 * Created by lawrence on 13/01/17.
 */

public class AbstractViewHolder {
    private View baseView;

    public AbstractViewHolder(View baseView) {
        this.baseView = baseView;
    }

    public View getBaseView() {
        return baseView;
    }
}
