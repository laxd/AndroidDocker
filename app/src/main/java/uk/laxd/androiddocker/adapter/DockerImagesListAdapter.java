package uk.laxd.androiddocker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.dsl.NotNull;

import java.util.List;

import timber.log.Timber;
import uk.laxd.androiddocker.activity.DockerContainerActivity;
import uk.laxd.androiddocker.databinding.DockerImageListRowBinding;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends RecyclerView.Adapter<DockerImagesListAdapter.DockerImageViewHolder> {

    private List<DockerImage> dockerImages;

    public DockerImagesListAdapter(@NotNull List<DockerImage> dockerImages) {
        this.dockerImages = dockerImages;
    }

    @Override
    public DockerImagesListAdapter.DockerImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        DockerImageListRowBinding binding = DockerImageListRowBinding.inflate(layoutInflater, parent, false);

        return new DockerImagesListAdapter.DockerImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DockerImagesListAdapter.DockerImageViewHolder holder, int position) {
        DockerImage dockerImage = dockerImages.get(position);

        holder.binding.setImage(dockerImage);
        holder.binding.executePendingBindings();

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("Replacing content_frame with container");
                DockerImage image = dockerImages.get(holder.getAdapterPosition());

                Intent intent = new Intent(v.getContext(), DockerContainerActivity.class);

                intent.putExtra("id", image.getId());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dockerImages.size();
    }

    public class DockerImageViewHolder extends RecyclerView.ViewHolder {

        public DockerImageListRowBinding binding;

        public DockerImageViewHolder(DockerImageListRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
