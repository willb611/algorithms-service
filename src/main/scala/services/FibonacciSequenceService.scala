package services

import algorithms.sequences.{CachingFibonacciFinder, FibonacciFinder}
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.util.Future
import io.finch.request.param
import io.finch.response._

object FibonacciSequenceService extends Service[Request, Response] {
  val fibonacciFinder: FibonacciFinder = new CachingFibonacciFinder()

  override def apply(request: Request): Future[Response] = {
    for {
      termNumber <- param("termNumber")(request).map(_.toInt)
    } yield {
      val result = fibonacciFinder.findNthTermInSequence(termNumber).intValue()
      Ok(s"$result")
    }
  }
}
