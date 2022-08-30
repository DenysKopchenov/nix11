package com.alevel.lesson10.shop.repository.impl.mongo;

import com.alevel.lesson10.shop.config.MongoDBConfig;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class LaptopRepositoryMongoImpl implements LaptopRepository {
    private final MongoCollection<Document> collection;
    private final Gson gson;

    public LaptopRepositoryMongoImpl() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Laptop.class.getSimpleName());
        gson = new Gson();
    }

    @Override
    public void save(Laptop product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Laptop> products) {
        List<Document> documents = products.stream()
                .map(laptop -> Document.parse(gson.toJson(laptop)))
                .toList();
        collection.insertMany(documents);
    }

    @Override
    public List<Laptop> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Laptop.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Laptop found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Laptop.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public void update(Laptop product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("id", id));
    }
}
