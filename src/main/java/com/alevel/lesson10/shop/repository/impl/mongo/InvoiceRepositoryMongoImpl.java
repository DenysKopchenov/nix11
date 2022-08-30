package com.alevel.lesson10.shop.repository.impl.mongo;

import com.alevel.lesson10.shop.config.MongoDBConfig;
import com.alevel.lesson10.shop.model.Invoice;
import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.InvoiceRepository;
import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import javassist.Modifier;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;


public class InvoiceRepositoryMongoImpl implements InvoiceRepository {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> collectionBalls;
    private final MongoCollection<Document> collectionLaptops;
    private final MongoCollection<Document> collectionPhones;
    private final Gson gson;

    public InvoiceRepositoryMongoImpl() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Invoice.class.getSimpleName());
        collectionBalls = MongoDBConfig.getMongoDatabase().getCollection(Ball.class.getSimpleName());
        collectionLaptops = MongoDBConfig.getMongoDatabase().getCollection(Laptop.class.getSimpleName());
        collectionPhones = MongoDBConfig.getMongoDatabase().getCollection(Phone.class.getSimpleName());
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString() + " 00:00",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();
    }

    @Override
    public void save(Invoice invoice) {
        collection.insertOne(Document.parse(gson.toJson(invoice)));
    }

    @Override
    public List<Invoice> findAllInvoicesWithSumGreaterThen(double sum) {
        List<Invoice> invoices = collection.find(gt("sum", sum))
                .map(document -> gson.fromJson(document.toJson(), Invoice.class))
                .into(new ArrayList<>());
        invoices.forEach(invoice -> invoice.setProducts(findAllProductsOfInvoice(invoice.getProductIds())));
        return invoices;
    }

    private List<Product> findAllProductsOfInvoice(List<String> productIds) {
        return productIds.stream()
                .map(id -> {
                    Ball ball = collectionBalls.find(eq("id", id))
                            .map(document -> gson.fromJson(document.toJson(), Ball.class))
                            .first();
                    if (ball != null) {
                        return ball;
                    }
                    Laptop laptop = collectionLaptops.find(eq("id", id))
                            .map(document -> gson.fromJson(document.toJson(), Laptop.class))
                            .first();
                    if (laptop != null) {
                        return laptop;
                    }
                    Phone phone = collectionPhones.find(eq("id", id))
                            .map(document -> gson.fromJson(document.toJson(), Phone.class))
                            .first();
                    if (phone != null) {
                        return phone;
                    }
                    throw new IllegalArgumentException("no products found");
                })
                .toList();
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Invoice invoice = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Invoice.class))
                .first();
        if (invoice != null) {
            invoice.setProducts(findAllProductsOfInvoice(invoice.getProductIds()));
        }
        return Optional.ofNullable(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        collection.updateOne(eq("id", invoice.getId()), new Document("$set", Document.parse(gson.toJson(invoice))));
    }

    @Override
    public long getInvoiceCount() {
        return collection.countDocuments();
    }

    @Override
    public Map<Double, Long> groupBySum() {
        Map<Double, Long> result = new TreeMap<>();
        collection.aggregate(List.of(Aggregates.group("$sum", Accumulators.sum("count", 1))))
                .map(document -> gson.fromJson(document.toJson(), JsonObject.class))
                .forEach((Consumer<? super JsonObject>) jsonObject -> {
                    double sum = jsonObject.get("_id").getAsDouble();
                    long count = jsonObject.get("count").getAsLong();
                    result.put(sum, count);
                });
        return result;
    }
}
