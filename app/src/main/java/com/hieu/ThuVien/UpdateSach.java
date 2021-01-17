package com.hieu.ThuVien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hieu.ThuVien.model.Sach;

public class UpdateSach extends AppCompatActivity {
    EditText edt_ma_sach, edt_so_luong_ban_sao, edt_tac_gia, edt_ten_sach;
    Button btn_update_sach, btn_close_update_sach;
    FirebaseFirestore db;
    Sach sach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sach);
        edt_ma_sach = findViewById(R.id.edt_ma_sach);
        edt_so_luong_ban_sao = findViewById(R.id.edt_so_luong_ban_sao);
        edt_tac_gia = findViewById(R.id.edt_tac_gia);
        edt_ten_sach = findViewById(R.id.edt_ten_sach);
        btn_update_sach = findViewById(R.id.btn_update_sach);
        btn_close_update_sach = findViewById(R.id.btn_close_update_sach);
        db = FirebaseFirestore.getInstance();
        final String id_sach = getIntent().getStringExtra("id");

        DocumentReference document = db.collection("Sach").document(id_sach);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        sach = document.toObject(Sach.class);
                        edt_ma_sach.setText(sach.getMaSach());
                        edt_so_luong_ban_sao.setText(sach.getSoLuongBanSao().toString());
                        edt_tac_gia.setText(sach.getTacGia());
                        edt_ten_sach.setText(sach.getTenSach().toString());
                    } else {
                        Log.d("Cập nhật sách: ", "Không có sách");
                    }
                } else {
                    Log.d("Cập nhật sách: ", "Lỗi đọc sách", task.getException());
                }
            }
        });

        btn_close_update_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_update_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Sach").document(id_sach)
                        .update("maSach", edt_ma_sach.getText().toString(),
                                "soLuongBanSao", Integer.valueOf(edt_so_luong_ban_sao.getText().toString()),
                                "tacGia", edt_tac_gia.getText().toString(),
                                "tenSach", edt_ten_sach.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Đã cập nhật thông tin sách thành công", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi khi cập nhật: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });
    }
}
