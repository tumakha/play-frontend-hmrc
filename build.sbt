import play.sbt.PlayImport.PlayKeys._

val libName         = "play-frontend-hmrc"
val silencerVersion = "1.7.2"

lazy val IntegrationTest = config("it") extend Test

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtTwirl, BuildInfoPlugin)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 3,
    scalaVersion := "2.12.13",
    libraryDependencies ++= LibDependencies(),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    isPublicArtefact := true,
    // ***************
    // Use the silencer plugin to suppress warnings from unused imports in compiled twirl templates
    scalacOptions += "-P:silencer:pathFilters=views;routes",
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    ),
    (generateGovukFixtures in Test) := {
      val generateFixtures = GenerateFixtures(
        fixturesDir = baseDirectory.value / "src/test/resources/fixtures/govuk-frontend",
        frontend = "govuk",
        version = LibDependencies.govukFrontendVersion
      )
      generateFixtures.generate()
    },
    (generateHmrcFixtures in Test) := {
      val generateFixtures = GenerateFixtures(
        fixturesDir = baseDirectory.value / "src/test/resources/fixtures/hmrc-frontend",
        frontend = "hmrc",
        version = LibDependencies.hmrcFrontendVersion
      )
      generateFixtures.generate()
    },
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    unmanagedResourceDirectories in Test ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion"          -> PlayCrossCompilation.playVersion,
      "govukFrontendVersion" -> LibDependencies.govukFrontendVersion,
      "hmrcFrontendVersion"  -> LibDependencies.hmrcFrontendVersion,
      sources in (Compile, TwirlKeys.compileTemplates)
    ),
    buildInfoPackage := "hmrcfrontendbuildinfo"
  )
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(inConfig(IntegrationTest)(org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings))

lazy val templateImports: Seq[String] = Seq(
  "_root_.play.twirl.api.Html",
  "_root_.play.twirl.api.HtmlFormat",
  "_root_.play.twirl.api.JavaScript",
  "_root_.play.twirl.api.Txt",
  "_root_.play.twirl.api.Xml",
  "play.api.mvc._",
  "play.api.data._",
  "play.api.i18n._",
  "play.api.templates.PlayMagic._",
  "uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._",
  "_root_.play.twirl.api.TwirlFeatureImports._",
  "_root_.play.twirl.api.TwirlHelperImports._"
)

lazy val generateGovukFixtures = taskKey[Unit]("Generate unit test fixtures for GOV.UK")
lazy val generateHmrcFixtures  = taskKey[Unit]("Generate unit test fixtures for HMRC")
