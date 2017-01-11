package uk.laxd.androiddocker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerVersion;
import uk.laxd.androiddocker.module.RetrofitModule;

/**
 * Created by lawrence on 06/01/17.
 */
public class SetupActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    DockerService dockerService;

    @BindView(R.id.docker_address)
    protected EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup);

        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onSetupSubmit() {
        final String address = editText.getText().toString();

        if (!URLUtil.isValidUrl(address)) {
            Toast toast = Toast.makeText(SetupActivity.this, "Invalid URL!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SetupActivity.this);
        progressDialog.setMessage("Checking docker address...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        // TODO: Find a way to inject this...
        RetrofitModule retrofitModule = new RetrofitModule(SetupActivity.this);
        final DockerService dockerService = retrofitModule.provideDockerService(retrofitModule.provideRetrofitWithAddress(address));

        dockerService.getVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DockerVersion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.w(SetupActivity.class.toString(), "Encountered error while trying to contact docker service", throwable);
                        progressDialog.cancel();

                        Toast toast = Toast.makeText(SetupActivity.this, "Could not contact docker service", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onNext(DockerVersion dockerVersion) {
                        if(TextUtils.isEmpty(dockerVersion.getVersion())) {
                            progressDialog.cancel();
                        }
                        else {

                            Log.d(SetupActivity.class.toString(), "Setting up docker address as " + address);

                            DockerDao dockerDao = DockerDao.getInstance(getApplicationContext());

                            dockerDao.setDockerAddress(address);

                            progressDialog.dismiss();

                            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
