package endpoints

import algorithms.sequences.{CachingFibonacciFinder, FibonacciFinder}
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.Method
import com.twitter.finagle.httpx.path.{->, /, Root}
import io.finch.request.RequiredParam
import io.finch.response._
import io.finch.{Endpoint, _}

object FibonacciSequenceEndPoint extends Endpoint[HttpRequest, HttpResponse] {
  val fibonacciFinder: FibonacciFinder = new CachingFibonacciFinder()

  override def route = {
    case Method.Get -> Root / "sequences" / "fibonacci" => Service.mk(req => {
        for {
          termNumber <- RequiredParam("n")(req).map(_.toInt)
        } yield {
          val result = fibonacciFinder.findNthTermInSequence(termNumber).intValue()
          Ok(s"$result")
        }
    })
  }
}
