package com.hieu.ThuVien;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hieu.ThuVien.adapter.SachAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<com.hieu.ThuVien.model.Sach> listSach = new ArrayList<com.hieu.ThuVien.model.Sach>();
    SachAdapter sachAdapter;
    ListView lst_sach;
    Button btn_add_sach;
    String id_sach;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        lst_sach = findViewById(R.id.lst_sach);
        btn_add_sach = findViewById(R.id.btn_add_sach);
        sachAdapter = new SachAdapter(this, R.layout.item_sach, listSach);
        lst_sach.setAdapter(sachAdapter);

        btn_add_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSach.size() == 0) {
                    id_sach = "1";
                } else {
                    id_sach = String.valueOf(Integer.valueOf(listSach.get(listSach.size() - 1).getId()) + 1);
                }
                Intent add_sach = new Intent(getApplicationContext(), AddSach.class);
                add_sach.putExtra("id", id_sach);
                startActivity(add_sach);
            }
        });
        lst_sach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Integer id = i;
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.menu_sach);
                Button btn_sua = dialog.findViewById(R.id.btn_sua);
                Button btn_xoa = dialog.findViewById(R.id.btn_xoa);
                Button btn_dong = dialog.findViewById(R.id.btn_dong);
                Button btn_chomuon = dialog.findViewById(R.id.btn_chomuon);
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("/Sach").document(listSach.get(id).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Đã xóa sách thành công", Toast.LENGTH_LONG).show();
                                        load_sach();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Lỗi khi xóa sách: " + e.toString(), Toast.LENGTH_LONG).show();
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

                        Intent update_sach = new Intent(MainActivity.this, UpdateSach.class);
                        update_sach.putExtra("id", listSach.get(id).getId());
                        startActivity(update_sach);
                        dialog.cancel();
                    }
                });
                btn_chomuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent chomuon = new Intent(MainActivity.this, MuonSach.class);
                        chomuon.putExtra("id", listSach.get(id).getId());
                        startActivity(chomuon);
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    public void load_sach() {
        listSach.clear();
        db.collection("/Sach")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                com.hieu.ThuVien.model.Sach sach = document.toObject(com.hieu.ThuVien.model.Sach.class);
                                sach.setId(document.getId());
                                listSach.add(sach);
                            }
                            sachAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("TAG", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_sach();
    }
}
