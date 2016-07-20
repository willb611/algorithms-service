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

