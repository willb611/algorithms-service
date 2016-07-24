package services

import algorithms.sequences.{FibonacciFinder, MatricesFibonacciFinder}
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.util.Future
import io.finch.request.param
import io.finch.response._

object FibonacciSequenceService extends Service[Request, Response] {
  val fibonacciFinder: FibonacciFinder = new MatricesFibonacciFinder()

  override def apply(request: Request): Future[Response] = {
    for {
      termNumber <- param("termNumber")(request).map(_.toInt)
    } yield {
      val result = fibonacciFinder.findNthTermInSequence(termNumber)
      Ok(s"$result")
    }
  }
}
