package vn.edu.greenwich.cw_1_sample.ui.resident;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Resident;
import vn.edu.greenwich.cw_1_sample.ui.dialog.CalendarFragment;

public class ResidentRegisterFragment extends Fragment
        implements ResidentRegisterConfirmFragment.FragmentListener, CalendarFragment.FragmentListener {
    public static final String ARG_PARAM_RESIDENT = "resident";

    protected TextInputLayout  fmResidentRegisterName,fmResidentRegisterDestination,fmResidentRegisterComment,fmResidentRegisterNote;
    protected LinearLayout fmResidentRegisterLinearLayout;
    protected TextView fmResidentRegisterStartDate;
    protected SwitchMaterial fmResidentRegisterOwner;
    protected TextView fmResidentRegisterError;
    protected Button fmResidentRegisterButton;

    protected ResimaDAO _db;

    public ResidentRegisterFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident_register, container, false);

        fmResidentRegisterError =  view.findViewById(R.id.fmResidentRegisterError);
        fmResidentRegisterName = view.findViewById(R.id.fmResidentRegisterName);
        fmResidentRegisterStartDate = view.findViewById(R.id.fmResidentRegisterStartDate);
        fmResidentRegisterOwner = view.findViewById(R.id.fmResidentRegisterOwner);
        fmResidentRegisterDestination = view.findViewById(R.id.fmResidentRegisterDestination);
        fmResidentRegisterComment = view.findViewById(R.id.fmResidentRegisterComment);
        fmResidentRegisterNote = view.findViewById(R.id.fmResidentRegisterNote);
        fmResidentRegisterButton = view.findViewById(R.id.fmResidentRegisterButton);
        fmResidentRegisterLinearLayout = view.findViewById(R.id.fmResidentRegisterLinearLayout);

        // Show Calendar for choosing a date.
        fmResidentRegisterStartDate.setOnTouchListener((v, motionEvent) -> showCalendar(motionEvent));

        // Update current resident.
        if (getArguments() != null) {
            Resident resident = (Resident) getArguments().getSerializable(ARG_PARAM_RESIDENT);
            Log.d("resident1", "onCreateView: "+resident.getName());
            fmResidentRegisterName.getEditText().setText(resident.getName());
            fmResidentRegisterDestination.getEditText().setText(resident.get_destination());
            fmResidentRegisterStartDate.setText(resident.getStartDate());
            fmResidentRegisterOwner.setChecked(resident.getOwner() == 1 ? true : false);
            fmResidentRegisterStartDate.setText(resident.getStartDate());

            fmResidentRegisterButton.setText(R.string.label_update);
            fmResidentRegisterButton.setOnClickListener(v -> update(resident.getId()));

            return view;
        }

        // Create new resident.
        fmResidentRegisterButton.setOnClickListener(v -> register());

        return view;
    }

    protected void register() {
        if (isValidForm()) {
            Resident resident = getResidentFromInput(-1);

            new ResidentRegisterConfirmFragment(resident).show(getChildFragmentManager(), null);

            return;
        }

        moveButton();
    }

    protected void update(long id) {
        if (isValidForm()) {
            Resident resident = getResidentFromInput(id);

            long status = _db.updateResident(resident);

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromResidentRegisterFragment(status);

            return;
        }

        moveButton();
    }

    protected boolean showCalendar(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    protected Resident getResidentFromInput(long id) {
        String name = fmResidentRegisterName.getEditText().getText().toString();
        String destination = fmResidentRegisterDestination.getEditText().getText().toString();
        String startDate = fmResidentRegisterStartDate.getText().toString();
        int owner = fmResidentRegisterOwner.isChecked() ? 1 : 0;
        String comment = fmResidentRegisterComment.getEditText().getText().toString();
        String note = fmResidentRegisterNote.getEditText().getText().toString();
        return new Resident(id, name,destination, startDate,note ,comment , owner);
    }

    protected boolean isValidForm() {
        boolean isValid = true;

        String error = "";
        String name = fmResidentRegisterName.getEditText().getText().toString();
        String startDate = fmResidentRegisterStartDate.getText().toString();

        if (name == null || name.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_name) + "\n";
            isValid = false;
        }

        if (startDate == null || startDate.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_start_date) + "\n";
            isValid = false;
        }

        fmResidentRegisterError.setText(error);

        return isValid;
    }

    protected void moveButton() {
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) fmResidentRegisterButton.getLayoutParams();

        int linearLayoutPaddingLeft = fmResidentRegisterLinearLayout.getPaddingLeft();
        int linearLayoutPaddingRight = fmResidentRegisterLinearLayout.getPaddingRight();
        int linearLayoutWidth = fmResidentRegisterLinearLayout.getWidth() - linearLayoutPaddingLeft - linearLayoutPaddingRight;

        btnParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        btnParams.topMargin += fmResidentRegisterButton.getHeight();
        btnParams.leftMargin = btnParams.leftMargin == 0 ? linearLayoutWidth - fmResidentRegisterButton.getWidth() : 0;

        fmResidentRegisterButton.setLayoutParams(btnParams);
    }

    @Override
    public void sendFromResidentRegisterConfirmFragment(long status) {
        switch ((int) status) {
            case -1:
                Toast.makeText(getContext(), R.string.notification_create_fail, Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), R.string.notification_create_success, Toast.LENGTH_SHORT).show();

                fmResidentRegisterName.getEditText().setText("");
                fmResidentRegisterStartDate.setText("");
                fmResidentRegisterNote.getEditText().setText("");
                fmResidentRegisterComment.getEditText().setText("");
                fmResidentRegisterDestination.getEditText().setText("");
                fmResidentRegisterName.requestFocus();
        }
    }

    @Override
    public void sendFromCalendarFragment(String date) {
        fmResidentRegisterStartDate.setText(date);
    }

    public interface FragmentListener {
        void sendFromResidentRegisterFragment(long status);
    }
}