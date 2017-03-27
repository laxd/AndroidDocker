package uk.laxd.androiddocker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.DockerVersionServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerVersion;

/**
 * Created by lawrence on 24/03/17.
 */
public class SetupActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    protected DockerDao dockerDao;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Inject
    protected DockerVersionServiceFactory dockerVersionServiceFactory;

    @BindView(R.id.docker_address)
    protected EditText editText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup);

        unbinder = ButterKnife.bind(this);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onSetupSubmit() {
        final String address = editText.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking docker address...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        // Validate connectivity
        try {
            dockerVersionServiceFactory.getForAddress(address)
                    .getVersion()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DockerVersion>() {
                        @Override
                        public void onCompleted() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.w("Failed to connect to docker");

                            Toast toast = Toast.makeText(SetupActivity.this, "Could not contact docker service", Toast.LENGTH_SHORT);
                            toast.show();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onNext(DockerVersion dockerVersion) {
                            Timber.d("Connected to docker v%s, API v%s", dockerVersion.getVersion(), dockerVersion.getApiVersion());

                            dockerDao.setDockerAddress(address);
                            dockerServiceFactory.updateDockerAddress(address);

                            finish();
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast toast = Toast.makeText(SetupActivity.this, "Invalid URL", Toast.LENGTH_SHORT);
            toast.show();

            progressDialog.dismiss();
        }
    }
}
