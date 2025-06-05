import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.yukonga.miuix.kmp.basic.Text

@Composable
fun Demo(demoId: String? = null) {
    if (demoId == null) {
        DemoSelection()
    } else {
        availableComponents.first { it.id == demoId }.demo()
    }
}

private data class AvailableComponent(val name: String, val id: String, val demo: @Composable () -> Unit)

private val availableComponents = listOf(
    AvailableComponent("Button", "button") { ButtonDemo() },
)

@Composable
private fun DemoSelection() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0xFFFAFAFAFA)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .systemBarsPadding()
                        .padding(16.dp)
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    availableComponents.forEach { demo ->
                        Text(demo.name, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        availableComponents.forEach { component ->
            composable(component.id) {
                Column {
                    component.demo()
                }
            }
        }
    }
}
