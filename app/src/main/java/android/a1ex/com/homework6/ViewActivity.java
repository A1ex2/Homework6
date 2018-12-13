package android.a1ex.com.homework6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        Student mStudent = intent.getParcelableExtra(MainActivity.EXTRA_STUDENT);

        TextView textView = findViewById(R.id.fio);
        textView.setText(mStudent.toString());

        ImageView mFoto = findViewById(R.id.viewFoto);

    }
}
