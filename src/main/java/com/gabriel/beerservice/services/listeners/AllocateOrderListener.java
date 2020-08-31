package com.gabriel.beerservice.services.listeners;

import com.gabriel.beerservice.config.JmsConfig;
import com.gabriel.beerservice.services.AllocationService;
import com.gabriel.model.events.AllocateBeerOrderRequest;
import com.gabriel.model.events.AllocateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocateOrderListener {

    private final JmsTemplate jmsTemplate;
    private final AllocationService allocationService;

    @Transactional
    @JmsListener(destination = JmsConfig.ALLOCATE_BEER_ORDER)
    public void listen(AllocateBeerOrderRequest request) {

        Boolean isAllocated = allocationService.allocateOrder(request.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_BEER_ORDER_RESPONSE,
                AllocateBeerOrderResult.builder()
                        .isAllocated(isAllocated)
                        .beerOrderDto(request.getBeerOrderDto())
                        .isPending(!isAllocated)
                        .build());
    }
}



