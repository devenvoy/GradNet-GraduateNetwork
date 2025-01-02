package com.sdjic.gradnet.presentation.screens.accountSetup.education

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
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
import androidx.compose.ui.text.input.KeyboardType
import com.sdjic.gradnet.presentation.composables.CustomInputArea
import com.sdjic.gradnet.presentation.composables.CustomInputField
import com.sdjic.gradnet.presentation.composables.DatePickerDialog
import com.sdjic.gradnet.presentation.composables.PrimaryButton
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.composables.SecondaryOutlinedButton
import com.sdjic.gradnet.presentation.composables.Title
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationSetUpScreen(
    educationState: EducationState, onAction: (EducationScreenAction) -> Unit, userRole: UserRole
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


    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SecondaryOutlinedButton(modifier = Modifier.padding(10.sdp).weight(1f), onClick = {
            onAction(
                EducationScreenAction.OnRemoveEducation(0)
            )
        }) { Title(text = "remove education") }
        PrimaryButton(
            modifier = Modifier.padding(10.sdp).weight(1f), onClick = {
                onAction(
                    EducationScreenAction.OnEducationBottomSheetStateChange(true)
                )
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) { Title(text = "Add Education", textColor = MaterialTheme.colorScheme.background) }
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
            placeholder = { SText(text = "Enter school name") })

        CustomInputField(fieldTitle = "Degree",
            textFieldValue = eduModel.degree ?: "",
            onValueChange = { eduModel = eduModel.copy(degree = it) },
            placeholder = { SText(text = "Enter degree") })

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
            ) { Title(text = "Cancel") }
            PrimaryButton(
                modifier = Modifier.padding(10.sdp).weight(1f),
                onClick = { onSave(eduModel) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) { Title(text = "Add", textColor = MaterialTheme.colorScheme.background) }
        }
    }
}
