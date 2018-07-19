package com.iquantile.fragmentcontactlist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iquantile.fragmentcontactlist.MainActivity;
import com.iquantile.fragmentcontactlist.R;
import com.iquantile.fragmentcontactlist.adapters.ContactListViewAdapter;
import com.iquantile.fragmentcontactlist.interfaces.ContactListHelper;
import com.iquantile.fragmentcontactlist.models.Contact;

import java.util.ArrayList;

public class ContactListFragment extends Fragment {
    View rootView;
    ArrayList<Contact> contactList;
    ContactListHelper helper;
    ListView contactListView;
    ContactListViewAdapter contactListViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        this.initWidget();
        return rootView;
    }

    private void initWidget() {
        this.contactListView = this.rootView.findViewById(R.id.listView_contact_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.helper = (ContactListHelper) getActivity();
        String stringList = helper.getBundle().getString("contact-list");
        this.contactList = new Gson().fromJson(stringList, new TypeToken<ArrayList<Contact>>(){}.getType());
        this.contactListViewAdapter = new ContactListViewAdapter(getActivity(), this.contactList, helper);
        this.contactListView.setAdapter(contactListViewAdapter);
    }
}
