package com.azhar.holicalendar.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.azhar.holicalendar.R;
import com.azhar.holicalendar.adapter.NowAdapter;
import com.azhar.holicalendar.model.ModelMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentNow extends Fragment {

    RecyclerView rvDateNow;
    ProgressDialog progressDialog;
    NowAdapter nowAdapter;
    List<ModelMain> modelMainList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Tunggu sebentar yaa");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("sedang memeriksa data...");

        rvDateNow = view.findViewById(R.id.rvDateNow);
        rvDateNow.setHasFixedSize(true);
        rvDateNow.setLayoutManager(new LinearLayoutManager(getContext()));

        //get list data API
        getListData();
    }

    private void getListData() {
        progressDialog.show();

        AndroidNetworking.get("https://dayoffapi.vercel.app/api")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelMain dataApi = new ModelMain();
                                dataApi.setStrTanggal(jsonObject.getString("tanggal"));
                                dataApi.setStrKeterangan(jsonObject.getString("keterangan"));
                                dataApi.setStrCuti(jsonObject.getString("is_cuti"));
                                modelMainList.add(dataApi);
                            }
                            nowAdapter = new NowAdapter(getContext(), modelMainList);
                            rvDateNow.setAdapter(nowAdapter);
                            nowAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Ups, gagal! " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Ups, error! " + anError.getErrorDetail(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}