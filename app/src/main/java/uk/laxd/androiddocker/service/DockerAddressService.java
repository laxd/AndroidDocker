package uk.laxd.androiddocker.service;

import java.util.Observable;

import uk.laxd.androiddocker.dao.DockerDao;

/**
 * Created by lawrence on 06/01/17.
 */

public interface DockerAddressService {

    boolean isSetup();
    boolean setupIfValid(String address);
    boolean isValid(String address);
    void setup(String address);

}
