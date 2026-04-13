import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var userInput: EditText
    private lateinit var rollButton: Button
    private lateinit var feedbackTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInput = findViewById(R.id.userInput)
        rollButton = findViewById(R.id.rollButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)

        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val userPrediction = userInput.text.toString().toIntOrNull()
        val diceRollResult = Random.nextInt(1, 7) // Simulates rolling a dice (1-6)

        when {
            userPrediction == null -> feedbackTextView.text = "Please enter a valid number."
            userPrediction == diceRollResult -> feedbackTextView.text = "Congratulations! You predicted correctly!"
            else -> feedbackTextView.text = "Sorry, you predicted $userPrediction but rolled a $diceRollResult. Better luck next time!"
        }
    }
}