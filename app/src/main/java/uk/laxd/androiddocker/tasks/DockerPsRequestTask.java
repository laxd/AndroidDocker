package uk.laxd.androiddocker.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lawrence on 03/01/17.
 */

public class DockerPsRequestTask extends AsyncTask<Void, DockerPsDto, Void> {

    private Context context;
    private TextView textView;
    private ProgressDialog progressDialog;

    public DockerPsRequestTask(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String url = "http://YOUR_IP_HERE:4243/containers/json";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            DockerPsDto[] dockerPsDtos = restTemplate.getForObject(url, DockerPsDto[].class);

            publishProgress(dockerPsDtos);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        textView.setText("");
    }

    @Override
    protected void onProgressUpdate(DockerPsDto... dockerPsDtos) {
        TextView text = (TextView) textView;

        for(DockerPsDto dockerPsDto : dockerPsDtos) {
            text.append("\n");

            for(String name : dockerPsDto.getNames()) {
                text.append(name + " ");
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
