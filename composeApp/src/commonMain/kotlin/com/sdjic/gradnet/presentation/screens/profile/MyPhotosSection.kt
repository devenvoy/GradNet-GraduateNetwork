package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import com.sdjic.shared.Resource as Res

@Composable
fun MyPhotosSection() {
    Text(
        text = "My Photography",
        style = typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    val imageModifier = Modifier
        .padding(vertical = 8.dp, horizontal = 4.dp)
        .size(120.dp)
        .clip(RoundedCornerShape(8.dp))

    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(Res.drawable.icAlumni),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(Res.drawable.icOrganization),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(Res.drawable.icOrganization),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(Res.drawable.icFaculty),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(Res.drawable.icDegree),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(Res.drawable.icOrganization),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}


