package com.example.blackjack

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class BlackjackActivity : AppCompatActivity() {

    var chaos = false
    val deckOfCards = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52)
    var playerHand = mutableListOf<Int>(drawCard(deckOfCards), drawCard(deckOfCards))
    var dealerHand = mutableListOf<Int>(drawCard(deckOfCards), drawCard(deckOfCards))
    var playerValue = countHand(playerHand, chaos)
    var dealerValue = countHand(dealerHand, chaos)
    var playerStand = false
    var dealerStand = false
    var lastDealerHand = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack)

        findViewById<TextView>(R.id.textView4).apply {text = cardstr(playerHand[0])}
        findViewById<TextView>(R.id.textView6).apply {text = cardstr(playerHand[1])}
        findViewById<TextView>(R.id.textView26).apply {text = "VALUE: $playerValue" }
        findViewById<TextView>(R.id.textView7).apply {text = cardstr(dealerHand[1])}
        chaos = intent.getBooleanExtra("chaosMode", false)
    }

    fun hit(view: View) {
        playerHand.add(drawCard(deckOfCards))
        playerValue = countHand(playerHand, chaos)
        if (playerValue > 21) playerStand = true
        dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
        if (!dealerStand) {
            dealerHand.add(drawCard(deckOfCards))
            dealerValue = countHand(dealerHand, chaos)
        }

        if (playerStand) {
            while (!dealerStand) {
                dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
                if (!dealerStand) {
                    dealerHand.add(drawCard(deckOfCards))
                    dealerValue = countHand(dealerHand, chaos)
                }
            }
        }

        redraw()
    }

    fun stand(view: View) {
        playerStand = true
        while (!dealerStand) {
            dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
            if (!dealerStand) {
                dealerHand.add(drawCard(deckOfCards))
                dealerValue = countHand(dealerHand, chaos)
            }
        }

        redraw()
    }

    fun terminate(view: View) {
        finish()
    }

    fun redraw() {
        //Draw only the latest card in the hand, since a player can't gain multiple cards between redraws
        when (playerHand.size) {
            3 -> {
                findViewById<TextView>(R.id.textView8).apply {
                    text = cardstr(playerHand[2])
                    visibility = View.VISIBLE
                }
            }
            4 -> {
                findViewById<TextView>(R.id.textView9).apply {
                    text = cardstr(playerHand[3])
                    visibility = View.VISIBLE
                }
            }
            5 -> {
                findViewById<TextView>(R.id.textView10).apply {
                    text = cardstr(playerHand[4])
                    visibility = View.VISIBLE
                }
            }
            6 -> {
                findViewById<TextView>(R.id.textView11).apply {
                    text = cardstr(playerHand[5])
                    visibility = View.VISIBLE
                }
            }
            7 -> {
                findViewById<TextView>(R.id.textView12).apply {
                    text = cardstr(playerHand[6])
                    visibility = View.VISIBLE
                }
            }
            8 -> {
                findViewById<TextView>(R.id.textView13).apply {
                    text = cardstr(playerHand[7])
                    visibility = View.VISIBLE
                }
            }
            9 -> {
                findViewById<TextView>(R.id.textView14).apply {
                    text = cardstr(playerHand[8])
                    visibility = View.VISIBLE
                }
            }
            10 -> {
                findViewById<TextView>(R.id.textView15).apply {
                    text = cardstr(playerHand[9])
                    visibility = View.VISIBLE
                }
            }
            11 -> {
                findViewById<TextView>(R.id.textView24).apply {
                    text = cardstr(playerHand[10])
                    visibility = View.VISIBLE
                }
            }
        }
        val playVal = "VALUE: $playerValue"
        findViewById<TextView>(R.id.textView26).apply {text = playVal}
        //Draw all cards between the last redraw and this one for the dealer, who can draw indefinitely as long as the player stands first
        for (i in lastDealerHand+1..dealerHand.size) {
            when (i) {
                3 -> findViewById<TextView>(R.id.textView16).apply {
                    text = cardstr(dealerHand[2])
                    visibility = View.VISIBLE
                }
                4 -> findViewById<TextView>(R.id.textView17).apply {
                    text = cardstr(dealerHand[3])
                    visibility = View.VISIBLE
                }
                5 -> findViewById<TextView>(R.id.textView18).apply {
                    text = cardstr(dealerHand[4])
                    visibility = View.VISIBLE
                }
                6 -> findViewById<TextView>(R.id.textView19).apply {
                    text = cardstr(dealerHand[5])
                    visibility = View.VISIBLE
                }
                7 -> findViewById<TextView>(R.id.textView20).apply {
                    text = cardstr(dealerHand[6])
                    visibility = View.VISIBLE
                }
                8 -> findViewById<TextView>(R.id.textView21).apply {
                    text = cardstr(dealerHand[7])
                    visibility = View.VISIBLE
                }
                9 -> findViewById<TextView>(R.id.textView22).apply {
                    text = cardstr(dealerHand[8])
                    visibility = View.VISIBLE
                }
                10 -> findViewById<TextView>(R.id.textView23).apply {
                    text = cardstr(dealerHand[9])
                    visibility = View.VISIBLE
                }
                11 -> findViewById<TextView>(R.id.textView25).apply {
                    text = cardstr(dealerHand[10])
                    visibility = View.VISIBLE
                }
            }
        }

        //Endgame cleanup - show dealer values, hide play buttons, show quit button
        if (playerStand && dealerStand) {
            val dealVal = "VALUE: $dealerValue"
            findViewById<TextView>(R.id.textView5).apply {text = cardstr(dealerHand[0])}
            findViewById<TextView>(R.id.textView27).apply {text = dealVal}
            var conclusion = "YOU WIN!"
            if (playerValue > 21) conclusion = "YOU WENT BUST!"
            if (dealerValue > playerValue && dealerValue < 22) conclusion = "YOU LOSE!"

            findViewById<TextView>(R.id.textView28).apply {
                text = conclusion
                visibility = View.VISIBLE
            }

            findViewById<Button>(R.id.button2).apply {visibility = View.INVISIBLE}
            findViewById<Button>(R.id.button3).apply {visibility = View.INVISIBLE}
            findViewById<Button>(R.id.button4).apply {visibility = View.VISIBLE}
        }
    }
}

fun drawCard(deck: MutableList<Int>): Int {
    val pos = (1..deck.size).random() - 1
    val card = deck[pos]
    deck.removeAt(pos)
    return card
}

fun countHand(cards: MutableList<Int>, chaos: Boolean): Int {
    //My original version of the counting function was... ineffective. Its horrifying inability to do math was amusing enough for me to keep it as a secondary mode.
    if (chaos) {
        var sum = 0
        var aces = 0
        for (i in cards) {
            //This equation does nothing close to what I expected - every card is treated as an Ace, 2, 3, or ignored entirely. This had... interesting results.
            when ((i + 3) % 4) {
                1 -> {
                    sum += 11
                    aces++
                }
                2 -> sum += 2
                3 -> sum += 3
                4 -> sum += 4
                5 -> sum += 5
                6 -> sum += 6
                7 -> sum += 7
                8 -> sum += 8
                9 -> sum += 9
                10 -> sum += 10
                11 -> sum += 10
                12 -> sum += 10
                13 -> sum += 10
            }
        }

        //Have assumed a default to high ace, but if high ace will cause the hand to go bust it will automatically become low ace.
        while (sum > 21 && aces > 0) {
            sum -= 10
            aces--
        }
        return sum
    }
    var sum = 0
    var aces = 0
    for (i in cards) {
        //This equation results in: ERROR = 0, ACE = 1, TWO = 2, etc
        when ((i + 3) / 4) {
            1 -> {
                sum += 11
                aces++
            }
            2 -> sum += 2
            3 -> sum += 3
            4 -> sum += 4
            5 -> sum += 5
            6 -> sum += 6
            7 -> sum += 7
            8 -> sum += 8
            9 -> sum += 9
            10 -> sum += 10
            11 -> sum += 10
            12 -> sum += 10
            13 -> sum += 10
        }
    }

    //Have assumed a default to high ace, but if high ace will cause the hand to go bust it will automatically become low ace.
    while (sum > 21 && aces > 0) {
        sum -= 10
        aces--
    }
    return sum
}

fun cardstr(card: Int) : String {
    when (card) {
        1 -> return "ACE OF CLUBS"
        2 -> return "ACE OF HEARTS"
        3 -> return "ACE OF DIAMONDS"
        4 -> return "ACE OF SPADES"
        5 -> return "TWO OF CLUBS"
        6 -> return "TWO OF HEARTS"
        7 -> return "TWO OF DIAMONDS"
        8 -> return "TWO OF SPADES"
        9 -> return "THREE OF CLUBS"
        10 -> return "THREE OF HEARTS"
        11 -> return "THREE OF DIAMONDS"
        12 -> return "THREE OF SPADES"
        13 -> return "FOUR OF CLUBS"
        14 -> return "FOUR OF HEARTS"
        15 -> return "FOUR OF DIAMONDS"
        16 -> return "FOUR OF SPADES"
        17 -> return "FIVE OF CLUBS"
        18 -> return "FIVE OF HEARTS"
        19 -> return "FIVE OF DIAMONDS"
        20 -> return "FIVE OF SPADES"
        21 -> return "SIX OF CLUBS"
        22 -> return "SIX OF HEARTS"
        23 -> return "SIX OF DIAMONDS"
        24 -> return "SIX OF SPADES"
        25 -> return "SEVEN OF CLUBS"
        26 -> return "SEVEN OF HEARTS"
        27 -> return "SEVEN OF DIAMONDS"
        28 -> return "SEVEN OF SPADES"
        29 -> return "EIGHT OF CLUBS"
        30 -> return "EIGHT OF HEARTS"
        31 -> return "EIGHT OF DIAMONDS"
        32 -> return "EIGHT OF SPADES"
        33 -> return "NINE OF CLUBS"
        34 -> return "NINE OF HEARTS"
        35 -> return "NINE OF DIAMONDS"
        36 -> return "NINE OF SPADES"
        37 -> return "TEN OF CLUBS"
        38 -> return "TEN OF HEARTS"
        39 -> return "TEN OF DIAMONDS"
        40 -> return "TEN OF SPADES"
        41 -> return "JACK OF CLUBS"
        42 -> return "JACK OF HEARTS"
        43 -> return "JACK OF DIAMONDS"
        44 -> return "JACK OF SPADES"
        45 -> return "QUEEN OF CLUBS"
        46 -> return "QUEEN OF HEARTS"
        47 -> return "QUEEN OF DIAMONDS"
        48 -> return "QUEEN OF SPADES"
        49 -> return "KING OF CLUBS"
        50 -> return "KING OF HEARTS"
        51 -> return "KING OF DIAMONDS"
        52 -> return "KING OF SPADES"
    }
    return "ERROR"
}

fun dealerChoose(value: Int, playerVal: Int, playerStand: Boolean) : Boolean {
    //If dealer has hand of 17 or greater, dealer stands
    if (value > 16) return true
    //If dealer hand beats player hand and player stands, dealer stands
    else if (value >= playerVal && playerStand) return true
    return false
}

