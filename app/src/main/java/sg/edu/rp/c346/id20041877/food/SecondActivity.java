package sg.edu.rp.c346.id20041877.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btn5Stars;
    ListView lv;
    ArrayList<Food> FoodList;
    CustomAdapter ca;
    int requestCode = 9;

    TextView tvID, tvName, tvLocation, tvComment;
    RatingBar ratingStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView) this.findViewById(R.id.listView2);
        btn5Stars = (Button) this.findViewById(R.id.buttonShow5Stars);

        tvID = (TextView) this.findViewById(R.id.textViewID);
        tvName = (TextView) this.findViewById(R.id.textViewName);
        tvLocation = (TextView) this.findViewById(R.id.textViewLocation);
        tvComment = (TextView) this.findViewById(R.id.textViewComment);
        ratingStars = (RatingBar) findViewById(R.id.ratingBar1);

        DBHelper dbh = new DBHelper(this);
        FoodList = dbh.getAllFood();
        dbh.close();

        ca = new CustomAdapter(this, R.layout.row, FoodList);
        lv.setAdapter(ca);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);

                Food currentFood = FoodList.get(position);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.edit, null);

                final EditText etName = viewDialog.findViewById(R.id.editTextName);
                final EditText etLocation = viewDialog.findViewById(R.id.editTextLocation);
                final EditText etComment = viewDialog.findViewById(R.id.editTextComment);
                final RatingBar rstars = viewDialog.findViewById(R.id.ratingStars);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Edit Food Ratings");


                ratingStars.setRating(FoodList.get(position).getStars());

                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FoodList.get(position).setName(etName.getText().toString().trim());
                        FoodList.get(position).setLocation(etLocation.getText().toString().trim());
                        FoodList.get(position).setComment(etComment.getText().toString().trim());

                        int result = dbh.updateFood(FoodList.get(position));
                        if (result>0){
                            Toast.makeText(SecondActivity.this, "Food updated", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(SecondActivity.this, "Food update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = dbh.deleteFood(FoodList.get(position).getId());
                        if (result>0){
                            Toast.makeText(SecondActivity.this, "Food deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(SecondActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                FoodList.clear();
                FoodList.addAll(dbh.getAllFoodByStars(5));
                ca.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            FoodList.clear();
            FoodList.addAll(dbh.getAllFood());
            dbh.close();
            ca.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}