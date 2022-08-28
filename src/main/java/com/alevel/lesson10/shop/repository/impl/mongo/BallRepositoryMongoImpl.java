package com.alevel.lesson10.shop.repository.impl.mongo;

import com.alevel.lesson10.shop.config.MongoDBConfig;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.repository.BallRepository;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class BallRepositoryMongoImpl implements BallRepository {

    private final MongoCollection<Document> collection;
    private final Gson gson;

    public BallRepositoryMongoImpl() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Ball.class.getSimpleName());
        gson = new Gson();
    }

    @Override
    public void save(Ball product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Ball> products) {
        List<Document> documents = products.stream()
                .map(ball -> Document.parse(gson.toJson(ball)))
                .toList();
        collection.insertMany(documents);
    }

    @Override
    public List<Ball> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Ball.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Ball> findById(String id) {
        Ball found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Ball.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public void update(Ball product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("id", id));
    }
}
