package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.adapters.ProductAdapter;
import ite.rupp.edu.test1.models.Product;
import ite.rupp.edu.test1.network.ApiService;
import ite.rupp.edu.test1.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private EditText edtSearch;
    private ProgressBar progressBar;
    private ChipGroup chipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Initialize Views
        recyclerView = view.findViewById(R.id.recycler_products);
        edtSearch = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.progress_home);
        chipGroup = view.findViewById(R.id.chip_group_category);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // 2. Setup adapter immediately (even if empty) to prevent "No adapter attached" error
        if (adapter == null) {
            adapter = new ProductAdapter(new ArrayList<>(), product -> {
                Bundle args = new Bundle();
                args.putInt("productId", product.getId());
                Navigation.findNavController(requireView()).navigate(R.id.action_home_to_detail, args);
            });
        }
        recyclerView.setAdapter(adapter);

        // 3. Setup Logic
        setupCategoryChips(view);
        setupSearch();

        // 4. Load Data
        loadProducts(null);
    }

    private void setupSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupCategoryChips(View view) {
        // Use individual click listeners to avoid navigation loops
        view.findViewById(R.id.chip_all).setOnClickListener(v -> {
            chipGroup.check(R.id.chip_all);
            loadProducts(null);
        });

        view.findViewById(R.id.chip_electronics).setOnClickListener(v -> navigateToCategory("electronics"));
        view.findViewById(R.id.chip_clothing).setOnClickListener(v -> navigateToCategory("men's clothing"));
        view.findViewById(R.id.chip_jewelery).setOnClickListener(v -> navigateToCategory("jewelery"));
    }

    private void navigateToCategory(String categoryName) {
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        Navigation.findNavController(requireView()).navigate(R.id.action_home_to_category, args);
    }

    private void loadProducts(String category) {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        ApiService api = RetrofitClient.getApiService();
        Call<List<Product>> call = (category == null) ? api.getAllProducts() : api.getProductsByCategory(category);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                    adapter.filter(edtSearch.getText().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}