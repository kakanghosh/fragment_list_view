package com.iquantile.fragmentcontactlist.interfaces;

import android.os.Bundle;

import com.iquantile.fragmentcontactlist.models.Contact;

public interface ContactListHelper {
    public Bundle getBundle();
    public void makeCall(int index);
    public void makeCall(String number);
    public void gotoDetails(int index);
    public Contact getSelectedContact();
}
