

//ST10472330 Kiresh Govindasamy



package vcmsa.ci.musicplaylistapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Details : AppCompatActivity() {


    // Declare lists to hold incoming data from MainActivity
    private lateinit var songs: ArrayList<String>
    private lateinit var artists: ArrayList<String>
    private lateinit var ratings: ArrayList<Int>
    private lateinit var comments: ArrayList<String>

    private lateinit var songDetailsTextView: TextView
    private lateinit var averageRatingTextView: TextView
    private lateinit var showSongsButton: Button
    private lateinit var averageButton: Button
    private lateinit var returnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)



        // Retrieve data from Intent
        songs = intent.getStringArrayListExtra("songs") ?: arrayListOf()
        artists = intent.getStringArrayListExtra("artists") ?: arrayListOf()
        ratings = intent.getIntegerArrayListExtra("ratings") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()

        // Bind views
        songDetailsTextView = findViewById(R.id.txtSongList)
        averageRatingTextView = findViewById(R.id.txtAverage)
        showSongsButton = findViewById(R.id.btnShowSongs)
        averageButton = findViewById(R.id.btnAverage)
        returnButton = findViewById(R.id.btnReturn)

        // Show song list
        showSongsButton.setOnClickListener {
            val details = StringBuilder()
            for (i in songs.indices) {
                details.append("Title: ${songs[i]}\n")
                details.append("Artist: ${artists[i]}\n")
                details.append("Rating: ${ratings[i]}\n")
                details.append("Comment: ${comments[i]}\n\n")
            }
            songDetailsTextView.text = details.toString()
        }

        // Show average rating
        averageButton.setOnClickListener {
            if (ratings.isNotEmpty()) {
                var total = 0
                for (rating in ratings) {
                    total += rating
                }
                val average = total.toDouble() / ratings.size
                averageRatingTextView.text = "Average Rating: %.2f".format(average)
            } else {
                averageRatingTextView.text = "No ratings available."
            }
        }

        // Return to main screen
        returnButton.setOnClickListener {
            finish() // ends this activity and returns
        }
    }


    }
