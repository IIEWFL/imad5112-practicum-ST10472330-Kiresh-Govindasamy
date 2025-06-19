

//ST10472330 Kiresh Govindasamy

package vcmsa.ci.musicplaylistapp

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.telecom.Call.Details
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    // Parallel arrays to store song details
    private val songTitles = mutableListOf<String>()
    private val artists = mutableListOf<String>()
    private val ratings = mutableListOf<Int>()
    private val comments = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        // Linking UI buttons from activity_main.xml
        val addButton: Button = findViewById(R.id.addButton)
        val viewButton: Button = findViewById(R.id.viewButton)
        val exitButton: Button = findViewById(R.id.exitButton)

        // When "Add to Playlist" button is clicked
        addButton.setOnClickListener {
            showAddSongDialog()
        }

        // When "View Playlist" is clicked
        viewButton.setOnClickListener {
            if (songTitles.size < 4 ) {
                // Show message if fewer than 4 songs have been added
                Snackbar.make(viewButton, "Please add at least 4 songs to view the playlist.", Snackbar.LENGTH_SHORT).show()
            } else {
                // Pass data to second screen (Details Screen)
                val intent = Intent(this,vcmsa.ci.musicplaylistapp.Details::class.java)
                intent.putStringArrayListExtra("songs", ArrayList(songTitles))
                intent.putStringArrayListExtra("artists", ArrayList(artists))
                intent.putIntegerArrayListExtra("ratings", ArrayList(ratings))
                intent.putStringArrayListExtra("comments", ArrayList(comments))
                startActivity(intent)
            }
        }

        // Exit the app completely
        exitButton.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    // Function to display a dialog for entering song details
    private fun showAddSongDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Song")

        val view = layoutInflater.inflate(R.layout.dialog_add_song, null)
        val titleEditText: EditText = view.findViewById(R.id.songTitleEditText)
        val artistEditText: EditText = view.findViewById(R.id.artistEditText)
        val ratingEditText: EditText = view.findViewById(R.id.ratingEditText)
        val commentEditText: EditText = view.findViewById(R.id.commentEditText)

        builder.setView(view)

        // Add button in dialog to confirm entry
        builder.setPositiveButton("Add") { dialog, _ ->
            // Retrieve input fields from the layout
            val title = titleEditText.text.toString().trim()
            val artist = artistEditText.text.toString().trim()
            val ratingStr = ratingEditText.text.toString().trim()
            val comment = commentEditText.text.toString().trim()


            // Check for required fields
            if (title.isEmpty() || artist.isEmpty() || ratingStr.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Title, artist, and rating cannot be empty.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            // Convert rating input to integer and validate range
            val rating = ratingStr.toIntOrNull()
            if (rating == null || rating !in 1..5) {
                Snackbar.make(findViewById(android.R.id.content), "Invalid rating. Enter a number from 1 to 5.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }


            // Add values to the parallel arrays
            songTitles.add(title)
            artists.add(artist)
            ratings.add(rating)
            comments.add(comment)

            //Confirms that it has been added
            Snackbar.make(findViewById(android.R.id.content), "$title added to the playlist.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Cancel button closes the dialog
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        // Show the dialog
        builder.show()
    }

    }
