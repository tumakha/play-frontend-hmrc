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
package footer

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Meta(
  visuallyHiddenTitle: Option[String] = None,
  content: Content = Empty,
  //FIXME Option[Seq[T]] is used to represent the 3 possible types of values of javascript arrays: undefined, non-empty array, and empty array
  // once https://github.com/alphagov/govuk-frontend/issues/1618 is solved we can think of a better type
  items: Option[Seq[FooterItem]] = None
)

object Meta {

  implicit val reads: Reads[Meta] = (
    (__ \ "visuallyHiddenTitle").readNullable[String] and
      Content.reads and
      (__ \ "items").readNullable[Seq[FooterItem]]
  )(Meta.apply _)

  implicit val writes: OWrites[Meta] = (
    (__ \ "visuallyHiddenTitle").writeNullable[String] and
      Content.writes and
      (__ \ "items").writeNullable[Seq[FooterItem]]
  )(unlift(Meta.unapply))
}
