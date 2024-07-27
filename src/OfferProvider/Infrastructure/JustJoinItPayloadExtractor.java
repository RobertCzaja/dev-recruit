package OfferProvider.Infrastructure;

import Model.JustJoinItException;
import Shared.Infrastructure.FileManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;

public class JustJoinItPayloadExtractor {

    private ObjectMapper mapper = new ObjectMapper();
    private FileManager fileManager = new FileManager();

    public final Iterator<JsonNode> extract(String rawJsonPayload) {
        if (rawJsonPayload.isEmpty()) {
            throw new JustJoinItException("Empty body payload");
        }

        try {
            return mapper.readTree(rawJsonPayload)
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
        } catch (JsonProcessingException e) {
            fileManager.saveFile(rawJsonPayload, "json");
            throw new JustJoinItException("Could not extract offers from raw JSON payload. "+e.getMessage());
        }
    }

}
