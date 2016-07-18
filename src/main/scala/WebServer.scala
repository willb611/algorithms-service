import java.net.InetSocketAddress

import com.twitter.finagle.{Httpx, _}
import com.twitter.finagle.httpx._
import com.twitter.finagle.httpx.path.{Path, _}
import com.twitter.util.Await
import endpoints.{BinomialEndpoint, FibonacciSequenceEndPoint}
import io.finch._
import io.finch.response._

object WebServer extends App {

  Await.ready(
    Httpx.serve(
      new InetSocketAddress("localhost", 9090),
      MyEndpoint))

}

object MyEndpoint extends Endpoint[HttpRequest, HttpResponse] {

  def route: PartialFunction[(Method, Path), Service[HttpRequest, HttpResponse]] = {
    case Method.Get -> Root / "binomial" / "coefficient" => BinomialEndpoint
    case Method.Get -> Root / "sequences" / "fibonacci" => FibonacciSequenceEndPoint

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

