package ru.akhramenko.api

import io.qameta.allure.Step
import io.restassured.RestAssured.get
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import mu.KotlinLogging
import org.apache.http.HttpStatus
import ru.akhramenko.config.ServiceMonitoringConfig

object ServiceMonitoringAPI: IServiceMonitoringAPI {

    private val logger = KotlinLogging.logger(this::class.java.canonicalName)

    override var specification = RequestSpecBuilder()
        .setBaseUri(ServiceMonitoringConfig.url)
        .setBasePath("/api/v1")
        .setContentType(ContentType.JSON)
        .build()

    @Step("Получить результат расчета факторов и триггеров по ID документа")
    fun getDiagnosticsInfoById(transactionId: String): Response {

        return Given {
            spec(specification)
            pathParam("transactionId", transactionId)
        } When{
            get("/monitoring/diagnostics/journal/{transactionId}/info")
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            response()
        }
    }

    @Step("Обработать платежный документ")
    fun analyzeDocument(document: Document): Response {
        return Given {
            spec(specification)
            body(document)
        } When{
            post("/monitoring/executor/documents/analyzeDocument")
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            response()
        }
    }

    @Step("Проинициализировать расшифровки для платежного документа")
    fun initDetails(document: Document): Response {
        return Given {
            spec(specification)
            body(document)
        } When{
            post("/monitoring/executor/documents/initDetails")
        } Then {
            statusCode(HttpStatus.SC_OK)
        } Extract {
            response()
        }
    }

}