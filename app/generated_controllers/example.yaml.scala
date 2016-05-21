
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._

import de.zalando.play.controllers.Base64String

import Base64String._

import scala.math.BigInt

import play.api.mvc.{Action, Controller, Results}
import java.net.URLEncoder
import play.api.http.DefaultWriteables

/**
 * This controller is re-generated after each change in the specification.
 * Please only place your hand-written code between appropriate comments in the body of the controller.
 */

package example.yaml {

    class ExampleYaml extends ExampleYamlBase {
        val getUserTodos = getUserTodosAction { input: (BigInt, TodosUser_idGetCount) =>
            val (user_id, count) = input
            // ----- Start of unmanaged code area for action  ExampleYaml.getUserTodos
            GetUserTodos200(Nil)
            // ----- End of unmanaged code area for action  ExampleYaml.getUserTodos
        }
    
    }
}
package example.yaml {

    class TokenService extends TokenServiceBase {
        val tokenGet = tokenGetAction { input: (String, String, String, TokenGetState) =>
            val (redirect_uri, scope, response_type, state) = input
            // ----- Start of unmanaged code area for action  TokenService.tokenGet
            val statePart = state map { s => s"""state=${URLEncoder.encode(s, "UTF-8")}""" } getOrElse ""
            val fullUrl = s"$redirect_uri?$statePart&access_token=abracadabra"
            val loginSuccessfull = true
            if (loginSuccessfull)
              TokenGet301(fullUrl)
            else
              TokenGet401
            // ----- End of unmanaged code area for action  TokenService.tokenGet
        }
        val tokenPost = tokenPostAction { (token: Base64String) =>  
            // ----- Start of unmanaged code area for action  TokenService.tokenPost
            // Use implicit conversion to decode the token
            val tokenValue: String = token
            if ("abracadabra" == tokenValue) TokenPost200(TokenPostResponses200("the name of the user", Seq("admin:org")))
            else TokenPost401("Wrong token: " + tokenValue)
            // ----- End of unmanaged code area for action  TokenService.tokenPost
        }
    
    }
}
