package com.example.idata_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

public class Fragment_crud_create extends Fragment implements View.OnClickListener {

    String url_elder;
    String baseUrl;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud_create, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                baseUrl = bundle.getString("baseUrl");
               if(url_elder==null&&baseUrl!=null){
                url_elder = baseUrl+"elder";}
               Log.i(TAG, "url_elder :"+ url_elder);
                Log.i(TAG, "baseUrl :"+ baseUrl);
        }});
        }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putString("baseUrl", baseUrl);
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        view.findViewById(R.id.previous_crud_create).setOnClickListener(this);
        view.findViewById(R.id.bttn_addElder_fragment_crud_create).setOnClickListener(this);
        view.findViewById(R.id.bttn_addTemp_fragment_crud_create).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previous_crud_retrieve:
                NavHostFragment.findNavController(Fragment_crud_create.this)
                        .navigate(R.id.action_fragment_crud_create_to_SecondFragmentCrudSelect2);
                break;

            case R.id.bttn_addElder_fragment_crud_create:


               NavHostFragment.findNavController(Fragment_crud_create.this)
                        .navigate(R.id.action_fragment_crud_create_to_fragment_crud_create_elder);
                break;



            case R.id.bttn_addTemp_fragment_crud_create:

             //   NavHostFragment.findNavController(Fragment_crud_create.this)
                 //       .navigate(R.id.action_ThirdfragmentCrudRetrieve_to_FourthfragmentfragmentCrudRetrieveRecyclerview);
                    break;
    }
}
 static String TAG = "Fragment_crud_create";
}