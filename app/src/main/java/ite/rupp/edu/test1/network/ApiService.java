package ite.rupp.edu.test1.network;

import ite.rupp.edu.test1.models.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Endpoint 1: Get all products
    @GET("products")
    Call<List<Product>> getAllProducts();

    // Endpoint 2: Get product by ID
    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    // Endpoint 3: Get products by category
    @GET("products/category/{category}")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);
}