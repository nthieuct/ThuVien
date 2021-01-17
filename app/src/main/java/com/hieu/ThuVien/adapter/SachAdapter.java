package com.hieu.ThuVien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hieu.ThuVien.R;
import com.hieu.ThuVien.model.Sach;

import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private int resoure;
    private List<Sach> listSach;

    public SachAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Sach> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.listSach = objects;
    }

    public class ViewHolder {
        private TextView txt_ma_sach;
        private TextView txt_so_luong_ban_sao;
        private TextView txt_tac_gia;
        private TextView txt_ten_sach;
        private ImageView img_hinh;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txt_ma_sach = (TextView) convertView.findViewById(R.id.txt_ma_sach);
            viewHolder.txt_so_luong_ban_sao = (TextView) convertView.findViewById(R.id.txt_so_luong_ban_sao);
            viewHolder.txt_tac_gia = (TextView) convertView.findViewById(R.id.txt_tac_gia);
            viewHolder.txt_ten_sach = (TextView) convertView.findViewById(R.id.txt_ten_sach);
            viewHolder.img_hinh = (ImageView) convertView.findViewById(R.id.img_hinh);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sach sach = listSach.get(position);
        viewHolder.txt_ma_sach.setText("Mã sách: " + sach.getMaSach());
        viewHolder.txt_so_luong_ban_sao.setText("Số lượng bản sao: " + sach.getSoLuongBanSao().toString());
        viewHolder.txt_tac_gia.setText("Tác giả: " + sach.getTacGia());
        viewHolder.txt_ten_sach.setText("Tên sách: " + sach.getTenSach());
        viewHolder.img_hinh.setImageDrawable(context.getResources().getDrawable(R.drawable.book01));
        return convertView;
    }
}
