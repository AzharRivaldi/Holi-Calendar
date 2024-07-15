package com.azhar.holicalendar.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.azhar.holicalendar.R;
import com.azhar.holicalendar.adapter.MonthlyAdapter;
import com.azhar.holicalendar.model.ModelMain;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentMonthly extends Fragment {

    Spinner spBulan;
    MaterialButton btnSearch;
    RecyclerView rvDateMonthly;
    LinearLayout linearHasil;
    ProgressDialog progressDialog;
    String strInputTeks;
    MonthlyAdapter nowAdapter;
    List<ModelMain> modelMainList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spBulan = view.findViewById(R.id.spBulan);
        rvDateMonthly = view.findViewById(R.id.rvDateMonthly);
        btnSearch = view.findViewById(R.id.btnSearch);
        linearHasil = view.findViewById(R.id.linearHasil);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Tunggu sebentar yaa");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("sedang memeriksa data...");

        linearHasil.setVisibility(View.GONE);
        rvDateMonthly.setHasFixedSize(true);
        rvDateMonthly.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.daftar_bulan));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBulan.setAdapter(arrayAdapter);

        spBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                strInputTeks = String.valueOf(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //get list data API
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strInputTeks.equals("0")) {
                    Toast.makeText(getContext(), "Form tidak boleh kosong!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    getListData(strInputTeks);
                }
            }
        });
    }

    private void getListData(String strInputTeks) {
        progressDialog.show();

        AndroidNetworking.get("https://dayoffapi.vercel.app/api?month=" + strInputTeks)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        modelMainList.clear();
                        if (response.length() == 0) {
                            progressDialog.dismiss();
                            linearHasil.setVisibility(View.GONE);
                            Toast.makeText(getContext(),
                                    "Ups, tidak ada data ditanggal tersebut! ",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ModelMain dataApi = new ModelMain();
                                    dataApi.setStrTanggal(jsonObject.getString("tanggal"));
                                    dataApi.setStrKeterangan(jsonObject.getString("keterangan"));
                                    dataApi.setStrCuti(jsonObject.getString("is_cuti"));
                                    modelMainList.add(dataApi);
                                }
                                nowAdapter = new MonthlyAdapter(getContext(), modelMainList);
                                rvDateMonthly.setAdapter(nowAdapter);
                                nowAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                linearHasil.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "Ups, " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Ups, " + anError.getErrorDetail(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}