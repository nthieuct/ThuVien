package com.hieu.ThuVien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hieu.ThuVien.model.MuonSach;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateMuonSach extends AppCompatActivity {
    EditText edt_ghi_chu, edt_han_tra, edt_ngay_muon, edt_ngay_tra, edt_nguoi_muon, edt_so_dien_thoai;
    Button btn_update, btn_close;
    FirebaseFirestore db;
    String id_sach, id_muon_sach;
    Calendar calendar;
    SimpleDateFormat dinhdangngay;
    MuonSach muonSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_muon_sach);

        db = FirebaseFirestore.getInstance();
        edt_ghi_chu = findViewById(R.id.edt_ghi_chu);
        edt_han_tra = findViewById(R.id.edt_han_tra);
        edt_ngay_muon = findViewById(R.id.edt_ngay_muon);
        edt_ngay_tra = findViewById(R.id.edt_ngay_tra);
        edt_nguoi_muon = findViewById(R.id.edt_nguoi_muon);
        edt_so_dien_thoai = findViewById(R.id.edt_so_dien_thoai);
        btn_update = findViewById(R.id.btn_update_muon_sach);
        btn_close = findViewById(R.id.btn_close_update_muon_sach);
        calendar = Calendar.getInstance();
        dinhdangngay = new SimpleDateFormat("dd/MM/yyyy");

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
                new DatePickerDialog(UpdateMuonSach.this, dateHT, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                new DatePickerDialog(UpdateMuonSach.this, dateNM, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                new DatePickerDialog(UpdateMuonSach.this, dateNT, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        id_sach = getIntent().getStringExtra("id_sach");
        id_muon_sach = getIntent().getStringExtra("id_muon_sach");

        DocumentReference document = db.collection("Sach").document(id_sach).collection("MuonSach").document(id_muon_sach);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        muonSach = document.toObject(MuonSach.class);
                        edt_ghi_chu.setText(muonSach.getGhiChu());
                        edt_han_tra.setText(muonSach.getHanTra());
                        edt_ngay_muon.setText(muonSach.getNgayMuon());
                        edt_ngay_tra.setText(muonSach.getNgayTra());
                        edt_nguoi_muon.setText(muonSach.getNguoiMuon());
                        edt_so_dien_thoai.setText(muonSach.getSoDienThoai());
                    } else {
                        Log.d("Cập nhật mượn sách: ", "Không có mượn sách");
                    }
                } else {
                    Log.d("Cập nhật mượn sách: ", "Lỗi đọc mượn sách", task.getException());
                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Sach").document(id_sach).collection("MuonSach").document(id_muon_sach)
                        .update("ghiChu", edt_ghi_chu.getText().toString(),
                                "hanTra", edt_han_tra.getText().toString(),
                                "ngayMuon", edt_ngay_muon.getText().toString(),
                                "ngayTra", edt_ngay_tra.getText().toString(),
                                "nguoiMuon", edt_nguoi_muon.getText().toString(),
                                "soDienThoai", edt_so_dien_thoai.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Đã cập nhật thông tin mượn sách thành công", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lôi khi cập nhật: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
