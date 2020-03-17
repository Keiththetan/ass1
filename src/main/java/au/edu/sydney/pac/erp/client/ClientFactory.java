package au.edu.sydney.pac.erp.client;

/**
 * Allows test suit to obtain implementation. Should not rely on guessing name of
 * ClientList implementation. ("Factory pattern") e.g might be ClientListImpl,
 * DefaultClientList, etc.
 */

public class ClientFactory {

    public ClientList makeClientList() {
        return new ClientListImpl();
    }
}
