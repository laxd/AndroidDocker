package uk.laxd.androiddocker.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerVersion;
import uk.laxd.androiddocker.service.DockerConnectivityService;

/**
 * Created by lawrence on 06/01/17.
 */
public class SetupFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    protected DockerDao dockerDao;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Inject
    protected DockerConnectivityService dockerConnectivityService;

    @BindView(R.id.docker_address)
    protected EditText editText;

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
        View view = inflater.inflate(R.layout.setup, null);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onSetupSubmit() {
        final String address = editText.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Checking docker address...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        try {
            // Validate connectivity
            DockerVersion dockerVersion = dockerConnectivityService.connect(address);

            Log.d(SetupFragment.class.toString(), "Connected to docker v" + dockerVersion.getVersion() + ", API v" + dockerVersion.getApiVersion());

            dockerDao.setDockerAddress(address);
            dockerServiceFactory.updateDockerAddress(address);

            FragmentTransaction tx = getFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame, new DockerContainersFragment());
            tx.commit();
        } catch (IOException e) {
            Log.w(SetupFragment.class.toString(), "Encountered error while trying to contact docker service", e);

            Toast toast = Toast.makeText(getActivity(), "Could not contact docker service", Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            progressDialog.dismiss();
        }
    }
}
