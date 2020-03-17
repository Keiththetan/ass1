package au.edu.sydney.pac.erp.feaa;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FEAAFacadeImpl implements FEAAFacade {
    private ClientList clientProvider = null;
    private boolean accountIDTrigger = false;
    private int nextAccountID = 1;
    private int nextClientID = 1;
    private List<Account> accounts = new ArrayList();

    public FEAAFacadeImpl() {
    }

    public Client addClient(String fName, String lName, String phoneNumber) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (null != fName && !"".equals(fName)) {
            if (null != lName && !"".equals(lName)) {
                if (null != phoneNumber && !"".equals(phoneNumber)) {
                    String phonePattern = "^[0-9()+ ]+$";
                    if (!phoneNumber.matches(phonePattern)) {
                        throw new IllegalArgumentException("Invalid phone number");
                    } else {
                        return this.clientProvider.addClient(this.nextClientID++, fName, lName, phoneNumber);
                    }
                } else {
                    throw new IllegalArgumentException("Phone number may not be null or empty");
                }
            } else {
                throw new IllegalArgumentException("Last name may not be null or empty");
            }
        } else {
            throw new IllegalArgumentException("First name may not be null or empty");
        }
    }

    public void assignClient(int clientID, String departmentCode) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (clientID < 1) {
            throw new IllegalArgumentException("Invalid client ID");
        } else if (null == departmentCode) {
            throw new IllegalArgumentException("Invalid department code");
        } else {
            List<String> validDept = new ArrayList(Arrays.asList("DOMESTIC", "DOM", "INTERNATIONAL", "INT", "LARGE ACCOUNTS", "LRG"));
            if (!validDept.contains(departmentCode)) {
                throw new IllegalArgumentException("Invalid department code");
            } else {
                if (departmentCode.equals("DOM")) {
                    departmentCode = "DOMESTIC";
                }

                if (departmentCode.equals("INT")) {
                    departmentCode = "INTERNATIONAL";
                }

                if (departmentCode.equals("LRG")) {
                    departmentCode = "LARGE ACCOUNTS";
                }

                Client client = this.clientProvider.findOne(clientID);
                if (null != client && !client.isAssigned()) {
                    client.assignDepartment(departmentCode);
                } else {
                    throw new IllegalStateException("Invalid client match");
                }
            }
        }
    }

    public List<String> viewAllClients() {
        List<String> results = new ArrayList();
        if (null == this.clientProvider) {
            return results;
        } else {
            Iterator var2 = this.clientProvider.findAll().iterator();

            while(var2.hasNext()) {
                Client client = (Client)var2.next();
                results.add(String.format("%s, %s", client.getLastName(), client.getFirstName()));
            }

            return results;
        }
    }

    public List<Client> getAllClients() throws IllegalStateException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else {
            return Collections.unmodifiableList(this.clientProvider.findAll());
        }
    }

    public int addAccount(Integer accountID, int clientID, String accountName, int initialIncomings, int initialOutgoings) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else {
            if (!this.accountIDTrigger) {
                if (null == accountID) {
                    accountID = this.nextAccountID++;
                } else {
                    this.accountIDTrigger = true;
                }
            } else if (null == accountID) {
                throw new IllegalStateException("Null account id not allowed after non-null");
            }

            if (accountID < 1) {
                throw new IllegalArgumentException("Account ID must be greater than zero");
            } else if (clientID < 1) {
                throw new IllegalArgumentException("Client ID must be greater than zero");
            } else if (null != accountName && !"".equals(accountName)) {
                if (initialIncomings < 0) {
                    throw new IllegalArgumentException("Initial incomings may not be negative");
                } else if (initialOutgoings < 0) {
                    throw new IllegalArgumentException("Initial outgoings may not be negative");
                } else {
                    Client client = this.clientProvider.findOne(clientID);
                    if (null == client) {
                        throw new IllegalStateException("No matching client");
                    } else {
                        Account account = new Account(accountID, clientID, accountName, initialIncomings, initialOutgoings);
                        this.accounts.add(account);
                        return accountID;
                    }
                }
            } else {
                throw new IllegalArgumentException("Account name may not be null or empty");
            }
        }
    }

    public List<String> getAccounts() {
        List<String> results = new ArrayList();
        Iterator var2 = this.accounts.iterator();

        while(var2.hasNext()) {
            Account account = (Account)var2.next();
            results.add(String.format("%s: %s", account.id, account.name));
        }

        return results;
    }

    public List<Integer> getAccounts(int clientID) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (clientID < 1) {
            throw new IllegalArgumentException("Client ID must be greater than zero");
        } else {
            Client client = this.clientProvider.findOne(clientID);
            if (null == client) {
                throw new IllegalStateException("Invalid client match");
            } else {
                List<Integer> results = new ArrayList();
                Iterator var4 = this.accounts.iterator();

                while(var4.hasNext()) {
                    Account account = (Account)var4.next();
                    if (account.client == clientID) {
                        results.add(account.id);
                    }
                }

                return results;
            }
        }
    }

    public String getAccountName(int id) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else {
            Iterator var2 = this.accounts.iterator();

            Account account;
            do {
                if (!var2.hasNext()) {
                    throw new IllegalStateException("No matching account");
                }

                account = (Account)var2.next();
            } while(account.id != id);

            return account.name;
        }
    }

    public int getAccountBalance(int id) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else {
            Iterator var2 = this.accounts.iterator();

            Account account;
            do {
                if (!var2.hasNext()) {
                    throw new IllegalStateException("No matching account");
                }

                account = (Account)var2.next();
            } while(account.id != id);

            return account.incomings - account.outgoings;
        }
    }

    public int getAccountIncomings(int id) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else {
            Iterator var2 = this.accounts.iterator();

            Account account;
            do {
                if (!var2.hasNext()) {
                    throw new IllegalStateException("No matching account");
                }

                account = (Account)var2.next();
            } while(account.id != id);

            return account.incomings;
        }
    }

    public int getAccountOutgoings(int id) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else {
            Iterator var2 = this.accounts.iterator();

            Account account;
            do {
                if (!var2.hasNext()) {
                    throw new IllegalStateException("No matching account");
                }

                account = (Account)var2.next();
            } while(account.id != id);

            return account.outgoings;
        }
    }

    public void setAccountIncomings(int id, int incomings) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else if (incomings < 0) {
            throw new IllegalArgumentException("Incomings may not be negative");
        } else {
            Iterator var3 = this.accounts.iterator();

            while(var3.hasNext()) {
                Account account = (Account)var3.next();
                if (account.id == id) {
                    account.incomings = incomings;
                }
            }

            throw new IllegalStateException("No matching account");
        }
    }

    public void setAccountOutgoings(int id, int outgoings) throws IllegalStateException, IllegalArgumentException {
        if (null == this.clientProvider) {
            throw new IllegalStateException("No client provider set");
        } else if (id < 1) {
            throw new IllegalArgumentException("Invalid ID");
        } else if (outgoings < 0) {
            throw new IllegalArgumentException("Outgoings may not be negative");
        } else {
            Iterator var3 = this.accounts.iterator();

            while(var3.hasNext()) {
                Account account = (Account)var3.next();
                if (account.id == id) {
                    account.outgoings = outgoings;
                }
            }

            throw new IllegalStateException("No matching account");
        }
    }

    public void setClientProvider(ClientList provider) {
        this.clientProvider = provider;
        this.accounts.clear();
        this.accountIDTrigger = false;
        this.nextAccountID = 1;
        if (null != provider) {
            int max = 0;
            Iterator var3 = provider.findAll().iterator();

            while(var3.hasNext()) {
                Client client = (Client)var3.next();
                if (client.getID() > max) {
                    max = client.getID();
                }
            }

            ++max;
            this.nextClientID = max;
        }
    }
}
