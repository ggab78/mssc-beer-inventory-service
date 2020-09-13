package com.gabriel.beerservice.services.listeners;

import com.gabriel.beerservice.config.JmsConfig;
import com.gabriel.beerservice.services.AllocationService;
import com.gabriel.model.events.DeallocateBeerOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeallocateOrderListener {

    private final AllocationService allocationService;

    @Transactional
    @JmsListener(destination = JmsConfig.DEALLOCATE_BEER_ORDER)
    public void listen(DeallocateBeerOrderRequest request) {

          allocationService.deallocateOrder(request.getBeerOrderDto());

    }
}
