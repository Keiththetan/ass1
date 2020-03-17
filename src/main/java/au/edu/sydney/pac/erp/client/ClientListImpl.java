package au.edu.sydney.pac.erp.client;

import java.util.ArrayList;
import java.util.List;

public class ClientListImpl implements ClientList {

    private List<Client> clientList = new ArrayList<>();

    public ClientListImpl() {}

    public Client addClient(int id, String firstName, String lastName, String phoneNumber) {
        for (Client client : this.clientList) {
            if (client.getID() == id) {
                throw new IllegalStateException();
            }
        }
        if (id <= 0) {
            throw new IllegalArgumentException("addClient: ID zero/negative");
        } else if (firstName == null || firstName.length() == 0) {
            throw new IllegalArgumentException("addClient: Null/No firstName");
        } else if (lastName == null || lastName.length() == 0) {
            throw new IllegalArgumentException("addClient: Null/No lastName");
        } else if (phoneNumber == null || phoneNumber.length() == 0) {
            throw new IllegalArgumentException("addClient: Null/No phoneNumber");
        }

        Client newClient = new ClientImpl(id, firstName, lastName, phoneNumber);
        this.clientList.add(newClient);
        return newClient;
    }

    public void clear() {
        this.clientList.clear();
    }

    public List<Client> findAll() {
        return this.clientList;
    }

    public List<Client> findAll(boolean assigned) {
        List<Client> listOfClients = new ArrayList<>();
        for (Client clients : this.clientList) {
            if (assigned == clients.isAssigned()) {
                listOfClients.add(clients);
            }
        }
        return listOfClients;
    }

    public List<Client> findAll(String... departmentCodes) {
        List<Client> listOfClients = new ArrayList<>();
        String[] departmentList = departmentCodes;

        if (departmentCodes == null) {
            return listOfClients;
        }
        for (String department : departmentCodes) {
            if (department != null) {
                for (Client client : this.clientList) {
                    if (department.equals(client.getDepartmentCode())) {
                        listOfClients.add(client);
                    }
                }
            }
        }
        return listOfClients;
    }

    public Client findOne(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("findOne: ID zero/negative");
        }
        for (Client client : this.clientList) {
            if (client.getID() == id) {
                return client;
            }
        }
        return null;
    }

    public boolean remove(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("findOne: ID zero/negative");
        }
        for (Client client : this.clientList) {
            if (client.getID() == id) {
                this.clientList.remove(client);
                return true;
            }
        }
        return false;
    }

    // Helper method for checking size of arraylist
//    public int getSize() {
//        return this.clientList.size();
//    }


}
