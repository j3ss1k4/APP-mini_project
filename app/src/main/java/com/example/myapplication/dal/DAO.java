package com.example.myapplication.dal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Account;
import com.example.myapplication.entities.Score;
import com.example.myapplication.entities.ScoreView;
import com.example.myapplication.entities.Student;

import java.util.List;

@Dao
public interface DAO {
    // ---------------- ACCOUNT ----------------
    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM Accounts WHERE username = :u AND password = :p")
    Account login(String u, String p);

    // ---------------- STUDENT ----------------
    @Insert
    void insertStudent(Student student);
    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM Students")
    List<Student> getAllStudents();

    @Query("SELECT * FROM Students WHERE code = :code")
    Student getStudentByCode(String code);

    // ---------------- SCORE ----------------
    @Insert
    void insertScore(Score score);

    @Query("SELECT st.name, s.subject, s.score " +
            "FROM Scores s JOIN Students st " +
            "ON s.codeStudent = st.code")
    List<ScoreView> getScores();
}
