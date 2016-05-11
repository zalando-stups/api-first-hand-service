
package security.api.yaml

import scala.concurrent.Future
import play.api.mvc._
import de.zalando.play.controllers.SwaggerSecurityExtractors._
import de.zalando.play.controllers.{ArrayWrapper, ResponseWriters}
import play.api.libs.json.JsValue

case class User(name: String, scopes: Set[String])

object SecurityExtractorsExecutionContext {
  // TODO override with proper ExecutionContext instance
  implicit val ec = scala.concurrent.ExecutionContext.global
}

trait SecurityExtractors {
    def petstoreImplicit_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)("http://petstore.swagger.wordnik.com/oauth/dialog")(header) { (token: play.api.libs.json.JsValue) =>
          userFromToken(token)
    }

    def githubAccessCode_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)("https://github.com/login/oauth/access_token")(header) { (token: play.api.libs.json.JsValue) =>
            userFromToken(token)
    }
    def petstorePassword_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)("http://petstore.swagger.wordnik.com/oauth/dialog")(header) { (token: play.api.libs.json.JsValue) =>
            userFromToken(token)
    }
    def justBasicStuff_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => basicAuth(header) { (username: String, password: String) =>
            if (username == "admin" && password == "pass") User("admin", Set.empty[String])
    }
    def petstoreApplication_Extractor[User >: Any](scopes: String*): RequestHeader => Future[Option[User]] =
        header => oAuth(scopes)("http://petstore.swagger.wordnik.com/oauth/token")(header) { (token: play.api.libs.json.JsValue) =>
            userFromToken(token)
    }
    def internalApiKey_Extractor[User >: Any](): RequestHeader => Future[Option[User]] =
        header => headerApiKey("api_key")(header) { (apiKey: String) =>
            if (apiKey == "Please, please let me in...") User("secret_user", Set.empty[String])
    }
    implicit val unauthorizedContentWriter = ResponseWriters.choose[String]("application/json")
    def unauthorizedContent(req: RequestHeader) = Results.Unauthorized("Unauthorized")

    private def userFromToken[User >: Any](token: JsValue): User = {
        val user = (token \ "username").get.as[String]
        val scopes = (token \ "scopes").get.as[Set[String]]
        User(user, scopes)
    }

}
