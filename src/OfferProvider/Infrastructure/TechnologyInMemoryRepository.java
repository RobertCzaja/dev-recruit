package OfferProvider.Infrastructure;

import OfferProvider.Application.TechnologyRepository;

import java.util.Arrays;
import java.util.List;

public class TechnologyInMemoryRepository implements TechnologyRepository {
    @Override
    public List<String> allActive() {
        return Arrays.asList(
                "php",
                "java",
                "javascript",
                "net",
                "devops"
                //"go"
        );
    }
}
