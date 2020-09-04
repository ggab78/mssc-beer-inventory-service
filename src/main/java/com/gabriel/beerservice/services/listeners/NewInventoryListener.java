package com.gabriel.beerservice.services.listeners;


import com.gabriel.beerservice.domain.BeerInventory;
import com.gabriel.model.events.NewInventoryEvent;
import com.gabriel.beerservice.config.JmsConfig;
import com.gabriel.beerservice.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewInventoryListener {

private final BeerInventoryRepository beerInventoryRepository;


@JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
public void listen(NewInventoryEvent event){

    log.debug("Got Inventory " + event.toString());
    log.debug("beerId "+ event.getBeerDto().getId().toString());

    beerInventoryRepository.save(BeerInventory.builder()
            .beerId(event.getBeerDto().getId())
            .upc(event.getBeerDto().getUpc())
            .quantityOnHand(event.getBeerDto().getQuantityOnHand())
            .build());
}

}
