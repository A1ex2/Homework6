package android.a1ex.com.homework6;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityArrayList extends AppCompatActivity {
    public static final String EXTRA_STUDENT = "android.a1ex.com.homework6.extra.STUDENT";
    public static final String EXTRA_POSITION = "android.a1ex.com.homework6.extra.POSITION";
    private static final int REQUEST_CODE = 1;

    private ListView mListView;
    private ArrayList<Student> mStudents = new ArrayList<>();
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_list);

        mListView = findViewById(R.id.listViewStudents);

//        for (int i = 0; i < 50; i++) {
//            mStudents.add(new Student("Ivan" + i, "Ivanov" + i, i));
//        }

        findViewById(R.id.addStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student mStudent = new Student();

                Intent intent = new Intent(ActivityArrayList.this, EditActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                intent.putExtra(EXTRA_POSITION, mStudents.size() + 1);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        adapter = new StudentAdapter(this, R.layout.student_item, mStudents);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student mStudent = mStudents.get(position);

                Intent intent = new Intent(ActivityArrayList.this, EditActivity.class);
                intent.putExtra(EXTRA_STUDENT, mStudent);
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student mStudent = mStudents.get(position);
                sendMessage(mStudent, "удален");
                mStudents.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAbout:

                Intent intent = new Intent(ActivityArrayList.this, AboutActivity.class);
                startActivity(intent);

                return true;
        }
        return  false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == REQUEST_CODE) {
            if (data != null) {
                int position = data.getIntExtra(EXTRA_POSITION, mStudents.size());
                Student mStudent = data.getParcelableExtra(EXTRA_STUDENT);

                if (position <= mStudents.size()){
                    Student mStudentOriginal = mStudents.get(position);

                    setTextView(mStudentOriginal, mStudent);
                    sendMessage(mStudentOriginal, "изменен");
                } else {
                    Student mStudentOriginal = new Student();

                    setTextView(mStudentOriginal, mStudent);
                    sendMessage(mStudentOriginal, "добавлен");

                    mStudents.add(mStudentOriginal);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void setTextView(Student sudentOriginal, Student student) {
        sudentOriginal.setFirstName(student.getFirstName());
        sudentOriginal.setLastName(student.getLastName());
        sudentOriginal.setAge(student.getAge());
        sudentOriginal.setFoto(student.getFoto());
    }
    public void sendMessage(Student mStudent, String action){
        Toast.makeText(ActivityArrayList.this, "студент " + mStudent.toString()+ " "+action, Toast.LENGTH_LONG).show();
    }
}
