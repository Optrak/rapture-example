package com.optrak

import grizzled.slf4j.Logging
import org.specs2.mutable.Specification
import rapture.data.{TypeMismatchException, BasicExtractor}
import rapture.json._
import jsonBackends.json4s._

class ParsePersonTest extends Specification with Logging {
  case class Person(name: String, age: Option[Int], years: Int)
  val jsonIn = json"""
  {"Person": [
    {
      "name": "joe",
      "years": "3"
    },
    {
      "name": "fred",
      "years": 5,
      "age": "55"
    }
  ]}"""

  val joe = Person("joe", None, 3)
  val fred = Person("fred", Some(55), 5)
  implicit val strToIntExtractor = BasicExtractor[Int, Json](_.as[String].toInt)

  "rapture-json" should {
    "parse joe successfully" in {
      val joeIn = jsonIn.Person(0).as[Person]
      joeIn mustEqual joe
    }

    "fail parsing fred" in {
      //don't see a way to cope with mixed quoted and unquoted ints
      {val fredIn = jsonIn.Person(1).as[Person]} must
        throwA[TypeMismatchException].like {
          case e => e.getMessage must_== "Type mismatch: Expected string but found number at <value>.Person(1)"
        }
    }
  }
}
 
