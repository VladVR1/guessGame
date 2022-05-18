import java.util.Scanner
import kotlin.random.Random

fun main() {
    println("Hello, user! It's the Guess The Number Game!")
    chooseThePlayerType()
}

private fun chooseTheLevel(): Level {
    println("***** Choose the level *****")
    println("1 - EASY")
    println("2 - STANDARD")
    println("3 - HARD")

    return when (readLine()) {
        "1" -> Level.Easy
        "2" -> Level.Standard
        "3" -> Level.Hard
        else -> {
            println("Try again!\n")
            chooseTheLevel()
        }
    }
}

private fun chooseThePlayerType() {
    println("***** Choose the game *****")
    println("1 - COMPUTER")
    println("2 - USER")

    when (readLine()) {
        "1" -> gamePlay(PlayerType.COMPUTER)
        "2" -> gamePlay(PlayerType.USER)
        else -> {
            println("Try again!\n")
            return chooseThePlayerType()
        }
    }
}

private fun gamePlay(playerType: PlayerType) {
    val level = chooseTheLevel()
    if (playerType == PlayerType.USER) {
        println("***** Computer guess the number from 0 to 100 *****")
        userGame(level.numberOfAttempts)
    } else {
        computerGame(level.numberOfAttempts)
    }
}

private fun userGame(numberOfAttempts: Int) {
    val guessNumber = Random.nextInt(0, 100)
    var currentNumberOfAttempts = 0
    var number: Int

    println("***** You have only $numberOfAttempts attempts *****")

    while (currentNumberOfAttempts < numberOfAttempts) {
        val remainNumberOfAttempts = numberOfAttempts - currentNumberOfAttempts
        try {
            number = Scanner(System.`in`).nextInt()
            when {
                number == guessNumber -> println("***** You win! Guessed number is: $number *****")
                number > guessNumber -> println("Less than $number")
                number < 0 -> throw Exception()
                else -> println("More than $number")
            }
        } catch (e: Exception) {
            println("Incorrect value")
        }
        currentNumberOfAttempts += 1

        if (remainNumberOfAttempts == 0) {
            println("***** You lose. Guessed number is: $guessNumber *****")
        } else {
            println("Remaining tries: $remainNumberOfAttempts")
        }
    }
}

private fun computerGame(numberOfAttempts: Int) {
    println("***** Guess the number from 0 to 100 *****")
    val userNumber: Int
    var until = 100
    var from = 0
    var computerNumber = Random.nextInt(from, until)
    var currentNumberOfAttempts = 0

    try {
        userNumber = Scanner(System.`in`).nextInt()
        println("$computerNumber")

        while (currentNumberOfAttempts < numberOfAttempts) {
            if (computerNumber != userNumber) {
                println("Is the $computerNumber more(1), less(2) or equal(3)?")
                when (readLine()) {
                    "1" -> from = computerNumber + 1
                    "2" -> until = computerNumber - 1
                    "3" -> {
                        println("Computer win. Guessed number is: $userNumber")
                        return
                    }
                    else -> continue
                }
                try {
                    computerNumber = Random.nextInt(from, until)
                    currentNumberOfAttempts += 1
                } catch (e: Exception) {
                    println("The computer thinks you are lying")
                    return
                }
            }
        }

        if (currentNumberOfAttempts == numberOfAttempts) {
            println("Computer lose. Guessed number is: $userNumber")
        }

    } catch (e: Exception) {
        println("Incorrect value")
    }
}

enum class PlayerType {
    USER, COMPUTER
}

sealed class Level(val numberOfAttempts: Int) {
    object Easy : Level(12)
    object Standard : Level(7)
    object Hard : Level(3)
}