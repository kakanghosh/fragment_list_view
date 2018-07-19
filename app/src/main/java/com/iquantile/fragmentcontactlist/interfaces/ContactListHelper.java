package com.iquantile.fragmentcontactlist.interfaces;

import android.os.Bundle;

public interface ContactListHelper {
    public Bundle getBundle();
    public void makeCall(int index);
    public void gotoDetails(int index);
}
