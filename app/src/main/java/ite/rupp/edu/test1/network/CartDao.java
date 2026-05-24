package ite.rupp.edu.test1.network;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import ite.rupp.edu.test1.models.CartItem;
import java.util.List;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem item);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItem>> getAllItems();

    @Query("DELETE FROM cart_items")
    void clearAll();
}