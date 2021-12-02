package firstpart

import scala.annotation.tailrec

object Recap extends App {

  // instructions vs expression

  // compiler infers types for us
  val aCodeBlock = {
    56
  }

  // Unit = void
  val theUnit = println("hello, scala")

  // functions
  def aFunction(x: Int): Int = x + 1

  // recursion: stack and tail
  // @tailrec force to recursion method to be tail recursion
  @tailrec
  def factorial(n: Int, acc: Int): Int =
    if (n <= 0) acc
    else factorial(n - 1, n * acc)

  // object-oriented programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polymorphism

  trait Carnivor {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivor {
    override def eat(a: Animal): Unit = ???
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  // operator rewritten using method
  // anonymous classes
  val aCarnivore = new Carnivor {
    override def eat(a: Animal): Unit = ???
  }

  // generics
  abstract class MyList[+A] // variance and variance problems in depth
  // singletons and companions
  object MyList

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try/catch/finally
  val throwsException = throw new RuntimeException // Nothing
  val aPotentialFailure = try {
    throw new RuntimeException
  }catch{
    case e: Exception => "I caught an exception"
  }finally{
    println("something")
  }

  // packaging and imports

  // functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  incrementer(1)

  val anonymousIncreenter = (x: Int) => x + 1
  List(1, 2, 3).map(anonymousIncreenter) // HOP
  // map, flatMap, filter

  // for-comprehension
  val pairs = for {
    num <- List(1, 2, 3)
    char <- List('a', 'b')
  } yield num + "-" + char

  // Scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples

  // "collections": Options, Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "First"
    case 2 => "Second"
    case 3 => "Third"
    case _ => x + "th"
  }
}
