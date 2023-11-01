// класс основной сущности контакта.
package org.example;

class Contact {
    private final String fullName;
    private final String phoneNumber;
    private final String email;

    public Contact(String fullName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}