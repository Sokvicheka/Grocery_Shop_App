package ite.rupp.edu.test1.network;

import java.util.List;
import ite.rupp.edu.test1.models.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products")
    Call<List<Product>> getProducts();
}
