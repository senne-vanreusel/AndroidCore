package be.appwise.core.extensions.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import java.util.*

fun Activity.showDateTimePicker(currentTimeStamp: Long, useDate: (Long) -> Unit) {
    val currentDate = Calendar.getInstance().apply {
        timeInMillis = currentTimeStamp
    }
    val chosenDate = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
        chosenDate.set(year, monthOfYear, dayOfMonth)
        TimePickerDialog(this,
            checkWhichThemeToUse(),
            { _, hourOfDay, minute ->
            chosenDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
            chosenDate.set(Calendar.MINUTE, minute)
            Log.d("DatePickerDialog", "The chosen date " + chosenDate.time)
            useDate(chosenDate.timeInMillis)
        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show()
    }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE))

    datePickerDialog.datePicker.maxDate = chosenDate.timeInMillis
    datePickerDialog.datePicker.minDate = chosenDate.timeInMillis - 24 * 3600 * 1000 //1 day

    datePickerDialog.show()
}

private fun checkWhichThemeToUse(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert
    } else {
        AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
    }
}
