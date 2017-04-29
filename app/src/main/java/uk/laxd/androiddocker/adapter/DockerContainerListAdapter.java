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
import uk.laxd.androiddocker.databinding.DockerContainerListRowBinding;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerContainerListAdapter extends RecyclerView.Adapter<DockerContainerListAdapter.DockerContainerViewHolder> {

    private List<DockerContainer> dockerContainers;

    public DockerContainerListAdapter(@NotNull List<DockerContainer> dockerContainers) {
        this.dockerContainers = dockerContainers;
    }

    @Override
    public DockerContainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        DockerContainerListRowBinding binding = DockerContainerListRowBinding.inflate(layoutInflater, parent, false);

        return new DockerContainerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DockerContainerViewHolder holder, int position) {
        DockerContainer dockerContainer = dockerContainers.get(position);

        holder.binding.setContainer(dockerContainer);
        holder.binding.executePendingBindings();

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("Replacing content_frame with container");
                DockerContainer container = dockerContainers.get(holder.getAdapterPosition());

                Intent intent = new Intent(v.getContext(), DockerContainerActivity.class);

                intent.putExtra("id", container.getId());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dockerContainers.size();
    }

    public class DockerContainerViewHolder extends RecyclerView.ViewHolder {

        public DockerContainerListRowBinding binding;

        public DockerContainerViewHolder(DockerContainerListRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
