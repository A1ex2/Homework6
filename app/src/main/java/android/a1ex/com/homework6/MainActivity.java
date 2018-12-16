package android.a1ex.com.homework6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_STUDENT = "android.a1ex.com.homework6.extra.STUDENT";
    private static final int REQUEST_CODE = 1;

    public TextView firstName;
    public TextView lastName;
    public TextView age;
    public ImageView mFoto;

    public Student mStudent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.itemView: {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                startActivity(intent);
            }
            break;
            case R.id.itemEdit: {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                startActivityForResult(intent, REQUEST_CODE);
            }
            break;

            case R.id.itemAbout:

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);

                return true;
        }
        return  false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);

        mFoto = findViewById(R.id.mFoto);

        mStudent = new Student("Александр", "Ярош", 30);
        setTextView(mStudent);
    }

    public void setTextView(Student student) {
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        age.setText(String.valueOf(student.getAge()));
        if (student.getFoto() != null) {
            mFoto.setImageURI(student.getFoto());
        }
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
                sendMessage();
            }
        }
    }

    public void sendMessage(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("111")
                .setContentTitle("студент изменен")
                .setContentText(mStudent.toString())
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }
}
