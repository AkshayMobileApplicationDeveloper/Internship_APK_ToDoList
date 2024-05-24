package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Interface.OnDialogCloseListener;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.skydoves.elasticviews.ElasticButton;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private EditText editTextTask;
    private EditText editTextDescription;
    private ElasticButton btnSaveEdit;
    private DataBaseHelper dataBaseHelper;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextTask = view.findViewById(R.id.Edit_textTask);
        editTextDescription = view.findViewById(R.id.Edit_text_disc);
        btnSaveEdit = view.findViewById(R.id.BTnSavedEdit);

        dataBaseHelper = new DataBaseHelper(getActivity());
        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("Task");
            String description = bundle.getString("Discription");

            editTextTask.setText(task);
            editTextDescription.setText(description);

            if (task.length() > 0 || description.length() > 0) {
                btnSaveEdit.setEnabled(true);
            }

            editTextTask.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().isEmpty()) {
                        btnSaveEdit.setEnabled(false);
                        btnSaveEdit.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    } else {
                        btnSaveEdit.setEnabled(true);
                        btnSaveEdit.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        final boolean finalIsUpdate = isUpdate;
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextTask.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (finalIsUpdate) {
                    dataBaseHelper.updateTask(bundle.getInt("id"), text);
                } else {
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setDiscription(description);
                    item.setStatus(0);
                    dataBaseHelper.insertTask(item);
                }
                dismiss();
                Log.w("TAG", "Task have saved right now" );
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) mainActivity).onDialogClose(dialog);
        }
    }
}
