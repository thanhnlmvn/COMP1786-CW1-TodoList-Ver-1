package com.example.todolistver1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTextTaskName, editTextTaskDetail;
    private Button buttonSaveTask;
    private Task task;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task); // Sử dụng layout mới

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDetail = findViewById(R.id.editTextTaskDetail);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        // Nhận dữ liệu từ MainActivity
        task = (Task) getIntent().getSerializableExtra("task");
        position = getIntent().getIntExtra("position", -1);

        // Điền vào các trường nhập liệu với thông tin của nhiệm vụ hiện tại
        editTextTaskName.setText(task.getName());
        editTextTaskDetail.setText(task.getDetail());

        // Xử lý sự kiện khi người dùng nhấn nút lưu
        buttonSaveTask.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String taskDetail = editTextTaskDetail.getText().toString().trim();

            if (!taskName.isEmpty() && !taskDetail.isEmpty()) {
                task.setName(taskName); // Cập nhật tên nhiệm vụ
                task.setDetail(taskDetail); // Cập nhật chi tiết nhiệm vụ

                // Gửi kết quả về MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("task", task);
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish(); // Đóng activity hiện tại và quay lại MainActivity
            }
        });
    }
}
