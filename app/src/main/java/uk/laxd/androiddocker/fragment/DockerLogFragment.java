package uk.laxd.androiddocker.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerImageDetail;
import uk.laxd.androiddocker.dto.DockerLog;
import uk.laxd.bytesize.ByteSize;

/**
 * Created by lawrence on 28/03/17.
 */

public class DockerLogFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.container_logs)
    protected TextView log;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.docker_log_fragment, container, false);

//        binding = DataBindingUtil.inflate(
//                inflater, R.layout.docker_image, container, false);
//        binding.setImage(new DockerImageDetail());
//        binding.setBytesize(new ByteSize());
//        binding.executePendingBindings();
//
//        View root = binding.getRoot();

        unbinder = ButterKnife.bind(this, root);

        ((AndroidDockerApplication) getActivity().getApplication())
                .getAndroidDockerComponent().inject(this);

        loadLogs();

        return root;
    }

    private void loadLogs() {
        dockerServiceFactory.getDockerService()
                .getLogs(getArguments().getString("id"), true, true, null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Completed log call");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.setText(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            log.setText(response.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
