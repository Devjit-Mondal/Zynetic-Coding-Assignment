package eu.tutorials.zyneticassignment

import ProductViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(viewModel: ProductViewModel, navController: NavHostController) {
    val products by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Product Catalog") }) }
    ) {
        if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown error", color = Color.Red)
            }
        } else {
            LazyColumn {
                items(products) { product ->
                    ProductCard(
                        title = product.title,
                        description = product.description,
                        imageUrl = product.images.firstOrNull() ?: "",
                        onClick = { navController.navigate(Screen.ProductDetails.createRoute(product.id)) }
                    )
                }
            }
        }
    }
}





@Composable
fun ProductCard(
    title: String,
    description: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}


@Composable

fun ProductDetailsScreen(productId: Int, viewModel: ProductViewModel) {
    viewModel.fetchProductDetails(productId)

    val product by viewModel.selectedProduct.collectAsState()

    product?.let { prod ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = prod.title, style = MaterialTheme.typography.h4)
            Text(text = "Price: $${prod.price}", style = MaterialTheme.typography.h6)
            Text(text = prod.description, style = MaterialTheme.typography.body1)
        }
    } ?: run {
        CircularProgressIndicator()
    }
}



