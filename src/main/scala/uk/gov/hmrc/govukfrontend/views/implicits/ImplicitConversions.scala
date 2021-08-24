/*
 * Copyright 2021 HM Revenue & Customs
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

import scala.language.implicitConversions
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.CheckboxItem
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{ActionItem, Actions, Key}
import uk.gov.hmrc.govukfrontend.views.viewmodels.table.HeadCell
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

trait ImplicitConversions {
  implicit def stringToHint(string: String)(implicit messages: Messages): Hint = Hint(content = Text(messages(string)))

  implicit def stringToText(string: String)(implicit messages: Messages): Content =
    Text(messages(string))

  implicit def stringToKey(string: String)(implicit messages: Messages): Key =
    Key(content = Text(messages(string)))

  implicit def stringToHeadCell(string: String)(implicit messages: Messages): HeadCell =
    HeadCell(content = Text(messages(string)))

  implicit def legendToFieldset(legend: Legend): Fieldset =
    Fieldset(
      legend = Some(legend)
    )

  implicit def stringToFieldset(string: String)(implicit messages: Messages): Fieldset =
    Fieldset(
      legend = Some(
        Legend(
          content = Text(messages(string))
        )
      )
    )

  implicit def stringToTag(string: String)(implicit messages: Messages): Tag =
    Tag(
      content = Text(messages(string))
    )

  implicit def stringToLabel(string: String)(implicit messages: Messages): Label =
    Label(
      content = Text(messages(string))
    )

  implicit def actionItemToActionItems(actionItem: ActionItem): Actions = Actions(items =
    Seq(
      actionItem
    )
  )

  implicit def tuplesToRadioItems(items: Seq[(String, String)])(implicit messages: Messages): Seq[RadioItem] =
    items map tupleToRadioItem

  implicit def tupleToRadioItem(tuple: (String, String))(implicit messages: Messages): RadioItem =
    RadioItem(
      content = Text(messages(tuple._2)),
      value = Some(tuple._1)
    )

  implicit def tuplesToCheckboxItems(items: Seq[(String, String)])(implicit messages: Messages): Seq[CheckboxItem] =
    items map { case (value: String, label: String) =>
      CheckboxItem(
        content = Text(label),
        value = messages(value)
      )
    }
}
