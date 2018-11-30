package com.tbsfactoringapp.hrgadgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private static List<EmployeeInfo> mEmployeeInfo = new ArrayList<>();
    private Context context;


    public EmployeeAdapter(Context parent, List<EmployeeInfo> employees) {
        this.context = parent;
        this.mEmployeeInfo = employees;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int empViewId = R.layout.employee_list_content;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(empViewId, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeInfo mEmp = mEmployeeInfo.get(position);
        String empId = mEmp.getId();
        String empName = mEmp.getEmployeeName();
        String empSalary = mEmp.getEmployeeSalary();

        holder.mEmpId.setText(empId);
        holder.mEmpName.setText(empName);
        holder.mEmpSalary.setText(empSalary);
    }

    @Override
    public int getItemCount() {
        if (mEmployeeInfo == null) {
            return 0;
        } else {
            return mEmployeeInfo.size();
        }
    }

    public static List<EmployeeInfo> getEmployees() {
        return mEmployeeInfo;
    }

    public void setEmpData(List<EmployeeInfo> empData) {
        mEmployeeInfo = empData;
        notifyDataSetChanged();
    }

    public interface EmployeeAdapterOnClickHandler {
        void onClick(String position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mEmpId;
        final TextView mEmpName;
        final TextView mEmpSalary;

        public ViewHolder(View view) {
            super(view);

            mEmpId = view.findViewById(R.id.rv_emp_list_id);
            mEmpName = view.findViewById(R.id.rv_emp_list_name);
            mEmpSalary = view.findViewById(R.id.rv_emp_list_salary);
        }
    }
}
