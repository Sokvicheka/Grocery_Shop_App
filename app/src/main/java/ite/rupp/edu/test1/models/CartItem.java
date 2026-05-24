package ite.rupp.edu.test1.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey
    public int productId;
    public String title;
    public double price;
    public String imageUrl;
    public int quantity;

    public CartItem(int productId, String title, double price, String imageUrl, int quantity) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }
}