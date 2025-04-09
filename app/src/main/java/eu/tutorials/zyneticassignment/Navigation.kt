package eu.tutorials.zyneticassignment
import ProductViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: Int): String = "product_details/$productId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val apiService = RetrofitInstance.apiService

    NavHost(navController, startDestination = Screen.ProductList.route) {
        composable(Screen.ProductList.route) {
            val viewModel = ProductViewModel(apiService)
            ProductListScreen(viewModel, navController)
        }

        composable(Screen.ProductDetails.route) { backStackEntry ->
            val productId =
                backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                val viewModel = ProductViewModel(apiService)
                ProductDetailsScreen(productId, viewModel)
            }
        }
    }
}
