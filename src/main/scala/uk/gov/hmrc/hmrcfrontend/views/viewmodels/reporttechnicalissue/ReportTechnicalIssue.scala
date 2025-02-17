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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.reporttechnicalissue

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}

case class ReportTechnicalIssue(
  serviceId: String = "",
  serviceCode: String = "",
  language: Language = En,
  classes: Option[String] = None,
  referrerUrl: Option[String] = None,
  baseUrl: Option[String] = None
)

object ReportTechnicalIssue {

  implicit def jsonFormats: OFormat[ReportTechnicalIssue] =
    Json.using[Json.WithDefaultValues].format[ReportTechnicalIssue]
}
