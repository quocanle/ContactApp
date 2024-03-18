package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityDetailContactBinding;

public class DetailContact extends AppCompatActivity {
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

        contactDAO = MainActivity.getInstance().contactDAO;

        Intent intent = getIntent();
        int position = intent.getIntExtra("contact", 0);
        contact = MainActivity.getInstance().contactList.get(position);
        binding.tvName.setText(contact.getName());
        binding.tvPhone.setText(contact.getMobile());
        binding.tvEmail.setText(contact.getEmail());

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
                        int id = contact.getContactId();
                        String name = binding.tvName.getText().toString();
                        String phone = binding.tvPhone.getText().toString();
                        String email = binding.tvEmail.getText().toString();
                        contactDAO.updateContact(id, name, phone, email);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                    }
                });
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        contactDAO.delete(contact);
                        MainActivity.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactList.remove(contact);
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