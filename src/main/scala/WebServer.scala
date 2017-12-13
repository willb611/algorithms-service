import java.net.InetSocketAddress

import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.finagle.{Httpx, _}
import com.twitter.util.Await
import services.{BinomialService, FibonacciSequenceService}
import io.finch.route.Get

object WebServer extends App {
  val portNumber: Int = 9090
  val binomialService = Get /  "binomial" / "coefficient" /> BinomialService
  val sequenceService = Get / "sequences" / "fibonacci" /> FibonacciSequenceService

  val myEndpoint: Service[Request, Response] =
    (binomialService :+: sequenceService).toService

  println(s"Starting server at localhost port: $portNumber")
  Await.ready(
    Httpx.serve(
      new InetSocketAddress("localhost", portNumber),
      myEndpoint))

}

