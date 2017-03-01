package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 06/01/17.
 */

public class DockerVersion extends DockerDto {

    @JsonProperty("Version")
    private String version;

    @JsonProperty("ApiVersion")
    private String apiVersion;

    @JsonProperty("GoVersion")
    private String goVersion;

    @JsonProperty("Os")
    private String os;

    @JsonProperty("Arch")
    private String arch;

    @JsonProperty("KernelVersion")
    private String kernelVersion;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getGoVersion() {
        return goVersion;
    }

    public void setGoVersion(String goVersion) {
        this.goVersion = goVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }
}
