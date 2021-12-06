package exersises

import scala.annotation.tailrec

// A ==> Boolean is a Function1 trait, so has apply method
trait MySet[A] extends (A => Boolean){
  def contains(elem: A): Boolean
  def +(elem:A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  // this comes from function type
  Function1
  def apply(elem: A): Boolean =
    contains(elem)
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false
  def +(elem:A): MySet[A] = new NotEmptySet(elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()
}

class NotEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean =
    (elem == head) || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this.contains(elem)) this
    else new NotEmptySet(elem, this)

  def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] =
    tail.map(f) + f(head)

  def flatMap[B](f: A => MySet[B]): MySet[B] =
     f(head) ++ tail.flatMap(f)

  def filter(predicate: A => Boolean): MySet[A] =
    if (predicate(head)) tail.filter(predicate) + head
    else tail.filter(predicate)

  def foreach(f: A => Unit): Unit =
    f(head)
    tail foreach f
}

object MySet {
  // A* means multiple A type of instance will be passed in
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlayground extends App {
  val s = MySet(1, 2, 3, 4)
  s + 5 ++ MySet(-1, 2) map (x => x * 10) foreach println
}