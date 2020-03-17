package au.edu.sydney.pac.erp.feaa;


import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import au.edu.sydney.pac.erp.client.ClientListImpl;
import com.sun.security.sasl.ClientFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FEAAFacadeImplTest {

    private FEAAFacadeImpl feaaFacade;
    private ClientList provider;

    @Before
    public void resetFEAAFacadeImpl() {
        this.feaaFacade = new FEAAFacadeImpl();
        this.provider = new ClientListImpl();
        this.provider.addClient(96, "a","b", "9754");
        this.provider.addClient(73, "c","d", "9729");
        this.provider.addClient(31, "e","f", "9650");

        this.provider.addClient(23, "g","h", "8855");
        this.provider.addClient(45, "i","j", "5546");
        this.provider.addClient(67, "k","l", "7838");

        this.provider.findOne(23).assignDepartment("DOMESTIC");
        this.provider.findOne(45).assignDepartment("INTERNATIONAL");
        this.provider.findOne(67).assignDepartment("LARGE ACCOUNTS");
    }

    /**
     * Testing addClient method
     */
    @Test(expected = IllegalStateException.class)
    public void addClient_NullProvider_IStateExcep() {
        feaaFacade.addClient("fName", "lName", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_No1stName_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("", "last","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_Null1stName_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        this.feaaFacade.addClient(null, "last","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_NoLastName_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", "", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_NullLastname_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", null, "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_NoPhone_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", "last", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_NullPhone_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", "last", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCLient_PNumNotDigit_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", "last", "abcd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addClient_PNumWierd_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addClient("first", "last", "*12*!&%!");
    }

    @Test
    public void addClient_PNum_Digit() {
        feaaFacade.setClientProvider(provider);
        Client client = feaaFacade.addClient("first", "last", "97543212");
        assertEquals("97543212", client.getPhoneNumber());
    }

    @Test
    public void addClient_PNumSpaces_String() {
        feaaFacade.setClientProvider(provider);
        Client client = feaaFacade.addClient("first", "last", "9  43 2");
        assertEquals("9  43 2", client.getPhoneNumber());
    }

    @Test
    public void addClient_PNumCombine_String() {
        feaaFacade.setClientProvider(provider);
        Client client = feaaFacade.addClient("first", "last", "+(61)0411 092 813");
        assertEquals("+(61)0411 092 813", client.getPhoneNumber());
    }

    @Test
    public void addClient_newC_CurrP_ID97() {
        feaaFacade.setClientProvider(provider);
        Client client = feaaFacade.addClient("James", "Vern", "+(61) 123 456 789");
        assertEquals(97, client.getID());
    }

    @Test
    public void addClient_newC_newP_ID1() {
        ClientListImpl clientList = new ClientListImpl();
        feaaFacade.setClientProvider(clientList);

        Client client1 = feaaFacade.addClient("James", "Vern", "+(61) 123 456 789");
        Client client2 = feaaFacade.addClient("Kent", "Lee", "+(01) 975 432 120");
        assertEquals(1, client1.getID());
        assertEquals(2, client2.getID());
    }



    /**
     * Testing assignClient method
     */
    @Test(expected = IllegalStateException.class)
    public void asgnClient_NullProvider_IStateExcep() {
        feaaFacade.assignClient(1, "HR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(0, "IT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(-2, "IT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_CodeEmpty_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(1, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_CodeNull_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_CodeWrong_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(96, "Human Resources");
    }

    @Test(expected = IllegalArgumentException.class)
    public void asgnClient_CodeLowercase_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(1,"domestic");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_EmptyProvider_IStateExcep() {
        feaaFacade.setClientProvider(new ClientListImpl());
        feaaFacade.assignClient(1,"DOMESTIC");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(1,"DOMESTIC");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_LongDOM_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(23, "DOMESTIC");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_DOM_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(23, "DOM");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_LongINT_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(45, "INTERNATIONAL");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_INT_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(45, "INT");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_LongACC_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(67, "LARGE ACCOUNTS");
    }

    @Test(expected = IllegalStateException.class)
    public void asgnClient_AsgnedMatchID_ACC_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(67, "LRG");
    }

    @Test
    public void asgnClient_AsgnID97_LongDOM() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(96, "DOMESTIC");
        assertEquals("DOMESTIC", provider.findOne(96).getDepartmentCode());
    }

    @Test
    public void asgnClient_AsgnID97_DOM() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(96, "DOM");
        assertEquals("DOMESTIC", provider.findOne(96).getDepartmentCode());
    }

    @Test
    public void asgnClient_AsgnID31_LongINT() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(31, "INTERNATIONAL");
        assertEquals("INTERNATIONAL", provider.findOne(31).getDepartmentCode());
    }

    @Test
    public void asgnClient_AsgnID31_INT() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(31, "INT");
        assertEquals("INTERNATIONAL", provider.findOne(31).getDepartmentCode());
    }

    @Test
    public void asgnClient_AsgnID73_LongACC() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(73, "LARGE ACCOUNTS");
        assertEquals("LARGE ACCOUNTS", provider.findOne(73).getDepartmentCode());
    }

    @Test
    public void asgnClient_AsgnID73_ACC() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.assignClient(73, "LRG");
        assertEquals("LARGE ACCOUNTS", provider.findOne(73).getDepartmentCode());
    }



    /**
     * Testing getAllClients
     */
    @Test
    public void viewAllC_NullProvider_EmptyList() {
        List<String> emptyList = feaaFacade.viewAllClients();
        assertEquals(0, emptyList.size());
    }

    @Test
    public void viewAllC_EmptyProvider_EmptyList() {
        ClientListImpl clientList = new ClientListImpl();
        feaaFacade.setClientProvider(clientList);
        List<String> emptyList = feaaFacade.viewAllClients();
        assertEquals(0, emptyList.size());
    }

    @Test
    public void viewAllC_Provider_CorrectFormat() {
        feaaFacade.setClientProvider(provider);
        List<String> nameList = feaaFacade.viewAllClients();
        String[] correctList = {"b, a", "d, c", "f, e", "h, g", "j, i", "l, k"};

        for (int i=0; i<correctList.length; i++) {
            assertEquals(correctList[i], nameList.get(i));
        }
    }



    /**
     * Testing getAllClients
     */
    @Test(expected = IllegalStateException.class)
    public void getAllC_NullProvider_IStateExcep() {
        feaaFacade.getAllClients();
    }

    @Test
    public void getAllC_Provider_6Clients() {
        feaaFacade.setClientProvider(provider);
        List<Client> clientList = feaaFacade.getAllClients();
        int[] correctList = {96, 73, 31, 23, 45, 67};

        for(int i=0; i<correctList.length; i++) {
            assertEquals(correctList[i], clientList.get(i).getID());
        }
    }



    /**
     * Testing addAccount
     * ******
     * NOTE: JAR FILE IMPLEMENTATION HAS MAJOR BUGS CONFLICTING WITH API GIVEN
     * 1) null accountID throws IllegalStateException instead of IllegalArgumentException
     * 2) No exception thrown if accountID's are not unique
     *
     * Take note of account ID, will write all other testcases before returning to check both
     * coverage and possible test scenarios.
     * ******
     */
    @Test(expected = IllegalStateException.class)
    public void addAcc_NullProvider_IStateExcep() {
        feaaFacade.addAccount(1, 96, "NewACC", 99, 20);
    }

    @Test
    public void addAcc_NullaccID_accID1() {
        feaaFacade.setClientProvider(provider);
        int accID = feaaFacade.addAccount(null, 96, "NewAcc", 99, 20);
        assertEquals(1, accID);
    }

    @Test
    public void addAcc_MultiNullaccID_accID3() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(null, 96, "NewAcc1", 99, 20);
        feaaFacade.addAccount(null, 96, "NewAcc2", 99, 20);
        int accID = feaaFacade.addAccount(null, 96, "NewAcc3", 99, 20);
        assertEquals(3, accID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_AccIDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(0, 96, "NewAcc1", 99, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_AccIDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(-2, 96, "NewAcc1", 99, 20);
    }

    @Test(expected = IllegalStateException.class)
    public void addAcc_FirstIDNotNull_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 96, "NewAcc1", 99, 20);
        feaaFacade.addAccount(null, 96, "NewAcc2", 99, 20);
    }

    @Test
    public void addAcc_SameAccID_AccIDList() {
        feaaFacade.setClientProvider(provider);
        int accID1 = feaaFacade.addAccount(7, 96, "NewAcc1", 99, 50);
        int accID2 = feaaFacade.addAccount(7, 73, "NewAcc2", 50, 0);
        assertEquals(7, accID1);
        assertEquals(7, accID2);
    }

    @Test
    public void addAcc_NullFirstID_CorrectID() {
        feaaFacade.setClientProvider(provider);
        List<Integer> accIDList = new ArrayList<Integer>();
        int[] result = {1,1,5,6};

        accIDList.add(feaaFacade.addAccount(null, 23, "Acc23", 10, 5));
        accIDList.add(feaaFacade.addAccount(1, 45, "Acc45", 20, 30));
        accIDList.add(feaaFacade.addAccount(5, 67, "Acc67", 10, 5));
        accIDList.add(feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0));

        for (int i=0; i<result.length; i++) {
            assertEquals(Integer.valueOf(result[i]), accIDList.get(i));
        }
    }

    @Test
    public void addAcc_MultiNullID_CorrectID() {
        feaaFacade.setClientProvider(provider);
        List<Integer> accIDList = new ArrayList<Integer>();
        int[] result = {1,2,3,6};

        accIDList.add(feaaFacade.addAccount(null, 23, "Acc23", 10, 5));
        accIDList.add(feaaFacade.addAccount(null, 45, "Acc45", 20, 30));
        accIDList.add(feaaFacade.addAccount(null, 67, "Acc67", 10, 5));
        accIDList.add(feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0));

        for (int i=0; i<result.length; i++) {
            assertEquals(Integer.valueOf(result[i]), accIDList.get(i));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void addAcc_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 21, "NoClient", 10, 10);
    }


    @Test(expected = IllegalArgumentException.class)
    public void addAcc_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 0,"NewACC", 99,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, -4,"NewACC", 99,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_NullAcc_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 96,null, 99,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_EmptyAcc_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 96,"", 99,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_InitInNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 96,"NewACC", -20,20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAcc_InitOutNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 96,"NewACC", 99,-29);
    }



    /**
     * Testing getAccounts
     */
    @Test
    public void getAcc_NoAcc_EmptyList(){
        feaaFacade.setClientProvider(provider);
        assertEquals(0, feaaFacade.getAccounts().size());
    }

    @Test
    public void getAcc_4Acc_ListSize4() {
        feaaFacade.setClientProvider(provider);
        List<Integer> accIDList = new ArrayList<Integer>();

        accIDList.add(feaaFacade.addAccount(null, 23, "Acc23", 10, 5));
        accIDList.add(feaaFacade.addAccount(1, 45, "Acc45", 20, 30));
        accIDList.add(feaaFacade.addAccount(5, 67, "Acc67", 10, 5));
        accIDList.add(feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0));

        assertEquals(4, feaaFacade.getAccounts().size());
    }

    @Test
    public void getAcc_4Acc_CorrectFormat() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(null, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(1, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(5, 67, "Acc67", 10, 5);
        feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0);

        List<String> accNameList = feaaFacade.getAccounts();
        String[] list = {"1: Acc23", "1: Acc45", "5: Acc67", "6: Acc23 no2"};

        for (int i=0; i<list.length; i++) {
            assertEquals(list[i], accNameList.get(i));
        }
    }



    /**
     * Testing getAccounts(int)
     */
    @Test(expected = IllegalStateException.class)
    public void getAccID_NullProvider_IStateExcep() {
        feaaFacade.getAccounts(96);
    }

    @Test(expected = IllegalStateException.class)
    public void getAccID_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccounts(77);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccID_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccounts(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccID_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccounts(-3);
    }

    @Test
    public void getAccID_getID23_ListSize2() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(null, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(1, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(5, 67, "Acc67", 10, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0);

        int[] list = {1, 23, 6};
        List<Integer> listInt = feaaFacade.getAccounts(23);

        for (int i=0; i<list.length; i++) {
            assertEquals(Integer.valueOf(list[i]), listInt.get(i));
        }
    }

    @Test
    public void getAccID_getID45_ListSize0() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(5, 67, "Acc67", 10, 5);
        assertEquals(0, feaaFacade.getAccounts(23).size());
    }



    /**
     * Testing getAccountName
     * ******
     * NOTE: JAR FILE IMPLEMENTATION HAS MAJOR BUGS CONFLICTING WITH API GIVEN
     * 1) There can a case where there are repeated accountIDs
     *     - First occurrence of accountID will take taken.
     *     - Caused by Bug in addAccount method
     *
     * 2) Affected Testcases:
     *     - getAccN_Acc23_EmptyList()
     *     - getAccN_RepeatAcc_EmptyList()
     *
     * ******
     */
    @Test(expected = IllegalStateException.class)
    public void getAccN_NullProvider_IStateExcep() {
        feaaFacade.getAccountName(1);
    }

    @Test(expected = IllegalStateException.class)
    public void getAccN_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(5, 67, "Acc67", 10, 5);
        feaaFacade.getAccountName(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccN_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountName(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccN_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountName(-8);
    }

    @Test
    public void getAccN_Acc23_EmptyList() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(2, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(3, 67, "Acc67", 10, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0);

        assertEquals("Acc23 no3", feaaFacade.getAccountName(23));
    }

    @Test
    public void getAccN_RepeatAcc_EmptyList() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(2, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(6, 67, "Acc67", 10, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        feaaFacade.addAccount(6, 23, "Acc23 no2", 35, 0);

        assertEquals("Acc67", feaaFacade.getAccountName(6));
    }



    /**
     * Testing getAccountBalance
     * ******
     * NOTE: JAR FILE IMPLEMENTATION HAS MAJOR BUGS CONFLICTING WITH API GIVEN
     * 1) There can a case where there are repeated accountIDs
     *     - First occurrence of accountID will take taken.
     *     - Caused by Bug in addAccount method
     *
     * 2) Affected Testcases:
     *     - getAccBal_DuplAcc_BalFirstAcc
     *
     * ******
     */
    @Test(expected = IllegalStateException.class)
    public void getAccBal_NullProvider_IStateExcep() {
        feaaFacade.getAccountBalance(1);
    }

    @Test(expected = IllegalStateException.class)
    public void getAccBal_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.getAccountBalance(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccBal_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountBalance(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccBal_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountBalance(-6);
    }

    @Test
    public void getAccBal_Acc1_Bal5() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        assertEquals(5, feaaFacade.getAccountBalance(1));
    }

    @Test
    public void getAccBal_Acc2_BalNeg10() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(2, 45, "Acc45", 20, 30);
        assertEquals(-10, feaaFacade.getAccountBalance(2));
    }

    @Test
    public void getAccBal_DuplAcc_BalFirstAcc() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 3);
        feaaFacade.addAccount(1, 45, "Acc45", 20, 30);
        assertEquals(7, feaaFacade.getAccountBalance(1));
    }



    /**
     * Testing getAccountIncomings
     */
    @Test(expected = IllegalStateException.class)
    public void getAccIn_NullProvider_IStateExcep() {
        feaaFacade.getAccountIncomings(1);
    }

    @Test(expected = IllegalStateException.class)
    public void getAccIn_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.getAccountIncomings(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccIn_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountIncomings(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccIn_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountIncomings(-4);
    }


    @Test
    public void getAccIn_Acc1_Incoming10() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        assertEquals(10, feaaFacade.getAccountIncomings(1));
    }

    @Test
    public void getAccIn_Acc6_Incoming11() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(2, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(6, 67, "Acc67", 11, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        assertEquals(11, feaaFacade.getAccountIncomings(6));
    }

    @Test
    public void getAccIn_DuplAcc23_Incoming20() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(23, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(6, 67, "Acc67", 11, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        assertEquals(20, feaaFacade.getAccountIncomings(23));
    }



    /**
     * Testing getAccountOutgoings
     */
    @Test(expected = IllegalStateException.class)
    public void getAccOut_NullProvider_IStateExcep() {
        feaaFacade.getAccountOutgoings(1);
    }

    @Test(expected = IllegalStateException.class)
    public void getAccOut_NoMatchID_IStateExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.getAccountOutgoings(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccOut_IDZero_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountOutgoings(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccOut_IDNeg_IArgExcep() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.getAccountOutgoings(-4);
    }

    @Test
    public void getAccOut_Acc1_Outgoing5() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        assertEquals(5, feaaFacade.getAccountOutgoings(1));
    }

    @Test
    public void getAccOut_Acc6_Outgoing7() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(2, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(6, 67, "Acc67", 11, 7);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        assertEquals(7, feaaFacade.getAccountOutgoings(6));
    }

    @Test
    public void getAccOut_DuplAcc23_Outgoing30() {
        feaaFacade.setClientProvider(provider);
        feaaFacade.addAccount(1, 23, "Acc23", 10, 5);
        feaaFacade.addAccount(23, 45, "Acc45", 20, 30);
        feaaFacade.addAccount(6, 67, "Acc67", 11, 5);
        feaaFacade.addAccount(23, 23, "Acc23 no3", 35, 0);
        assertEquals(30, feaaFacade.getAccountOutgoings(23));
    }


    /**
     * Testing setAccountIncomings
     */

    /**
     * Testing setClientProvider
     */
    @Test
    public void setClientP_clientL_CheckID() {
        this.feaaFacade.setClientProvider(provider);

    }
}