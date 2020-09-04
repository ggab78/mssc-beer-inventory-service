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

        AllocateBeerOrderResult.AllocateBeerOrderResultBuilder builder = AllocateBeerOrderResult.builder();
        builder.beerOrderDto(request.getBeerOrderDto());
        Boolean isAllocated = false;
        try {
            isAllocated = allocationService.allocateOrder(request.getBeerOrderDto());
            builder.pendingInventory(!isAllocated);
        } catch (Exception e) {
            log.error("Allocation faild for beer order Id " + request.getBeerOrderDto().getId() + " with message : " + e.getMessage());
        }
        builder.allocationError(!isAllocated);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_BEER_ORDER_RESPONSE, builder.build());
    }
}



