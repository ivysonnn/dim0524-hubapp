package br.ufrn.dim0524.hub.bascore

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import br.ufrn.dim0524.hub.R
import br.ufrn.dim0524.hub.utils.hideSystemBars

class BascoreActivity : AppCompatActivity() {

    private var teamA = Team("Lakers")
    private var teamB = Team("Mavericks")

    private lateinit var tvScoreA: TextView
    private lateinit var tvScoreB: TextView
    private lateinit var tvTeamAName: TextView
    private lateinit var tvTeamBName: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bascore)
        enableEdgeToEdge()
        hideSystemBars(window)

        tvScoreA = findViewById(R.id.placarTimeA)
        tvScoreB = findViewById(R.id.placarTimeB)
        tvTeamAName = findViewById<TextView>(R.id.timeA)
        tvTeamBName = findViewById<TextView>(R.id.timeB)
        tvTeamAName.text = teamA.name
        tvTeamBName.text = teamB.name


        if(savedInstanceState != null) {
            teamA = BundleCompat.getParcelable(savedInstanceState, "teamA", Team::class.java) ?: Team("Team A", 0)
            teamB = BundleCompat.getParcelable(savedInstanceState, "teamB", Team::class.java) ?: Team("Team B", 0)

            displayScore()
        }

        findViewById<ImageButton>(R.id.btn_return_bascore).setOnClickListener { finish() }

        findViewById<Button>(R.id.tresPontosA).setOnClickListener { updateScore(3, teamA) }
        findViewById<Button>(R.id.doisPontosA).setOnClickListener { updateScore(2, teamA) }
        findViewById<Button>(R.id.tiroLivreA).setOnClickListener { updateScore(1, teamA) }

        findViewById<Button>(R.id.tresPontosB).setOnClickListener { updateScore(3, teamB) }
        findViewById<Button>(R.id.doisPontosB).setOnClickListener { updateScore(2, teamB) }
        findViewById<Button>(R.id.tiroLivreB).setOnClickListener { updateScore(1, teamB) }

        findViewById<Button>(R.id.reiniciarPartida).setOnClickListener {
            resetScores()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("teamA", teamA)
        outState.putParcelable("teamB", teamB)
    }

    private fun updateScore(points: Int, team: Team) {
        team.addPoints(points)
        displayScore()
    }

    private fun displayScore() {
        tvScoreA.text = teamA.score.toString()
        tvScoreB.text = teamB.score.toString()
    }

    private fun resetScores() {
        teamA.resetPoints()
        teamB.resetPoints()
        displayScore()
    }
}