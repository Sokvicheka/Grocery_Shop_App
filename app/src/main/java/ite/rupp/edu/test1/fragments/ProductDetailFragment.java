package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.models.*;
import ite.rupp.edu.test1.network.*;
import java.util.concurrent.Executors;
import retrofit2.*;

public class ProductDetailFragment extends Fragment {

    private int productId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt("productId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgDetail = view.findViewById(R.id.img_detail);
        TextView txtName = view.findViewById(R.id.txt_detail_name);
        TextView txtPrice = view.findViewById(R.id.txt_detail_price);
        TextView txtDesc = view.findViewById(R.id.txt_description);
        TextView txtCat = view.findViewById(R.id.txt_category);
        Button btnAdd = view.findViewById(R.id.btn_add_to_cart);

        RetrofitClient.getApiService().getProductById(productId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Product p = response.body();
                            txtName.setText(p.getTitle());
                            txtPrice.setText(String.format("$%.2f", p.getPrice()));
                            txtDesc.setText(p.getDescription());
                            txtCat.setText(p.getCategory().toUpperCase());
                            Glide.with(requireContext()).load(p.getImageUrl()).into(imgDetail);

                            btnAdd.setOnClickListener(v -> {
                                CartItem item = new CartItem(
                                        p.getId(), p.getTitle(), p.getPrice(), p.getImageUrl(), 1
                                );
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    AppDatabase.getInstance(requireContext())
                                            .cartDao().insert(item);
                                });
                                Toast.makeText(getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Error loading product", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}