package android.a1ex.com.homework6;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity {
    public TextView firstName;
    public TextView lastName;
    public TextView age;
    public Uri uriFoto;
    public ImageView mFoto;
    private int position;

    Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        age = findViewById(R.id.editAge);
        mFoto = findViewById(R.id.editFoto);
        registerForContextMenu(mFoto);
        mFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EditActivity.this, mFoto);
                popupMenu.inflate(R.menu.photo_menu_context);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String text = "";
                        switch (item.getItemId()) {
                            case R.id.editFoto:
                                editFoto(mFoto);
                                text = "выбор фото...";
                                break;
                            case R.id.deleteFoto:
                                mFoto.setImageResource(R.drawable.nophoto);
                                text = "фото удалено...";
                                break;
                        }

                        Toast.makeText(EditActivity.this, text, Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        Intent intent = getIntent();
        mStudent = intent.getParcelableExtra(ActivityArrayList.EXTRA_STUDENT);
        position = intent.getIntExtra(ActivityArrayList.EXTRA_POSITION, 0);

        firstName.setText(mStudent.getFirstName());
        lastName.setText(mStudent.getLastName());
        age.setText(String.valueOf(mStudent.getAge()));

        if (mStudent.getFoto() != null) {
            mFoto.setImageURI(mStudent.getFoto());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == 1) {
            if (data != null) {
                uriFoto = data.getData();
                mFoto.setImageURI(uriFoto);
                Toast.makeText(this, "выбрано фото...", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void editFoto(View view) {
        Intent fileIntent = new Intent(Intent.ACTION_PICK);
        fileIntent.setType("image/*");
        startActivityForResult(fileIntent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.student_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemOk:
                saveStudent();
                break;
        }
        return false;
    }

    public void saveStudent() {
        mStudent.setFirstName(firstName.getText().

                toString());
        mStudent.setLastName(lastName.getText().

                toString());

        int mAge;
        try {
            mAge = Integer.valueOf(age.getText().toString());
        } catch (
                NumberFormatException e)

        {
            mAge = 0;
        }
        mStudent.setAge(mAge);
        mStudent.setFoto(uriFoto);

        Intent intent = new Intent();
        intent.putExtra(ActivityArrayList.EXTRA_STUDENT, mStudent);
        intent.putExtra(ActivityArrayList.EXTRA_POSITION, position);

        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.photo_menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String text = "";
        switch (item.getItemId()) {
            case R.id.editFoto:
                editFoto(mFoto);
                text = "выбор фото...";
                break;
            case R.id.deleteFoto:
                mFoto.setImageResource(R.drawable.nophoto);
                text = "фото удалено...";
                break;
        }
        Toast.makeText(EditActivity.this, text, Toast.LENGTH_LONG).show();
        return false;
    }
}
