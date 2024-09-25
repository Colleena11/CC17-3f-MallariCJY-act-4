package com.example.tipcalcu

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalcu.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Use lateinit to initialize the binding object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the click listener for the calculate button
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        // Get the text from the cost input field
        val costString = binding.costOfService.text.toString()

        // Check if the cost input is empty or invalid
        if (costString.isEmpty()) {
            // Display a message to the user if the input is empty
            Toast.makeText(this, "Please enter a cost value", Toast.LENGTH_SHORT).show()
            return
        }

        // Convert the cost input to a double
        val cost = costString.toDoubleOrNull()

        // Check if the cost is valid
        if (cost == null || cost == 0.0) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        // Determine which tip percentage was selected
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
            R.id.option_20_percent -> 0.20
            R.id.option_18_percent -> 0.18
            else -> 0.15
        }

        // Calculate the tip
        var tip = cost * tipPercentage

        // Check if the user wants to round up the tip
        val roundup = binding.roundTip.isChecked
        if (roundup) {
            tip = ceil(tip)
        }

        // Format the tip as currency
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        // Display the tip result in the TextView
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}
