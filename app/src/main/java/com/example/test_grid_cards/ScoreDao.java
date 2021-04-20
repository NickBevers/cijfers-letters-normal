package com.example.test_grid_cards;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM Score ORDER BY score DESC")
    List<Score> getAll();

    @Insert
    void insert(Score score);


}

