import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.Back
import top.yukonga.miuix.kmp.icon.icons.useful.More

@Composable
fun TopAppBarDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xfff77062), Color(0xfffe5196)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val scrollBehavior = MiuixScrollBehavior()
                Scaffold(
                    modifier = Modifier.weight(0.5f),
                    topBar = {
                        TopAppBar(
                            title = "Title",
                            largeTitle = "Large Title", // If not specified, title value will be used
                            scrollBehavior = scrollBehavior,
                            navigationIcon = {
                                IconButton(onClick = { /* Handle click event */ }) {
                                    Icon(MiuixIcons.Useful.Back, contentDescription = "Back")
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* Handle click event */ }) {
                                    Icon(MiuixIcons.Useful.More, contentDescription = "More")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(top = paddingValues.calculateTopPadding())
                    ) {
                        items(100) {
                            SuperArrow(
                                title = "Something"
                            )
                        }
                    }
                }
                Scaffold(
                    modifier = Modifier.weight(0.5f),
                    topBar = {
                        SmallTopAppBar(
                            title = "Title",
                            navigationIcon = {
                                IconButton(onClick = { /* Handle click event */ }) {
                                    Icon(MiuixIcons.Useful.Back, contentDescription = "Back")
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* Handle click event */ }) {
                                    Icon(MiuixIcons.Useful.More, contentDescription = "More")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        contentPadding = PaddingValues(top = paddingValues.calculateTopPadding())
                    ) {
                        items(100) {
                            SuperArrow(
                                title = "Something"
                            )
                        }
                    }
                }
            }
        }
    }
}
