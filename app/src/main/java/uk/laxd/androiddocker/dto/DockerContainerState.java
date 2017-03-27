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

    CREATED("created", android.R.color.holo_red_dark),
    RESTARTING("restarting", android.R.color.holo_orange_dark),
    RUNNING("running", android.R.color.holo_green_dark),
    PAUSED("paused", android.R.color.holo_orange_dark),
    EXITED("exited", android.R.color.holo_red_dark),
    DEAD("dead", android.R.color.holo_red_dark);

    private String stateName;

    @JsonIgnore
    private int imageResource;

    DockerContainerState(String stateName, int imageResource) {
        this.stateName = stateName;
        this.imageResource = imageResource;
    }

    @JsonCreator
    public static DockerContainerState getState(String state) {
        for (DockerContainerState containerState : values()) {
            if (containerState.stateName.equals(state)) {
                return containerState;
            }
        }

        Timber.w("Couldn't find state '%s', assuming '%s' instead", state, EXITED);
        return EXITED;
    }

    public int getImageResource() {
        return imageResource;
    }
}
