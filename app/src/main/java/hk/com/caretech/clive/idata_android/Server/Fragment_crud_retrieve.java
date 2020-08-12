package hk.com.caretech.clive.idata_android.Server;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import hk.com.caretech.clive.idata_android.R;
import okhttp3.HttpUrl;

public class Fragment_crud_retrieve extends Fragment implements View.OnClickListener {

    private String url_elder;
    private Spinner spinner;
    private EditText editText;
    private String baseUrl;
    private Intent intent;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud_retrive, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        view.findViewById(R.id.previous_crud_retrieve).setOnClickListener(this);
        view.findViewById(R.id.retriveBysummit_bttn_crud_retrieve).setOnClickListener(this);
        view.findViewById(R.id.retrieveAllsummit_bttn_crud_retrieve).setOnClickListener(this);

        spinner = view.findViewById(R.id.spinner_crud_retrieve);
        editText = view.findViewById(R.id.editText_crud_retrieve);
        intent = new Intent(getActivity(), RetrieveElderActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previous_crud_retrieve:
                NavHostFragment.findNavController(Fragment_crud_retrieve.this)
                        .navigate(R.id.action_ThirdfragmentCrudRetriev_to_SecondFragmentCrudSelect);
                break;

            case R.id.retriveBysummit_bttn_crud_retrieve:
                String[] searchelders_array =getResources().getStringArray(R.array.searchelders_array);
                int spinnerSelected=spinner.getSelectedItemPosition();

                String searchBy = searchelders_array[spinnerSelected];
                String searchValue = editText.getText().toString();

                String searchByUrl = url_elder+"/"+searchBy;
                 HttpUrl.Builder httpBuilder = HttpUrl.parse(searchByUrl).newBuilder();


                if(!TextUtils.isEmpty(searchValue)) {
                    HttpUrl finalUrl = httpBuilder.addQueryParameter(searchBy, searchValue).build();



                    intent.putExtra("finalUrl", finalUrl.toString());
                    Log.i(TAG, "finalUrl : " + finalUrl.toString());
                    Log.i(TAG, "baseUrl : "+baseUrl);
                    startActivity(intent);

//                    bundle.putString("finalUrl", finalUrl.toString());
//                    getParentFragmentManager().setFragmentResult("requestKey", bundle);
//                NavHostFragment.findNavController(Fragment_crud_retrieve.this)
//                        .navigate(R.id.action_ThirdfragmentCrudRetrieve_to_FourthfragmentfragmentCrudRetrieveRecyclerview);
                }
                break;


            case R.id.retrieveAllsummit_bttn_crud_retrieve:

                intent.putExtra("finalUrl", url_elder);
                intent.putExtra("baseUrl", baseUrl);
                Log.i(TAG, "baseUrl : "+baseUrl);
                startActivity(intent);

//                NavHostFragment.findNavController(Fragment_crud_retrieve.this)
//                        .navigate(R.id.action_ThirdfragmentCrudRetrieve_to_FourthfragmentfragmentCrudRetrieveRecyclerview);
                    break;
    }
}
 static String TAG = Fragment_crud_retrieve.class.getName();
}