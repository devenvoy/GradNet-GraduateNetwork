package com.sdjic.gradnet.presentation.screens.accountSetup.profession

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.sdjic.gradnet.presentation.composables.button.PlusIconButton
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.button.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputArea
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.theme.errorColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Globe
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.empty_trash
import gradnet_graduatenetwork.composeapp.generated.resources.github
import gradnet_graduatenetwork.composeapp.generated.resources.linkedin
import gradnet_graduatenetwork.composeapp.generated.resources.twitter_bird
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfessionSetUpScreen(
    isVerified: Boolean,
    professionState: ProfessionState,
    userRole: UserRole,
    onAction: (ProfessionScreenAction) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    if (professionState.showExperienceBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.windowInsetsPadding(insets = WindowInsets.systemBars),
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
            sheetState = sheetState,
            dragHandle = null,
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                onAction(
                    ProfessionScreenAction.OnExperienceBottomSheetStateChange(false)
                )
                softwareKeyboardController?.hide()
            },
        ) {
            AddEditExperienceModal(
                onSave = { onAction(ProfessionScreenAction.OnAddExperience(it)) },
                onCancel = {
                    onAction(
                        ProfessionScreenAction.OnExperienceBottomSheetStateChange(false)
                    )
                }
            )
        }
    }

    if (professionState.showAddOtherUrlDialog) {
        BasicAlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = {}
        ) {
            Surface(
                modifier = Modifier.padding(10.sdp),
                shape = RoundedCornerShape(12.sdp)
            ) {
                var selectedText by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.padding(10.sdp),
                ) {
                    Title(text = "Add Url", size = 16.ssp, modifier = Modifier.padding(8.sdp))
                    CustomInputField(
                        textFieldValue = selectedText,
                        onValueChange = { selectedText = it },
                        placeholder = { SText(text = "enter url") },
                        trailingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Globe,
                                contentDescription = "web"
                            )
                        }
                    )
                    Row(modifier = Modifier.padding(top = 10.sdp).align(Alignment.End)) {
                        TextButton(onClick = {
                            softwareKeyboardController?.hide()
                            onAction(
                                ProfessionScreenAction.OnAddOtherUrlDialogStateChange(false)
                            )
                        }) { SText("Cancel", fontSize = 12.ssp) }
                        TextButton(onClick = {
                            softwareKeyboardController?.hide()
                            onAction(ProfessionScreenAction.OnAddOtherUrl(selectedText))
                            selectedText = ""
                            onAction(
                                ProfessionScreenAction.OnAddOtherUrlDialogStateChange(false)
                            )
                        }) {
                            SText(
                                "Ok",
                                fontSize = 12.ssp,
                                textColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(8.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {

        Row(
            modifier = Modifier.padding().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title(text = "Social Url", size = 16.ssp)
        }

        CustomInputField(fieldTitle = "Github",
            textFieldValue = professionState.githubUrl,
            onValueChange = { onAction(ProfessionScreenAction.OnUpdateGithubUrl(it)) },
            placeholder = { SText(text = "github profile url") },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(Res.drawable.github),
                    contentDescription = "github"
                )
            })

        CustomInputField(fieldTitle = "LinkedIn",
            textFieldValue = professionState.linkedinUrl,
            onValueChange = { onAction(ProfessionScreenAction.OnUpdateLinkedinUrl(it)) },
            placeholder = { SText(text = "linkedin profile url") },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(Res.drawable.linkedin),
                    contentDescription = "LinkedIn"
                )
            })

        CustomInputField(fieldTitle = "Twitter ( X )",
            textFieldValue = professionState.twitterUrl,
            onValueChange = { onAction(ProfessionScreenAction.OnUpdateTwitterUrl(it)) },
            placeholder = { SText(text = "twitter profile url") },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(Res.drawable.twitter_bird),
                    contentDescription = "Twitter"
                )
            })

        Row(
            modifier = Modifier.padding().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title(text = "Other Url", size = 16.ssp)
            PlusIconButton {
                onAction(ProfessionScreenAction.OnAddOtherUrlDialogStateChange(true))
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            professionState.otherUrls.fastForEachIndexed { index, item ->
                Row(
                    modifier = Modifier.padding(horizontal = 10.sdp, vertical = 2.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SText(item, fontSize = 12.ssp, modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.size(16.sdp).clickable(onClick = {
                            onAction(ProfessionScreenAction.OnRemoveOtherUrl(item))
                        }),
                        painter = painterResource(Res.drawable.empty_trash),
                        tint = errorColor,
                        contentDescription = "remove",
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title(text = "Experience", size = 16.ssp)
            PlusIconButton {
                onAction(
                    ProfessionScreenAction.OnExperienceBottomSheetStateChange(true)
                )
            }
        }

        if (professionState.experienceList.isNotEmpty()) {
            Card(
                modifier = Modifier.padding(bottom = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.sdp)
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    professionState.experienceList.fastForEachIndexed { index, item ->
                        Box(
                            modifier = Modifier.padding(horizontal = 8.sdp),
                        ) {

                            ExperienceItem(experience = item)

                            Icon(
                                modifier = Modifier.size(16.sdp).align(Alignment.TopEnd)
                                    .clickable(onClick = {
                                        onAction(ProfessionScreenAction.OnRemoveExperience(index))
                                    }),
                                painter = painterResource(Res.drawable.empty_trash),
                                tint = errorColor,
                                contentDescription = "cross",
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExperienceModal(
    experienceModel: ExperienceModel? = null,
    onSave: (ExperienceModel) -> Unit,
    onCancel: () -> Unit
) {

    var expModel by remember { mutableStateOf(experienceModel ?: ExperienceModel()) }
    val startDatePicker = rememberUseCaseState(visible = false)
    val endDatePicker = rememberUseCaseState(visible = false)

    CalendarDialog(
        state = startDatePicker,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { newDate ->
            expModel = expModel.copy(startDate = newDate.toString())
        },
        header = Header.Default("Select Start Date", null)
    )

    CalendarDialog(
        state = endDatePicker,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { newDate ->
            expModel = expModel.copy(endDate = newDate.toString())
        },
        header = Header.Default("Select End Date", null)
    )

    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(12.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {

        Box(
            modifier = Modifier.width(50.sdp).height(3.sdp).clip(RoundedCornerShape(50))
                .background(Color(0xFFBBC0C4)).align(Alignment.CenterHorizontally)
        )

        Title(
            text = "${if (experienceModel != null) "Edit" else "Add"} Experience Detail",
            size = 16.ssp,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.sdp)
        )

        CustomInputField(fieldTitle = "Job Title",
            textFieldValue = expModel.title,
            onValueChange = { expModel = expModel.copy(title = it) },
            placeholder = { SText(text = "Enter job title") },
            supportingText = { SText(text = "Required", textColor = Color.Red) })

        CustomInputField(fieldTitle = "Job Type",
            textFieldValue = expModel.type ?: "",
            onValueChange = { expModel = expModel.copy(type = it) },
            placeholder = { SText(text = "Enter job type") })

        CustomInputField(fieldTitle = "Company name",
            textFieldValue = expModel.company ?: "",
            onValueChange = { expModel = expModel.copy(company = it) },
            placeholder = { SText(text = "Enter company name") })

        CustomInputField(fieldTitle = "Location",
            textFieldValue = expModel.location ?: "",
            onValueChange = { expModel = expModel.copy(location = it) },
            placeholder = { SText(text = "Enter location") })

        CustomInputArea(
            fieldTitle = "Description",
            textFieldValue = expModel.description ?: "",
            onValueChange = { expModel = expModel.copy(description = it) },
            placeholder = { SText(text = "Enter description") },
            singleLine = false
        )

        Box(modifier = Modifier.clickable(onClick = { startDatePicker.show() })) {
            CustomInputField(
                fieldTitle = "Start Date",
                textFieldValue = expModel.startDate ?: "",
                onValueChange = { expModel = expModel.copy(startDate = it) },
                placeholder = { SText(text = "Enter start date") },
                readOnly = true,
                isEnable = false,
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Box(modifier = Modifier.clickable(onClick = { endDatePicker.show() })) {
            CustomInputField(
                fieldTitle = "End Date",
                textFieldValue = expModel.endDate ?: "",
                onValueChange = { expModel = expModel.copy(endDate = it) },
                placeholder = { SText(text = "Enter end date") },
                readOnly = true,
                isEnable = false,
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecondaryOutlinedButton(
                modifier = Modifier.weight(1f), onClick = onCancel
            ) { Title(text = "Cancel", textColor = MaterialTheme.colorScheme.primary) }
            Spacer(modifier = Modifier.width(20.dp))
            PrimaryButton(
                modifier = Modifier.weight(1f), onClick = {
                    onSave(expModel)
                    onCancel()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Title(
                    text = if (experienceModel != null) "Update" else "Add",
                    textColor = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}


@Composable
fun ExperienceItem(experience: ExperienceModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {

        Column(modifier = Modifier.weight(1f)) {
            // Job title
            SText(
                text = experience.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Company and location
            experience.company?.let { company ->
                SText(
                    text = "$company • ${experience.location ?: ""}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Duration
            SText(
                text = "${experience.startDate ?: ""} - ${experience.endDate ?: "Present"}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(top = 4.dp),
                textColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Description
            experience.description?.let {
                SText(
                    text = it,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}