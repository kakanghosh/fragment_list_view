package com.iquantile.fragmentcontactlist.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.iquantile.fragmentcontactlist.R;
import com.iquantile.fragmentcontactlist.interfaces.ContactListHelper;
import com.iquantile.fragmentcontactlist.models.Contact;
import com.iquantile.fragmentcontactlist.viewHolders.ContactHolder;

import java.util.ArrayList;

public class ContactListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contactList;
    ContactListHelper helper;

    public ContactListViewAdapter(Context context, ArrayList<Contact> contactList, ContactListHelper helper) {
        this.context = context;
        this.contactList = contactList;
        this.helper = helper;
    }

    @Override
    public int getCount() {
        return this.contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        View view;
        final ContactHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contact_list_row, parent, false);
            holder = new ContactHolder();
            holder.contactrootLinearLayout = view.findViewById(R.id.linearLayout_contact_root);
            holder.contactImageView = view.findViewById(R.id.imageView_contact);
            holder.contactNameTextView = view.findViewById(R.id.textView_contact_name);
            holder.callImageButton = view.findViewById(R.id.imageButton_call);
            holder.messageImagebutton = view.findViewById(R.id.imageButton_message);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ContactHolder) view.getTag();
        }
        holder.contactrootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.contact_root));
        holder.contactImageView.setImageResource(this.contactList.get(index).contactImage);
        holder.contactNameTextView.setText(this.contactList.get(index).contactName);

        holder.callImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.makeCall(index);
            }
        });

        holder.contactrootLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.contactrootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.contact_row_bg_1));
                helper.gotoDetails(index);
                holder.contactrootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.contact_row_bg_2));
            }
        });

        return view;
    }
}
