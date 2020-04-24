package ru.akhramenko

import org.junit.jupiter.api.TestInstance
import ru.akhramenko.api.IServiceMonitoringAPI

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTest<API : IServiceMonitoringAPI>(protected val api: API) {

}