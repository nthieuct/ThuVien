package com.hieu.ThuVien;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hieu.ThuVien.adapter.MuonSachAdapter;
import com.hieu.ThuVien.model.Sach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MuonSach extends AppCompatActivity {
    ArrayList<com.hieu.ThuVien.model.MuonSach> listMuonSach = new ArrayList<com.hieu.ThuVien.model.MuonSach>();
    MuonSachAdapter muonSachAdapter;
    ListView lst_muon_sach;
    Button btn_add_muon_sach;
    String id_sach, id_muon_sach;
    FirebaseFirestore db;
    TextView txt_ma_sach, txt_so_luong_ban_sao, txt_tac_gia, txt_ten_sach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muon_sach);
        db = FirebaseFirestore.getInstance();

        lst_muon_sach = findViewById(R.id.lst_muon_sach);
        btn_add_muon_sach = findViewById(R.id.btn_add_muon_sach);
        muonSachAdapter = new MuonSachAdapter(this, R.layout.item_muon_sach, listMuonSach);
        lst_muon_sach.setAdapter(muonSachAdapter);

        id_sach = getIntent().getStringExtra("id");

        txt_ma_sach = findViewById(R.id.txt_ma_sach);
        txt_so_luong_ban_sao = findViewById(R.id.txt_so_luong_ban_sao);
        txt_tac_gia = findViewById(R.id.txt_tac_gia);
        txt_ten_sach = findViewById(R.id.txt_ten_sach);
        DocumentReference document = db.collection("Sach").document(id_sach);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Sach sach = document.toObject(Sach.class);
                        txt_ma_sach.setText("Mã sách: " + sach.getMaSach());
                        txt_so_luong_ban_sao.setText("Số lượng bản sao: " + sach.getSoLuongBanSao().toString());
                        txt_tac_gia.setText("Tác giả: " + sach.getTacGia());
                        txt_ten_sach.setText("Tên sách: " + sach.getTenSach());
                    } else {
                        Log.d("Cho mượn sách: ", "Không có sách");
                    }
                } else {
                    Log.d("Cho mượn sách: ", "Lỗi đọc sách", task.getException());
                }
            }
        });

        btn_add_muon_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMuonSach.size() == 0) {
                    id_muon_sach = "1";
                } else {
                    id_muon_sach = String.valueOf(Integer.valueOf(listMuonSach.get(listMuonSach.size() - 1).getId()) + 1);
                }
                Intent add_muon_sach = new Intent(getApplicationContext(), AddMuonSach.class);
                add_muon_sach.putExtra("id_sach", "/Sach/" + id_sach + "/MuonSach");
                add_muon_sach.putExtra("id_muon_sach", id_muon_sach);
                startActivity(add_muon_sach);
            }
        });
        lst_muon_sach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Integer id = i;
                final Dialog dialog = new Dialog(MuonSach.this);
                dialog.setContentView(R.layout.menu_muon_sach);
                Button btn_sua = dialog.findViewById(R.id.btn_sua);
                Button btn_xoa = dialog.findViewById(R.id.btn_xoa);
                Button btn_dong = dialog.findViewById(R.id.btn_dong);
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("/Sach/" + id_sach + "/MuonSach").document(listMuonSach.get(id).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MuonSach.this, "Đã xóa mượn sách thành công", Toast.LENGTH_LONG).show();
                                        load_muon_sach();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MuonSach.this, "Lỗi khi xóa mượn sách: " + e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                        dialog.cancel();
                    }
                });
                btn_dong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent update_muon_sach = new Intent(MuonSach.this, UpdateMuonSach.class);
                        update_muon_sach.putExtra("id_muon_sach", listMuonSach.get(id).getId());
                        update_muon_sach.putExtra("id_sach", id_sach);
                        startActivity(update_muon_sach);
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    public void load_muon_sach() {
        listMuonSach.clear();
        db.collection("/Sach/" + id_sach + "/MuonSach")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                com.hieu.ThuVien.model.MuonSach muonSach = document.toObject(com.hieu.ThuVien.model.MuonSach.class);
                                muonSach.setId(document.getId());
                                listMuonSach.add(muonSach);
                            }
                            muonSachAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("TAG", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_muon_sach();
    }
}
