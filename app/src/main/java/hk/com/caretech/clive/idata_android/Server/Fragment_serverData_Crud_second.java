package hk.com.caretech.clive.idata_android.Server;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import hk.com.caretech.clive.idata_android.R;


public class Fragment_serverData_Crud_second extends Fragment implements View.OnClickListener {

    private String baseUrl;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serverdata_second_crud, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.create_bttn).setOnClickListener(this);
        view.findViewById(R.id.retrive_bttn).setOnClickListener(this);
        view.findViewById(R.id.update_bttn).setOnClickListener(this);
        view.findViewById(R.id.del_bttn).setOnClickListener(this);
        view.findViewById(R.id.previous_serverdata_crud).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                baseUrl = bundle.getString("baseUrl");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putString("baseUrl", baseUrl);
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.previous_serverdata_crud:
                NavHostFragment.findNavController(Fragment_serverData_Crud_second.this)
                        .navigate(R.id.action_SecondFragment_serverData_Crud_to_FirstFragmentIpAddress);
                break;

            case R.id.create_bttn:
                NavHostFragment.findNavController(Fragment_serverData_Crud_second.this)
                        .navigate(R.id.action_SecondFragment_serverData_Crud_to_fragment_crud_create_elder);
                break;

            case R.id.retrive_bttn:

                NavHostFragment.findNavController(Fragment_serverData_Crud_second.this)
                        .navigate(R.id.action_SecondFragment_serverData_Crud_to_ThirdfragmentCrudRetrieve);
                break;

            case R.id.del_bttn:
                Log.i(TAG, "bundle-outside 2 :" + baseUrl);
                break;


        }
    }
    static String TAG = "Fragment_serverCrud_second";
}