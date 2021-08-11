/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.internalheader.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.internalheader.InternalHeader

import scala.util.Try

object hmrcInternalHeaderIntegrationSpec
    extends TemplateIntegrationSpec[InternalHeader, HmrcInternalHeader](
      hmrcComponentName = "hmrcInternalHeader",
      seed = None
    ) {

  override def render(internalHeader: InternalHeader): Try[HtmlFormat.Appendable] =
    Try(component(internalHeader))
}
