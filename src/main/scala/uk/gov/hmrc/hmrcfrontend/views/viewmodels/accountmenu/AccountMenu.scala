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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}

case class AccountMenu(
  accountHome: AccountHome = AccountHome(),
  messages: AccountMessages = AccountMessages(),
  checkProgress: CheckProgress = CheckProgress(),
  yourProfile: YourProfile = YourProfile(),
  businessTaxAccount: Option[BusinessTaxAccount] = None,
  signOut: SignOut = SignOut(),
  language: Language = En
)

object AccountMenu {

  implicit def jsonFormats: OFormat[AccountMenu] = Json.using[Json.WithDefaultValues].format[AccountMenu]
}
