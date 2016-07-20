package services

import algorithms.BinomialCoefficient
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.util.Future
import io.finch.request.param
import io.finch.response._

object BinomialService extends Service[Request, Response] {

  override def apply(request: Request): Future[Response] = {
    for {
      n <- param("n")(request).map(_.toInt)
      k <- param("k")(request).map(_.toInt)
    } yield {
      val result = BinomialCoefficient.nChooseK(n, k)
      Ok(s"$result")
    }
  }
}
