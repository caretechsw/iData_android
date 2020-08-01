package com.example.idata_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

public class Fragment_2_crudSelect extends Fragment implements View.OnClickListener {


    String url;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2_crudselect, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.create_bttn).setOnClickListener(this);
        view.findViewById(R.id.retrive_bttn).setOnClickListener(this);
        view.findViewById(R.id.update_bttn).setOnClickListener(this);
        view.findViewById(R.id.del_bttn).setOnClickListener(this);
        view.findViewById(R.id.previous_crudselect).setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                url = bundle.getString("url");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        bundle.putString("url", url);
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.retrive_bttn:

                NavHostFragment.findNavController(Fragment_2_crudSelect.this)
                .navigate(R.id.action_SecondFragmentCrudSelect_to_ThirdfragmentCrudRetrieve);
        break;

            case R.id.previous_crudselect:
                NavHostFragment.findNavController(Fragment_2_crudSelect.this)
                        .navigate(R.id.action_SecondFragmentCrudSelect_to_FirstFragmentIpAddress);
                break;

            case R.id.del_bttn:
                Log.i(TAG, "bundle-outside 2 :"+url);
                break;
        }
    }
    static String TAG = "Fragment_2_crudSelect";
}