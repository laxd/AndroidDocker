package uk.laxd.androiddocker.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.container_list)
    protected RecyclerView listView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

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

        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DockerContainersFragment.this.onRefresh();
            }
        });

        onRefresh();
    }

    public void onRefresh() {
        sub = dockerServiceFactory.getDockerService()
                .getContainers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DockerContainer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DockerContainer> dockerContainers) {
                        DockerContainerListAdapter adapter = new DockerContainerListAdapter(dockerContainers);

                        listView.setAdapter(adapter);
                        listView.smoothScrollToPosition(0);

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
