import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.zyneticassignment.Product
import eu.tutorials.zyneticassignment.ProductApiService
import eu.tutorials.zyneticassignment.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ProductViewModel(private val apiService: ProductApiService) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchProducts() {
        viewModelScope.launch {
            _loading.value = true
            retryOnFailure {
                _products.value = apiService.getProducts().products
            }
            _loading.value = false
        }
    }

    fun fetchProductDetails(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            retryOnFailure {
                _selectedProduct.value = apiService.getProductDetails(productId)
            }
            _loading.value = false
        }
    }

    private suspend fun retryOnFailure(action: suspend () -> Unit) {
        var attempt = 0
        while (attempt < 3) {
            try {
                action()
                return
            } catch (e: IOException) {
                attempt++
                if (attempt == 3) {
                    _errorMessage.value = "Failed to fetch data after 3 attempts"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
                return
            }
        }
    }
}



