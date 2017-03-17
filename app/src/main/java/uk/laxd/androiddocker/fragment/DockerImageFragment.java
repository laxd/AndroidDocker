package uk.laxd.androiddocker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

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
import uk.laxd.androiddocker.dto.DockerImageDetail;
import uk.laxd.bytesize.ByteSize;

/**
 * Created by lawrence on 01/03/17.
 */

public class DockerImageFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Inject
    protected ByteSize byteSize;

    @BindView(R.id.image_id)
    protected TextView id;

    @BindView(R.id.image_tags)
    protected ViewGroup tags;

    @BindView(R.id.image_size)
    protected TextView size;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.docker_image, container, false);

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
                .getImage(getArguments().getString("id"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DockerImageDetail>() {
                    @Override
                    public void call(DockerImageDetail dockerImage) {
                        id.setText(dockerImage.getId());

                        for(String tag : dockerImage.getTags()) {
                            View view = getLayoutInflater(getArguments()).inflate(R.layout.docker_image_tag, tags);

                            TextView textView = (TextView) view.findViewById(R.id.image_tag);
                            textView.setText(tag);
                        }

                        size.setText(byteSize.getFormattedFileSize(new BigDecimal(dockerImage.getSize())));
                    }
                });
    }


}
