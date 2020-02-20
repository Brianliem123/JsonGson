package com.fa.merchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fa.merchant.Deskripsi;
import com.fa.merchant.R;
import com.fa.merchant.modelclass.Product;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();


    public CustomAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_product,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvNama.setText(products.get(position).getProductName());
        holder.tvMerchant.setText(products.get(position).getMerchant().getMerchantName());

        String baseUrl = "http://192.168.6.221:81/storage/";
        String url = baseUrl+products.get(position).getProductImg();

        Glide.with(context).load(url).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Deskripsi.class);
                intent.putExtra("data",products.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void adddata(ArrayList<Product> products) {
        this.products = products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvMerchant;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_product);
            img = itemView.findViewById(R.id.image_product);
            tvMerchant = itemView.findViewById(R.id.tv_merchant);
        }
    }
}
