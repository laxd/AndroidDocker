package uk.laxd.androiddocker.dto;

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
        RUNNING("running", android.R.drawable.presence_online),
        STOPPED("stopped", android.R.drawable.presence_offline);

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

            // Assume stopped
            return STOPPED;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
