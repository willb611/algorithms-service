package services

import algorithms.BinomialCoefficient
import com.twitter.finagle.Service
import com.twitter.util.Future
import io.finch.request.param
import io.finch.response._
import io.finch._

object BinomialService extends Service[HttpRequest, HttpResponse] {
  val binomialCoefficientFinder = new BinomialCoefficient()

  override def apply(request: HttpRequest): Future[HttpResponse] = {
    for {
      n <- param("n")(request).map(_.toInt)
      k <- param("k")(request).map(_.toInt)
    } yield {
      val result = BinomialCoefficient.nChooseK(n, k)
      Ok(s"$result")
    }
  }
}
