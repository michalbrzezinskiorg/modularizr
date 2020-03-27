package com.decentralizer.spreadr.apiGateway.security;


import com.decentralizer.spreadr.modules.appconfig.AppconfigController;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Set;
import java.util.stream.Collectors;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
public class SpringControllersForSecurityTest {

    private final AppconfigController appconfigController = mock(AppconfigController.class);

    @Test
    public void shouldDetectControllers() {
        // given
        ArgumentCaptor<Controller> controllersCaptor = ArgumentCaptor.forClass(Controller.class);
        ArgumentCaptor<String> instanceIdCaptor = ArgumentCaptor.forClass(String.class);
        // when
        new SpringControllersForSecurity(appconfigController);
        // then
        verify(appconfigController, times(5)).addNewControllerToDatabase(controllersCaptor.capture(), instanceIdCaptor.capture());
        assert instanceIdCaptor.getValue().equals(INSTANCE_ID);
        assert !controllersCaptor.getAllValues().isEmpty();

    }

}