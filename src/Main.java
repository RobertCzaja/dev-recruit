import OfferProvider.Application.JustJoinItProvider;
import OfferProvider.Infrastructure.JustJoinItConnector;
import OfferProvider.Infrastructure.JustJoinItMongoRepository;
import OfferProvider.Infrastructure.JustJoinItPayloadExtractor;
import Shared.Infrastructure.MongoDBConnection;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.MalformedURLException;
import java.net.URL;
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
        JustJoinItProvider justJoinItProvider = new JustJoinItProvider(
                new JustJoinItConnector(new URL(dotenv.get("JUST_JOIN_IT_ORIGIN"))),
                new JustJoinItPayloadExtractor(),
                new JustJoinItMongoRepository(mongoDBConnection)
        );
        justJoinItProvider.fetch(TECHNOLOGY, FETCHING_ID);

        System.out.println("Script executed");
    }
}