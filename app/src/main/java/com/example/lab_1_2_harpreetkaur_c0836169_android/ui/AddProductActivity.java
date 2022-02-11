package com.example.lab_1_2_harpreetkaur_c0836169_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lab_1_2_harpreetkaur_c0836169_android.R;
import com.example.lab_1_2_harpreetkaur_c0836169_android.databinding.ActivityAddProductBinding;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.DatabaseClient;
import com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb.Product;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddProductActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityAddProductBinding binding;
    private GoogleMap map;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Product");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (getIntent() != null) {
            product = (Product) getIntent().getSerializableExtra("DATA");
            if (product != null) {
                binding.edtName.setText(product.getName());
                binding.edtDesc.setText(product.getDesc());
                binding.edtPrice.setText(String.valueOf(product.getPrice()));
                binding.edtlat.setText(product.getLatitude());
                binding.edtLongitude.setText(product.getLongitude());
                binding.btnAdd.setVisibility(View.GONE);
                binding.btnUpdate.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("View/Update Product");
            }
        }

        binding.btnAdd.setOnClickListener(v -> {
            saveProduct();
        });
        binding.btnUpdate.setOnClickListener(v -> {
            if (product != null)
                updateProduct(product);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;
                binding.edtlat.setText(String.valueOf(latitude));
                binding.edtLongitude.setText(String.valueOf(longitude));
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });

        double latitude = 56.1304;
        double longitude = 106.3468;
        if (product != null) {
            latitude = Double.valueOf(product.getLatitude());
            longitude = Double.valueOf(product.getLongitude());
        }
        LatLng canada = new LatLng(latitude, longitude);
        binding.edtlat.setText(String.valueOf(latitude));
        binding.edtLongitude.setText(String.valueOf(longitude));
        map.addMarker(new MarkerOptions()
                .position(canada)
                .draggable(true)
                .title("Canada"));

        map.moveCamera(CameraUpdateFactory.newLatLng(canada));
    }

    void saveProduct() {

        final String name = binding.edtName.getText().toString().trim();
        final String desc = binding.edtDesc.getText().toString().trim();
        final String price = binding.edtPrice.getText().toString().trim();
        final String lat = binding.edtlat.getText().toString().trim();
        final String longitude = binding.edtLongitude.getText().toString().trim();

        if (name.isEmpty()) {
            binding.edtName.setError("Name required");
            binding.edtName.requestFocus();
            return;
        }

        if (desc.isEmpty()) {
            binding.edtDesc.setError("Description required");
            binding.edtPrice.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            binding.edtPrice.setError("Price by required");
            binding.edtPrice.requestFocus();
            return;
        }
        if (lat.isEmpty()) {
            binding.edtlat.setError("Latitude is required");
            binding.edtPrice.requestFocus();
            return;
        }
        if (longitude.isEmpty()) {
            binding.edtLongitude.setError("Longitude is required");
            binding.edtLongitude.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {


                Product product = new Product(name, desc, price, lat, longitude);


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .insert(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), ViewAllProductActivity.class));
                Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    void updateProduct(Product product) {

        final String name = binding.edtName.getText().toString().trim();
        final String desc = binding.edtDesc.getText().toString().trim();
        final String price = binding.edtPrice.getText().toString().trim();
        final String lat = binding.edtlat.getText().toString().trim();
        final String longitude = binding.edtLongitude.getText().toString().trim();

        if (name.isEmpty()) {
            binding.edtName.setError("Name required");
            binding.edtName.requestFocus();
            return;
        }

        if (desc.isEmpty()) {
            binding.edtDesc.setError("Description required");
            binding.edtPrice.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            binding.edtPrice.setError("Price by required");
            binding.edtPrice.requestFocus();
            return;
        }
        if (lat.isEmpty()) {
            binding.edtlat.setError("Latitude is required");
            binding.edtPrice.requestFocus();
            return;
        }
        if (longitude.isEmpty()) {
            binding.edtLongitude.setError("Longitude is required");
            binding.edtLongitude.requestFocus();
            return;
        }

        class UpdateProduct extends AsyncTask<Void, Void, Void> {
            private Product eproduct;

            UpdateProduct(Product product) {
                this.eproduct = product;
            }

            @Override
            protected Void doInBackground(Void... voids) {


                Product product = new Product(eproduct.getId(), name, desc, price, lat, longitude);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .update(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), ViewAllProductActivity.class));
                Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
            }
        }

        UpdateProduct up = new UpdateProduct(product);
        up.execute();
    }
}