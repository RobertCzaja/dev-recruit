import OfferProvider.Application.JustJoinItProvider;
import OfferProvider.Application.TechnologyRepository;
import Shared.DependencyContainer;

import java.net.MalformedURLException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        JustJoinItProvider justJoinItProvider = DependencyContainer.createJustJoinItProvider();
        TechnologyRepository technologyRepository = DependencyContainer.createTechnologyRepository();

        UUID scrapingId = UUID.randomUUID();
        for (String technology : technologyRepository.allActive()) {
            justJoinItProvider.fetch(technology, scrapingId);
        }

        System.out.println("Script executed");
    }
}