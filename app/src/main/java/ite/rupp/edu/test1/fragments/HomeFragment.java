package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.adapters.ProductAdapter;
import ite.rupp.edu.test1.models.Product;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize with sample data
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Fresh Apple", 1.50, ""));
        productList.add(new Product(2, "Organic Banana", 0.99, ""));
        productList.add(new Product(3, "Orange Juice", 1.25, ""));
        productList.add(new Product(4, "Sweet Grapes", 2.00, ""));
        productList.add(new Product(5, "Red Strawberry", 3.50, ""));
        productList.add(new Product(6, "Fresh Milk", 2.20, ""));

        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
