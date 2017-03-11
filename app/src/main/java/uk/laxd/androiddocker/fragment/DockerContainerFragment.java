package uk.laxd.androiddocker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

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

    @BindView(R.id.container_id)
    protected TextView id;

    @BindView(R.id.container_name)
    protected TextView name;

    @BindView(R.id.container_mounts)
    protected ViewGroup mountContainer;

    @BindView(R.id.gateway)
    protected TextView gateway;

    @BindView(R.id.ipaddress)
    protected TextView ipAddress;

    @BindView(R.id.port_binding)
    protected TextView portBinding;

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

                        List<Mount> mounts = dockerContainerDetail.getMounts();

                        // TODO: refactor this out?
                        for(Mount mount : mounts) {
                            View view = getLayoutInflater(getArguments()).inflate(R.layout.docker_container_mount_row, null);

                            TextView source = (TextView) view.findViewById(R.id.mount_source);
                            TextView destination = (TextView) view.findViewById(R.id.mount_destination);

                            source.setText(mount.getSource());
                            destination.setText(mount.getDestination());

                            mountContainer.addView(view);
                        }

                        NetworkSettings networkSettings = dockerContainerDetail.getNetworkSettings();

                        if(networkSettings != null) {
                            gateway.setText(networkSettings.getGateway());
                            ipAddress.setText(networkSettings.getIp());

                            if(networkSettings.getPorts() != null) {
                                for(PortMapping portMapping : networkSettings.getPorts().getPortMappings()) {
                                    View view = getLayoutInflater(getArguments()).inflate(R.layout.docker_container_port_mapping_row, null);

                                    TextView source = (TextView) view.findViewById(R.id.port_source);
                                    TextView destination = (TextView) view.findViewById(R.id.port_destination);

                                    source.setText(portMapping.getSource());
                                    destination.setText(TextUtils.join(", ", portMapping.getDestinations()));
                                }
                            }

                            if(networkSettings.getPorts() != null &&
                                    networkSettings.getPorts().getPortMappings() != null &&
                                    networkSettings.getPorts().getPortMappings().size() > 0) {
                                portBinding.setText(networkSettings.getPorts().getPortMappings().get(0).getSource());
                            }
                        }
                    }
                });
    }
}
