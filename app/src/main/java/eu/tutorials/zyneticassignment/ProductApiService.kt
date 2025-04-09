package eu.tutorials.zyneticassignment

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Product

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductResponse

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductResponse
}
