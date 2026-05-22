package ite.rupp.edu.test1.network;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import ite.rupp.edu.test1.models.CartItem;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart_items")
    List<CartItem> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem cartItem);

    @Query("DELETE FROM cart_items WHERE id = :id")
    void delete(int id);
}
