package com.iquantile.fragmentcontactlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iquantile.fragmentcontactlist.fragments.ContactDetailsFragment;
import com.iquantile.fragmentcontactlist.fragments.ContactListFragment;
import com.iquantile.fragmentcontactlist.interfaces.ContactListHelper;
import com.iquantile.fragmentcontactlist.models.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactListHelper {
    private static final int CALL_REQUEST_CODE = 101;
    FragmentManager fragmentManager;
    ArrayList<Contact> contactList;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initActivity();
        this.initProperty();
        this.initContactListFragment();
    }

    private void initActivity() {
        getSupportActionBar().hide();
        this.fragmentManager = getSupportFragmentManager();
    }

    public void initProperty() {
        this.contactList = new ArrayList<>();
        this.populateContactList();
    }

    public void populateContactList() {
        this.contactList.add(new Contact(R.drawable.dependable, "Dependable", "01676342090"));
        this.contactList.add(new Contact(R.drawable.friendship, "FriendShip", "01676102577"));
        this.contactList.add(new Contact(R.drawable.friendship_2, "Another FriendShip", "01676311880"));
        this.contactList.add(new Contact(R.drawable.honest, "Honest", "01688505322"));
        this.contactList.add(new Contact(R.drawable.laugh, "Laugh", "01675362584"));
        this.contactList.add(new Contact(R.drawable.reunion, "Reunion", "01816525412"));
        this.contactList.add(new Contact(R.drawable.sharing, "Sharing", "01965412512"));
    }

    private void initContactListFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment contactListFragment = new ContactListFragment();
        Bundle bundle = new Bundle();
        String contactListString = new Gson().toJson(this.contactList);
        bundle.putString("contact-list", contactListString);
        contactListFragment.setArguments(bundle);
        transaction.add(R.id.frameLayout_contact_list, contactListFragment);
        transaction.commit();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        String contactListString = new Gson().toJson(this.contactList);
        bundle.putString("contact-list", contactListString);

        return bundle;
    }

    @Override
    public void makeCall(int index) {
        this.selectedIndex = index;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contactList.get(index).phoneNumber));
            startActivity(intent);
        }
    }

    @Override
    public void makeCall(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
    }

    @Override
    public void gotoDetails(int index) {
        this.selectedIndex = index;
        Log.d("config", "Details: " + selectedIndex);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment contactDetailsFragment = new ContactDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("contact", new Gson().toJson(contactList.get(index)));
        contactDetailsFragment.setArguments(bundle);

        if (findViewById(R.id.main_activity_pot) != null){
            transaction.add(R.id.frameLayout_contact_list, contactDetailsFragment);
            transaction.addToBackStack(null);
        }else {
            transaction.replace(R.id.frameLayout_contact_details, contactDetailsFragment);
        }
        /*transaction.addToBackStack(null);*/
        transaction.commit();
    }

    public void gotoDetails(int parent, int index) {
        this.selectedIndex = index;
        Log.d("config", "Details: " + selectedIndex);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment contactDetailsFragment = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contact", new Gson().toJson(contactList.get(index)));
        contactDetailsFragment.setArguments(bundle);
        if (findViewById(R.id.main_activity_pot) != null){
            transaction.add(parent, contactDetailsFragment);
            transaction.addToBackStack(null);
        }else {
            transaction.replace(parent, contactDetailsFragment);
        }
        /*transaction.addToBackStack(null);*/
        transaction.commit();
    }

    @Override
    public Contact getSelectedContact() {
        if (this.selectedIndex != -1)
            return this.contactList.get(this.selectedIndex);
        else
            return this.contactList.get(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactList.get(selectedIndex).phoneNumber));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        fragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        initContactListFragment();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if (selectedIndex >= 0){
                gotoDetails(R.id.frameLayout_contact_list, selectedIndex);
            }else {
                gotoDetails(R.id.frameLayout_contact_list, 0);
            }
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (selectedIndex >= 0){
                gotoDetails(R.id.frameLayout_contact_details, selectedIndex);
            }else {
                gotoDetails(R.id.frameLayout_contact_details, 0);
            }
        }
    }
}
