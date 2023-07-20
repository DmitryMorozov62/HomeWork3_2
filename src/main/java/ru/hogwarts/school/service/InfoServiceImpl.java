package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService{

    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Value("${server.port}")
    private int port;

    @Override
    public Integer getPort() {
        logger.debug("Был вызван метод getPort");
        return port;
    }

    @Override
    public Integer getSum() {
        int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        return sum;
    }
}
