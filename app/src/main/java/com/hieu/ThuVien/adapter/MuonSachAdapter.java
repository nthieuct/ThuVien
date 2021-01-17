package com.hieu.ThuVien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hieu.ThuVien.R;
import com.hieu.ThuVien.model.MuonSach;

import java.util.List;

public class MuonSachAdapter extends ArrayAdapter<MuonSach> {
    private Context context;
    private int resoure;
    private List<MuonSach> listMuonSach;

    public MuonSachAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MuonSach> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.listMuonSach = objects;
    }

    public class ViewHolder {
        private TextView txt_ghi_chu;
        private TextView txt_han_tra;
        private TextView txt_ngay_muon;
        private TextView txt_ngay_tra;
        private TextView txt_nguoi_muon;
        private TextView txt_so_dien_thoai;
        private TextView id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MuonSachAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_muon_sach, parent, false);
            viewHolder = new MuonSachAdapter.ViewHolder();
            viewHolder.txt_ghi_chu = (TextView) convertView.findViewById(R.id.txt_ghi_chu);
            viewHolder.txt_han_tra = (TextView) convertView.findViewById(R.id.txt_han_tra);
            viewHolder.txt_ngay_muon = (TextView) convertView.findViewById(R.id.txt_ngay_muon);
            viewHolder.txt_ngay_tra = (TextView) convertView.findViewById(R.id.txt_ngay_tra);
            viewHolder.txt_nguoi_muon = (TextView) convertView.findViewById(R.id.txt_nguoi_muon);
            viewHolder.txt_so_dien_thoai = (TextView) convertView.findViewById(R.id.txt_so_dien_thoai);
            viewHolder.id = (TextView) convertView.findViewById(R.id.txt_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MuonSachAdapter.ViewHolder) convertView.getTag();
        }

        MuonSach muonSach = listMuonSach.get(position);
        viewHolder.txt_ghi_chu.setText("Ghi chú: " + muonSach.getGhiChu());
        viewHolder.txt_han_tra.setText("Hạn trả: " + muonSach.getHanTra());
        viewHolder.txt_ngay_muon.setText("Ngày mượn: " + muonSach.getNgayMuon());
        viewHolder.txt_ngay_tra.setText("Ngày trả: " + muonSach.getNgayTra());
        viewHolder.txt_nguoi_muon.setText("Người mượn: " + muonSach.getNguoiMuon());
        viewHolder.txt_so_dien_thoai.setText("Số điện thoại: " + muonSach.getSoDienThoai());
        viewHolder.id.setText(String.valueOf(position + 1));
        return convertView;
    }
}
