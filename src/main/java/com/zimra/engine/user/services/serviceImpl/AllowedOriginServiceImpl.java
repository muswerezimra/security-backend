package com.zimra.engine.user.services.serviceImpl;


import com.zimra.engine.user.models.AllowedOrigin;
import com.zimra.engine.user.repositories.AllowedOriginRepository;
import com.zimra.engine.user.services.AllowedOriginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllowedOriginServiceImpl implements AllowedOriginService {

    private final AllowedOriginRepository allowedOriginRepository;

    @Override
    public List<String> getAllowedOrigins() {
        return allowedOriginRepository.findAll()
                .stream()
                .map(AllowedOrigin::getOriginUrl)
                .collect(Collectors.toList());
    }


}