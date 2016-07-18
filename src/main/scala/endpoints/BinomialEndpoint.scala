package endpoints

import algorithms.BinomialCoefficient
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.Method
import com.twitter.finagle.httpx.path.{->, /, Path, Root}
import io.finch.request.RequiredParam
import io.finch.response._
import io.finch.{Endpoint, _}

object BinomialEndpoint extends Endpoint[HttpRequest, HttpResponse] {
  val binomialCoefficientFinder = new BinomialCoefficient()

  override def route: PartialFunction[(Method, Path), Service[HttpRequest, HttpResponse]] = {
    case Method.Get -> Root / "binomial" / "coefficient" => Service.mk(req => {
      for {
        n <- RequiredParam("n")(req).map(_.toInt)
        k <- RequiredParam("k")(req).map(_.toInt)
      } yield {
        val result = BinomialCoefficient.nChooseK(n, k)
        Ok(s"$result")
      }
    })
  }
}
