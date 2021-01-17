package com.hieu.ThuVien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddSach extends AppCompatActivity {
    EditText edt_ma_sach, edt_so_luong_ban_sao, edt_tac_gia, edt_ten_sach;
    Button btn_add_sach, btn_close_add_sach;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sach);
        edt_ma_sach = findViewById(R.id.edt_ma_sach);
        edt_so_luong_ban_sao = findViewById(R.id.edt_so_luong_ban_sao);
        edt_tac_gia = findViewById(R.id.edt_tac_gia);
        edt_ten_sach = findViewById(R.id.edt_ten_sach);
        btn_add_sach = findViewById(R.id.btn_add_sach);
        btn_close_add_sach = findViewById(R.id.btn_close_add_sach);
        db = FirebaseFirestore.getInstance();
        final String id_sach = getIntent().getStringExtra("id");
        btn_close_add_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> sach = new HashMap<>();
                sach.put("id", id_sach);
                sach.put("maSach", edt_ma_sach.getText().toString());
                sach.put("soLuongBanSao", Integer.valueOf(edt_so_luong_ban_sao.getText().toString()));
                sach.put("tacGia", edt_tac_gia.getText().toString());
                sach.put("tenSach", edt_ten_sach.getText().toString());

                db.collection("Sach").document(id_sach).set(sach)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Đã thêm sách mới thành công", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi khi thêm sách mới", Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });
    }
}
