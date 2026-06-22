package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.adapters.ProductAdapter;
import ite.rupp.edu.test1.models.Product;
import ite.rupp.edu.test1.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_products);
        progressBar = view.findViewById(R.id.progress_home);
        
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize with empty list and set click listener
        adapter = new ProductAdapter(new ArrayList<>(), product -> {
            Bundle args = new Bundle();
            args.putInt("productId", product.getId());
            Navigation.findNavController(requireView()).navigate(R.id.action_home_to_detail, args);
        });
        recyclerView.setAdapter(adapter);

        setupCategoryChips(view);
        loadProducts();
    }

    private void setupCategoryChips(@NonNull View view) {
        Chip chipAll = view.findViewById(R.id.chip_all);
        Chip chipJewelery = view.findViewById(R.id.chip_jewelery);
        Chip chipElectronics = view.findViewById(R.id.chip_electronics);
        Chip chipClothing = view.findViewById(R.id.chip_clothing);

        chipAll.setOnClickListener(v -> loadProducts());
        chipJewelery.setOnClickListener(v -> openCategory("jewelery"));
        chipElectronics.setOnClickListener(v -> openCategory("electronics"));
        chipClothing.setOnClickListener(v -> openCategory("women's clothing"));
    }

    private void openCategory(String categoryName) {
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        Navigation.findNavController(requireView()).navigate(R.id.action_home_to_category, args);
    }

    private void loadProducts() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getApiService().getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                } else {
                    showSampleData();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                showSampleData();
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Showing Sample Data (Offline)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSampleData() {
        List<Product> sampleData = new ArrayList<>();
        sampleData.add(new Product(1, "Fresh Apple", 1.50, ""));
        sampleData.add(new Product(2, "Banana", 0.99, ""));
        sampleData.add(new Product(3, "Milk", 2.10, ""));
        sampleData.add(new Product(4, "Bread", 1.20, ""));
        adapter.updateData(sampleData);
    }
}
