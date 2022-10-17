package vn.edu.greenwich.cw_1_sample.ui.resident.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.models.Resident;
import vn.edu.greenwich.cw_1_sample.ui.resident.ResidentDetailFragment;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.ViewHolder> implements Filterable {
    protected ArrayList<Resident> _list;
    protected ArrayList<Resident> _listFilter;

    public ResidentAdapter(ArrayList<Resident> list) {
        _list = list;
        _listFilter = _list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_resident, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resident resident = _list.get(position);

        String owner = holder.itemView.getResources().getString(R.string.label_owner);
        String tenant = holder.itemView.getResources().getString(R.string.label_tenant);

        holder.listItemResidentName.setText(resident.getName());
        holder.listItemResidentStartDate.setText(resident.getStartDate());
        holder.listItemResidentOwner.setText(resident.getOwner() == 1 ? owner : tenant);
    }

    @Override
    public int getItemCount() {
        return _list == null ? 0 : _list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSeach = constraint.toString();
                if (strSeach.isEmpty()){
                    _list = _listFilter;
                } else {
                    List<Resident> list = new ArrayList<>();
                    for (Resident user: _listFilter){
                        if (user.getName().toLowerCase().contains(strSeach.toLowerCase())){
                            list.add(user);
                        }
                    }
                    _list = (ArrayList<Resident>) list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = _list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _list = (ArrayList<Resident>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout listItemResident;
        protected TextView listItemResidentName, listItemResidentStartDate, listItemResidentOwner;

        public ViewHolder(View itemView) {
            super(itemView);

            listItemResidentName = itemView.findViewById(R.id.listItemResidentName);
            listItemResidentStartDate = itemView.findViewById(R.id.listItemResidentStartDate);
            listItemResidentOwner = itemView.findViewById(R.id.listItemResidentOwner);

            listItemResident = itemView.findViewById(R.id.listItemResident);
            listItemResident.setOnClickListener(v -> showDetail(v));
        }

        protected void showDetail(View view) {
            Resident resident = _list.get(getAdapterPosition());

            Bundle bundle = new Bundle();
            bundle.putSerializable(ResidentDetailFragment.ARG_PARAM_RESIDENT, resident);

            Navigation.findNavController(view).navigate(R.id.residentDetailFragment, bundle);
        }
    }

}