package vn.edu.greenwich.cw_1_sample.ui.request.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.models.Request;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    protected ArrayList<Request> _list;

    public RequestAdapter(ArrayList<Request> list) {
        _list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_request, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = _list.get(position);
        holder.listItemRequestMoney.setText(request.getMoney()+"");
        holder.listItemRequestDate.setText(request.getDate());
        holder.listItemRequestTime.setText(request.getTime());
        holder.listItemRequestType.setText(request.getType());
        holder.listItemRequestContent.setText(request.getContent());
    }

    @Override
    public int getItemCount() {
        return _list == null ? 0 : _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView listItemRequestDate, listItemRequestTime, listItemRequestType, listItemRequestContent,listItemRequestMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemRequestMoney = itemView.findViewById(R.id.listItemRequestMoney);
            listItemRequestDate = itemView.findViewById(R.id.listItemRequestDate);
            listItemRequestTime = itemView.findViewById(R.id.listItemRequestTime);
            listItemRequestType = itemView.findViewById(R.id.listItemRequestType);
            listItemRequestContent = itemView.findViewById(R.id.listItemRequestContent);
        }
    }
}