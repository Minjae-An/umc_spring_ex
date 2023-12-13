package umc.spring.service.store_service;

import umc.spring.domain.Store;

public interface StoreQueryService {
    Store findById(Long storeId);
    boolean isStoreExist(Long storeId);
}
