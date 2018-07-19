package com.iquantile.fragmentcontactlist.models;

public class Contact {
    public int contactImage;
    public String contactName;
    public String phoneNumber;

    public Contact(int contactImage, String contactName, String phoneNumber) {
        this.contactImage = contactImage;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }
}
