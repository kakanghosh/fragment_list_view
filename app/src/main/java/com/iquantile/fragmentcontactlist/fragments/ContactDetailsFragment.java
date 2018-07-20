package com.iquantile.fragmentcontactlist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.iquantile.fragmentcontactlist.R;
import com.iquantile.fragmentcontactlist.interfaces.ContactListHelper;
import com.iquantile.fragmentcontactlist.models.Contact;
import com.iquantile.fragmentcontactlist.viewHolders.ContactHolder;

public class ContactDetailsFragment extends Fragment {

    View rootView;
    ContactHolder contactHolder;
    Contact contact;
    ContactListHelper helper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);
        this.initFragment();
        this.initWidget();
        return this.rootView;
    }

    private void initFragment() {
        this.contactHolder = new ContactHolder();
    }

    private void initWidget() {
        contactHolder.contactImageView = rootView.findViewById(R.id.imageView_contact_details);
        contactHolder.contactNameTextView = rootView.findViewById(R.id.textView_contact_name_details);
        contactHolder.callImageButton = rootView.findViewById(R.id.imageButton_call_details);
        contactHolder.messageImagebutton = rootView.findViewById(R.id.imageButton_message_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.helper = (ContactListHelper) getActivity();
        contact = new Gson().fromJson(getArguments().getString("contact"), Contact.class);
        contactHolder.contactImageView.setImageResource(contact.contactImage);
        contactHolder.contactNameTextView.setText(contact.contactName);

        contactHolder.callImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.makeCall(contact.phoneNumber);
            }
        });
    }
}
