package com.example

import akka.actor._
import akka.util.Timeout
import com.example._

import scala.concurrent.duration._

object RiskAssessmentDriver extends CompletableApp(2) {
  implicit val timeout = Timeout(5 seconds)
}

case class AttacheDocument(documentText: String)
case class ClassifyRisk()
case class RiskClasified(classification: String)

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
