package com.example.todolistver1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList = new ArrayList<>();
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onEditClick(int position) {
                // Mở màn hình chỉnh sửa
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra("task", taskList.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);
            }

            @Override
            public void onDeleteClick(int position) {
                // Xóa nhiệm vụ
                taskList.remove(position);
                taskAdapter.notifyDataSetChanged();
                saveTasks();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        buttonAddTask.setOnClickListener(view -> {
            // Mở màn hình thêm nhiệm vụ
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });

        // Tải danh sách nhiệm vụ từ SharedPreferences
        loadTasks();
    }

    // Lưu nhiệm vụ vào SharedPreferences
    private void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("todo_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Dữ liệu được lưu trữ dưới dạng chuỗi
        StringBuilder taskString = new StringBuilder();
        for (Task task : taskList) {
            taskString.append(task.getName()).append(",").append(task.getDetail()).append(";");
        }

        editor.putString("task_list", taskString.toString());
        editor.apply();
    }

    // Tải danh sách nhiệm vụ từ SharedPreferences
    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("todo_app", MODE_PRIVATE);
        String taskString = sharedPreferences.getString("task_list", "");

        if (!taskString.isEmpty()) {
            String[] tasksArray = taskString.split(";");
            for (String taskData : tasksArray) {
                String[] taskDetails = taskData.split(",");
                if (taskDetails.length == 2) {
                    taskList.add(new Task(taskDetails[0], taskDetails[1]));
                }
            }
        }

        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Lưu danh sách nhiệm vụ khi ứng dụng bị tạm dừng
        saveTasks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Task newTask = (Task) data.getSerializableExtra("task");
            taskList.add(newTask);
            taskAdapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // Cập nhật nhiệm vụ sau khi chỉnh sửa
            Task updatedTask = (Task) data.getSerializableExtra("task");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                taskList.set(position, updatedTask);
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
