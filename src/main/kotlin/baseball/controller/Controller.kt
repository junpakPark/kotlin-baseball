package baseball.controller

import baseball.model.MatchUp
import baseball.model.PlayingNumber
import baseball.model.Score
import baseball.view.InputView
import baseball.view.OutputView

private const val START_CODE = 1
private const val END_CODE = 2

class Controller(
        private val inputView: InputView,
        private val outputView: OutputView
) {

    fun process() {
        do {
            playingInning()
        } while (shouldContinueGame())
    }

    private fun playingInning() {
        outputView.printStartMessage()
        val computerNumber = PlayingNumber.pitchBall()
        val matchUp = MatchUp(computerNumber)

        while (true) {
            val userNumber = inputView.readNumbers()
            val score = matchUp.play(userNumber)

            if (score.isStrike()) {
                outputView.printEndMessage()
                break
            }
            printResult(score)
        }
    }

    private fun printResult(score: Score) {
        if (score.isNothing()) {
            outputView.printNothing()
            return
        }
        outputView.printResult(ball = score.balls, strike = score.strikes)
    }

    private fun shouldContinueGame(): Boolean {
        val restartCode = inputView.readRestart()

        return when (restartCode) {
            START_CODE -> true
            END_CODE -> false
            else -> throw IllegalArgumentException("$START_CODE 또는 ${END_CODE}만 입력해주세요")
        }
    }
}
