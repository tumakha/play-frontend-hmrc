import sbt._
import play.api.libs.json.{JsObject, Json}
import scalaj.http.{Http, HttpOptions, HttpRequest}

import scala.util.{Failure, Success, Try}

case class GenerateFixtures(fixturesDir: File, frontend: String, version: String) {
  private val rendererPort: String = "3000"
  private val baseUrl: String      = s"http://localhost:$rendererPort"

  def generate(): Unit = {
    generateTestFixtures()
    updateAdditionalFixtures()
  }

  private def getExamples(): Seq[JsObject] = {
    val endpoint: String = s"$baseUrl/snapshot/$frontend/$version"

    val attempt: Try[Seq[JsObject]] = Try {
      val response = http(endpoint).asString.body

      Json.parse(response).as[Seq[JsObject]]
    }

    attempt match {
      case Success(snapshot) => snapshot
      case Failure(e)        =>
        println(s"Failed to fetch snapshot from Template Service at $endpoint. Details: [${e.getLocalizedMessage}].")
        Seq()
    }
  }

  private def generateTestFixtures(): Unit = {
    val testFixturesDir =
      fixturesDir / s"test-fixtures"

    IO.delete(testFixturesDir)
    IO.createDirectory(testFixturesDir)

    IO.write(testFixturesDir / "VERSION.txt", version)

    val patchedFixturesDir                                     = fixturesDir / s"patched-fixtures"
    def patchedVersionDoesNotExist(example: JsObject): Boolean =
      !(patchedFixturesDir / (example \ "exampleId").as[String]).isDirectory

    for (example <- getExamples() if patchedVersionDoesNotExist(example)) {
      val componentName = (example \ "componentName").as[String]
      val componentJson = Json.obj(
        "name" -> componentName
      )
      val inputJson     = (example \ "input").as[JsObject]
      val exampleDir    = testFixturesDir / (example \ "exampleId").as[String]

      IO.createDirectory(exampleDir)
      IO.write(exampleDir / "input.json", Json.prettyPrint(inputJson))
      IO.write(
        exampleDir / "component.json",
        Json.prettyPrint(componentJson)
      )
      IO.write(exampleDir / "output.txt", (example \ "output").as[String])
    }
  }

  private def updateAdditionalFixtures(): Unit =
    for (exampleDirectory <- IO.listFiles(fixturesDir / s"additional-fixtures")) {
      val input     = IO.read(exampleDirectory / "input.json")
      val component = IO.read(exampleDirectory / "component.json")
      val name      = (Json.parse(component) \ "name").as[String]

      IO.write(exampleDirectory / "output.txt", getOutput(input, name))
    }

  private def getOutput(input: String, name: String): String = {
    val endpoint: String =
      if (name == "govukTemplate") s"$baseUrl/template/$frontend/$version/default"
      else s"$baseUrl/component/$frontend/$version/$name"

    val attempt: Try[String] = Try {
      val response = http(endpoint)
        .header("content-type", "application/json")
        .postData(input)
        .asString
        .body

      response
    }

    attempt match {
      case Success(output) => output
      case Failure(e)      =>
        println(
          s"Failed to fetch output for $name from Template Service at $endpoint. Details: [${e.getLocalizedMessage}]."
        )
        ""
    }
  }

  private def http(endpoint: String): HttpRequest =
    Http(endpoint)
      .option(HttpOptions.connTimeout(2000))
      .option(HttpOptions.readTimeout(5000))
}
