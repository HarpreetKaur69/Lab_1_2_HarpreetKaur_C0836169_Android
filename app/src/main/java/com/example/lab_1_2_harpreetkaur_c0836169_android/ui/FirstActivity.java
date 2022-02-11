package com.example.lab_1_2_harpreetkaur_c0836169_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab_1_2_harpreetkaur_c0836169_android.R;
import com.example.lab_1_2_harpreetkaur_c0836169_android.databinding.ActivityFirstBinding;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.DatabaseClient;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.Product;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    private ActivityFirstBinding binding;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAllProducts();
        setFirstProduct();

        binding.btnAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });
        binding.btnViewProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewAllProductActivity.class));
        });
     //   setDummyList();

    }

    private void setDummyList() {
        for (int i = 0; i < 10; i++) {
            saveProduct("Product" + i, "Description of product " + i, 10 + i, 56.1304 + "", 106.3468 + i + "");
        }
    }

    private void setFirstProduct() {

        if (productList != null && productList.size() > 0) {
            binding.tvTotalProducts.setText("Total Product= " + productList.size());
            binding.tvProductId.setText("#" + productList.get(0).getId());
            binding.tvProductName.setText(productList.get(0).getName());
            binding.tvProductDecription.setText(productList.get(0).getDesc());
            binding.tvProductPrice.setText("$ " + productList.get(0).getPrice());
        } else {
           // setDummyList();
        }
    }

    private void getAllProducts() {
        GetProducts gt = new GetProducts();
        gt.execute();

    }

    void saveProduct(String name, String desc, int price, String lat, String longitude) {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {


                Product product = new Product(name, desc, price + "", lat, longitude);


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .insert(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //finish();
//                startActivity(new Intent(getApplicationContext(), ViewAllProductActivity.class));
//                Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }


    class GetProducts extends AsyncTask<Void, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(Void... voids) {
            List<Product> productList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getAppDatabase()
                    .productDao()
                    .getAll();
            return productList;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            productList = products;
            if(productList.isEmpty() && productList.size()==0)
            {
                setDummyList();
            }else{
                setFirstProduct();
            }

//            TasksAdapter adapter = new TasksAdapter(FirstActivity.this, tasks);
//            recyclerView.setAdapter(adapter);
        }
    }

}