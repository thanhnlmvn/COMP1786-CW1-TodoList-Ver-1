package com.example.todolistver1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTaskName, editTextTaskDetail;
    private Button buttonSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDetail = findViewById(R.id.editTextTaskDetail);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        buttonSaveTask.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String taskDetail = editTextTaskDetail.getText().toString().trim();

            if (!taskName.isEmpty() && !taskDetail.isEmpty()) {
                Task task = new Task(taskName, taskDetail);

                // Gửi kết quả về MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("task", task);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AddTaskActivity.this, "Please enter task details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
