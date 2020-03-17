package au.edu.sydney.pac.erp.feaa;

class Account {
    public int id;
    public int client;
    public String name;
    public int incomings;
    public int outgoings;

    public Account(int id, int client, String name, int incomings, int outgoings) {
        this.id = id;
        this.client = client;
        this.name = name;
        this.incomings = incomings;
        this.outgoings = outgoings;
    }
}