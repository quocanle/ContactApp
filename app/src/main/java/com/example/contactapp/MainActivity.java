package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    protected ArrayList<Contact> contactList;
    protected ContactAdapter contactAdapter;
    protected AppDatabase appDatabase;
    protected ContactDAO contactDAO;
    private ImageView iv;
    private static MainActivity instance;

    public MainActivity() {
        instance = this;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(contactList, MainActivity.this);
        binding.rvContact.setAdapter(contactAdapter);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDAO = appDatabase.contactDao();

//                contactDAO.insertAll(new Contact("Quốc Ân Lê", "0123", "quocanle@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Phan Gia Luật", "0124", "gialuatphan@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Huy Minh Phạm Nguyễn", "0125", "huyminhphamnguyen@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Quang Vinh Phạm", "0126", "quangvinhpham@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Minh Nhân Nguyễn", "0127", "minhnhannguyen@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Trúc Quỳnh Đỗ Vũ", "0128", "trucquynhdovu@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Anh Thi Nguyễn", "0129", "anhthinguyen@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Hữu Tín Nguyễn", "0120", "huutinnguyen@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Thảo Vy Huỳnh Hồng", "0121", "thaovyhuynhhong@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Khánh Trang Nguyễn", "0122", "khanhtrangnguyen@vnuk.edu.vn"));

                final ArrayList<Contact> contacts = new ArrayList<>(contactDAO.getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        contactList.addAll(contacts);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        binding.btnClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvSearch.setText("");
                contactAdapter = new ContactAdapter(contactList, MainActivity.this);
                binding.rvContact.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, AddContact.class);
                startActivity(intentAdd);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = binding.tvSearch.getText().toString().toLowerCase();
                ArrayList<Contact> filteredList = new ArrayList<>();
                for (Contact contact : contactList) {
                    if (contact.getName().toLowerCase().contains(searchText) || 
                        contact.getMobile().toLowerCase().contains(searchText) || 
                        contact.getEmail().toLowerCase().contains(searchText)) {
                        filteredList.add(contact);
                    }
                }
                contactAdapter = new ContactAdapter(filteredList, MainActivity.this);
                binding.rvContact.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }
}