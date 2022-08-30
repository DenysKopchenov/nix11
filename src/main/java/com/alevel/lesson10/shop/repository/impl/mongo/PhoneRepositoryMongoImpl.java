package com.alevel.lesson10.shop.repository.impl.mongo;

import com.alevel.lesson10.shop.config.MongoDBConfig;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class PhoneRepositoryMongoImpl implements PhoneRepository {
    private final MongoCollection<Document> collection;
    private final Gson gson;

    public PhoneRepositoryMongoImpl() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Phone.class.getSimpleName());
        gson = new Gson();
    }

    @Override
    public void save(Phone product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Phone> products) {
        List<Document> documents = products.stream()
                .map(phone -> Document.parse(gson.toJson(phone)))
                .toList();
        collection.insertMany(documents);
    }

    @Override
    public List<Phone> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Phone.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Phone> findById(String id) {
        Phone found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Phone.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public void update(Phone product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("id", id));
    }
}
