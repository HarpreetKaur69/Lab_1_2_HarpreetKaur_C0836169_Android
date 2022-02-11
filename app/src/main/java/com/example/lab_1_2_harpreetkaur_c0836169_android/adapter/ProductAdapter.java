package com.example.lab_1_2_harpreetkaur_c0836169_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_1_2_harpreetkaur_c0836169_android.databinding.ItemProductListBinding;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> productList = new ArrayList<>();
    ProductOnClickListener clickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProductListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        holder.binding.btnView.setOnClickListener(v -> {
            clickListener.onClick(product, 1);
        });
        holder.binding.btnDelete.setOnClickListener(v -> {
            clickListener.onClick(product, 2);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductListBinding binding;

        public ViewHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

        void bind(Product product) {
            binding.tvProductId.setText("#" + product.getId());
            binding.tvProductName.setText(product.getName());
            binding.tvProductDecription.setText(product.getDesc());
            binding.tvProductPrice.setText("$ " + product.getPrice());

        }
    }

   public interface ProductOnClickListener {
        void onClick(Product product, int type);
    }

    public void setOnClickListener(ProductOnClickListener listener) {
        clickListener = listener;
    }

    public void updateList(List<Product> list) {
        productList = new ArrayList<>(list);
        notifyDataSetChanged();
    }
}
