package uk.laxd.androiddocker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainerDetail;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @BindView(R.id.container_id)
    protected TextView id;

    @BindView(R.id.container_name)
    protected TextView name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.docker_container, container, false);
        unbinder = ButterKnife.bind(this, root);

        ((AndroidDockerApplication) getActivity().getApplication())
                .getAndroidDockerComponent().inject(this);

        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

        dockerServiceFactory.getDockerService()
                .getContainer(getArguments().getString("id"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DockerContainerDetail>() {
                    @Override
                    public void call(DockerContainerDetail dockerContainerDetail) {
                        id.setText(dockerContainerDetail.getId());
                        name.setText(dockerContainerDetail.getName());
                    }
                });
    }
}
