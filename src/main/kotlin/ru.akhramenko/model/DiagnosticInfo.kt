package ru.akhramenko.model

import ru.akhramenko.model.Factor
import ru.akhramenko.model.Trigger

data class DiagnosticInfo(
    val calcId: String,
    val timestamp: String,
    val transactionId: String,
    val transactionDate: String,
    val transactionState: String,
    val factors: ArrayList<Factor>,
    val triggers: ArrayList<Trigger>
) {

}