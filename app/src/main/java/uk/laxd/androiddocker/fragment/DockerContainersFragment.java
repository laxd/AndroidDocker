package uk.laxd.androiddocker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.rx.AdapterSubscriber;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder unbinder;

    private DockerContainerListAdapter dockerContainerAdapter;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.container_list)
    protected ListView listView;

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

        Log.i("log", "Starting ContainersFragment");

        dockerContainerAdapter = new DockerContainerListAdapter(getActivity(), R.layout.docker_container_list_row, new ArrayList<DockerContainer>());
        listView.setAdapter(dockerContainerAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @OnItemClick(R.id.container_list)
    public void onContainerClick(int position) {
        Log.i("log", "Replacing content_frame with container");
        DockerContainer dockerContainer = dockerContainerAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putString("id", dockerContainer.getId());

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        Fragment fragment = new DockerContainerFragment();
        fragment.setArguments(bundle);
        tx.replace(R.id.content_frame, fragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    @Override
    public void onRefresh() {

        sub = dockerServiceFactory.getDockerService()
                .getContainers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdapterSubscriber<>(getContext(), dockerContainerAdapter, swipeRefreshLayout));
    }
}
