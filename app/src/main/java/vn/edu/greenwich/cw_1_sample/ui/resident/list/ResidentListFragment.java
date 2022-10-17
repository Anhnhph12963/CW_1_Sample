package vn.edu.greenwich.cw_1_sample.ui.resident.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.greenwich.cw_1_sample.OnCurrencySelected;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Resident;
import vn.edu.greenwich.cw_1_sample.ui.dialog.ResetDataConfirmFragment;

public class ResidentListFragment extends Fragment {
    protected ArrayList<Resident> residentList = new ArrayList<>();
    private ResidentAdapter residentAdapter;
    protected ResimaDAO _db;
    protected TextView fmResidentListEmptyNotice;
    protected RecyclerView fmResidentListRecylerView;
    private SearchView searchView;
    private String a;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            a = intent.getStringExtra("data");
            if (!a.isEmpty()){
                residentList = _db.getResidentList(null, null, false);
                residentAdapter = new ResidentAdapter(residentList);
                fmResidentListRecylerView.setAdapter(residentAdapter);
                fmResidentListEmptyNotice.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        _db = new ResimaDAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident_list, container, false);
        residentList = _db.getResidentList(null, null, false);
        searchView = view.findViewById(R.id.fmFilter);
        fmResidentListRecylerView = view.findViewById(R.id.fmResidentListRecylerView);
        fmResidentListEmptyNotice = view.findViewById(R.id.fmResidentListEmptyNotice);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        residentAdapter = new ResidentAdapter(residentList);
        fmResidentListRecylerView.addItemDecoration(dividerItemDecoration);
        Log.d("getData", "onResume: " + residentList.size());
        fmResidentListRecylerView.setAdapter(residentAdapter);
        fmResidentListRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("test", "onReceive1: "+residentList.size());
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadcastReceiver,new IntentFilter("hello"));
        // Show "No Resident." message.
        fmResidentListEmptyNotice.setVisibility(residentList.isEmpty() ? View.VISIBLE : View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                residentAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                residentAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver);
    }
}