import com.twitter.util.{Await, Future}
import java.net.InetSocketAddress

import algorithms.sequences.{FibonacciFinder, MatricesFibonacciFinder}
import io.finch._
import io.finch.request._
import io.finch.response._
import com.twitter.finagle._
import com.twitter.finagle.Httpx
import com.twitter.finagle.httpx._
import com.twitter.finagle.httpx.path.{Path, _}

object WebServer extends App {

  Await.ready(
    Httpx.serve(
      new InetSocketAddress("localhost", 9090),
      MyEndpoint))

}

object MyEndpoint extends Endpoint[HttpRequest, HttpResponse] {

  def route = {
//    case Method.Get -> Root / "sequences" / String =>
//      //curl 'http://localhost:9090/hello/Andrea'
//      Service.mk(req =>
//        Ok(s"Skipping sequence ${}").toFuture
//      )
    case Method.Get -> Root / "sequences" / "fibonacci" => SequenceEndPoint

//    case Method.Post -> Root / "user" =>
//      //curl -X POST 'http://localhost:9090/user?name=Andrea&age=65'
//      Service.mk(req => {
//        for {
//          name <- RequiredParam("name")(req)
//          age <- RequiredParam("age")(req).map(_.toInt)
//        } yield {
//          val user = User(name, age)
//          Ok(s"Hello ${user.greet}")
//        }
//      })
    case _ -> path =>
      Service.mk(req =>
        BadRequest(s"Service not found for path: ${path.toString.replace(Root.toString, "")}").toFuture
      )
  }
}

object SequenceEndPoint extends Endpoint[HttpRequest, HttpResponse] {
  val fibonacciFinder: FibonacciFinder = new MatricesFibonacciFinder()

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