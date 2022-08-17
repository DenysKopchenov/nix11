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
        long start = System.currentTimeMillis();
        String selectInvoice = "SELECT * FROM shop.invoice WHERE sum > ?";
        try (PreparedStatement statement = connection.prepareStatement(selectInvoice)) {
            statement.setDouble(1, sum);
            ResultSet resultSet = statement.executeQuery();
            Map<String, Invoice> invoicesById = new HashMap<>();
            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(resultSet.getString("id"));
                invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.now()));
                invoice.setSum(resultSet.getDouble("sum"));
                invoicesById.put(invoice.getId(), invoice);
            }

            for (Invoice invoice : invoicesById.values()) {
                List<Product> products = new ArrayList<>();
                products.addAll(findAllPhonesOfInvoice(invoice.getId()));
                products.addAll(findAllBallsOfInvoice(invoice.getId()));
                products.addAll(findAllLaptopsOfInvoice(invoice.getId()));
                invoice.setProducts(products);
            }

            System.out.println(System.currentTimeMillis() - start);
            return invoicesById.values().stream().toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Ball> findAllBallsOfInvoice(String invoiceId) {
        List<Ball> balls = new ArrayList<>();
        String selectAll = "SELECT * FROM shop.ball WHERE invoice_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            statement.setString(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                balls.add(mapBall(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balls;
    }

    private List<Laptop> findAllLaptopsOfInvoice(String invoiceId) {
        List<Laptop> laptops = new ArrayList<>();
        String selectAll = "SELECT * FROM shop.laptop WHERE invoice_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            statement.setString(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laptops.add(mapLaptop(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return laptops;
    }

    private List<Phone> findAllPhonesOfInvoice(String invoiceId) {
        List<Phone> phones = new ArrayList<>();
        String selectAll = "SELECT * FROM shop.phone WHERE invoice_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            statement.setString(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                phones.add(mapPhone(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phones;
    }

    private Laptop mapLaptop(ResultSet resultSet) throws SQLException {
        Laptop laptop = new Laptop.Builder(
                resultSet.getLong("price"),
                EnumUtils.getEnum(CPU.class, resultSet.getString("cpu"), CPU.NONE)).build();
        laptop.setId(resultSet.getString("id"));
        laptop.setCount(resultSet.getInt("count"));
        laptop.setTitle(resultSet.getString("title"));
        return laptop;
    }


    private Ball mapBall(ResultSet resultSet) throws SQLException {
        Ball ball = new Ball();
        ball.setId(resultSet.getString("id"));
        ball.setCount(resultSet.getInt("count"));
        ball.setPrice(resultSet.getLong("price"));
        ball.setSize(EnumUtils.getEnum(Size.class, resultSet.getString("size"), Size.NONE));
        ball.setTitle(resultSet.getString("title"));
        return ball;
    }

    private Phone mapPhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone(resultSet.getString("title"),
                resultSet.getInt("count"),
                resultSet.getLong("price"),
                resultSet.getString("model"),
                EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer")));
        phone.setId(resultSet.getString("id"));
        return phone;
    }

    @Override
    public Optional<Invoice> findById(String id) {
        long start = System.currentTimeMillis();
        String selectInvoice = "SELECT * FROM shop.invoice WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectInvoice)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(resultSet.getString("id"));
                invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.now()));
                invoice.setSum(resultSet.getDouble("sum"));

                List<Product> products = new ArrayList<>();
                products.addAll(findAllPhonesOfInvoice(invoice.getId()));
                products.addAll(findAllBallsOfInvoice(invoice.getId()));
                products.addAll(findAllLaptopsOfInvoice(invoice.getId()));
                invoice.setProducts(products);

                System.out.println(System.currentTimeMillis() - start);
                return Optional.of(invoice);
            }
            return Optional.empty();
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
