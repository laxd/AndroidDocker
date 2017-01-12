package uk.laxd.androiddocker.service;

/**
 * Created by lawrence on 06/01/17.
 */
public interface DockerAddressService {
    boolean isSetup();
    boolean setupIfValid(String address);
    boolean isValid(String address);
    void setup(String address);
}
