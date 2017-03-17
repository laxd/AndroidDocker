package uk.laxd.androiddocker.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import uk.laxd.androiddocker.DockerVersionService;
import uk.laxd.androiddocker.DockerVersionServiceFactory;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.dto.DockerVersion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lawrence on 12/01/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DockerAddressServiceImplTest {

    private static final String VALID_ADDRESS = "http://example.com";
    private static final String INVALID_ADDRESS = "not_a_url";

    @Mock
    private Observable<DockerVersion> VALID_VERSION;
    private Observable<DockerVersion> INVALID_VERSION = null;

    @Mock
    protected DockerDao dockerDao;

    @Mock
    protected DockerVersionServiceFactory dockerServiceFactory;

    @Mock
    private DockerVersionService dockerService;

    @InjectMocks
    private DockerAddressService dockerAddressService = new DockerAddressServiceImpl();

    @Before
    public void setUp() throws Exception {
        when(dockerDao.requiresSetup()).thenReturn(false);
        when(dockerServiceFactory.getForAddress(VALID_ADDRESS)).thenReturn(dockerService);
        when(dockerServiceFactory.getForAddress(INVALID_ADDRESS)).thenThrow(new IllegalArgumentException());
    }

    @Test
    public void testIsSetupDelegatesToRequiresSetup() throws Exception {
        dockerAddressService.isSetup();

        verify(dockerDao).requiresSetup();
    }

    @Test
    public void testIsSetupReturnsFalseWhenRequiresSetupIsTrue() throws Exception {
        when(dockerDao.requiresSetup()).thenReturn(true);

        boolean isSetup = dockerAddressService.isSetup();

        assertFalse(isSetup);
    }

    @Test
    public void testIsValidReturnsTrueIfNonNullVersionIsReturned() throws Exception {
        when(dockerService.getVersion()).thenReturn(VALID_VERSION);

        boolean isValid = dockerAddressService.isValid(VALID_ADDRESS);

        assertTrue(isValid);
    }

    @Test
    public void testIsValidReturnsFalseIfNullVersionIsReturned() throws Exception {
        when(dockerService.getVersion()).thenReturn(INVALID_VERSION);

        boolean isValid = dockerAddressService.isValid(VALID_ADDRESS);

        assertFalse(isValid);
    }

    @Test
    public void testIsValidReturnsFalseIfExceptionIsThrown() throws Exception {
        boolean isValid = dockerAddressService.isValid(INVALID_ADDRESS);

        assertFalse(isValid);
    }

    @Test
    public void testSetupPersistsInDockerDao() throws Exception {
        dockerAddressService.setup(VALID_ADDRESS);

        verify(dockerDao).setDockerAddress(VALID_ADDRESS);
    }

    @Test
    public void testSetupIfValidExecutesSetupIfValid() throws Exception {
        when(dockerService.getVersion()).thenReturn(VALID_VERSION);

        dockerAddressService.setupIfValid(VALID_ADDRESS);

        verify(dockerDao).setDockerAddress(VALID_ADDRESS);
    }

    @Test
    public void testSetupifValidDoesntExecuteSetupIfInvalid() throws Exception {
        when(dockerService.getVersion()).thenReturn(INVALID_VERSION);

        dockerAddressService.setupIfValid(INVALID_ADDRESS);

        verify(dockerDao, never()).setDockerAddress(INVALID_ADDRESS);

    }
}