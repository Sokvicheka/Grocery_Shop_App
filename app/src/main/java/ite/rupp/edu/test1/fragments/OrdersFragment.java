package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ite.rupp.edu.test1.R;
import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Mocking a list of orders
        List<String> orders = new ArrayList<>();
        orders.add("Order #G-1024 - Processing");
        orders.add("Order #G-0988 - Delivered");
        orders.add("Order #G-0872 - Delivered");

        recyclerView.setAdapter(new RecyclerView.Adapter<OrderViewHolder>() {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
                return new OrderViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
                holder.text.setText(orders.get(position));
                holder.itemView.setOnClickListener(v -> {
                    // Clicking an order takes you to the specific tracking page
                    Navigation.findNavController(v).navigate(R.id.trackingFragment);
                });
            }

            @Override
            public int getItemCount() { return orders.size(); }
        });
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        OrderViewHolder(View v) {
            super(v);
            text = v.findViewById(android.R.id.text1);
        }
    }
}