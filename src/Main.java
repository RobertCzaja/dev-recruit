
import Model.JustJoinItOffer;
import OfferProvider.Infrastructure.JustJoinItConnector;
import OfferProvider.Infrastructure.JustJoinItOfferFactory;
import OfferProvider.Infrastructure.JustJoinItPayloadExtractor;
import Shared.Infrastructure.MongoDBConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final String TECHNOLOGY = "php";
    private static UUID FETCHING_ID;

    public static void main(String[] args) throws MalformedURLException {
        FETCHING_ID = UUID.randomUUID();
        Dotenv dotenv = Dotenv.load();
        MongoDBConnection mongoDBConnection = new MongoDBConnection(
                dotenv.get("MONGO_DB_CONNECTION"),
                dotenv.get("MONGO_DB_NAME")
        );
        JustJoinItConnector connector = new JustJoinItConnector(new URL(dotenv.get("JUST_JOIN_IT_ORIGIN")));
        JustJoinItPayloadExtractor payloadExtractor = new JustJoinItPayloadExtractor();
        //JustJoinItOfferFactory factory = new JustJoinItOfferFactory();

        String rawJsonPayload = connector.fetchOffersHtmlPage(TECHNOLOGY);
        Iterator<JsonNode> offersNode = payloadExtractor.extract(rawJsonPayload);
        //List<JustJoinItOffer> offers = factory.createFromRawPayload(offersNode);

        //----------------------------------------------------------------------
        MongoCollection<Document> collection = mongoDBConnection
                .getDatabase()
                .getCollection("just_join_it_raw_offers");
        //----------------------------------------------------------------------

        ObjectMapper mapper = new ObjectMapper();
        int i = 0;
        while (offersNode.hasNext()) {
            collection.insertOne(
                new Document()
                    .append("offer", mapper.convertValue(offersNode.next(), new TypeReference<Map<String, Object>>() {}))
                    .append("createdAt", LocalDateTime.now())
                    .append("technology", TECHNOLOGY)
                    .append("fetchingId", FETCHING_ID.toString()
                )
            );
            i++;
        }

        System.out.println("JustJoinIt offers count: "+i);
    }
}