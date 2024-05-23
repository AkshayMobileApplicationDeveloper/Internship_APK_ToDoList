package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> modelList;
    private MainActivity mainActivity;
    private DataBaseHelper dataBaseHelper;

    public ToDoAdapter(List<ToDoModel> modelList, MainActivity mainActivity, DataBaseHelper dataBaseHelper) {
        this.modelList = modelList;
        this.mainActivity = mainActivity;
        this.dataBaseHelper = dataBaseHelper;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToDoAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_lauout,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        final ToDoModel item= modelList.get(position);
        holder.idMaterialCheckBoxitemView.setText(item.getTask());
        boolean toBoolean;
        holder.idMaterialCheckBoxitemView.setChecked(toBoolean(item.getStatus()) );
        holder.idMaterialCheckBoxitemView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dataBaseHelper.updateStatus(item.getStatus(),1);
                }
                else{
                    dataBaseHelper.updateStatus(item.getStatus(),0);
                }
            }
        });

    }

    private boolean toBoolean(int status) {
        return status!=0;
    }
    public Context getContext(){
        return mainActivity;
    }

    public void setTask(List<ToDoModel> modelList){
        this.modelList=modelList;
        notifyDataSetChanged();
    }
    public void deleteTak(int position){
        ToDoModel item=modelList.get(position);
        dataBaseHelper.deleteTask(item.getId());
        modelList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        ToDoModel item=modelList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("Task",item.getTask());
        bundle.putString("Discription",item.getDiscription());
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox idMaterialCheckBoxitemView;
        TextView todo_list_task_descriptionitemView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            idMaterialCheckBoxitemView=itemView.findViewById(R.id.idMaterialCheckBoxitemView);
            todo_list_task_descriptionitemView=itemView.findViewById(R.id.todo_list_task_description);

        }
    }
}
