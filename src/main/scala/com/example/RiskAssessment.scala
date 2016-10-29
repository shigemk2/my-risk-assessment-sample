package com.example

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import akka.util.Timeout
import akka.pattern.ask
import akka.actor._

object RiskAssessmentDriver extends CompletableApp(2) {
  implicit val timeout = Timeout(5 seconds)
}

case class AttacheDocument(documentText: String)
case class ClassifyRisk()
case class RiskClassified(classification: String)

case class Document(documentText: Option[String]) {
  if (documentText.isDefined) {
    val text = documentText.get
    if (text == null || text.trim.isEmpty) {
      throw new IllegalStateException("Document must have text.")
    }
  }

  def determineClassification = {
    val text = documentText.get.toLowerCase

    if (text.contains("low")) "Low"
    else if (text.contains("medium")) "Medium"
    else if (text.contains("high")) "High"
    else "Unknown"
  }

  def isNotAttached = documentText.isEmpty
  def isAttached = documentText.isDefined
}

class RiskAssessment extends Actor {
  var document = Document(None)

  def documented: Receive = {
    case attachment: AttacheDocument =>
      // already received; ignore
    case classify: ClassifyRisk =>
      sender ! RiskClassified(document.determineClassification)
  }

  def undocumented: Receive = {
    case attachement: AttacheDocument =>
      document = Document(Some(attachement.documentText))
      context.become(document)
    case classify: ClassifyRisk =>
      sender ! RiskClassified("Unknown")
  }

  def receive = undocumented
}
