package au.edu.sydney.pac.erp.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/** PLAUSIBLE BUG LIST --- These are not mentioned in the API.
 *  1) String params being only whitespaces (first/last name, phone no. & department)
 *  2) PhoneNumber param must be checked if numeric (Possible to input letters instead)
 *  3)
 */

public class ClientImplTest {

    private Client client;
    
    @Before()
    public void resetClient() {
        client = new ClientImpl(1, "keith", "tan", "12345");
    }

    /**
     *  Testing ClientImpl constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_IDzero_IArgExcep() {
       Client newClient = new ClientImpl(0, "Keith", "Tan", "12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_IDNeg_IArgExcep() {
        Client newClient = new ClientImpl(-5, "Keith", "Tan", "12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_1stNameNull_IArgExcep() {
        Client newClient = new ClientImpl(1, null, "Tan", "12345");
    }

//    // ---------------------- Potential Bug ----------------------
//    @Test(expected = IllegalArgumentException.class)
//    public void ClientImpl_1stNameSpaces_IArgExcep() {
//        Client newClient = new ClientImpl(1, "  ", "tan", "12345");
//    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_1stNameEmpty_IArgExcep() {
        Client newClient = new ClientImpl(1, "", "Tan", "12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_LastNameNull_IArgExcep() {
        Client newClient = new ClientImpl(1, "Keith", null, "12345");
    }

//    // ---------------------- Potential Bug ----------------------
//    @Test(expected = IllegalArgumentException.class)
//    public void ClientImpl_LastNameSpaces_IArgExcep() {
//        Client newClient = new ClientImpl(1, "Keith", "  ", "12345");
//    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_LastNameEmpty_IArgExcep() {
        Client newClient = new ClientImpl(1, "Keith", "", "12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_phoneNull_IArgExcep() {
        Client newClient = new ClientImpl(1, "Keith", "Tan", null);
    }

//    // ---------------------- Potential Bug ----------------------
//    @Test(expected = IllegalArgumentException.class)
//    public void ClientImpl_phoneSpaces_IArgExcep() {
//        Client newClient = new ClientImpl(1, "  ", "tan", "  ");
//    }

    @Test(expected = IllegalArgumentException.class)
    public void ClientImpl_phoneEmpty_IArgExcep() {
        Client newClient = new ClientImpl(1, "Keith", "Tan", "");
    }

    @Test
    public void ClientImpl_hasID_Int() {
        assertEquals(1, client.getID());
    }

    @Test
    public void ClientImpl_hasFirst_String() {
        assertEquals("keith", client.getFirstName());
    }

    @Test
    public void ClientImpl_hasLast_String() {
        assertEquals("tan", client.getLastName());
    }

    @Test
    public void ClientImpl_hasNum_String() {
        assertEquals("12345", client.getPhoneNumber());
    }

    @Test
    public void ClientImpl_notAssigned_False() {
        assertFalse(client.isAssigned());
    }



    /**
     * Testing assignDepartment method
     */
    @Test(expected = IllegalStateException.class)
    public void assignDpt_AlredyAssign_IStateExcep() {
        client.assignDepartment("IT");
        client.assignDepartment("HR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assignDpt_NullParam_IArgExcep() {
        client.assignDepartment(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assignDpt_EmptyParam_IArgExcep() {
        client.assignDepartment("");
    }

//    // ---------------------- Potential Bug ----------------------
//    @Test(expected = IllegalArgumentException.class)
//    public void assignDpt_Whitespaces_IArgExcep() {
//        client.assignDepartment("     ");
//    }

    @Test
    public void assignDpt_GetDpt_String() {
        client.assignDepartment("IT");
        assertEquals("IT", client.getDepartmentCode());

    }

    @Test
    public void assignDpt_IsAssigned_True() {
        client.assignDepartment("HR");
        assertTrue(client.isAssigned());
    }



    /**
     * Testing getDepartmentCode method
     */
    @Test
    public void getDpt_isNull_Null() {
        assertNull(client.getDepartmentCode());
    }

    @Test
    public void getDpt_Assigned_String() {
        client.assignDepartment("IT");
        assertEquals("IT", client.getDepartmentCode());
    }



    /**
     * Test getFirstName method
     */
    @Test
    public void getFirstName_void_String() {
        assertEquals("keith", client.getFirstName());
    }



    /**
     * Test getLastName method
     */
    @Test
    public void getLastName_void_String() {
        assertEquals("tan", client.getLastName());
    }



    /**
     * Test getPhoneNumber method
     */
    @Test
    public void getPhoneNo_void_String() {
        assertEquals("12345", client.getPhoneNumber());
    }
}