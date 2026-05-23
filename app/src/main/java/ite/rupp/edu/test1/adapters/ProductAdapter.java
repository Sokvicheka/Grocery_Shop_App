package ite.rupp.edu.test1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import ite.rupp.edu.test1.R;
import ite.rupp.edu.test1.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private List<Product> productsFull;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = new ArrayList<>(products);
        this.productsFull = new ArrayList<>(products);
        this.listener = listener;
    }

    public void updateData(List<Product> newProducts) {
        this.productsFull = new ArrayList<>(newProducts);
        this.products = new ArrayList<>(newProducts);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        List<Product> filteredList = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            filteredList.addAll(productsFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Product item : productsFull) {
                if (item.getTitle().toLowerCase().contains(filterPattern) ||
                        item.getCategory().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }
        this.products = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = products.get(position);
        holder.txtName.setText(p.getTitle());
        holder.txtPrice.setText(String.format("$%.2f", p.getPrice()));

        Glide.with(holder.itemView.getContext())
                .load(p.getImageUrl())
                .into(holder.imgProduct);

        holder.itemView.setOnClickListener(v -> listener.onProductClick(p));
    }

    @Override
    public int getItemCount() { return products.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice;

        ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            txtName = view.findViewById(R.id.txt_name);
            txtPrice = view.findViewById(R.id.txt_price);
        }
    }
}