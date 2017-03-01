package uk.laxd.androiddocker.dto;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names = new String[0];

    @JsonProperty("State")
    private ContainerState state;

    @JsonProperty("Image")
    private String image;

    public DockerContainer() {
    }

    public DockerContainer(String[] names, ContainerState state, String image) {
        this.names = names;
        this.state = state;
        this.image = image;
    }

    public String getName() {
        if(names.length == 0) {
            return "";
        }
        return names[0].replaceFirst("/", "");
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public ContainerState getState() {
        return state;
    }

    public void setState(ContainerState state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum ContainerState {

        CREATED("created", android.R.color.holo_red_dark),
        RESTARTING("restarting", android.R.color.holo_orange_dark),
        RUNNING("running", android.R.color.holo_green_dark),
        PAUSED("paused", android.R.color.holo_orange_dark),
        EXITED("exited", android.R.color.holo_red_dark),
        DEAD("dead", android.R.color.holo_red_dark);

        private String stateName;

        @JsonIgnore
        private int imageResource;

        ContainerState(String stateName, int imageResource) {
            this.stateName = stateName;
            this.imageResource = imageResource;
        }

        @JsonCreator
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
