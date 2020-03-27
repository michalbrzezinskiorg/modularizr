package com.decentralizer.spreadr.apiGateway.security;


import com.decentralizer.spreadr.modules.appconfig.AppconfigController;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertEquals(instanceIdCaptor.getValue(), INSTANCE_ID);
        assertEquals(controllersCaptor.getAllValues().size(), 5);
    }

}