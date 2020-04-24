package  ru.akhramenko

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.akhramenko.api.ServiceMonitoringAPI
import ru.akhramenko.data.DocumentFactory
import ru.akhramenko.model.DiagnosticInfo


class DiagnosticInfoTest: BaseTest<ServiceMonitoringAPI>(api = ServiceMonitoringAPI) {

    @DisplayName("Получение результатов расчета факторов и триггеров для созданного документа")
    @Test
    fun getDiagnosticInfoForDocument() {
        val document = DocumentFactory().getTestDoc()
        document.procDate = null
        document.state = "FORM"
        val expectedResult = api.analyzeDocument(document)
            .jsonPath().getString("")

        Thread.sleep(1000)
        val actualResult = api.getDiagnosticsInfoById("${document.src?.bic}:${document.src?.source}:${document.src?.id}")
            .jsonPath().getList<DiagnosticInfo>("")

        assertThat(actualResult.size.equals(1))
    }

    @DisplayName("Получение результатов расчета факторов и триггеров для проведенного документа, по которому не считаются факторы и триггеры")
    @Test
    fun getEmptyDiagnosticInfoForDocument() {
        val document = DocumentFactory().getTestDoc()
        api.analyzeDocument(document)
        Thread.sleep(1000)
        val actualResult = api.getDiagnosticsInfoById("${document.src?.bic}:${document.src?.source}:${document.src?.id}")

        assertThat(actualResult.equals("[]"))
    }

    @DisplayName("Получение результатов расчета факторов и триггеров для повторно пришедшего документа")
    @Test
    fun getDiagnosticInfoForDuplicateDocument() {
        val document = DocumentFactory().getTestDoc()
        document.procDate = null
        document.state = "FORM"
        val expectedResult1 = api.analyzeDocument(document)
            .jsonPath().getString("")

        Thread.sleep(1000)
        val actualResult1 = api.getDiagnosticsInfoById("${document.src?.bic}:${document.src?.source}:${document.src?.id}")
            .jsonPath().getList<DiagnosticInfo>("")

        document.state = "FORM"
        document.amountNat = 500000.0
        document.amount = 500000.0

        val expectedResult2 = api.analyzeDocument(document)
            .jsonPath().getString("")

        Thread.sleep(1000)
        val actualResult2 = api.getDiagnosticsInfoById("${document.src?.bic}:${document.src?.source}:${document.src?.id}")
            .jsonPath().getList<DiagnosticInfo>("")

        assertThat(actualResult2.size.equals(2))
    }

    @DisplayName("Получение результатов расчета факторов и триггеров для несуществующего документа")
    @Test
    fun getDiagnosticInfoForNonExistingDocument() {

        val actualResult = api.getDiagnosticsInfoById("12345678")

        assertThat(actualResult.equals("[]"))

    }
}