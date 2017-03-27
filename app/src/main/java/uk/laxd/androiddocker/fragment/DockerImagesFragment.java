package uk.laxd.androiddocker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.activity.DockerImageActivity;
import uk.laxd.androiddocker.adapter.DockerImagesListAdapter;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;
import uk.laxd.androiddocker.rx.AdapterSubscriber;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesFragment extends DockerDtoListFragment {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    private DockerService dockerService;

    @BindView(R.id.image_list)
    protected ListView listView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private ArrayAdapter<DockerImage> dockerImageAdapter;

    private Subscription sub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getActivity().getApplication())
                .getAndroidDockerComponent()
                .inject(this);

        dockerService = dockerServiceFactory.getDockerService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.docker_images, null);

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

        dockerImageAdapter = new DockerImagesListAdapter(getActivity(), R.layout.docker_image_list_row, new ArrayList<DockerImage>());
        listView.setAdapter(dockerImageAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DockerImagesFragment.this.onRefresh();
            }
        });

        onRefresh();
    }

    public void onRefresh() {
        sub = dockerServiceFactory.getDockerService()
                .getImages(true, false)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdapterSubscriber<>(getContext(), dockerImageAdapter, swipeRefreshLayout));
    }

    @Override
    public Class<? extends Activity> getActivityClass() {
        return DockerImageActivity.class;
    }

    @Override
    public ListView getListView() {
        return listView;
    }
}
