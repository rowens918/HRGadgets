package com.tbsfactoringapp.hrgadgets;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.DecompositionType.VERTICAL;
import static java.security.AccessController.getContext;

public class LandingActivity extends AppCompatActivity {

    public static List<EmployeeInfo> mEmployees = new ArrayList<>();
    public static EmployeeInfo SingleEmployee = new EmployeeInfo();
    public static String employeeJsonString = "";
    EditText mEditText;
    private static ProgressBar mLoadingIndicator;
    private static RecyclerView mRecyclerView;
    private static EmployeeAdapter mEmployeeAdapter;
    Context context;
    Button mButtonLookup;
    Button mButtonAllEmp;
    FrameLayout fEmpCard;
    FrameLayout fEmpList;
    String allEmpUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.landing_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mEditText = findViewById(R.id.et_emp_id);
        setSupportActionBar(toolbar);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mButtonLookup = findViewById(R.id.bt_emp_id);
        mButtonAllEmp = findViewById(R.id.bt_emp_all);
        fEmpCard = findViewById(R.id.emp_lookup_card);
        fEmpList = findViewById(R.id.emp_list_container);
        allEmpUrl = getString(R.string.all_employees_url);

        mRecyclerView = findViewById(R.id.rv_emp_list);
        LinearLayoutManager empLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(empLayoutManager);
        mEmployeeAdapter = new EmployeeAdapter(this, mEmployees);
        mEmployeeAdapter.setEmpData(mEmployees);
        mRecyclerView.setAdapter(mEmployeeAdapter);

        loadAllEmployeeData(allEmpUrl);

        mButtonLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editText = mEditText.getText().toString();
                showEmpCard(editText);
            }
        });

        mButtonAllEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadEmployeeRecyclerView();
                showEmpList();
            }
        });
    }


    void showEmpCard(String empId) {
        if (empId.equals("")) {
            Toast.makeText(context, "Please enter a valid ID", Toast.LENGTH_LONG).show();
        } else {
            String baseUrl = getString(R.string.employee_by_id_url);
            baseUrl += empId;
            loadSingleEmployeeData(baseUrl);
            fEmpCard.setVisibility(View.VISIBLE);
            fEmpList.setVisibility(View.GONE);

            setCardInfo();
        }
    }

    void setCardInfo() {
        if (SingleEmployee != null) {
            EmployeeInfo emp = SingleEmployee;
            TextView name = findViewById(R.id.tv_employee_name);
            TextView empID = findViewById(R.id.tv_employee_id);
            TextView salary = findViewById(R.id.tv_employee_salary);
            TextView age = findViewById(R.id.tv_employee_age);

            name.setText(emp.getEmployeeName());
            empID.setText(emp.getId());
            salary.setText(emp.getEmployeeSalary());
            age.setText(emp.getEmployeeAge());
        } else {
            Toast.makeText(context, "An error has occurred. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    void showEmpList() {
        fEmpCard.setVisibility(View.GONE);
        fEmpList.setVisibility(View.VISIBLE);
    }

    public void reloadEmployeeRecyclerView() {
        EmployeeAdapter oldAdapter = mEmployeeAdapter;
        mRecyclerView.swapAdapter(oldAdapter, false);
        loadAllEmployeeData(allEmpUrl);
        mEmployeeAdapter.setEmpData(mEmployees);
        mRecyclerView.swapAdapter(mEmployeeAdapter, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAllEmployeeData(String url) {
        new FetchAllEmployeeDataTask().execute(url);
    }

    private void loadSingleEmployeeData(String url) {
        new FetchEmployeeDataTask().execute(url);
    }

    public static class FetchAllEmployeeDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                String employeeJson = NetworkUtils.fetch(url);
                employeeJsonString = employeeJson;
                return employeeJson;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String employeeJson) {
            mLoadingIndicator.setVisibility(View.GONE);
            if(employeeJson != null) {
                mEmployees = JsonUtils.parseEmployeeData(employeeJson);
            }
        }
    }

    public static class FetchEmployeeDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                String employeeJson = NetworkUtils.fetch(url);
                employeeJsonString = employeeJson;
                return employeeJson;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String employeeJson) {
            mLoadingIndicator.setVisibility(View.GONE);
            if(employeeJson != null) {
                SingleEmployee = JsonUtils.parseSingleEmployeeData(employeeJson);
            }
        }
    }
}
