package au.edu.sydney.pac.erp.client;

public class ClientImpl implements Client {

    private int id;
    private String firstName, lastName, phoneNumber;
    private boolean isAssigned;
    private String departmentCode;

    public ClientImpl(int id, String firstName, String lastName, String phoneNumber) {
        if (id <= 0) {
            throw new IllegalArgumentException("ClientImpl: ID zero/negative");
        } else if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("ClientImpl: Null/No firstName");
        } else if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("ClientImpl: Null/No lastName");
        } else if (phoneNumber == null || phoneNumber.trim().length() == 0) {
            throw new IllegalArgumentException("ClientImpl: Null/No phoneNumber");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.isAssigned = false;
    }

    public void assignDepartment(String departmentCode)  {
        if (isAssigned == true) {
            throw new IllegalStateException("assignDepartment: Client Already Assigned");
        } else if (departmentCode == null || departmentCode.trim().length() == 0) {
            throw new IllegalArgumentException("assginDepartment: Null/No DepartmentCode");
        }
        this.isAssigned = true;
        this.departmentCode = departmentCode;
    }


    public String getDepartmentCode() {
        if (departmentCode == null) {
            return null;
        } else {
            return this.departmentCode;
        }
    }


    public String getFirstName() {
        return this.firstName;
    }


    public int getID() {
        return this.id;
    }


    public String getLastName() {
        return this.lastName;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    public boolean isAssigned() {
        return isAssigned;
    }

}

//public class ClientImpl implements Client {
//    private final int id;
//    private final String firstName;
//    private final String lastName;
//    private final String phoneNumber;
//    private String departmentCode;
//    private boolean assigned = false;
//
//    public ClientImpl(int id, String firstName, String lastName, String phoneNumber) throws IllegalArgumentException {
//        if (id < 1) {
//            throw new IllegalArgumentException("ID must be greater than 0");
//        } else if (null != firstName && !"".equals(firstName)) {
//            if (null != lastName && !"".equals(lastName)) {
//                if (null != phoneNumber && !"".equals(phoneNumber)) {
//                    this.id = id;
//                    this.firstName = firstName;
//                    this.lastName = lastName;
//                    this.phoneNumber = phoneNumber;
//                } else {
//                    throw new IllegalArgumentException("Phone number may not be null or empty");
//                }
//            } else {
//                throw new IllegalArgumentException("Last name may not be null or empty");
//            }
//        } else {
//            throw new IllegalArgumentException("First name may not be null or empty");
//        }
//    }
//
//    public void assignDepartment(String departmentCode) {
//        if (this.assigned) {
//            throw new IllegalStateException("Already assigned.");
//        } else if (null != departmentCode && !"".equals(departmentCode)) {
//            this.assigned = true;
//            this.departmentCode = departmentCode;
//        } else {
//            throw new IllegalArgumentException("Department code may not be null or empty");
//        }
//    }
//
//    public boolean isAssigned() {
//        return this.assigned;
//    }
//
//    public String getDepartmentCode() {
//        return this.departmentCode;
//    }
//
//    public int getID() {
//        return this.id;
//    }
//
//    public String getFirstName() {
//        return this.firstName;
//    }
//
//    public String getLastName() {
//        return this.lastName;
//    }
//
//    public String getPhoneNumber() {
//        return this.phoneNumber;
//    }
//}


