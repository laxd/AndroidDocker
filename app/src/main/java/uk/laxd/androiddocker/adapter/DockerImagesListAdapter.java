package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends ViewHolderArrayAdapter<DockerImage, DockerImagesListAdapter.ViewHolder> {
    public DockerImagesListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerImagesListAdapter(Context context, int resource, List<DockerImage> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = createOrRestoreViewHolder(view, R.layout.docker_image_list_row);

        DockerImage dockerImage = getItem(position);

        if(dockerImage != null) {
            if(dockerImage.getRepoTags() != null) {
                viewHolder.imageName.setText(TextUtils.join(",", dockerImage.getRepoTags()));
            }
        }

        return viewHolder.getBaseView();
    }

    @Override
    public ViewHolder createNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbstractViewHolder {
        @BindView(R.id.image_name)
        TextView imageName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
