package com.example.ourwhatsapp.Database.DatabaseDAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ourwhatsapp.Database.Entities.Messages;
import com.example.ourwhatsapp.Database.Entities.User;

import java.util.List;

@Dao
public interface MessagesDao {

    @Query("SELECT * FROM messages")
    List<Messages> getAll();

    @Query("SELECT * FROM messages WHERE id = :id")
    Messages get(int id);

    @Query("SELECT * FROM messages WHERE chatID = :chatID")
    List<Messages> getChatsMessages(String chatID);

    @Insert
    void insert(Messages... messages);

    @Update
    void update(Messages... messages);

    @Delete
    void delete(Messages... messages);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("DELETE FROM messages WHERE chatID = :chatID")
    void delete(String chatID);
}
