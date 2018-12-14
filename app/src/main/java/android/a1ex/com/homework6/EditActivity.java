package android.a1ex.com.homework6;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EditActivity extends AppCompatActivity {
    public TextView firstName;
    public TextView lastName;
    public TextView age;
    public Uri uriFoto;
    public ImageView mFoto;

    Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        age = findViewById(R.id.editAge);
        mFoto = findViewById(R.id.editFoto);

        Intent intent = getIntent();
        mStudent = intent.getParcelableExtra(MainActivity.EXTRA_STUDENT);

        firstName.setText(mStudent.getFirstName());
        lastName.setText(mStudent.getLastName());
        age.setText(String.valueOf(mStudent.getAge()));

        if (mStudent.getFoto() != null){
            mFoto.setImageURI(mStudent.getFoto());
        }

        findViewById(R.id.buttonEditFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_PICK);
                fileIntent.setType("image/*");
                startActivityForResult(fileIntent, 1);
            }
        });

        findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStudent.setFirstName(firstName.getText().toString());
                mStudent.setLastName(lastName.getText().toString());

                int mAge;
                try {
                    mAge = Integer.valueOf(age.getText().toString());
                } catch (NumberFormatException e) {
                    mAge = 0;
                }
                mStudent.setAge(mAge);

                mStudent.setFoto(uriFoto);

                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_STUDENT, mStudent);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == 1) {
            if (data != null) {
                uriFoto = data.getData();
                mFoto.setImageURI(uriFoto);
            }
        }
    }
}
