//package hk.com.caretech.clive.idata_android.Server;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentResultListener;
//import androidx.navigation.fragment.NavHostFragment;
//import hk.com.caretech.clive.idata_android.R;
//
//
//public class Fragment_crud_createeeeeeeee extends Fragment implements View.OnClickListener {
//
//    private String baseUrl;
//
//    @Override
//    public View onCreateView(
//            LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState
//    ) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_crud_createeeeeeeeeeee, container, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
//                baseUrl = bundle.getString("baseUrl");
//                Log.i(TAG, "baseUrl :"+ baseUrl);
//            }});
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Bundle bundle = new Bundle();
//        bundle.putString("baseUrl", baseUrl);
//        getParentFragmentManager().setFragmentResult("requestKey", bundle);
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.previous_crud_create).setOnClickListener(this);
//        view.findViewById(R.id.bttn_addElder_fragment_crud_create).setOnClickListener(this);
//        view.findViewById(R.id.bttn_addTemp_fragment_crud_create).setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.previous_crud_create:
//                NavHostFragment.findNavController(Fragment_crud_createeeeeeeee.this)
//                        .navigate(R.id.actiecondFragmentCrudSelect2);
//                break;
//
//            case R.id.bttn_addElder_fragment_crud_create:
//               NavHostFragment.findNavController(Fragment_crud_createeeeeeeee.this)
//                        .navigate(R.id.action_fragment_crud_create_to_fragment_crud_create_elder);
//                break;
//
//
//            case R.id.bttn_addTemp_fragment_crud_create:
//
//                NavHostFragment.findNavController(Fragment_crud_createeeeeeeee.this)
//                        .navigate(R.id.action_fragment_crud_create_to_fragment_crud_create_temp);
//                    break;
//    }
//}
// static String TAG = "Fragment_crud_create";
//}