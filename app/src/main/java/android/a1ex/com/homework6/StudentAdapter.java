package android.a1ex.com.homework6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private ArrayList<Student> mStudents;
    private int mResourse;
    private LayoutInflater mInflater;

    public StudentAdapter(Context context, int resource, ArrayList<Student> objects) {
        super(context, resource, objects);

        mStudents = objects;
        mResourse = resource;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mResourse, null);

        Student student = mStudents.get(position);

        ((TextView)convertView.findViewById(R.id.fio2)).setText(student.toString());

        if (student.getFoto() != null) {
            ((ImageView)convertView.findViewById(R.id.viewFoto2)).setImageURI(student.getFoto());
        }

        return convertView;
    }
}
