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

package uk.gov.hmrc.govukfrontend.views.implicits

import play.api.data.{Field, FormError}
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Legend
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcPageHeadingLegend, HmrcSectionCaption}

trait ImplicitsSupport[T] {

  implicit val messages: Messages

  def withFormField(field: Field): T

  def withFormFieldWithErrorAsHtml(field: Field): T

  protected[views] def withStringProperty(propFromField: String, currentProp: String, currentFormInput: T)(
    update: (T, String) => T
  ): T =
    withProperty[String, T](
      propertyFromField = propFromField,
      propertyFromUnderlying = currentProp,
      default = "",
      formInput = currentFormInput
    )(update)

  protected[views] def withOptStringProperty(
    propFromField: Option[String],
    currentProp: Option[String],
    currentFormInput: T
  )(update: (T, Option[String]) => T): T =
    withProperty[Option[String], T](
      propertyFromField = propFromField,
      propertyFromUnderlying = currentProp,
      default = None,
      formInput = currentFormInput
    )(update)

  protected[views] def withOptTextErrorMessageProperty(
    formError: Option[FormError],
    currentProp: Option[ErrorMessage],
    currentFormInput: T
  )(update: (T, Option[ErrorMessage]) => T): T =
    withProperty[Option[ErrorMessage], T](
      propertyFromField = formErrorToTextErrorMessage(formError),
      propertyFromUnderlying = currentProp,
      default = None,
      formInput = currentFormInput
    )(update)

  protected[views] def withOptHtmlErrorMessageProperty(
    formError: Option[FormError],
    currentProp: Option[ErrorMessage],
    currentFormInput: T
  )(update: (T, Option[ErrorMessage]) => T): T =
    withProperty[Option[ErrorMessage], T](
      propertyFromField = formErrorToHtmlErrorMessage(formError),
      propertyFromUnderlying = currentProp,
      default = None,
      formInput = currentFormInput
    )(update)

  protected def withHeadingLabel[A](original: A, headingContent: Content, captionContent: Option[Content])(
    update: (A, Label) => A
  ): A = {
    val heading = HmrcPageHeadingLabel(
      content = headingContent,
      caption = captionContent.map(cc => HmrcSectionCaption(cc)).getOrElse(Empty)
    )
    update(original, heading)
  }

  protected def withHeadingLegend[A](original: A, headingContent: Content, captionContent: Option[Content])(
    update: (A, Legend) => A
  ): A = {
    val heading = HmrcPageHeadingLegend(
      content = headingContent,
      caption = captionContent.map(cc => HmrcSectionCaption(cc)).getOrElse(Empty)
    )
    update(original, heading)
  }

  private[views] def withProperty[A, T](propertyFromField: A, propertyFromUnderlying: A, default: A, formInput: T)(
    update: (T, A) => T
  ): T =
    if (propertyFromUnderlying == default) update(formInput, propertyFromField)
    else formInput

  private def formErrorToTextErrorMessage(formError: Option[FormError]): Option[ErrorMessage] =
    formError.map(formError =>
      errorMessageWithDefaultStringsTranslated(content = Text(messages(formError.message, formError.args: _*)))
    )

  private def formErrorToHtmlErrorMessage(formError: Option[FormError]): Option[ErrorMessage] =
    formError.map(formError =>
      errorMessageWithDefaultStringsTranslated(content = HtmlContent(messages(formError.message, formError.args: _*)))
    )
}
