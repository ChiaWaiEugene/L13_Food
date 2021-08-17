package sg.edu.rp.c346.id20041877.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etLocation, etComment;
    RatingBar ratingbar1;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etLocation = findViewById(R.id.editTextLocation);
        etComment = findViewById(R.id.editTextComment);
        ratingbar1 = findViewById(R.id.ratingBar1);
        btnInsert = findViewById(R.id.buttonInsert);
        btnShowList = findViewById(R.id.buttonList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String comment = etComment.getText().toString().trim();
                int stars = (int) ratingbar1.getRating();

                if(name.length() == 0 || location.length() == 0) {
                    Toast.makeText(MainActivity.this, "Incomplete input", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertFood(name, location, comment, stars);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Food inserted", Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etLocation.setText("");
                    etComment.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(I);
            }
        });
    }
}