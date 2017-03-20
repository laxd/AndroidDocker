package uk.laxd.androiddocker.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.databinding.DockerContainerBinding;
import uk.laxd.androiddocker.databinding.DockerContainerMountRowBinding;
import uk.laxd.androiddocker.databinding.DockerContainerPortMappingRowBinding;
import uk.laxd.androiddocker.dto.DockerContainerDetail;
import uk.laxd.androiddocker.dto.DockerImageDetail;
import uk.laxd.androiddocker.dto.Mount;
import uk.laxd.androiddocker.dto.NetworkSettings;
import uk.laxd.androiddocker.dto.PortMapping;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @BindView(R.id.container_image)
    protected TextView image;

    @BindView(R.id.container_mounts)
    protected ViewGroup mountContainer;

    @BindView(R.id.port_bindings)
    protected ViewGroup portBindings;

    private DockerContainerBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.docker_container, container, false);

        View root = binding.getRoot();

        unbinder = ButterKnife.bind(this, root);

        ((AndroidDockerApplication) getActivity().getApplication())
                .getAndroidDockerComponent().inject(this);


        return root;
    }

    @OnClick(R.id.container_image)
    public void onImageClick() {
        if(image != null && !TextUtils.isEmpty(image.getText())) {
            String containerImage = image.getText().toString();

            dockerServiceFactory.getDockerService()
                    .getImage(containerImage)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<DockerImageDetail>() {
                        @Override
                        public void call(DockerImageDetail dockerImageDetail) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", dockerImageDetail.getId());

                            FragmentTransaction tx = getFragmentManager().beginTransaction();

                            Fragment fragment = new DockerImageFragment();
                            fragment.setArguments(bundle);
                            tx.replace(R.id.content_frame, fragment);
                            tx.addToBackStack(null);
                            tx.commit();
                        }
                    });
        }
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

                        binding.setContainer(dockerContainerDetail);

                        for(Mount mount : dockerContainerDetail.getMounts()) {
                            DockerContainerMountRowBinding mountRowBinding = DockerContainerMountRowBinding.inflate(
                                    getLayoutInflater(getArguments()),
                                    mountContainer,
                                    true);

                            mountRowBinding.setMount(mount);
                        }

                        NetworkSettings networkSettings = dockerContainerDetail.getNetworkSettings();

                        if(networkSettings != null) {
                            if(networkSettings.getPorts() != null) {
                                for(PortMapping portMapping : networkSettings.getPorts().getPortMappings()) {

                                    DockerContainerPortMappingRowBinding portMappingRowBinding = DockerContainerPortMappingRowBinding.inflate(
                                            getLayoutInflater(getArguments()),
                                            portBindings,
                                            true);

                                    portMappingRowBinding.setPort(portMapping);
                                }
                            }
                        }
                    }
                });
    }
}
