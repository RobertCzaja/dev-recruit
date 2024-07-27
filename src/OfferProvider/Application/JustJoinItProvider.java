package OfferProvider.Application;

import OfferProvider.Infrastructure.JustJoinItConnector;
import OfferProvider.Infrastructure.JustJoinItMongoRepository;
import OfferProvider.Infrastructure.JustJoinItPayloadExtractor;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class JustJoinItProvider {

    private final JustJoinItConnector connector;
    private final JustJoinItPayloadExtractor payloadExtractor;
    private final JustJoinItMongoRepository repository;

    public JustJoinItProvider(
            JustJoinItConnector connector,
            JustJoinItPayloadExtractor payloadExtractor,
            JustJoinItMongoRepository repository
    ) {
        this.connector = connector;
        this.payloadExtractor = payloadExtractor;
        this.repository = repository;
    }

    public void fetch(String technology, UUID fetchingId)
    {
        String stringifyJsonPayload = connector.fetchOffersHtmlPage(technology);
        ArrayList<Map<String, Object>> offers = payloadExtractor.extract(stringifyJsonPayload);

        for (Map<String, Object> offer : offers) {
            repository.save(
                    new Document()
                            .append("offer", offer)
                            .append("createdAt", LocalDateTime.now())
                            .append("technology", technology)
                            .append("fetchingId", fetchingId.toString()
                    )
            );
        }
    }

}
