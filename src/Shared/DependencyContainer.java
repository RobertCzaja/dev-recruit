package Shared;

import OfferProvider.Application.JustJoinItProvider;
import OfferProvider.Application.TechnologyRepository;
import OfferProvider.Infrastructure.JustJoinItHttpConnector;
import OfferProvider.Infrastructure.JustJoinItMongoRepository;
import OfferProvider.Application.JustJoinItPayloadExtractor;
import OfferProvider.Infrastructure.TechnologyInMemoryRepository;
import Shared.Infrastructure.MongoDBConnection;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.MalformedURLException;
import java.net.URL;

public final class DependencyContainer {


    public static MongoDBConnection createMongoDBConnection()
    {
        return new MongoDBConnection(
                Dotenv.load().get("MONGO_DB_CONNECTION"),
                Dotenv.load().get("MONGO_DB_NAME")
        );
    }

    public static JustJoinItProvider createJustJoinItProvider() throws MalformedURLException {
        return new JustJoinItProvider(
                new JustJoinItHttpConnector(new URL(Dotenv.load().get("JUST_JOIN_IT_ORIGIN"))),
                new JustJoinItPayloadExtractor(),
                new JustJoinItMongoRepository(createMongoDBConnection())
        );
    }

    public static TechnologyRepository createTechnologyRepository()
    {
        return new TechnologyInMemoryRepository();
    }
}
