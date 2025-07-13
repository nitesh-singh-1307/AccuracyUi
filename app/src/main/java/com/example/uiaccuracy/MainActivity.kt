package com.example.uiaccuracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.uiaccuracy.ui.theme.UIAccuracyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIAccuracyTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                SocialFeedScreen()
//                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

@Composable
fun SocialFeedScreen() {
    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = { AppBottomBar(selectedIndex = 0, onItemSelected = {}) },
        containerColor = Color(0xFFF0F2F5) // A light grey background
    ) { paddingValues ->
        // The main scrollable content
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item { PassionAndPursuitsSection() }
            item { LiveAndTrendingSection() }
            item { PlannedActivityCard() }
            item { LivePostCard() }
            // Add a spacer at the bottom so content doesn't hide behind the FAB
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_top_map),
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(23.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Prestige Lakeside Habitat.....",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    color = Color(0xFF4B4544),
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: Handle Search */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_searching),
                    contentDescription = "Search",
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = { /* TODO: Handle Notifications */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Search",
                    modifier = Modifier.size(18.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF0F2F5)
        )
    )
}

@Composable
fun AppBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val navItems = listOf(
        "All" to R.drawable.ic_bottom_home,
        "Basketball" to R.drawable.ic_bottom_map,
        "BMX" to R.drawable.ic_bottom_message,
        "Debate" to R.drawable.ic_bottom_book
    )

    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp), // BOTTOM PADDING ADDED
            shape = RoundedCornerShape(50),
            color = Color.White,
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                navItems.forEachIndexed { index, icon ->
                    if (index == 2) {
                        Spacer(Modifier.width(48.dp)) // Space for FAB
                    }
                    IconButton(onClick = { onItemSelected(index) }) {
                        Icon(
                            painter = painterResource(id = icon.second),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (selectedIndex == index) Color(0xFF7E22CE) else Color.Gray
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { /* Add */ },
            containerColor = Color(0xFF8E2DE2), // Solid purple color
            shape = CircleShape,
            modifier = Modifier
                .offset(y = (-28).dp)
                .size(56.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun PassionAndPursuitsSection() {
    val categories = listOf(
        "All" to R.drawable.all,
        "Basketball" to R.drawable.basketball,
        "BMX" to R.drawable.bmx,
        "Debate" to R.drawable.ic_chat,
        "Critical..." to R.drawable.psychology,
        "Community" to R.drawable.ic_eco,
        "Blood Dona..." to R.drawable.ic_favorite
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionTitleWithDivider(title = "Passion & Pursuits")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(categories) { (label, icon) ->
                PassionCategoryItem(label, icon, isSelected = label == "All")
            }
        }
    }
}

@Composable
fun SectionTitleWithDivider(title: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF4B4544),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            modifier = Modifier.weight(1f), // This makes the divider fill the remaining space
            color = Color.LightGray.copy(alpha = 0.7f),
            thickness = 1.dp
        )
    }
}

@Composable
fun PassionCategoryItem(label: String, iconRes: Int, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        val selectedIconBrush = Brush.verticalGradient(
            colors = listOf(Color(0xFFC655F6), Color(0xFFE91E63))
        )
        val selectedUnderlineColor = Color(0xFF007BFF) // Blue color for underline

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White,
                    CircleShape
                )
                .border(1.dp, Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier
                    .size(30.dp)
                    .then(
                        if (isSelected) {
                            Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(selectedIconBrush, blendMode = BlendMode.SrcAtop)
                                    }
                                }
                        } else {
                            Modifier
                        }
                    ),
                tint = if (isSelected) Color.Unspecified else Color.Gray
            )

        }
        Spacer(modifier = Modifier.height(4.dp))

        // --- KEY CHANGES ARE HERE ---
        Text(
            text = label,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isSelected) Color.Black else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )

        // 3. Underline color is blue when selected, otherwise transparent
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .width(25.dp)
                .height(3.dp)
                .background(
                    if (isSelected) selectedUnderlineColor else Color.Transparent,
                    RoundedCornerShape(2.dp)
                )
        )
    }
}

@Composable
fun LiveAndTrendingSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionTitleWithDivider(title = "Live & Trending Near You")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // 1. More pronounced rounding
                .background(Color.White)
                // 2. Add the border to the white background
                .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp), // Padding inside the white container
            // 3. Use SpaceAround to create space between the items
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Call the new, more accurate filter item composable
            ActivityFilterItem(text = "All", isSelected = true)
            ActivityFilterItem(text = "Planned Activity")
            ActivityFilterItem(text = "Live Activity", hasLiveDot = true)
        }
    }
}


@Composable
fun ActivityFilterItem(text: String, isSelected: Boolean = false, hasLiveDot: Boolean = false) {
    // Define colors for clarity
    val selectedBackgroundColor = Color(0xFF7B1FA2) // A rich purple
    val selectedContentColor = Color.White
    val unselectedContentColor = Color(0xFF212121)

    // Determine background based on selection
    val backgroundModifier = if (isSelected) {
        Modifier.background(selectedBackgroundColor, RoundedCornerShape(12.dp))
    } else {
        Modifier // No background if not selected
    }

    // This is the individual clickable item
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp)) // Ensures the ripple effect is also rounded
            .then(backgroundModifier)
            // Add padding to give the text space and create the pill shape
            .padding(horizontal = 20.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (hasLiveDot) {
                // 4. Create the "glowing" green dot effect by nesting two boxes
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color(0xFFC8E6C9), CircleShape), // Outer light green
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(Color(0xFF4CAF50), CircleShape) // Inner dark green
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = if (isSelected) selectedContentColor else unselectedContentColor,
                fontSize = 12.sp,
                softWrap = false,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        }
    }
}

@Composable
fun PlannedActivityCard() {
    Column(
        modifier =
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
    ) {
        // Top Row: Profile Pic, Name, Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                    contentDescription = "Jemmy Ray",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        // 1. Add the border with its color, width, and shape
                        .border(
                            width = 2.dp,
                            color = Color(0xFF673AB7), // Vibrant purple color
                            shape = CircleShape
                        )
                        // 2. Add padding to create a small gap between the border and the image
                        .padding(3.dp)
                        // 3. Finally, clip the image itself into a circle
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Green, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Jemmy Ray",
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Text(
                        "2 hour ago", fontSize = 12.sp,
                        color = Color(0xCC6C7A9C)
                    )
                }
            }
            // 1. Define the horizontal gradient brush
            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF8E44AD), Color(0xFFE91E63))
            )

            // 2. Call the GradientButton composable
            GradientButton(
                onClick = { /* TODO: Handle Interest click */ },
                brush = gradientBrush,
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 8.dp)
                // The content inside the button is defined here
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_hand),
                    contentDescription = "Interest",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Interest\nRequested",
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp
                    )
//                        Text(
//                            text = "Requested",
//                            color = Color.White,
//                            fontSize = 12.sp,
//                        )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Info Rows
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "Interest",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Chilling at Summer House Café Lorem Ipsum has been the industry's standard..",
                fontSize = 12.sp,
                color = Color(0xFF4B4544),
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendor),
                contentDescription = "Interest",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Saturday, October 21 at 7:00 PM",
                color = Color(0xFF4B4544),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            VerticalDivider(height = 16.dp)
            Spacer(modifier = Modifier.width(8.dp))
//                Icon(Icons.Default.People, contentDescription = "Joined", tint = MaterialTheme.colorScheme.primary)
            Image(
                painter = painterResource(id = R.drawable.ic_pepole),
                contentDescription = "Interest",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
//                Text("16",
//                    fontWeight = FontWeight.SemiBold,
//                    color = MaterialTheme.colorScheme.primary)

            // 1. Define the brush for the text
            val textGradientBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF8E44AD), Color(0xFFF44336))
            )
            // 2. Use the GradientText composable
            GradientText(
                text = "16",
                brush = textGradientBrush,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Joined", fontSize = 12.sp,
                color = Color(0xFF4B4544)
            )
        }
    }
}

@Composable
fun GradientButton(
    onClick: () -> Unit,
    brush: Brush,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50.dp), // Default to a pill shape
    contentPadding: PaddingValues,
    content: @Composable RowScope.() -> Unit
) {
    // We use a Row as the base container for the button.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .clip(shape)             // 1. Clip the content to the desired shape
            .background(brush)       // 2. Apply the gradient background
            .clickable(onClick = onClick), // 3. Make it clickable
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content // 4. Pass in the content (Text, Icon, etc.)
    )
}

@Composable
fun VerticalDivider(height: Dp, color: Color = Color.LightGray, thickness: Dp = 1.dp) {
    Box(
        modifier = Modifier
            .height(height)
            .width(thickness)
            .background(color)
    )
}

@Composable
fun GradientText(
    text: String,
    brush: Brush,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        // The key is to copy the existing style and override the brush
        style = style.copy(brush = brush)
    )
}

@Composable
fun LivePostCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            AsyncImage(
                model = "https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                contentDescription = "Ferris Wheel",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = "https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                    contentDescription = "Ferris Wheel",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 15.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                // Top user info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                        contentDescription = "Emma Lily",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color(0xFF673AB7), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            "Emma Lily",
                            color = Color.White,
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "9 min ago",
                            color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes content to bottom

                // Bottom Content
                // The "Mera Joseph" overlay seems like a tag, let's replicate it
                Surface(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://images.pexels.com/photos/91227/pexels-photo-91227.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                            contentDescription = "Mera Joseph",
                            modifier = Modifier
                                .padding(1.dp)
                                .size(30.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                // This pushes the name to the start and the date to the end
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Mera Joseph",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White, fontSize = 13.sp
                                )

                                Text(
                                    "Jan 14 2024 | 10:30",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(1.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_post_location),
                                    contentDescription = "Likes",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Chilling at Summer House Café Lorem....",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_post_close),
                            contentDescription = "Close tag",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                // Post text
                Text(
                    "Today, I experienced the most amazing music and The air feels amazing...",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    "more",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Engagement actions
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_post_fevorite),
                        contentDescription = "Likes",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "7",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp, end = 16.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_message),
                        contentDescription = "Comments",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "2",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp, end = 16.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Share",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "12", color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_post_more),
                        contentDescription = "More options",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SocialFeedScreenPreview() {
    // Wrap with your app's theme for consistent styling
    // If you don't have one, MaterialTheme is a good default.
    UIAccuracyTheme {
        SocialFeedScreen()
    }
}