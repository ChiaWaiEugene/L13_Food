package sg.edu.rp.c346.id20041877.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context context;
    int layout_id;
    ArrayList<Food> FoodList;

    public CustomAdapter(Context context, int resource, ArrayList<Food> FoodList) {
        super(context, resource, FoodList);

        this.context = context;
        this.layout_id = resource;
        this.FoodList = FoodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvLocation = rowView.findViewById(R.id.textViewLocation);
        TextView tvComment = rowView.findViewById(R.id.textViewComment);
        RatingBar rating = rowView.findViewById(R.id.ratingStars);

        Food FoodItem = FoodList.get(position);
        tvName.setText(FoodItem.getName());
        tvLocation.setText(FoodItem.getLocation());
        tvComment.setText(FoodItem.getComment());



        return rowView;
    }
}
