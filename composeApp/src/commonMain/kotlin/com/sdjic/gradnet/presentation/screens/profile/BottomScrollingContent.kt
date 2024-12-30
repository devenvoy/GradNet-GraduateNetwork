package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.about_me
import gradnet_graduatenetwork.composeapp.generated.resources.about_project
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomScrollingContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        SocialRow()
        Text(
            text = "About Me",
            style = typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = stringResource(Res.string.about_me),
            style = typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        InterestsSection()
//        MyPhotosSection()
        Text(
            text = "About Project",
            style = typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = stringResource(Res.string.about_project),
            style = typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        MoreInfoSection()
    }
}
