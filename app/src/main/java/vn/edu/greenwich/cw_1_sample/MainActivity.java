package vn.edu.greenwich.cw_1_sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.greenwich.cw_1_sample.api.JsonPlaceHolderApi;
import vn.edu.greenwich.cw_1_sample.models.UploadModels;
import vn.edu.greenwich.cw_1_sample.models.UploadResponse;
import vn.edu.greenwich.cw_1_sample.models.detailListChild;
import vn.edu.greenwich.cw_1_sample.ui.dialog.ResetDataConfirmFragment;

public class MainActivity extends AppCompatActivity{
    public static final String BASE_URL = "https://cwservice1786.herokuapp.com/";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    protected NavHostFragment navHostFragment;
    protected NavController navController;
    protected BottomNavigationView bottomNavigationView;
    protected AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.main_nav_bottom);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.residentListFragment, R.id.residentRegisterFragment, R.id.aboutUsFragment
        ).build();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#EBE771"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.resetUsFragment:
                onResetData();
                return true;

            case  R.id.uploadUsFragment:
                onUploadata();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void onUploadata() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

       UploadJson();

//        final RequestQueue queue = Volley.newRequestQueue(this);
//        final String url = "http://serverdomainorip/postdata"; // your URL
//
//        queue.start();
//                HashMap<String, String> params = new HashMap<String,String>();
//                params.put("data", "hello"); // the entered data as the JSON body.
//
//                JsonObjectRequest jsObjRequest = new
//                        JsonObjectRequest(Request.Method.POST,
//                        url,
//                        new JSONObject(params),
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Toast.makeText(getApplicationContext(),"hello"+response,Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
//                    }
//                });
//                queue.add(jsObjRequest);
//            }
//        JSONObject object = new JSONObject();
//        JSONObject Package = new JSONObject();
//        ResimaDAO resimaDAO = new ResimaDAO(this);
//        List<Resident> lstResident = resimaDAO.getResidentList(null, null, false);
//        List<Request> lstRequest = resimaDAO.getRequestList(null, null, false);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("ListResident");
//        DatabaseReference myRef1 = database.getReference("ListRequest");
//        myRef.removeValue();
//        myRef1.removeValue();
//        for (int i =0 ;i < lstResident.size();i++)
//        {
//            String _id = String.valueOf(lstResident.get(i).getId());
//            myRef.child(_id).setValue(lstResident.get(i));
//        }
//        for (int i =0 ;i < lstRequest.size();i++)
//        {
//            String _id = String.valueOf(lstRequest.get(i).getId());
//            myRef1.child(_id).setValue(lstRequest.get(i));
//        }

    }

    private void UploadJson() {
        ArrayList<detailListChild> lstdetailchild = new ArrayList<>();
        lstdetailchild.add(new detailListChild("Android studi","Hello good bye"));
        UploadModels uploadModels = new UploadModels("cw12345",lstdetailchild);
        Call<UploadResponse> call;
        call = jsonPlaceHolderApi.createPost(uploadModels.toString());
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onResetData() {
        new ResetDataConfirmFragment(getString(R.string.notification_delete_confirm)).show(getSupportFragmentManager(),null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_data, menu);
        return true;
    }
}