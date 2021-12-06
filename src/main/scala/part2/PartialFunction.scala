package part2

object PartialFunction extends App {
  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value
  println(aPartialFunction(2))

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  // lift
  val lifted = aPartialFunction.lift // Return total function now
  println(lifted(2))
  println(lifted(98))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(pfChain(2))

  // PF extned normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1=> 42
    case 2 => 78
    case 3 => 1000
  }

  /*
  Note: PF can only have ONE parameter type
  */

  /**
   * Exercises
   * 1 - construct a PF instance myself
   * 2 - dumb chatbot as a PF
   */
  val somePF = new PartialFunction[String, String] {
    override def apply(v1: String): String = {
      v1 match {
        case "a" => "b"
        case "b" => "c"
        case "c" => "d"
      }
    }

    override def isDefinedAt(x: String): Boolean =
      x == "a" || x == "b" || x == "c"
  }

  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hi my name is HAL9000"
    case "goodbye" => "Once you start talking to me, there is no return, human!"
    case "call mom" => "Unable to find your phone"
  }
  scala.io.Source.stdin.getLines().foreach(line => println("chatbot said:" + chatbot(line)))
}
