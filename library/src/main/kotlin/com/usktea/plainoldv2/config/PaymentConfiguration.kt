package com.usktea.plainoldv2.config

import com.usktea.plainoldv2.domain.payment.application.KakaopayService
import com.usktea.plainoldv2.domain.payment.application.PaymentOrderAmountService
import com.usktea.plainoldv2.domain.payment.application.PaymentService
import com.usktea.plainoldv2.domain.payment.application.PaymentServiceFactory
import com.usktea.plainoldv2.domain.payment.repository.PrePaymentRepository
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.KakaopayProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(KakaopayProperties::class)
class PaymentConfiguration {
    @Autowired
    private lateinit var kakaopayProperties: KakaopayProperties

    @Autowired
    private lateinit var orderAmountService: PaymentOrderAmountService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var prePaymentRepository: PrePaymentRepository

    @Autowired
    private lateinit var webClient: WebClient

    @Bean
    fun paymentServiceFactory(): PaymentServiceFactory {
        val services: MutableMap<String, PaymentService> = mutableMapOf()

        services["KAKAOPAY"] = KakaopayService(
            properties = kakaopayProperties,
            paymentOrderAmountService = orderAmountService,
            prePaymentRepository = prePaymentRepository,
            userRepository = userRepository,
            client = webClient
        )

        return PaymentServiceFactory(services)
    }
}
