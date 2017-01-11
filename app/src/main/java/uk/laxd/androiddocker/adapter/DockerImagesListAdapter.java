package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends ArrayAdapter<DockerImage> {
    public DockerImagesListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerImagesListAdapter(Context context, int resource, List<DockerImage> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            view = inf.inflate(R.layout.docker_image_list_row, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DockerImage dockerImage = getItem(position);

        if(dockerImage != null) {
            if(dockerImage.getRepoTags() != null) {
                viewHolder.imageName.setText(TextUtils.join(",", dockerImage.getRepoTags()));
            }
        }

        return view;
    }

    private static class ViewHolder {
        @BindView(R.id.image_name)
        TextView imageName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
