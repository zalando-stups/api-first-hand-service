
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._


/**
 * This controller is re-generated after each change in the specification.
 * Please only place your hand-written code between appropriate comments in the body of the controller.
 */

package security.api.yaml {

    class SecurityApiYaml extends SecurityApiYamlBase {
        val getPetsById = getPetsByIdAction { input: (PetsIdGetId, PetsIdGetCount) =>
            val (id, count) = input
            // ----- Start of unmanaged code area for action  SecurityApiYaml.getPetsById
            val nonPositiveId = id.find(_.toInt <= 0)
            nonPositiveId.map { npId: String =>
                Failure(new NoSuchElementException(npId))
            }.getOrElse {
                val result: Seq[Pet] = Seq.empty[Pet]
                Success(200, result)
            }
            // ----- End of unmanaged code area for action  SecurityApiYaml.getPetsById
        }
    
    }
}
