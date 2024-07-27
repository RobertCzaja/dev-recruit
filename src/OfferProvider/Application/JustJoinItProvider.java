package OfferProvider.Application;

import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class JustJoinItProvider {

    private final JustJoinItConnector connector;
    private final JustJoinItPayloadExtractor payloadExtractor;
    private final JustJoinItRepository repository;

    public JustJoinItProvider(
            JustJoinItConnector connector,
            JustJoinItPayloadExtractor payloadExtractor,
            JustJoinItRepository repository
    ) {
        this.connector = connector;
        this.payloadExtractor = payloadExtractor;
        this.repository = repository;
    }

    public void fetch(String technology, UUID scrapingId)
    {
        String stringifyJsonPayload = connector.fetchStringifyJsonPayload(technology);
        ArrayList<Map<String, Object>> offers = payloadExtractor.extract(stringifyJsonPayload);

        for (Map<String, Object> offer : offers) {
            repository.save(
                    new Document()
                            .append("offer", offer)
                            .append("createdAt", LocalDateTime.now())
                            .append("technology", technology)
                            .append("scrapingId", scrapingId.toString()
                    )
            );
        }
    }

}
