/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views
package html

import play.api.Play
import uk.gov.hmrc.govukfrontend.views.html.components.{GovukErrorMessage, GovukHint, GovukLabel}
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.hmrcFooterHelper
import uk.gov.hmrc.hmrcfrontend.config.AccessibilityStatementConfig
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcFooterItems => HmrcFooterItemsType}

package object components extends Utils with Aliases {

  /**
    * Top-level implicits for all components
    */
  object implicits extends Implicits

  private lazy val accessibilityStatementConfig = Play.current.injector.instanceOf[AccessibilityStatementConfig]

  type HmrcAccountMenu = hmrcAccountMenu
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcAccountMenu = new hmrcAccountMenu(HmrcNotificationBadge)

  type HmrcBanner = hmrcBanner
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcBanner = new hmrcBanner()

  type HmrcHeader = hmrcHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcHeader = new hmrcHeader(HmrcBanner)

  type HmrcFooter = hmrcFooter
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooter = new hmrcFooter()

  type HmrcFooterItems = HmrcFooterItemsType
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooterItems = new HmrcFooterItems(accessibilityStatementConfig)

  type HmrcFooterHelper = hmrcFooterHelper
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooterHelper = new hmrcFooterHelper(HmrcFooter, HmrcFooterItems)

  type HmrcInternalHeader = hmrcInternalHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcInternalHeader = new hmrcInternalHeader()

  type HmrcLanguageSelect = hmrcLanguageSelect
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcLanguageSelect = new hmrcLanguageSelect()

  type HmrcNewTabLink = hmrcNewTabLink
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcNewTabLink = new hmrcNewTabLink()

  type HmrcNotificationBadge = hmrcNotificationBadge
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcNotificationBadge = new hmrcNotificationBadge()

  type HmrcPageHeading = hmrcPageHeading
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcPageHeading = new hmrcPageHeading()

  type HmrcTimeoutDialog = hmrcTimeoutDialog
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcTimeoutDialog = new hmrcTimeoutDialog()

  type HmrcReportTechnicalIssue = hmrcReportTechnicalIssue
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcReportTechnicalIssue = new hmrcReportTechnicalIssue()

  type HmrcCurrencyInput = hmrcCurrencyInput
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcCurrencyInput = new hmrcCurrencyInput(GovukErrorMessage, GovukHint, GovukLabel)
}
