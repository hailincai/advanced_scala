package firstpart

import scala.None

/*
1. pattern matching case is <obejectClass>(something) => {}
   -- something is an unapply method return type of objectClass
   -- underlying compile to objectClass.unapply(matchedInstance) => {}
   object Sample {
     def unapply(a: Int): String = "abc"
   }
   val desc = 1 match {
    case Sample(desc) => println(desc)
   }
2. If the object class is generic for two types, then the object class name can be used as infix operator
   object Sample[A, B] {
     def unapply(a: A, b: B): String = "something"
   }
   val desc = Sample(1, "a") match {
     case number Sample string => s"$number with $string"
   }
3. if the object class define unapplySeq(type) => Option[Seq], then the case match can have _*. It is called decomposed
4. unapply or unapplySeq method return type can be any type which implements two special methods. Most of case we will use Option
   -- def isEmpty: Boolean
   -- def get: T
* */
object AdvancedPatternMatching extends App {
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ => println("nothing happen")
  }

  /*
   - constants
   - wildcards
   - case classes
   - tuples
   - some special magic like above
  */
  class Person(val name: String, val age: Int)

  // tricks to make a class work with match
  object Person {
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] =
      Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a yo."
  }
  println(greeting)

  val leagalStatus = bob.age match {
    case Person(status) => println(s"My leagal status is $status")
  }

  /*
    Exercise.
  */
  val n: Int = 10

  object even {
    def unapply(x: Int): Boolean = x % 2 == 0
  }

  object singleDigit {
    def unapply(x: Int): Boolean = x > -10 && x < 10
  }

  val mathProperty = n match {
    case even() => println("even number")
    case singleDigit() => println("single digit")
    case _ => println("no property")
  }

  // infix patterns
  // only work with the thing with two classes
  case class Or[A, B](a: A, b: B) //either
  val either = Or(2, "two")
  val humanDescription = either match {
//    case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription)

  // decomposing sequences _*
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }
  println(vararg)

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    // decompose key is make your list represented as a Seq
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "hello"
    case _ => "something else"
  }

  // custom return types for unapply
  // The type requires to implement two method:
  //  - isEmpty: Boolean,
  //  - get: something
  // Based on this rule, Wrapper[T] can be used as return type for unapply
  abstract class Wrapper[T] {
    def isEmpty: Boolean;
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person's name is $n"
    case _ => "An alien"
  })
}
