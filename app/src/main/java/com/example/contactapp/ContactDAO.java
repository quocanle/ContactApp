package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE contactId IN (:contactIds)")
    List<Contact> loadAllByIds(int[] contactIds);

    @Query("SELECT * FROM contact WHERE name LIKE :name LIMIT 1")
    Contact findByName(String name);

    @Insert
    void insertAll(Contact... contacts);

//    @Query("UPDATE contact SET name = ")
//    void updateContact(Contact contact);

    @Query("UPDATE contact SET name = :name, mobile = :mobile, email = :email WHERE contactId = :id")
    void updateContact(int id, String name, String mobile, String email);

    @Delete
    void delete(Contact contact);
}
