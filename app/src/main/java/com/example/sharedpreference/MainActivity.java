package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText etNote;
    private Button btnSaveNote;
    private TextView tvNotes;
    private SharedPreferences sharedPreferences;
    private Set<String> notesSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNote = findViewById(R.id.etNote);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        tvNotes = findViewById(R.id.tvNotes);

        // Khởi tạo SharedPreferences và lấy danh sách các ghi chú đã lưu
        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);
        notesSet = sharedPreferences.getStringSet("notes", new HashSet<>());

        // Hiển thị các ghi chú đã lưu
        displaySavedNotes();

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = etNote.getText().toString().trim();
                if (!newNote.isEmpty()) {
                    // Lưu ghi chú mới
                    notesSet.add(newNote);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("notes", notesSet);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Lưu ghi chú thành công!", Toast.LENGTH_SHORT).show();
                    etNote.setText("");

                    // Hiển thị các ghi chú mới sau khi lưu
                    displaySavedNotes();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung ghi chú!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm hiển thị các ghi chú đã lưu
    private void displaySavedNotes() {
        StringBuilder allNotes = new StringBuilder("Các ghi chú đã lưu:\n");

        for (String note : notesSet) {
            allNotes.append("- ").append(note).append("\n");
        }

        tvNotes.setText(allNotes.toString());
    }
}