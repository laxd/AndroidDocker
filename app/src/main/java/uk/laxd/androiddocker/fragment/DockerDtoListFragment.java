package uk.laxd.androiddocker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import timber.log.Timber;
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
                Timber.i("Replacing content_frame with container");
                DockerDto dockerDto = (DockerDto) getListView().getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(), getActivityClass());

                intent.putExtra("id", dockerDto.getId());

                startActivity(intent);
            }
        });
    }

    public abstract Class<? extends Activity> getActivityClass();
    public abstract ListView getListView();
}
