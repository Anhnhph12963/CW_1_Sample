package vn.edu.greenwich.cw_1_sample.ui.resident;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Request;
import vn.edu.greenwich.cw_1_sample.models.Resident;
import vn.edu.greenwich.cw_1_sample.ui.dialog.DeleteConfirmFragment;
import vn.edu.greenwich.cw_1_sample.ui.request.RequestCreateFragment;
import vn.edu.greenwich.cw_1_sample.ui.request.list.RequestListFragment;

public class ResidentDetailFragment extends Fragment
        implements DeleteConfirmFragment.FragmentListener, RequestCreateFragment.FragmentListener {
    public static final String ARG_PARAM_RESIDENT = "resident";

    protected ResimaDAO _db;
    protected Resident _resident;
    protected Button fmResidentDetailRequestButton;
    protected BottomAppBar fmResidentDetailBottomAppBar;
    protected FragmentContainerView fmResidentDetailRequestList;
    protected TextView fmResidentDetailName, fmResidentDetailStartDate, fmResidentDetailOwner,  fmResidentDetailDestination , fmResidentDetailNote, fmResidentDetailComment;

    public ResidentDetailFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident_detail, container, false);

        fmResidentDetailName = view.findViewById(R.id.fmResidentDetailName);
        fmResidentDetailStartDate = view.findViewById(R.id.fmResidentDetailStartDate);
        fmResidentDetailOwner = view.findViewById(R.id.fmResidentDetailOwner);
        fmResidentDetailBottomAppBar = view.findViewById(R.id.fmResidentDetailBottomAppBar);
        fmResidentDetailRequestButton = view.findViewById(R.id.fmResidentDetailRequestButton);
        fmResidentDetailRequestList = view.findViewById(R.id.fmResidentDetailRequestList);
        fmResidentDetailComment  = view.findViewById(R.id.fmResidentDetailComment);
        fmResidentDetailNote = view.findViewById(R.id.fmResidentDetailNote);
        fmResidentDetailDestination = view.findViewById(R.id.fmResidentDetailDestination);

        fmResidentDetailBottomAppBar.setOnMenuItemClickListener(item -> menuItemSelected(item));
        fmResidentDetailRequestButton.setOnClickListener(v -> showAddRequestFragment());

        showDetails();
        showRequestList();

        return view;
    }

    protected void showDetails() {
        String name = getString(R.string.error_not_found);
        String startDate = getString(R.string.error_not_found);
        String owner = getString(R.string.error_not_found);
        String destination = getString(R.string.error_not_found);
        String note = getString(R.string.error_not_found);
        String comment = getString(R.string.error_not_found);

        if (getArguments() != null) {
            _resident = (Resident) getArguments().getSerializable(ARG_PARAM_RESIDENT);
            _resident = _db.getResidentById(_resident.getId()); // Retrieve data from Database.

            name = _resident.getName();
            startDate = _resident.getStartDate();
            owner = _resident.getOwner() == 1 ? getString(R.string.label_owner) : getString(R.string.label_tenant);
            destination = _resident.get_destination();
            comment = _resident.get_comment();
            note = _resident.get_note();
        }

        fmResidentDetailName.setText(name);
        fmResidentDetailStartDate.setText(startDate);
        fmResidentDetailOwner.setText(owner);
        fmResidentDetailDestination.setText(destination);
        fmResidentDetailNote.setText(note);
        fmResidentDetailComment.setText(comment);
    }

    protected void showRequestList() {
        if (getArguments() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RequestListFragment.ARG_PARAM_RESIDENT_ID, _resident.getId());

            // Send arguments (resident id) to RequestListFragment.
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }
    }

    protected boolean menuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.residentUpdateFragment:
                showUpdateFragment();
                return true;

            case R.id.residentDeleteFragment:
                showDeleteConfirmFragment();
                return true;
        }

        return true;
    }

    protected void showUpdateFragment() {
        Bundle bundle = null;

        if (_resident != null) {
            bundle = new Bundle();
            bundle.putSerializable(ResidentUpdateFragment.ARG_PARAM_RESIDENT, _resident);
        }

        Navigation.findNavController(getView()).navigate(R.id.residentUpdateFragment, bundle);
    }

    protected void showDeleteConfirmFragment() {
        new DeleteConfirmFragment(getString(R.string.notification_delete_confirm)).show(getChildFragmentManager(), null);
    }

    protected void showAddRequestFragment() {
        new RequestCreateFragment(_resident.getId()).show(getChildFragmentManager(), null);
    }

    @Override
    public void sendFromDeleteConfirmFragment(int status) {
        if (status == 1 && _resident != null) {
            long numOfDeletedRows = _db.deleteResident(_resident.getId());

            if (numOfDeletedRows > 0) {
                Toast.makeText(getContext(), R.string.notification_delete_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();

                return;
            }
        }

        Toast.makeText(getContext(), R.string.notification_delete_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFromRequestCreateFragment(Request request) {
        if (request != null) {
            request.setResidentId(_resident.getId());

            long id = _db.insertRequest(request);

            Toast.makeText(getContext(), id == -1 ? R.string.notification_create_fail : R.string.notification_create_success, Toast.LENGTH_SHORT).show();

            reloadRequestList();
        }
    }

    protected void reloadRequestList() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RequestListFragment.ARG_PARAM_RESIDENT_ID, _resident.getId());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fmResidentDetailRequestList, RequestListFragment.class, bundle)
                .commit();
    }
}