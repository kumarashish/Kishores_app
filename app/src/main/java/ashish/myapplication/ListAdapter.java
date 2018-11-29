package ashish.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 29-11-2018.
 */

public class ListAdapter extends BaseAdapter {
    ArrayList<LorryReportModel> list;
    Activity act;
    LayoutInflater inflater;

    public ListAdapter(ArrayList<LorryReportModel> list, Activity act)
    {this.list=list;
    this.act=act;
        inflater = act.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LorryReportModel model=list.get(position);
        View view = inflater.inflate(R.layout.booking_row, null, true);
        TextView bookingId=(TextView )view.findViewById(R.id.bookingId);
        TextView type=(TextView )view.findViewById(R.id.type);
        TextView source_dest=(TextView )view.findViewById(R.id.source_dest);
        bookingId.setText("Booking Id : "+model.getId());
        type.setText("Lorry Type : "+model.getLorrytype() +"\n\nItem :" +model.getItem());
        source_dest.setText(model.getBookfrom() +" - "+model.getBookto());
        return view;
    }
}
