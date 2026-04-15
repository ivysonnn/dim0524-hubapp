package br.ufrn.dim0524.hub.bascore


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(val name: String, var score: Int = 0) : Parcelable {
    fun resetPoints() {
        this.score = 0
    }

    fun addPoints(points: Int) {
        this.score += points
    }
}