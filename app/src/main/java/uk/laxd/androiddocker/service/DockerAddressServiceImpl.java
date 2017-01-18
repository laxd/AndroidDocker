package uk.laxd.androiddocker.service;

import javax.inject.Inject;

import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.dao.DockerDao;

/**
 * Created by lawrence on 06/01/17.
 */
public class DockerAddressServiceImpl implements DockerAddressService {

    @Inject
    protected DockerDao dockerDao;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Override
    public boolean isSetup() {
        return !dockerDao.requiresSetup();
    }

    @Override
    public boolean setupIfValid(String address) {
        boolean wasSetup = false;

        if(isValid(address)) {
            setup(address);
            wasSetup = true;
        }

        return wasSetup;
    }

    @Override
    public boolean isValid(String address) {
        try {
            return (dockerServiceFactory.createWithAddress(address)
                    .getVersion() != null);
        } catch (IllegalArgumentException e) {
            // Not a valid URL
            return false;
        }
    }

    @Override
    public void setup(String address) {
        dockerDao.setDockerAddress(address);
    }
}
