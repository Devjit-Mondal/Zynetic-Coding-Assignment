import eu.tutorials.zyneticassignment.Product
import eu.tutorials.zyneticassignment.ProductApiService
import eu.tutorials.zyneticassignment.ProductResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class ProductViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProductViewModel
    private lateinit var mockApiService: ProductApiService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockApiService = mock(ProductApiService::class.java)

        viewModel = ProductViewModel(mockApiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts should update products flow`() = runTest {
        val mockProducts = listOf(
            Product(1, "iPhone 9", "An apple mobile", 549.0, 4.69, "smartphones", listOf()),
            Product(2, "Samsung Galaxy", "A Samsung phone", 499.0, 4.5, "smartphones", listOf())
        )
        `when`(mockApiService.getProducts()).thenReturn(ProductResponse(mockProducts, 2, 0, 30))

        viewModel.fetchProducts()
        advanceUntilIdle()

        val products = viewModel.products.first()
        assertEquals(2, products.size)
        assertEquals("iPhone 9", products[0].title)
    }

    @Test
    fun `fetchProductDetails should update selectedProduct flow`() = runTest {
        val mockProduct = Product(1, "iPhone 9", "An apple mobile", 549.0, 4.69, "smartphones", listOf())
        `when`(mockApiService.getProductDetails(1)).thenReturn(mockProduct)

        viewModel.fetchProductDetails(1)
        advanceUntilIdle()

        val product = viewModel.selectedProduct.first()
        assertEquals("iPhone 9", product?.title)
    }
}
