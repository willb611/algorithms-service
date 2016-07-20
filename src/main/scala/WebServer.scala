import java.net.InetSocketAddress

import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.finagle.{Httpx, _}
import com.twitter.util.Await
import services.{BinomialService, FibonacciSequenceService}
import io.finch.route.Get

object WebServer extends App {

  val binomialService = Get /  "binomial" / "coefficient" /> BinomialService
  val sequenceService = Get / "sequences" / "fibonacci" /> FibonacciSequenceService

  val myEndpoint: Service[Request, Response] =
    (binomialService :+: sequenceService).toService

  Await.ready(
    Httpx.serve(
      new InetSocketAddress("localhost", 9090),
      myEndpoint))

}

