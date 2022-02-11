package com.example.lab_1_2_harpreetkaur_c0836169_android.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.lab_1_2_harpreetkaur_c0836169_android.R;
import com.example.lab_1_2_harpreetkaur_c0836169_android.adapter.ProductAdapter;
import com.example.lab_1_2_harpreetkaur_c0836169_android.databinding.ActivityViewAllProductBinding;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.DatabaseClient;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.Product;

import java.util.ArrayList;
import java.util.List;

public class ViewAllProductActivity extends AppCompatActivity implements ProductAdapter.ProductOnClickListener {
    private ActivityViewAllProductBinding binding;
    private List<Product> productList = new ArrayList<>();
    private final ProductAdapter adapter = new ProductAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("All Products");

        binding.rvAllProducts.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });
        binding.searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    new Handler().postDelayed(() -> {
                        searchProducts(adapter, query);
                    }, 3000);
                } else {
                    getAllProducts(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    new Handler().postDelayed(() -> {
                        // productList.clear();
                        searchProducts(adapter, newText);
                    }, 3000);
                } else {
                    productList.clear();
                    getAllProducts(adapter);
                }
                return false;
            }
        });
        adapter.setOnClickListener(this);
        getAllProducts(adapter);
    }

    private void getAllProducts(ProductAdapter adapter) {
        GetProducts gt = new GetProducts(adapter);
        gt.execute();

    }

    private void searchProducts(ProductAdapter adapter, String query) {
        if (query.isEmpty())
            getAllProducts(adapter);
        else {
            SearchProducts gt = new SearchProducts(adapter, query);
            gt.execute();
        }


    }

    @Override
    public void onClick(Product product, int type) {
        switch (type) {
            case 1:
                startActivity(new Intent(this, AddProductActivity.class).putExtra("DATA", product));
                break;
            case 2:
                showDeleteDialog(product, adapter);
                break;
        }

    }

    private void showDeleteDialog(Product product, ProductAdapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProduct(product, adapter);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    private void deleteProduct(Product product, ProductAdapter adapter) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .delete(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                getAllProducts(adapter);
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    class SearchProducts extends AsyncTask<Void, Void, List<Product>> {
        private final ProductAdapter adapter;
        private final String query;

        public SearchProducts(ProductAdapter adapter, String q) {
            this.adapter = adapter;
            this.query = q;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {

            List<Product> productList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getAppDatabase()
                    .productDao()
                    .search(query);
            return productList;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            productList = new ArrayList<>(products);
            adapter.updateList(productList);

        }
    }

    class GetProducts extends AsyncTask<Void, Void, List<Product>> {
        private final ProductAdapter adapter;

        public GetProducts(ProductAdapter adapter) {
            this.adapter = adapter;
        }

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
            adapter.updateList(productList);

        }
    }
}