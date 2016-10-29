package com.example

import akka.actor._
import akka.util.Timeout
import com.example._

import scala.concurrent.duration._

object RiskAssessmentDriver extends CompletableApp(2) {
  implicit val timeout = Timeout(5 seconds)
}


