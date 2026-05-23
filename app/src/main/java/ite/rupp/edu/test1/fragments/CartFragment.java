package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.*;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.models.CartItem;
import ite.rupp.edu.test1.network.AppDatabase;
import ite.rupp.edu.test1.network.CartDao;
import java.util.List;
import java.util.concurrent.Executors;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView recyclerView;
    private TextView txtTotal;
    private CartAdapter adapter;
    private CartDao cartDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_cart);
        txtTotal = view.findViewById(R.id.txt_total);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartDao = AppDatabase.getInstance(requireContext()).cartDao();

        // Observe LiveData from Room
        cartDao.getAllItems().observe(getViewLifecycleOwner(), items -> {
            adapter = new CartAdapter(items, this);
            recyclerView.setAdapter(adapter);

            double total = 0;
            for (CartItem item : items) total += item.price * item.quantity;
            txtTotal.setText(String.format("$%.2f", total));
        });

        view.findViewById(R.id.btn_checkout).setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_cart_to_tracking)
        );
    }

    @Override
    public void onDeleteItem(CartItem item) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.delete(item));
    }

    @Override
    public void onIncreaseQty(CartItem item) {
        item.quantity++;
        Executors.newSingleThreadExecutor().execute(() -> cartDao.update(item));
    }

    @Override
    public void onDecreaseQty(CartItem item) {
        if (item.quantity > 1) {
            item.quantity--;
            Executors.newSingleThreadExecutor().execute(() -> cartDao.update(item));
        }
    }
}