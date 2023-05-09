import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.vgs.R
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var cadInput: EditText
    private lateinit var usdInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to the input fields
        cadInput = findViewById(R.id.cad_input)
        usdInput = findViewById(R.id.usd_input)

        // Set up text change listeners for the input fields
        cadInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Convert the CAD input to USD and display the result in the USD input field
                val cadValue = s.toString().toDoubleOrNull() ?: 0.0
                val usdValue = convertCadToUsd(cadValue)
                usdInput.setText(usdValue.toPlainString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        usdInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Convert the USD input to CAD and display the result in the CAD input field
                val usdValue = s.toString().toDoubleOrNull() ?: 0.0
                val cadValue = convertUsdToCad(usdValue)
                cadInput.setText(cadValue.toPlainString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Convert CAD to USD using the current exchange rate
    private fun convertCadToUsd(cadValue: Double): BigDecimal {
        val exchangeRate = BigDecimal("0.7806")
        val usdValue = BigDecimal.valueOf(cadValue).multiply(exchangeRate)
        return usdValue.setScale(2, RoundingMode.HALF_UP)
    }

    // Convert USD to CAD using the current exchange rate
    private fun convertUsdToCad(usdValue: Double): BigDecimal {
        val exchangeRate = BigDecimal("0.7806")
        val cadValue = BigDecimal.valueOf(usdValue).divide(exchangeRate, 2, RoundingMode.HALF_UP)
        return cadValue
    }
}
