package com.optrak

import grizzled.slf4j.Logging
import org.specs2.mutable.Specification
import rapture.core.scalazModes
import rapture.data._
import rapture.json._
import jsonBackends.json4s._
import scalaz._

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
  implicit val maybeQuotedIntExtractor = implicitly[Extractor[Int,Json]] orElse Json.extractor[String].map(_.toInt)

  val personExtractor = BasicExtractor[Person, Json] { js =>
    val name = js.name.as[String]
    val maybeAge = js.age.as[Option[Int]] //this is the source of failure
    val years = js.years.as[Int]
    Person(name, maybeAge, years)
  }

  "rapture-json" should {
    "fail parsing Joe" in {
      implicit val manualPersonEx = personExtractor;
      {val joeIn = jsonIn.Person(0).as[Person]} must throwA[MissingValueException](message =
        "Missing value: <value>.age")
    }

    "parse Fred ok" in {
      implicit val manualPersonEx = personExtractor
      val fredIn = jsonIn.Person(1).as[Person]
      fredIn mustEqual fred
    }

    "parse Joe validated ok (?)" in {
      import scalazModes.returnValidations

      implicit val validPersonExtractor = BasicExtractor[Validation[DataGetException,Person], Json] { js =>
        for {
          name <- js.name.as[String]
          maybeAge <- js.age.as[Option[Int]]
          years <- js.years.as[Int]
        } yield Person(name, maybeAge, years)
      }
      val vJoe = jsonIn.Person(0).as[Person]
      vJoe mustEqual Success(joe)
    }
  }
}
 
