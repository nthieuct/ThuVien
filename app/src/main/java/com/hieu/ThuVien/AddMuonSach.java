package com.hieu.ThuVien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMuonSach extends AppCompatActivity {
    EditText edt_ghi_chu, edt_han_tra, edt_ngay_muon, edt_ngay_tra, edt_nguoi_muon, edt_so_dien_thoai;
    Button btn_add, btn_close;
    FirebaseFirestore db;
    String id_sach, id_muon_sach;
    Calendar calendar;
    SimpleDateFormat dinhdangngay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_muon_sach);
        db = FirebaseFirestore.getInstance();
        edt_ghi_chu = findViewById(R.id.edt_ghi_chu);
        edt_han_tra = findViewById(R.id.edt_han_tra);
        edt_ngay_muon = findViewById(R.id.edt_ngay_muon);
        edt_ngay_tra = findViewById(R.id.edt_ngay_tra);
        edt_nguoi_muon = findViewById(R.id.edt_nguoi_muon);
        edt_so_dien_thoai = findViewById(R.id.edt_so_dien_thoai);
        btn_add = findViewById(R.id.btn_add_muon_sach);
        btn_close = findViewById(R.id.btn_close_add_muon_sach);
        calendar = Calendar.getInstance();
        dinhdangngay = new SimpleDateFormat("dd/MM/yyyy");
        edt_han_tra.setText(dinhdangngay.format(calendar.getTime()));
        final DatePickerDialog.OnDateSetListener dateHT = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_han_tra.setText(dinhdangngay.format(calendar.getTime()));
            }
        };
        edt_han_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddMuonSach.this, dateHT, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_ngay_muon.setText(dinhdangngay.format(calendar.getTime()));
        final DatePickerDialog.OnDateSetListener dateNM = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_ngay_muon.setText(dinhdangngay.format(calendar.getTime()));
            }
        };
        edt_ngay_muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddMuonSach.this, dateNM, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_ngay_tra.setText(dinhdangngay.format(calendar.getTime()));
        final DatePickerDialog.OnDateSetListener dateNT = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_ngay_tra.setText(dinhdangngay.format(calendar.getTime()));
            }
        };
        edt_ngay_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddMuonSach.this, dateNT, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        id_sach = getIntent().getStringExtra("id_sach");
        id_muon_sach = getIntent().getStringExtra("id_muon_sach");
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> muon_sach = new HashMap<>();
                muon_sach.put("id", id_muon_sach);
                muon_sach.put("ghiChu", edt_ghi_chu.getText().toString());
                muon_sach.put("hanTra", edt_han_tra.getText().toString());
                muon_sach.put("ngayMuon", edt_ngay_muon.getText().toString());
                muon_sach.put("ngayTra", edt_ngay_tra.getText().toString());
                muon_sach.put("nguoiMuon", edt_nguoi_muon.getText().toString());
                muon_sach.put("soDienThoai", edt_so_dien_thoai.getText().toString());

                db.collection(id_sach).document(id_muon_sach).set(muon_sach)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Đã thêm mới mượn sách thành công", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi khi thêm mới mượn sách: ", Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });
    }
}
