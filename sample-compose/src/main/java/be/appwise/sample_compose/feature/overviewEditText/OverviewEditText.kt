package be.appwise.sample_compose.feature.overviewEditText

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.appwise.ui.EditText
import be.appwise.ui.EditTextCheckbox
import be.appwise.ui.EditTextDate
import be.appwise.ui.EditTextRadioButton
import be.appwise.ui.EditTextSlider
import com.example.compose.CoreDemoTheme
import java.time.LocalDate

@Composable
fun OverviewEditText() {
    var basic by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var date by remember {
        mutableStateOf(LocalDate.now().toString())
    }

    var slider by remember {
        mutableStateOf(0f)
    }

    var passwordVisibility by remember {
        mutableStateOf(true)
    }

    val scroll = rememberScrollState()


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditText(
            input = basic,
            label = "Basic Edit Text that's required",
            placeholder = "Type here ...",
            onInputChange = { basic = it },
            isRequired = true
        )

        EditText(
            input = basic,
            label = "Basic Edit Text",
            placeholder = "Type here ...",
            onInputChange = { basic = it },
            enabled = false
        )

        EditText(
            input = basic,
            label = "Basic Edit Text",
            placeholder = "Type here ...",
            onInputChange = { basic = it },
            message = "This is a error message",
            shape = CircleShape,
            isError = true
        )

        EditText(
            input = basic,
            label = "Password",
            placeholder = "******",
            onInputChange = { basic = it },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = Icons.Outlined.Visibility,
                        contentDescription = "Change password visibility"
                    )
                }
            },
            isPassword = passwordVisibility,
            singleLine = true
        )

        EditText(
            input = password,
            label = "Visa",
            placeholder = "1111.2222.3333.4444",
            onInputChange = { password = it },
            isCreditCard = true,
            singleLine = true
        )

        EditTextSlider(
            label = "Slider",
            value = slider,
            onValueChange = { slider = it }
        )

        EditTextSlider(
            label = "Slider with custom thumb",
            value = slider,
            onValueChange = { slider = it },
            thumb = Brush.radialGradient(
                listOf(Color(0xFF243484), Color(0xFF2be4dc))
            ),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                disabledThumbColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                activeTrackColor = Color(0xFF2be4dc)
            ),
            backgroundColor = Color.Transparent
        )

        EditTextSlider(
            label = "Slider",
            value = slider,
            onValueChange = { slider = it },
            enabled = false
        )

        EditTextRadioButton(
            values = listOf<String>("Test", "Test2"),
            label = "Radio Buttons",
            isRequired = true,
        )

        EditTextCheckbox(
            values = listOf<String>("Test", "Test2"),
            label = "Checkbox"
        )

        EditTextDate(
            label = "Date",
            placeholder = LocalDate.now().toString(),
            value = date,
            onChange = { date = it }
        )
    }
}

@Preview
@Composable
fun OverviewEditTextPreview() {
    CoreDemoTheme {
        OverviewEditText()
    }
}