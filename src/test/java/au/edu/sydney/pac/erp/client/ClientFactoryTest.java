package au.edu.sydney.pac.erp.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientFactoryTest {

    @Test
    public void mkClientList_newObj_NotNull() {
        ClientFactory factory = new ClientFactory();
        assertNotNull(factory.makeClientList());
    }
}