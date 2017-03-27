package uk.laxd.androiddocker.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.activity.DockerContainerActivity;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.rx.AdapterSubscriber;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersFragment extends DockerDtoListFragment {

    private Unbinder unbinder;

    private DockerContainerListAdapter dockerContainerAdapter;

    @BindView(R.id.container_list)
    protected ListView listView;

    @BindView(R.id.empty_list)
    protected TextView textView;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Inject
    protected DockerDao dockerDao;

    private Subscription sub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getActivity().getApplication())
                .getAndroidDockerComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.docker_containers, null);

        unbinder = ButterKnife.bind(this, view);

        listView.setEmptyView(textView);

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        if(sub != null) {
            sub.unsubscribe();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Timber.i("Starting ContainersFragment");

        dockerContainerAdapter = new DockerContainerListAdapter(getActivity(), R.layout.docker_container_list_row, new ArrayList<DockerContainer>());
        listView.setAdapter(dockerContainerAdapter);

        onRefresh();
    }

    @Override
    public Class<? extends Activity> getActivityClass() {
        return DockerContainerActivity.class;
    }

    public void onRefresh() {
        sub = dockerServiceFactory.getDockerService()
                .getContainers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdapterSubscriber<>(getContext(), dockerContainerAdapter));
    }

    @Override
    public ListView getListView() {
        return listView;
    }
}
