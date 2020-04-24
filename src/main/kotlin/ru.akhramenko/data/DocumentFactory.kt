package ru.akhramenko.data

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.javafaker.Faker
import java.util.*

class DocumentFactory {

    private val faker = Faker(Locale("ru"))

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .registerModule(KotlinModule())
        .registerModule(JavaTimeModule())
        .registerModule(Jdk8Module())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    private val docString = """
            {
              "payment_source": "ДБО",
              "src": {
                "bic": "src_bic_doc",
                "source": "source",
                "id": "id_doc"
              },
              "number": "105",
              "state": "PROV",
              "cash": true,
              "type": "01",
              "date": "2020-03-06T06:32:55.464946000",
              "timezone": "UTC+05:00",
              "currency": "RUB",
              "amount": 1050000,
              "purpose": "Назначение платежа для автоматического теста",
              "priority": 5,
              "docNum": "105",
              "payer_account": {
                "src": {
                  "bic": "src_bic_p",
                  "source": "source",
                  "id": "id_ap"
                },
                "client": {
                  "src": {
                    "bic": "src_bic_p",
                    "source": "source",
                    "id": "id_p"
                  },
                  "uid":"uid_p",
                  "name": "ООО \"Фирма 1\"",
                  "inn": "inn_11",
                  "kio": null,
                  "kpp": "kpp_11",
                  "type": "ORG",
                  "main_okved": {
                    "code": "71.11.1",
                    "name": "Деятельность в области архитектуры, связанная с созданием архитектурного объекта",
                    "version": "2",
                    "main": true,
                    "high_risk": null,
                    "begin_date": null,
                    "end_date": null
                  }
                },
                "number": "40702810011080001111",
                "saldo": 1603514.88,
                "currency": null,
                "bank": {
                  "name": "ФИЛИАЛ ПАО \"БАНК ТЕСТОВЫЙ\" В Г.НОВОСИБИРСК",
                  "bic": "bic_bank_p",
                  "head_bic": null,
                  "corr_acc_number": "30101810400000001111"
                },
                "acc_type": null,
                "saldo_nat": 1603514.88
              },
              "recipient_account": {
                "src": {
                  "bic": "bic_r",
                  "source": "source",
                  "id": "id_ar"
                },
                "client": {
                  "src": {
                    "bic": "bic_r",
                    "source": "source",
                    "id": "id_r"
                  },
                  "uid":"uid_r",
                  "name": "ООО \"Фирма 2\"",
                  "inn": "inn_22",
                  "kio": null,
                  "kpp": "kpp_22",
                  "type": "ORG",
                  "main_okved": {
                    "code": "71.11.1",
                    "name": "Деятельность в области архитектуры, связанная с созданием архитектурного объекта",
                    "version": "2",
                    "main": true,
                    "high_risk": null,
                    "begin_date": null,
                    "end_date": null
                  }
                },
                "number": "40817810222204002222",
                "saldo": null,
                "currency": null,
                "bank": {
                  "name": "ФИЛИАЛ ПАО \"БАНК ТЕСТОВЫЙ\" В Г.НОВОСИБИРСК",
                  "bic": "bic_bank_r",
                  "head_bic": "head_bic_r",
                  "corr_acc_number": "30101810450040002222"
                },
                "acc_type": null,
                "saldo_nat": null
              },
              "amount_nat": 1050000,
              "proc_date": "2020-03-06T06:32:55.464946000",
              "budget_reqs": {
                "kbk": null,
                "oktmo": null,
                "tax_period": null,
                "tax_number": null,
                "tax_date": null,
                "tax_reason": null,
                "pay_type": null,
                "payer_status": null
              },
              "acc_dt_number": "40702810011080001111",
              "acc_kt_number": "30101810450040002222"
            }
    """

    fun getTestDoc(): Document {
        var document = objectMapper.readValue(docString, Document::class.java)
        val src = Src("123456", "source", generateNumber(8))
        document.src = src
        return document
    }

    private fun generateNumber(length: Int): String {
        return  faker.number()
            .randomNumber(length, true)
            .toString()
    }

}