import de.zalando.play.controllers.PlayBodyParsing
import play.api._
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

/**
  * The purpose of this object is to override default play's error reporting with application/json content type.
  */
object Global extends GlobalSettings {

  private def contentType(request: RequestHeader): String =
    request.acceptedTypes.map(_.toString).filterNot(_ == "text/html").headOption.getOrElse("application/json")

  // called when a route is found, but it was not possible to bind the request parameters
  override def onBadRequest(request: RequestHeader, error: String): Future[Result] = {
    implicit val writer = PlayBodyParsing.anyToWritable[String](contentType(request))
    Future.successful(BadRequest("Bad Request: " + error))
  }

  // 500 - internal server error
  override def onError(request: RequestHeader, throwable: Throwable): Future[Result] = {
    implicit val writer = PlayBodyParsing.anyToWritable[Throwable](contentType(request))
    Future.successful(InternalServerError(throwable))
  }

  // 404 - page not found error
  override def onHandlerNotFound(request: RequestHeader): Future[Result] = {
    implicit val writer = PlayBodyParsing.anyToWritable[String](contentType(request))
    Future.successful(NotFound(request.path))
  }

}
