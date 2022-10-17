package vn.edu.greenwich.cw_1_sample.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import vn.edu.greenwich.cw_1_sample.OnCurrencySelected;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;

public class ResetDataConfirmFragment extends DialogFragment  {
    protected String _message;
    private OnCurrencySelected callback = null;

    public ResetDataConfirmFragment(OnCurrencySelected callback) {
        this.callback = callback;
    }

    public ResetDataConfirmFragment() {
        _message = "";
    }

    public ResetDataConfirmFragment(String message) {
        _message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.label_confirmation)
                .setMessage(_message)
                .setNegativeButton(R.string.label_cancel, (dialog, id) -> dismiss())
                .setPositiveButton(R.string.label_confirm, (dialog, id) -> delete())
                .create();
    }

    protected void delete() {
        Intent intent = new Intent();
        intent.setAction("hello");
        intent.putExtra("data","reset");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        ResimaDAO resimaDAO = new ResimaDAO(getContext());
        long checkRequest =  resimaDAO.resertDataRequest();
        long checkResident = resimaDAO.resertDataResident();
        if (checkResident >0 || checkRequest >0){
            Toast.makeText(getContext(),R.string.notification_delete_success,Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getContext(),R.string.notification_delete_fail,Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }
}