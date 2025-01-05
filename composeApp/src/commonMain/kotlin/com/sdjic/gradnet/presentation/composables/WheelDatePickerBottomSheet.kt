package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

/**
 * @param modifier: Modifies the layout of the date picker.
 * @param title: Title displayed above the date picker.
 * @param doneLabel: Label for the "Done" button.
 * @param titleStyle: Style for the title text.
 * @param doneLabelStyle: Style for the "Done" label text.
 * @param startDate: Initial date selected in the picker.
 * @param minDate: Minimum selectable date.
 * @param maxDate: Maximum selectable date.
 * @param yearsRange: Initial years range.
 * @param height: height of the date picker component.
 * @param rowCount: Number of rows displayed in the picker and it's depending on height also.
 * @param showShortMonths: show short month name.
 * @param dateTextStyle: Text style for the date display.
 * @param dateTextColor: Text color for the date display.
 * @param hideHeader: Hide header of picker.
 * @param selectorProperties: Properties defining the interaction with the date picker.
 * @param onDoneClick: Callback triggered when the "Done" button is clicked, passing the selected date.
 * @param onDateChangeListener: Callback triggered when the Date is changed, passing the selected date.
 * @param showMonthAsNumber: flag to show month as a number.
 */

@Composable
fun WheelDatePickerBottomSheet(
    title: String,
    showDatePicker: Boolean,
    onDoneClick: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    /* var showDatePicker by remember { mutableStateOf(false) }
     var selectedDate by remember { mutableStateOf("") }*/

    if (showDatePicker) {
        WheelDatePickerView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.sdp, bottom = 22.sdp),
            showDatePicker = showDatePicker,
            title = title,
            doneLabel = "Done",
            titleStyle = TextStyle(
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
            doneLabelStyle = TextStyle(
                fontSize = 13.ssp,
                fontWeight = FontWeight(600),
                color = Color(0xFF007AFF),
            ),
            dateTextColor = Color(0xff007AFF),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            rowCount = 5,
            height = 150.sdp,
            dateTextStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            dragHandle = {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 6.sdp).width(42.sdp).clip(CircleShape),
                    thickness = 4.sdp,
                    color = Color(0xFFE8E4E4)
                )
            },
            shape = RoundedCornerShape(topStart = 14.sdp, topEnd = 14.sdp),
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            onDoneClick = {
                onDoneClick(it)
                onDismiss()
//                selectedDate = it.toString()
//                showDatePicker = false
            },
            onDismiss = {
                onDismiss()
//                showDatePicker = false
            }
        )
    }

    // Example
    /* Surface(
         modifier = Modifier.fillMaxSize(),
         color = MaterialTheme.colorScheme.background
     ) {
         Column(
             modifier = Modifier
                 .height(160.sdp)
                 .fillMaxSize(),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Button(
                 onClick = {
                     showDatePicker = true
                 },
                 colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
             ) {
                 Text(
                     text = "Show Date Picker",
                     modifier = Modifier.background(Color.Transparent)
                         .padding(horizontal = 10.sdp, vertical = 10.sdp),
                     fontSize = 12.ssp
                 )
             }
             Text(
                 text = selectedDate,
                 modifier = Modifier
                     .padding(top = 8.sdp)
                     .fillMaxWidth(),
                 textAlign = TextAlign.Center
             )
         }
     }*/
}