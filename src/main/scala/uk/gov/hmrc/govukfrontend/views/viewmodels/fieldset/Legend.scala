/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.views.viewmodels
package fieldset

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Legend(
  content: Content = Empty,
  classes: String = "",
  isPageHeading: Boolean = false
)

object Legend {

  def defaultObject: Legend = Legend()

  implicit def jsonReads: Reads[Legend] =
    (
      Content.reads and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "isPageHeading").readWithDefault[Boolean](defaultObject.isPageHeading)
    )(Legend.apply _)

  implicit def jsonWrites: OWrites[Legend] =
    (
      Content.writes and
        (__ \ "classes").write[String] and
        (__ \ "isPageHeading").write[Boolean]
    )(unlift(Legend.unapply))

}
