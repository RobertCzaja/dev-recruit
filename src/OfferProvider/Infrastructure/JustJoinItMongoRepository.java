package OfferProvider.Infrastructure;

import OfferProvider.Application.JustJoinItRepository;
import Shared.Infrastructure.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class JustJoinItMongoRepository implements JustJoinItRepository {

    private final MongoCollection<Document> collection;

    public JustJoinItMongoRepository(MongoDBConnection connection) {
        collection = connection.getDatabase().getCollection("just_join_it_raw_offers");
    }

    public void save(Document document)
    {
        collection.insertOne(document);
    }
}
