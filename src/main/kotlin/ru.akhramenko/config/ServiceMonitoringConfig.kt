package ru.akhramenko.config

import com.natpryce.konfig.*
import org.slf4j.LoggerFactory

object ServiceMonitoringConfig{

    val config: Configuration = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("env.properties")

    val url: String = config[service.url]

    private object service : PropertyGroup() {
        val url by stringType

    }

    init {
        val logger = LoggerFactory.getLogger(this::class.java)
        logger.info(this.toString())
    }
}
