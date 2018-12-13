package android.a1ex.com.homework6;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_STUDENT = "android.a1ex.com.homework6.extra.STUDENT";
    private static final int REQUEST_CODE = 1;

    public TextView firstName;
    public TextView lastName;
    public TextView age;
    public ImageView mFoto;

    public Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);

        ImageView mFoto = findViewById(R.id.foto);

        mStudent = new Student("Александр", "Ярош", 30);
        setTextView(mStudent);
    }

    public void setTextView(Student student) {
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        age.setText(String.valueOf(student.getAge()));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view: {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                startActivity(intent);
            }
            break;
            case R.id.edit: {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                startActivityForResult(intent, REQUEST_CODE);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == REQUEST_CODE) {
            if (data != null) {
                mStudent = data.getParcelableExtra(EXTRA_STUDENT);
                setTextView(mStudent);
            }
        }
    }
}
