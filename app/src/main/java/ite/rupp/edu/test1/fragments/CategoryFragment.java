package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.adapters.ProductAdapter;
import ite.rupp.edu.test1.models.Product;
import ite.rupp.edu.test1.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private String categoryName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("categoryName");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtTitle = view.findViewById(R.id.txt_category_title);
        if (categoryName != null) {
            txtTitle.setText(categoryName.toUpperCase());
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_category_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (categoryName != null) {
            RetrofitClient.getApiService().getProductsByCategory(categoryName).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ProductAdapter adapter = new ProductAdapter(response.body(), product -> {
                            Bundle args = new Bundle();
                            args.putInt("productId", product.getId());
                            Navigation.findNavController(view).navigate(R.id.action_category_to_detail, args);
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error loading category", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}