package uk.laxd.androiddocker.dto;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Image")
    private String image;

    public String getName() {
        return names[0].replaceFirst("/", "");
    }

    public ContainerState getState() {
        return ContainerState.getState(state);
    }

    public String getImage() {
        return image;
    }

    public enum ContainerState {

        CREATED("created", android.R.color.holo_red_dark),
        RESTARTING("restarting", android.R.color.holo_orange_dark),
        RUNNING("running", android.R.color.holo_green_dark),
        PAUSED("paused", android.R.color.holo_orange_dark),
        EXITED("exited", android.R.color.holo_red_dark),
        DEAD("dead", android.R.color.holo_red_dark);

        private String stateName;
        private int imageResource;

        ContainerState(String stateName, int imageResource) {
            this.stateName = stateName;
            this.imageResource = imageResource;
        }

        public static ContainerState getState(String state) {
            for(ContainerState containerState : values()) {
                if(containerState.stateName.equals(state)) {
                    return containerState;
                }
            }

            Log.w(DockerContainer.class.toString(), "Couldn't find state '" + state + "', assuming 'exited' instead");
            return EXITED;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
