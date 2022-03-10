package pl.minecodes.mineeconomy.data.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import eu.okaeri.injector.annotation.Inject;
import org.bson.Document;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.database.element.DatabaseData;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.profile.Profile;

import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

public class MongoDbService implements DataService {

    @Inject private Logger logger;
    @Inject private Configuration configuration;

    private MongoCollection<Document> mongoCollection;

    @Override
    public Profile loadData(UUID uniqueId) {
        FindIterable<Document> findDocument = mongoCollection.find(Filters.eq("uniqueId", uniqueId.toString()));
        Document document = findDocument.first();
        if (document == null) return null;

        return new Profile(UUID.fromString(document.getString("uniqueId")), document.getDouble("balance"));
    }

    @Override
    public void saveData(Profile profile) {
        Document document = new Document();
        document.put("uniqueId", profile.getUniqueId().toString());
        document.put("balance", profile.getBalance());
        mongoCollection.replaceOne(Filters.eq("uniqueId", profile.getUniqueId().toString()), document, new ReplaceOptions().upsert(true));
    }

    @Override
    public void deleteData(Profile profile) {
        mongoCollection.deleteOne(Filters.eq("uniqueId", profile.getUniqueId().toString()));
    }

    @Override
    public void connect() {
        DatabaseData databaseData = this.configuration.getDatabaseData();
        MongoCredential mongoCredential = MongoCredential.createCredential(databaseData.getUsername(), databaseData.getDatabase(), databaseData.getPassword().toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(databaseData.getHost(), databaseData.getPort()), Collections.singletonList(mongoCredential));
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseData.getDatabase());
        mongoCollection = mongoDatabase.getCollection("economyUsers");
        this.logger.info("Successfully connected to MongoDb database.");
    }

    @Override
    public Profile order(int order) {
        int i = 1;
        for (Document document : mongoCollection.find().sort(Sorts.descending("balance"))) {
            if (i == order) return new Profile(UUID.fromString(document.getString("uniqueId")), document.getDouble("balance"));
            i++;
        }
        return null;
    }
}
