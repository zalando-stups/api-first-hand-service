package example.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.{ArrayWrapper, ResponseWriters}
import play.api.Play
import play.api.libs.json.JsValue


import scala.math.BigInt

object SecurityExtractorsExecutionContext {
    // this ExecutionContext might be overriden if default configuration is not suitable for some reason
    implicit val ec = de.zalando.play.controllers.Contexts.tokenChecking
}

trait SecurityExtractors {
    def internalOAuth_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)("http://localhost:9000/example/token")(header) { (token: play.api.libs.json.JsValue) =>
            userFromToken(token)
    }
    implicit val unauthorizedContentWriter = ResponseWriters.choose[String]("application/json")
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized("Unauthorized")

    private def userFromToken[User >: Any](token: JsValue): User = {
        val user = (token \ "username").get.as[String]
        val scopes = (token \ "scopes").get.as[Seq[String]]
        TokenPostResponses200(user, scopes)
    }
}
