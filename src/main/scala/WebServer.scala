import java.net.InetSocketAddress

import com.twitter.finagle.{Httpx, _}
import com.twitter.util.Await
import services.{BinomialService, FibonacciSequenceService}
import io.finch.route.Get

object WebServer extends App {

  val myEndpoint =
    (Get /  "binomial" / "coefficient" /> BinomialService) |
      (Get / "sequences" / "fibonacci" /> FibonacciSequenceService)

  Await.ready(
    Httpx.serve(
      new InetSocketAddress("localhost", 9090),
      myEndpoint))

}


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
//    case _ -> path =>
//    Service.mk(req =>
//      BadRequest(s"Service not found for path: ${path.toString.replace(Root.toString, "")}").toFuture
//    )
//}

