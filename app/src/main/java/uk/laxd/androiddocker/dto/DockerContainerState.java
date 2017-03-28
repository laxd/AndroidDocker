package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import timber.log.Timber;

/**
 * Created by lawrence on 11/03/17.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DockerContainerState {

    CREATED("Created", android.R.color.holo_red_dark),
    RESTARTING("Restarting", android.R.color.holo_orange_dark),
    RUNNING("Running", android.R.color.holo_green_dark),
    PAUSED("Paused", android.R.color.holo_orange_dark),
    EXITED("Exited", android.R.color.holo_red_dark),
    DEAD("Dead", android.R.color.holo_red_dark);

    private String name;

    @JsonIgnore
    private int imageResource;

    DockerContainerState(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    @JsonCreator
    public static DockerContainerState getState(String state) {
        for (DockerContainerState containerState : values()) {
            if (containerState.name.toUpperCase().equals(state.toUpperCase())) {
                return containerState;
            }
        }

        Timber.w("Couldn't find state '%s', assuming '%s' instead", state, EXITED);
        return EXITED;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }
}
