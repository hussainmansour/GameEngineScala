package Games.TicTacToe
import Model._
object Controller extends Controller {
  override def apply(state: State, input: String): State = {
    // for the first time
    if (state == null) {
      var temp = new State
      temp.board = Array.ofDim[Char](3, 3)
      temp.board = Array(
        Array(' ', ' ', ' '),
        Array(' ', ' ', ' '),
        Array(' ', ' ', ' ')
      )
      return temp
    }
    val row: Int = (input.charAt(0) - '1')
    val col: Int = (input.charAt(1) - '1')

    def valid : Boolean = {
      if (row < 0 || row > 2 || col < 0 || col > 2 )
        return false
      if (state.board(row)(col) != ' ')
        return false
      true
    }

    if (valid) {
      if (state.turn)
        state.board(row)(col) = 'X'
      else
        state.board(row)(col) = 'O'
      state.turn = !state.turn //change turn

      /*
      *
      * check if game is over
      * */
    }
    state
  }
}