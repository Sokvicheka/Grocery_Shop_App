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
import ite.rupp.edu.test1.models.CartItem;
import java.util.List;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> items;
    private OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onDeleteItem(CartItem item);
        void onIncreaseQty(CartItem item);
        void onDecreaseQty(CartItem item);
    }

    public CartAdapter(List<CartItem> items, OnCartItemChangeListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.txtName.setText(item.title);
        holder.txtPrice.setText(String.format("$%.2f", item.price));
        holder.txtQty.setText(String.valueOf(item.quantity));

        Glide.with(holder.itemView.getContext())
                .load(item.imageUrl)
                .into(holder.imgProduct);

        holder.btnPlus.setOnClickListener(v -> listener.onIncreaseQty(item));
        holder.btnMinus.setOnClickListener(v -> {
            if (item.quantity > 1) {
                listener.onDecreaseQty(item);
            }
        });
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteItem(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnPlus, btnMinus, btnDelete;
        TextView txtName, txtPrice, txtQty;

        ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_cart_product);
            txtName = view.findViewById(R.id.txt_cart_name);
            txtPrice = view.findViewById(R.id.txt_cart_price);
            txtQty = view.findViewById(R.id.txt_cart_qty);
            btnPlus = view.findViewById(R.id.btn_plus);
            btnMinus = view.findViewById(R.id.btn_minus);
            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }
}