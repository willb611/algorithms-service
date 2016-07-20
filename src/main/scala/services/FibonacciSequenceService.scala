package services

import algorithms.sequences.{CachingFibonacciFinder, FibonacciFinder}
import com.twitter.finagle.Service
import com.twitter.util.Future
import io.finch.request.RequiredParam
import io.finch.response._
import io.finch._

object FibonacciSequenceService extends Service[HttpRequest, HttpResponse] {
  val fibonacciFinder: FibonacciFinder = new CachingFibonacciFinder()

  override def apply(request: HttpRequest): Future[HttpResponse] = {
    for {
      termNumber <- RequiredParam("termNumber")(request).map(_.toInt)
    } yield {
      val result = fibonacciFinder.findNthTermInSequence(termNumber).intValue()
      Ok(s"$result")
    }
  }
}
