package com.example.todolist.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String TASK_ID = "ID";
    private static final String TASK_NAME = "Task";
    private static final String TASK_STATUS = "Status";
    private static final String TASK_DESCRIPTION = "Description"; // Corrected spelling

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_NAME + " TEXT, " +
                TASK_STATUS + " INTEGER, " +
                TASK_DESCRIPTION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert Task
    public void insertTask(ToDoModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NAME, model.getTask());
        values.put(TASK_STATUS, 0);
        values.put(TASK_DESCRIPTION, model.getDiscription()); // Corrected method name
        db.insert(TABLE_NAME, null, values);
        db.close(); // Close the database connection
    }

    // Update Task
    public void updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task);
        db.update(TABLE_NAME, values, TASK_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    // Update Description
    public void updateDescription(int id, String description) { // Corrected method name
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_DESCRIPTION, description);
        db.update(TABLE_NAME, values, TASK_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    // Update Status
    public void updateStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_STATUS, status);
        db.update(TABLE_NAME, values, TASK_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    // Delete Task
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TASK_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    // Get All Tasks
    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ToDoModel task = new ToDoModel();
                task.setId(cursor.getInt(cursor.getColumnIndex(TASK_ID)));
                task.setTask(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                task.setStatus(cursor.getInt(cursor.getColumnIndex(TASK_STATUS)));
                task.setDiscription(cursor.getString(cursor.getColumnIndex(TASK_DESCRIPTION))); // Corrected method name
                modelList.add(task);
            } while (cursor.moveToNext());
            cursor.close(); // Close the cursor
        }

        db.close(); // Close the database connection
        return modelList;
    }
}
