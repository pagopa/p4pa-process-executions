import java.util.*
import com.github.jk1.license.render.*
import com.github.jk1.license.filter.*

plugins {
  java
  id("org.springframework.boot") version "4.0.0"
  id("io.spring.dependency-management") version "1.1.7"
  jacoco
  id("org.sonarqube") version "7.2.1.6560"
  id("com.github.ben-manes.versions") version "0.53.0"
  id("org.openapi.generator") version "7.17.0"
  id("org.ajoberstar.grgit") version "5.3.2"
  id("com.gorylenko.gradle-git-properties") version "2.5.4"
  id("com.github.jk1.dependency-license-report") version "3.0.1"
}

group = "it.gov.pagopa.payhub"
version = "0.0.1"
description = "p4pa-process-executions"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
  compileClasspath {
    resolutionStrategy.activateDependencyLocking()
  }
}

licenseReport {
  renderers =
    arrayOf(XmlReportRenderer("third-party-libs.xml", "Back-End Libraries"))
  outputDir = "$projectDir/dependency-licenses"
  filters = arrayOf(SpdxLicenseBundleNormalizer())
}
tasks.classes {
  finalizedBy(tasks.generateLicenseReport)
}

repositories {
  mavenCentral()
}

val springDocOpenApiVersion = "3.0.0"
val janinoVersion = "3.1.12"
val openApiToolsVersion = "0.2.8"
val micrometerVersion = "1.6.1"
val bouncycastleVersion = "1.83"
val postgresJdbcVersion = "42.7.8"
val httpClientVersion = "5.5.1"
val commonsLang3Version = "3.20.0"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-webmvc")
  implementation("org.springframework.boot:spring-boot-starter-opentelemetry")
  implementation("org.springframework.boot:spring-boot-starter-restclient")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-hateoas")
  implementation("org.springframework.boot:spring-boot-starter-data-rest")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("io.micrometer:micrometer-tracing-bridge-otel:$micrometerVersion")
  implementation("io.micrometer:micrometer-registry-prometheus")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion") {
    exclude(group = "org.apache.commons", module = "commons-lang3")
  }
  implementation("org.apache.commons:commons-lang3:$commonsLang3Version")
  implementation("org.codehaus.janino:janino:$janinoVersion")
  implementation("org.openapitools:jackson-databind-nullable:$openApiToolsVersion")
  implementation("org.bouncycastle:bcprov-jdk18on:$bouncycastleVersion")
  implementation("org.postgresql:postgresql:$postgresJdbcVersion")
  implementation("org.apache.httpcomponents.client5:httpclient5:$httpClientVersion")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  //	Testing
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("org.springframework.boot:spring-boot-starter-security-test")
  testImplementation("org.mockito:mockito-core")
  testImplementation("org.projectlombok:lombok")
  testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
  useJUnitPlatform()
  finalizedBy(tasks.jacocoTestReport)
}

val mockitoAgent = configurations.create("mockitoAgent")
dependencies {
  mockitoAgent("org.mockito:mockito-core") { isTransitive = false }
}
tasks {
  test {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
  }
}

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required = true
  }
}

val projectInfo = mapOf(
  "artifactId" to project.name,
  "version" to project.version
)

tasks {
  val processResources by getting(ProcessResources::class) {
    filesMatching("**/application.yml") {
      expand(projectInfo)
    }
  }
}

tasks.compileJava {
  dependsOn("dependenciesBuild")
}

tasks.register("dependenciesBuild") {
  group = "AutomaticallyGeneratedCode"
  description = "grouping all together automatically generate code tasks"

  dependsOn(
    "openApiGeneratePROCESSEXECUTIONS",
    "openApiGenerateWORKFLOWHUB",
    "openApiGenerateCLASSIFICATIONS"
  )
}

configure<SourceSetContainer> {
  named("main") {
    java.srcDir("$projectDir/build/generated/src/main/java")
  }
}

springBoot {
  buildInfo()
  mainClass.value("it.gov.pagopa.pu.processecexutions.ProcessExecutionsApplication")
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGeneratePROCESSEXECUTIONS") {
  group = "openapi"
  description = "description"

  generatorName.set("spring")
  inputSpec.set("$rootDir/openapi/p4pa-process-executions.openapi.yaml")
  outputDir.set("$projectDir/build/generated")
  apiPackage.set("it.gov.pagopa.pu.processecexutions.controller.generated")
  modelPackage.set("it.gov.pagopa.pu.processecexutions.dto.generated")
  typeMappings.set(
    mapOf(
      "IngestionFlowFileType" to "it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum",
      "IngestionFlowFileStatusEnum" to "it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus",
      "PaidExportFileRequestDTO" to "it.gov.pagopa.pu.processecexutions.dto.exportFile.PaidExportFileRequestDTO",
      "ClassificationsExportFileRequestDTO" to "it.gov.pagopa.pu.processecexutions.dto.exportFile.ClassificationsExportFileRequestDTO",
      "PaymentsReportingExportFileRequestDTO" to "it.gov.pagopa.pu.processecexutions.dto.exportFile.PaymentsReportingExportFileRequestDTO",
      "ReceiptsArchivingExportFileRequestDTO" to "it.gov.pagopa.pu.processecexutions.dto.exportFile.ReceiptsArchivingExportFileRequestDTO",
      "ExportFileType" to "it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum",
      "ExportFileStatusEnum" to "it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus",
      "ExportFileTypeVersions" to "it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileTypeVersions"
    )
  )
  additionalProperties.set(
    mapOf(
      "removeEnumValuePrefix" to "false"
    )
  )
  configOptions.set(
    mapOf(
      "dateLibrary" to "java8",
      "requestMappingMode" to "api_interface",
      "useSpringBoot3" to "true",
      "interfaceOnly" to "true",
      "useTags" to "true",
      "useBeanValidation" to "true",
      "generateConstructorWithAllArgs" to "true",
      "generatedConstructorWithRequiredArgs" to "true",
      "additionalModelTypeAnnotations" to "@lombok.experimental.SuperBuilder"
    )
  )
}

var targetEnv = when (Objects.requireNonNullElse(
  System.getProperty("targetBranch"),
  grgit.branch.current().name
)) {
  "uat" -> "uat"
  "main" -> "main"
  else -> "develop"
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerateWORKFLOWHUB") {
  group = "openapi"
  description = "description"

  generatorName.set("java")
  remoteInputSpec.set("https://raw.githubusercontent.com/pagopa/p4pa-workflow-hub/refs/heads/$targetEnv/openapi/generated.openapi.json")
  outputDir.set("$projectDir/build/generated")
  apiPackage.set("it.gov.pagopa.pu.workflowhub.controller.generated")
  modelPackage.set("it.gov.pagopa.pu.workflowhub.dto.generated")
  importMappings.set(
    mapOf(
      "ExportFileTypeEnum" to "it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum",
      "IngestionFlowFileTypeEnum" to "it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum"
    )
  )
  configOptions.set(
    mapOf(
      "swaggerAnnotations" to "false",
      "openApiNullable" to "false",
      "dateLibrary" to "java8",
      "serializableModel" to "true",
      "useSpringBoot3" to "true",
      "useJakartaEe" to "true",
      "useOneOfInterfaces" to "true",
      "useBeanValidation" to "true",
      "serializationLibrary" to "jackson",
      "generateSupportingFiles" to "true",
      "generateConstructorWithAllArgs" to "true",
      "generatedConstructorWithRequiredArgs" to "true",
      "enumPropertyNaming" to "original",
      "additionalModelTypeAnnotations" to "@lombok.experimental.SuperBuilder"
    )
  )
  library.set("resttemplate")
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerateCLASSIFICATIONS") {
  group = "openapi"
  description = "description"

  generatorName.set("java")
  remoteInputSpec.set("https://raw.githubusercontent.com/pagopa/p4pa-classification/refs/heads/$targetEnv/openapi/generated.openapi.json")
  outputDir.set("$projectDir/build/generated")
  apiPackage.set("it.gov.pagopa.pu.classification.controller.generated")
  modelPackage.set("it.gov.pagopa.pu.classification.dto.generated")
  importMappings.set(
    mapOf(
      "ExportFileTypeEnum" to "it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum",
      "IngestionFlowFileTypeEnum" to "it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum"
    )
  )
  configOptions.set(
    mapOf(
      "swaggerAnnotations" to "false",
      "openApiNullable" to "false",
      "dateLibrary" to "java8",
      "serializableModel" to "true",
      "useSpringBoot3" to "true",
      "useJakartaEe" to "true",
      "useOneOfInterfaces" to "true",
      "useBeanValidation" to "true",
      "serializationLibrary" to "jackson",
      "generateSupportingFiles" to "true",
      "generateConstructorWithAllArgs" to "true",
      "generatedConstructorWithRequiredArgs" to "true",
      "enumPropertyNaming" to "original",
      "additionalModelTypeAnnotations" to "@lombok.experimental.SuperBuilder"
    )
  )
  library.set("resttemplate")
  typeMappings.set(
    mapOf(
      "LocalDateTime" to "java.time.LocalDateTime"
    )
  )
}
