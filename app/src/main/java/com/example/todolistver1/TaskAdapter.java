package com.example.todolistver1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;
    private OnTaskClickListener onTaskClickListener;

    public TaskAdapter(ArrayList<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.taskDetail.setText(task.getDetail());

        holder.buttonEdit.setOnClickListener(v -> onTaskClickListener.onEditClick(position));
        holder.buttonDelete.setOnClickListener(v -> onTaskClickListener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDetail;
        Button buttonEdit, buttonDelete;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.textViewTaskName);
            taskDetail = itemView.findViewById(R.id.textViewTaskDetail);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    // Interface để xử lý sự kiện chỉnh sửa và xóa
    public interface OnTaskClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
