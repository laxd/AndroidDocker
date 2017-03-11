package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

/**
 * Created by lawrence on 11/03/17.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum DockerContainerState {

    CREATED("created", android.R.color.holo_red_dark),
    RESTARTING("restarting", android.R.color.holo_orange_dark),
    RUNNING("running", android.R.color.holo_green_dark),
    PAUSED("paused", android.R.color.holo_orange_dark),
    EXITED("exited", android.R.color.holo_red_dark),
    DEAD("dead", android.R.color.holo_red_dark);

    private String stateName;

    @JsonIgnore
    @Getter
    private int imageResource;

    @JsonCreator
    public static DockerContainerState getState(String state) {
        for (DockerContainerState containerState : values()) {
            if (containerState.stateName.equals(state)) {
                return containerState;
            }
        }

        Timber.w("Couldn't find state '%s', assuming 'exited' instead", state);
        return EXITED;
    }
}
