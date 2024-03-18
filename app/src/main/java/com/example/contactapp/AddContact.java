package com.example.contactapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactapp.databinding.ActivityDetailContactBinding;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {
    private Contact contact;
    private ContactDAO contactDAO;
    private ActivityDetailContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.btnDelete.setVisibility(View.INVISIBLE);

        contactDAO = MainActivity.getInstance().contactDAO;

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String name = binding.tvName.getText().toString();
                        String phone = binding.tvPhone.getText().toString();
                        String email = binding.tvEmail.getText().toString();
                        contact = new Contact(name, phone, email);
                        contactDAO.insertAll(contact);
                        final ArrayList<Contact> contacts = new ArrayList<>(contactDAO.getAll());
                        MainActivity.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactList.clear();
                                MainActivity.getInstance().contactList.addAll(contacts);
                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                    }
                });

            }
        });
    }
}
