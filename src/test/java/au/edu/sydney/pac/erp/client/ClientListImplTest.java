package au.edu.sydney.pac.erp.client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClientListImplTest {

    private ClientListImpl clientListImpl;

    @Before
    public void resetClientList() {
        this.clientListImpl = new ClientListImpl();
    }


    /**
     *  Testing addClient method
     *  Testing of creation of new Client object will be done in findAll method instead. Testcases written
     *  checks for only breached argument requirements.
     */
    @Test(expected = IllegalStateException.class)
    public void addClient_IDNotUnique_IStateExcep() {
        this.clientListImpl.addClient(197423, "ghi", "jkl", "411");
        this.clientListImpl.addClient(197423, "abc", "def", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_IDZero_IArgExcep() {
        this.clientListImpl.addClient(0,"abc", "def", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_IDNeg_IArgExcep() {
        this.clientListImpl.addClient(-5,"abc", "def", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_1stNameNull_IArgExcep() {
        this.clientListImpl.addClient(1, null, "def", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_1stNameEmpty_IArgExcep() {
        this.clientListImpl.addClient(1, "", "def", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_LastNameNull_IArgExcep() {
        this.clientListImpl.addClient(1, "abc", null, "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_LastNameEmpty_IArgExcep() {
        this.clientListImpl.addClient(1, "abc", "", "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_phoneNull_IArgExcep() {
        this.clientListImpl.addClient(1,"abc", "def", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_phoneEmpty_IArgExcep() {
        this.clientListImpl.addClient(1, "abc", "def", "");
    }



    /**
     *  Testing clear method
     *  Cannot get size of List<Client>, so try clearing all existing clients in list, before re-adding
     *  them into List. Since List only allows unique client ID's if Clients not removed, error should
     *  appear.
     */
    @Test
    public void clear_rmClients_AddSameID() {
        this.clientListImpl.addClient(112843,"abc", "def", "123");
        this.clientListImpl.addClient(200377,"ghi", "jkl", "456");
        this.clientListImpl.clear();
        this.clientListImpl.addClient(200377,"ccc","ddd", "097");
        this.clientListImpl.addClient(112843,"aaa","bbb","111");
    }


    /**
     * Testing findAll method
     */
    @Test
    public void findAll_ListEmpty_SizeZero() {
        List<Client> resultList = this.clientListImpl.findAll();
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAll_ListNotNull_True() {
        List<Client> resultList = this.clientListImpl.findAll();
        assertNotNull(resultList);
    }

    @Test
    public void findAll_NotEmpty_CheckIDs() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(9730, "c", "d", "0987");
        this.clientListImpl.addClient(8387, "e", "f", "1994");
        List<Client> resultList = this.clientListImpl.findAll();

        assertEquals(4110, resultList.get(0).getID());
        assertEquals(9730, resultList.get(1).getID());
        assertEquals(8387, resultList.get(2).getID());
    }


    /**
     * Testing findAll(boolean) method
     */
    @Test
    public void findAllBool_AllAsgFal_SizeZero() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(9730, "c", "d", "0987");

        this.clientListImpl.findOne(4110).assignDepartment("IT");
        this.clientListImpl.findOne(9730).assignDepartment("IT");

        assertEquals(0, this.clientListImpl.findAll(false).size());
    }

    @Test
    public void findAllBool_AllNotAsgFal_SizeTwo() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(9730, "c", "d", "0987");

        List<Client> resultList = this.clientListImpl.findAll(false);
        assertEquals(2, resultList.size());
        assertEquals(4110, resultList.get(0).getID());
        assertEquals(9730, resultList.get(1).getID());
    }

    @Test
    public void findAllBool_AllAsgTrue_SizeTwo() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(9730, "c", "d", "0987");

        this.clientListImpl.findOne(4110).assignDepartment("IT");
        this.clientListImpl.findOne(9730).assignDepartment("IT");

        List<Client> resultList = this.clientListImpl.findAll(true);
        assertEquals(2, resultList.size());
        assertEquals(4110, resultList.get(0).getID());
        assertEquals(9730, resultList.get(1).getID());
    }

    @Test
    public void findAllBool_AllNotAsgTrue_SizeZero() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(9730, "c", "d", "0987");

        List<Client> resultList = this.clientListImpl.findAll(true);
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAllBool_NotAsg_SizeOne() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(123, "c", "d", "0987");
        this.clientListImpl.addClient(596, "e", "f", "8792");
        this.clientListImpl.addClient(888, "g", "h", "4127");
        this.clientListImpl.addClient(561, "i", "i", "6737");

        this.clientListImpl.findOne(596).assignDepartment("HR");
        List<Client> resultList = this.clientListImpl.findAll(true);
        assertEquals(1, resultList.size());
        assertEquals("e", resultList.get(0).getFirstName());
    }

    @Test
    public void findAllBool_Asg_SizeFour() {
        this.clientListImpl.addClient(4110, "a", "b", "1234");
        this.clientListImpl.addClient(123, "c", "d", "0987");
        this.clientListImpl.addClient(596, "e", "f", "8792");
        this.clientListImpl.addClient(888, "g", "h", "4127");
        this.clientListImpl.addClient(561, "i", "i", "6737");

        this.clientListImpl.findOne(596).assignDepartment("HR");
        List<Client> resultList = this.clientListImpl.findAll(false);
        assertEquals(4, resultList.size());
        assertEquals("a", resultList.get(0).getFirstName());
        assertEquals("c", resultList.get(1).getFirstName());
        assertEquals("g", resultList.get(2).getFirstName());
        assertEquals("i", resultList.get(3).getFirstName());
    }



    /**
     * Testing findAll(String...) method
     */
    // Solved the warning message by casting null as a String. However line 55-56
    // would then be skipped, but handled by line 59
    @Test
    public void findAllStr_ParamNull_SizeZero() {
        List<Client> resultList = this.clientListImpl.findAll((String) null);
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAllStr_ArrayNull_SizeZero() {
        String[] dptCodes = null;
        List<Client> resultList = this.clientListImpl.findAll(dptCodes);
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAllStr_ArrayEmpty_SizeZero() {
        this.clientListImpl.addClient(1, "a", "b", "10");
        this.clientListImpl.addClient(2, "c", "d", "11");
        this.clientListImpl.findOne(1).assignDepartment("IT");
        this.clientListImpl.findOne(2).assignDepartment("HR");

        String[] dptCodes = {};
        List<Client> resultList = this.clientListImpl.findAll(dptCodes);
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAllStr_NoDptMatch_SizeZero() {
        this.clientListImpl.addClient(1, "a", "b", "10");
        this.clientListImpl.addClient(2, "c", "d", "11");
        this.clientListImpl.findOne(1).assignDepartment("IT");
        this.clientListImpl.findOne(2).assignDepartment("HR");

        String[] dptCodes = {"Management", "Admin"};
        List<Client> resultList = this.clientListImpl.findAll(dptCodes);
        assertEquals(0, resultList.size());
    }

    @Test
    public void findAllStr_DptNull_SizeZero() {
        this.clientListImpl.addClient(1, "a", "b", "10");
        this.clientListImpl.addClient(2, "c", "d", "11");
        this.clientListImpl.findOne(1).assignDepartment("IT");
        this.clientListImpl.findOne(2).assignDepartment("HR");

        String[] dptCodes = {null, "HR"};
        List<Client> resultList = this.clientListImpl.findAll(dptCodes);
        assertEquals(1, resultList.size());
        assertEquals(2, resultList.get(0).getID());
    }

    @Test
    public void findAllStr_SameCode_SizeTwo() {
        // ID 3 not assigned
        this.clientListImpl.addClient(1, "a", "b", "10");
        this.clientListImpl.addClient(2, "c", "d", "11");
        this.clientListImpl.addClient(3, "e", "f", "12");
        this.clientListImpl.findOne(1).assignDepartment("IT");
        this.clientListImpl.findOne(2).assignDepartment("HR");

        String[] dptCodes = {"HR", "IT"};
        List<Client> resultList = this.clientListImpl.findAll(dptCodes);
        assertEquals(2, resultList.size());
        assertEquals(2, resultList.get(0).getID());
        assertEquals(1, resultList.get(1).getID());
    }


    /**
     * Testing findOne method
     */
    @Test(expected = IllegalArgumentException.class)
    public void findOne_IDZero_IArgExcep() {
        this.clientListImpl.findOne(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOne_IDNeg_IArgExcep() {
        this.clientListImpl.findOne(-10);
    }

    @Test
    public void findOne_EmptyList_Null() {
        assertNull(this.clientListImpl.findOne(1324));
    }

    @Test
    public void findOne_NoID_Null() {
        this.clientListImpl.addClient(1,"a","b","123");
        assertNull(this.clientListImpl.findOne(2));
    }

    @Test
    public void findOne_HaveID_ID11324() {
        this.clientListImpl.addClient(12345, "a", "b","123");
        this.clientListImpl.addClient(11324, "c", "d","432");
        this.clientListImpl.addClient(99274, "e", "f","3789");
        Client client = this.clientListImpl.findOne(11324);
        assertEquals(11324, client.getID());
    }


    /**
     * Testing remove method
     */
    @Test(expected = IllegalArgumentException.class)
    public void remove_IDZero_IArgExcep() {
        this.clientListImpl.remove(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_IDNeg_IArgExcep() {
        this.clientListImpl.remove(-7);
    }

    @Test
    public void remove_EmptyList_IArgExcep() {
        assertFalse(this.clientListImpl.remove(11324));
    }

    @Test
    public void remove_rmID123_True() {
        this.clientListImpl.addClient(123, "a", "b", "1234");
        assertEquals(1, this.clientListImpl.findAll().size());
        assertTrue(this.clientListImpl.remove(123));
    }

    @Test
    public void remove_NoID123_False() {
        this.clientListImpl.addClient(453, "a", "b", "1234");
        this.clientListImpl.addClient(223, "a", "b", "1234");
        this.clientListImpl.addClient(973, "a", "b", "1234");
        assertFalse(this.clientListImpl.remove(123));
    }


}