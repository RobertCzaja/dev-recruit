package OfferProvider.Infrastructure;

import Model.JustJoinItException;
import Shared.Infrastructure.FileManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JustJoinItPayloadExtractor {

    private ObjectMapper mapper = new ObjectMapper();
    private FileManager fileManager = new FileManager();

    public final ArrayList<Map<String, Object>> extract(String rawJsonPayload) {
        if (rawJsonPayload.isEmpty()) {
            throw new JustJoinItException("Empty body payload");
        }

        try {
            Iterator<JsonNode> offersNode = mapper.readTree(rawJsonPayload)
                    .path("props")
                    .path("pageProps")
                    .path("dehydratedState")
                    .path("queries")
                    .get(0)
                    .path("state")
                    .path("data")
                    .path("pages")
                    .get(0)
                    .path("data")
                    .elements();
            ArrayList<Map<String, Object>> offers = new ArrayList<>();
            while (offersNode.hasNext()) {
                JsonNode offerNode = offersNode.next();
                offers.add(mapper.convertValue(offersNode.next(), new TypeReference<Map<String, Object>>() {}));
            }
            return offers;

        } catch (JsonProcessingException e) {
            fileManager.saveFile(rawJsonPayload, "json");
            throw new JustJoinItException("Could not extract offers from raw JSON payload. " + e.getMessage());
        }
    }

}
