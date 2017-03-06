package uk.laxd.androiddocker.service;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.laxd.androiddocker.dto.DockerVersion;
import uk.laxd.androiddocker.fragment.SetupFragment;

/**
 * Created by lawrence on 04/03/17.
 */

public class DockerConnectivityService {

    public DockerVersion connect(String address) throws IOException {
        if (!URLUtil.isValidUrl(address)) {
            throw new IOException("Invalid URL");
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address + "/version")
                .build();

        Response response = client.newCall(request).execute();
        String version = response.body().string();

        ObjectMapper objectMapper = new ObjectMapper();
        DockerVersion dockerVersion = objectMapper.readValue(version, DockerVersion.class);

        Log.i(SetupFragment.class.toString(), "Contacted " + address + " successfully");

        if(TextUtils.isEmpty(dockerVersion.getVersion())) {
            throw new IOException("Invalid docker version: " + dockerVersion.getVersion());
        }

        return dockerVersion;
    }

}
