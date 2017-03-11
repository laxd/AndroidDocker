package uk.laxd.androiddocker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerDto;

/**
 * Created by lawrence on 11/03/17.
 */

public abstract class DockerDtoListFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("log", "Replacing content_frame with container");
                DockerDto dockerDto = (DockerDto) getListView().getAdapter().getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("id", dockerDto.getId());

                FragmentTransaction tx = getFragmentManager().beginTransaction();

                try {
                    Fragment fragment = getFragmentClass().newInstance();
                    fragment.setArguments(bundle);
                    tx.replace(R.id.content_frame, fragment);
                    tx.addToBackStack(null);
                    tx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Invalid fragment class: %s", getFragmentClass()), e);
                }
            }
        });
    }

    public abstract Class<? extends Fragment> getFragmentClass();
    public abstract ListView getListView();
}
