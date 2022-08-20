package com.alevel.lesson10.shop.repository.impl.postgre;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.config.JDBCConfig;
import com.alevel.lesson10.shop.model.Invoice;
import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.InvoiceRepository;
import org.apache.commons.lang3.EnumUtils;

import java.sql.Date;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;

@Singleton
public class InvoiceRepositoryPostgresImpl implements InvoiceRepository {

    private final Connection connection;

    @Autowired
    public InvoiceRepositoryPostgresImpl() {
        connection = JDBCConfig.getConnection();
    }

    @Override
    public void save(Invoice invoice) {
        String insert = "INSERT INTO shop.invoice (id, sum, time) VALUES (?, ?, ?);";
        String updatePhone = "UPDATE shop.phone SET invoice_id = ? WHERE id = ?;";
        String updateBall = "UPDATE shop.ball SET invoice_id = ? WHERE id = ?;";
        String updateLaptop = "UPDATE shop.laptop SET invoice_id = ? WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setDouble(2, invoice.getSum());
            preparedStatement.setDate(3, Date.valueOf(invoice.getTime().toLocalDate()));
            preparedStatement.execute();

            List<Product> products = invoice.getProducts();
            for (Product product : products) {
                if (product.getClass().isAssignableFrom(Phone.class)) {
                    updateProduct(updatePhone, invoice, product);
                }
                if (product.getClass().isAssignableFrom(Ball.class)) {
                    updateProduct(updateBall, invoice, product);
                }
                if (product.getClass().isAssignableFrom(Laptop.class)) {
                    updateProduct(updateLaptop, invoice, product);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    private void updateProduct(String query, Invoice invoice, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, invoice.getId());
            statement.setString(2, product.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Invoice> findAllInvoicesWithSumGreaterThen(double sum) {
        String selectInvoice = """
                SELECT shop.invoice.*,
                phone.id AS phone_id,
                phone.title AS phone_title,
                phone.count AS phone_count,
                phone.price AS phone_price,
                phone.manufacturer AS phone_manufacturer,
                phone.model AS phone_model,
                ball.id AS ball_id,
                ball.title AS ball_title,
                ball.price AS ball_price,
                ball.count AS ball_count,
                ball.size AS ball_size,
                laptop.id AS laptop_id,
                laptop.title AS laptop_title,
                laptop.price AS laptop_price,
                laptop.count AS laptop_count,
                laptop.cpu AS laptop_cpu
                FROM shop.invoice
                LEFT JOIN shop.phone ON phone.invoice_id = invoice.id
                LEFT JOIN shop.laptop ON laptop.invoice_id = invoice.id
                LEFT JOIN shop.ball ON ball.invoice_id = invoice.id
                WHERE sum > ? ORDER BY invoice.id;""";
        try (PreparedStatement statement = connection.prepareStatement(selectInvoice)) {
            statement.setDouble(1, sum);
            ResultSet resultSet = statement.executeQuery();
            Map<String, Invoice> invoicesById = new HashMap<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                if (!invoicesById.containsKey(id)){
                    Invoice invoice = new Invoice();
                    invoice.setId(id);
                    invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.of(0,0)));
                    invoice.setSum(resultSet.getDouble("sum"));
                    invoice.setProducts(new ArrayList<>());
                    invoicesById.put(invoice.getId(), invoice);
                }
                List<Product> products = invoicesById.get(id).getProducts();
                if (resultSet.getString("ball_id") != null){
                    Ball ball = mapBall(resultSet);
                    if (!products.contains(ball)) {
                        products.add(ball);
                    }
                }
                if (resultSet.getString("phone_id") != null){
                    Phone phone = mapPhone(resultSet);
                    if (!products.contains(phone)) {
                        products.add(phone);
                    }
                }
                if (resultSet.getString("laptop_id") != null){
                    Laptop laptop = mapLaptop(resultSet);
                    if (!products.contains(laptop)) {
                        products.add(laptop);
                    }
                }
            }
            return invoicesById.values().stream().toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Laptop mapLaptop(ResultSet resultSet) throws SQLException {
        Laptop laptop = new Laptop.Builder(
                resultSet.getLong("laptop_price"),
                EnumUtils.getEnum(CPU.class, resultSet.getString("laptop_cpu"), CPU.NONE)).build();
        laptop.setId(resultSet.getString("laptop_id"));
        laptop.setCount(resultSet.getInt("laptop_count"));
        laptop.setTitle(resultSet.getString("laptop_title"));
        return laptop;
    }


    private Ball mapBall(ResultSet resultSet) throws SQLException {
        Ball ball = new Ball();
        ball.setId(resultSet.getString("ball_id"));
        ball.setCount(resultSet.getInt("ball_count"));
        ball.setPrice(resultSet.getLong("ball_price"));
        ball.setSize(EnumUtils.getEnum(Size.class, resultSet.getString("ball_size"), Size.NONE));
        ball.setTitle(resultSet.getString("ball_title"));
        return ball;
    }

    private Phone mapPhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone(resultSet.getString("phone_title"),
                resultSet.getInt("phone_count"),
                resultSet.getLong("phone_price"),
                resultSet.getString("phone_model"),
                EnumUtils.getEnum(Manufacturer.class, resultSet.getString("phone_manufacturer")));
        phone.setId(resultSet.getString("phone_id"));
        return phone;
    }

    @Override
    public Optional<Invoice> findById(String id) {
        String selectInvoice = """
                SELECT shop.invoice.*,
                phone.id AS phone_id,
                phone.title AS phone_title,
                phone.count AS phone_count,
                phone.price AS phone_price,
                phone.manufacturer AS phone_manufacturer,
                phone.model AS phone_model,
                ball.id AS ball_id,
                ball.title AS ball_title,
                ball.price AS ball_price,
                ball.count AS ball_count,
                ball.size AS ball_size,
                laptop.id AS laptop_id,
                laptop.title AS laptop_title,
                laptop.price AS laptop_price,
                laptop.count AS laptop_count,
                laptop.cpu AS laptop_cpu
                FROM shop.invoice
                LEFT JOIN shop.phone ON phone.invoice_id = invoice.id
                LEFT JOIN shop.laptop ON laptop.invoice_id = invoice.id
                LEFT JOIN shop.ball ON ball.invoice_id = invoice.id
                WHERE invoice.id = ? ORDER BY invoice.id;""";
        try (PreparedStatement statement = connection.prepareStatement(selectInvoice)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Map<String, Invoice> invoicesById = new HashMap<>();
            while (resultSet.next()) {
                String idFromDB = resultSet.getString("id");
                if (!invoicesById.containsKey(idFromDB)){
                    Invoice invoice = new Invoice();
                    invoice.setId(idFromDB);
                    invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.of(0,0)));
                    invoice.setSum(resultSet.getDouble("sum"));
                    invoice.setProducts(new ArrayList<>());
                    invoicesById.put(invoice.getId(), invoice);
                }
                List<Product> products = invoicesById.get(idFromDB).getProducts();
                if (resultSet.getString("ball_id") != null){
                    Ball ball = mapBall(resultSet);
                    if (!products.contains(ball)) {
                        products.add(ball);
                    }
                }
                if (resultSet.getString("phone_id") != null){
                    Phone phone = mapPhone(resultSet);
                    if (!products.contains(phone)) {
                        products.add(phone);
                    }
                }
                if (resultSet.getString("laptop_id") != null){
                    Laptop laptop = mapLaptop(resultSet);
                    if (!products.contains(laptop)) {
                        products.add(laptop);
                    }
                }
            }
            return invoicesById.values().stream().findFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Invoice invoice) {
        String update = "UPDATE shop.invoice SET sum = ?, time = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setDouble(1, invoice.getSum());
            preparedStatement.setDate(2, Date.valueOf(invoice.getTime().toLocalDate()));
            preparedStatement.setString(3, invoice.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getInvoiceCount() {
        String count = "SELECT count(id) AS count FROM shop.invoice";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(count);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Long> groupBySum() {
        Map<Long, Long> countSum = new HashMap<>();
        String groupBySum = "SELECT count(id) AS count, invoice.sum FROM shop.invoice GROUP BY invoice.sum;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(groupBySum);
            while (resultSet.next()) {
                countSum.put(resultSet.getLong("sum"), resultSet.getLong("count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countSum;
    }

}
