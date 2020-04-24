package ru.akhramenko.api

import io.restassured.specification.RequestSpecification

interface IServiceMonitoringAPI {

    var specification: RequestSpecification
}