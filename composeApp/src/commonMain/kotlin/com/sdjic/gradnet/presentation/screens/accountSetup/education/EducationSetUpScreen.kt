package com.sdjic.gradnet.presentation.screens.accountSetup.education

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import com.maxkeppeler.sheets.color.models.MultipleColors
import com.maxkeppeler.sheets.color.models.SingleColor
import com.sdjic.gradnet.presentation.composables.DatePickerDialog
import com.sdjic.gradnet.presentation.composables.button.PlusIconButton
import com.sdjic.gradnet.presentation.composables.button.PrimaryButton
import com.sdjic.gradnet.presentation.composables.button.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.filter.CustomImageChip
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputArea
import com.sdjic.gradnet.presentation.composables.textInput.CustomInputField
import com.sdjic.gradnet.presentation.composables.textInput.DropDownTextField
import com.sdjic.gradnet.presentation.core.LanguagesList
import com.sdjic.gradnet.presentation.core.SkillList
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.theme.errorColor
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.empty_trash
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EducationSetUpScreen(
    isVerified: Boolean,
    educationState: EducationState,
    onAction: (EducationScreenAction) -> Unit,
    userRole: UserRole
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (educationState.showEducationBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
            sheetState = sheetState,
            dragHandle = null,
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                onAction(
                    EducationScreenAction.OnEducationBottomSheetStateChange(false)
                )
            },
        ) {
            AddEducationModal(
                educationModel = EducationModel(),
                onSave = { onAction(EducationScreenAction.OnAddEducation(it)) },
                onCancel = { onAction(EducationScreenAction.OnEducationBottomSheetStateChange(false)) })
        }
    }

    if (educationState.showLanguageDialog) {
        BasicAlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = {}
        ) {
            Surface(
                modifier = Modifier.padding(10.sdp),
                shape = RoundedCornerShape(12.sdp)
            ) {
                val isAssertExpanded = remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.padding(10.sdp),
                ) {
                    Title(
                        text = "Select Language",
                        size = 16.ssp,
                        modifier = Modifier.padding(8.sdp)
                    )
                    DropDownTextField(
                        modifier = Modifier.padding(vertical = 10.sdp),
                        hintText = "Select language",
                        options = LanguagesList.filter { it !in educationState.languages },
                        onClick = { isAssertExpanded.value = it },
                        expanded = isAssertExpanded,
                        onDismissReq = { isAssertExpanded.value = false },
                        selectedText = selectedText,
                        onValueSelected = { selectedText = it }
                    )
                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = {
                            isAssertExpanded.value = false
                            onAction(
                                EducationScreenAction.OnLanguageDialogStateChange(false)
                            )
                        }) { SText("Cancel", fontSize = 12.ssp) }
                        TextButton(onClick = {
                            onAction(EducationScreenAction.OnAddLanguage(selectedText))
                            selectedText = ""
                            isAssertExpanded.value = false
                            onAction(
                                EducationScreenAction.OnLanguageDialogStateChange(false)
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

    if (educationState.showSkillDialog) {
        BasicAlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = {}
        ) {
            Surface(
                modifier = Modifier.padding(10.sdp),
                shape = RoundedCornerShape(12.sdp)
            ) {
                val isAssertExpanded = remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.padding(10.sdp),
                ) {
                    Title(text = "Select Skill", size = 16.ssp, modifier = Modifier.padding(8.sdp))
                    DropDownTextField(
                        modifier = Modifier.padding(vertical = 10.sdp),
                        hintText = "Select Skill",
                        options = SkillList.filter { it !in educationState.skills }.sorted(),
                        onClick = { isAssertExpanded.value = it },
                        expanded = isAssertExpanded,
                        onDismissReq = { isAssertExpanded.value = false },
                        selectedText = selectedText,
                        onValueSelected = { selectedText = it }
                    )
                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = {
                            isAssertExpanded.value = false
                            onAction(
                                EducationScreenAction.OnSkillDialogStateChange(false)
                            )
                        }) { SText("Cancel", fontSize = 12.ssp) }
                        TextButton(onClick = {
                            onAction(EducationScreenAction.OnAddSkill(selectedText))
                            selectedText = ""
                            isAssertExpanded.value = false
                            onAction(
                                EducationScreenAction.OnSkillDialogStateChange(false)
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
            Title(text = "Languages", size = 16.ssp)
            PlusIconButton {
                onAction(
                    EducationScreenAction.OnLanguageDialogStateChange(true)
                )
            }
        }

        FlowRow(
            Modifier.fillMaxWidth().wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            educationState.languages.fastForEachIndexed { _, language ->
                CustomImageChip(
                    text = language,
                    modifier = Modifier.padding(2.sdp),
                    selected = true,
                    onClick = {
                        onAction(EducationScreenAction.OnRemoveLanguage(language))
                    }
                )
            }
        }

        Row(
            modifier = Modifier.padding().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title(text = "Skills", size = 16.ssp)
            PlusIconButton {
                onAction(
                    EducationScreenAction.OnSkillDialogStateChange(true)
                )
            }
        }
        FlowRow(
            Modifier.fillMaxWidth().wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            educationState.skills.fastForEachIndexed { _, skill ->
                CustomImageChip(
                    text = skill,
                    modifier = Modifier.padding(2.sdp),
                    selected = true,
                    onClick = {
                        onAction(EducationScreenAction.OnRemoveSkill(skill))
                    }
                )
            }
        }

        Row(
            modifier = Modifier.padding().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title(text = "Education", size = 16.ssp)
            PlusIconButton {
                onAction(
                    EducationScreenAction.OnEducationBottomSheetStateChange(true)
                )
            }
        }
        if (educationState.eductionList.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.sdp)
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    educationState.eductionList.fastForEachIndexed { index, item ->
                        Box(
                            modifier = Modifier.padding(horizontal = 8.sdp, vertical = 4.sdp),
                        ) {

                            EducationItem(education = item)

                            Icon(
                                modifier = Modifier.padding(8.sdp).size(16.sdp)
                                    .align(Alignment.TopEnd)
                                    .clickable(onClick = {
                                        onAction(EducationScreenAction.OnRemoveEducation(index))
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

        Spacer(modifier = Modifier.height(16.sdp))

        var color by remember { mutableStateOf(Color.Red.toArgb()) }

        val colorState =
            rememberUseCaseState(
                visible = false,
                onDismissRequest = {  }
            )

        val templateColors = MultipleColors.ColorsInt(
            Color.Red.copy(alpha = 0.1f).toArgb(),
            Color.Red.copy(alpha = 0.3f).toArgb(),
            Color.Red.copy(alpha = 0.5f).toArgb(),
            Color.Red.toArgb(),
            Color.Green.toArgb(),
            Color.Yellow.toArgb(),
        )

            ColorDialog(
                state = colorState,
                selection = ColorSelection(
                    selectedColor = SingleColor(color),
                    onSelectColor = { color = it },
                ),
                config = ColorConfig(
                    templateColors = templateColors,
                    defaultDisplayMode = ColorSelectionMode.TEMPLATE,
                    allowCustomColorAlphaValues = true
                ),
            )

        Button(
            onClick = { colorState.show() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color)
            )
        ) { Text("$color") }
    }

}

@Composable
fun AddEducationModal(
    educationModel: EducationModel,
    onSave: (EducationModel) -> Unit,
    onCancel: () -> Unit
) {

    var eduModel by remember { mutableStateOf(educationModel) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    DatePickerDialog(
        title = "Select start date",
        showDatePicker = showStartDatePicker,
        onDismiss = { showStartDatePicker = false },
        onDateSelected = { date ->
            eduModel = eduModel.copy(startDate = date)
            showStartDatePicker = false
        }
    )

    DatePickerDialog(
        title = "Select end date",
        showDatePicker = showEndDatePicker,
        onDismiss = { showEndDatePicker = false },
        onDateSelected = { date ->
            eduModel = eduModel.copy(endDate = date)
            showEndDatePicker = false
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(12.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {

        Box(
            modifier = Modifier
                .width(50.sdp)
                .height(3.sdp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFBBC0C4))
                .align(Alignment.CenterHorizontally)
        )

        Title(
            text = "Add Education",
            size = 16.ssp,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.sdp)
        )

        CustomInputField(fieldTitle = "School Name",
            textFieldValue = eduModel.schoolName,
            onValueChange = { eduModel = eduModel.copy(schoolName = it) },
            placeholder = { SText(text = "Enter school name") },
            supportingText = { SText(text = "Required", textColor = Color.Red) })

        CustomInputField(fieldTitle = "Degree",
            textFieldValue = eduModel.degree ?: "",
            onValueChange = { eduModel = eduModel.copy(degree = it) },
            placeholder = { SText(text = "Enter degree") },
            supportingText = { SText(text = "Required", textColor = Color.Red) })

        CustomInputField(fieldTitle = "Field of Study",
            textFieldValue = eduModel.field ?: "",
            onValueChange = { eduModel = eduModel.copy(field = it) },
            placeholder = { SText(text = "Enter field of study") })

        CustomInputField(fieldTitle = "Location",
            textFieldValue = eduModel.location ?: "",
            onValueChange = { eduModel = eduModel.copy(location = it) },
            placeholder = { SText(text = "Enter location") })

        CustomInputArea(
            fieldTitle = "Description",
            textFieldValue = eduModel.description ?: "",
            onValueChange = { eduModel = eduModel.copy(description = it) },
            placeholder = { SText(text = "Enter description") },
            singleLine = false
        )

        Box(
            modifier = Modifier.clickable(
                onClick = { showStartDatePicker = true }
            )
        ) {
            CustomInputField(
                fieldTitle = "Start Date",
                textFieldValue = eduModel.startDate ?: "",
                onValueChange = { eduModel = eduModel.copy(startDate = it) },
                placeholder = { SText(text = "Enter start date") },
                readOnly = true,
                isEnable = false,
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Box(
            modifier = Modifier.clickable(
                onClick = { showEndDatePicker = true }
            )
        ) {
            CustomInputField(
                fieldTitle = "End Date",
                textFieldValue = eduModel.endDate ?: "",
                onValueChange = { eduModel = eduModel.copy(endDate = it) },
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
                modifier = Modifier.padding(10.sdp).weight(1f), onClick = onCancel
            ) { Title(text = "Cancel", textColor = MaterialTheme.colorScheme.primary) }
            PrimaryButton(
                modifier = Modifier.padding(10.sdp).weight(1f),
                onClick = {
                    onSave(eduModel)
                    onCancel()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) { Title(text = "Add", textColor = MaterialTheme.colorScheme.background) }
        }
    }
}


@Composable
fun EducationItem(education: EducationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {

        Column(modifier = Modifier.weight(1f)) {
            // School name
            SText(
                text = education.schoolName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Degree and Field
            education.degree?.let { degree ->
                SText(
                    text = "$degree${if (education.field != null) ", ${education.field}" else ""}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Location
            education.location?.let {
                SText(
                    text = it,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 4.dp),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Duration
            SText(
                text = "${education.startDate ?: ""} - ${education.endDate ?: "Present"}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(top = 4.dp),
                textColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Description
            education.description?.let {
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