package firstpart

import scala.util.Try

object DarkSugars extends App{
  // #1 methods wih single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks"
  val description = singleArgMethod {
    // write some complex code
    42
  }

  val aTryInstance = Try {
    // java's try {...}
    throw new RuntimeException
  }

  List(1, 2, 3).map {
    x => x + 1
  }

  // #2 single abstract method
  // functional interface
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = ???
  }

  val aFunkyInstance: Action = (x: Int) => x + 1

  // example: Runnable
  val aSweeterThread = new Thread(() => {println("Sweeter Scala")})

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractIntace: AnAbstractType = (a: Int) => println("sweet")

  // #3 the :: and #:: method are special
  val prependedList = 2 :: List(3, 4)
  // scala spec: last char decides assoiciativity of method
  // List(3, 4) :: 2

  class MyStream[T] {
    def -->: (vaule: T): MyStream[T] = this // actual implementation ere
  }
  // : at the right, so is right associativity
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // #4 multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }
  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // #5 infix types
  class Composite[A, B]
//  val composite: Composite[Int, String] = ???
  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // #6 update() is very special, much like apply
  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() AND update()

  // #7 setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0

    def member = internalMember // getter
    def member_=(value:Int): Unit =
      internalMember = value    // setter
  }
  val aMutalbeContainer = new Mutable
  aMutalbeContainer.member = 42 // rewritten aMutaableContainer.member_=(42)
}
