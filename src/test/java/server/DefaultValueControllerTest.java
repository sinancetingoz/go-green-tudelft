package server;

import org.junit.Before;
import org.junit.Test;
import pojos.DefaultValue;
import services.DefaultValueService;
import services.SessionService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultValueControllerTest {
    DefaultValueController dvc = new DefaultValueController();
    DefaultValueService dvs = mock(DefaultValueService.class);
    SessionService ss = mock(SessionService.class);
    DefaultValue dv;

    @Before
    public void init() {
        dvc.setDvs(dvs);
        dvc.setSs(ss);
        dv = new DefaultValue();
    }

    @Test
    public void testAddDefaultValue() {
        when(ss.sessionExists("test")).thenReturn(false);
        assertFalse(dvc.addDefaultValue(dv, "test"));

        when(ss.sessionExists("test")).thenReturn(true);
        when(dvs.createDefaultValue(dv)).thenReturn(true);
        assertTrue(dvc.addDefaultValue(dv, "test"));
    }

    @Test
    public void testDeleteDefaultValue() {
        when(ss.sessionExists("test")).thenReturn(false);
        dvc.deleteDefaultValue("dv", "test");
        when(ss.sessionExists("test")).thenReturn(true);
        dvc.deleteDefaultValue("dv", "test");
    }
}