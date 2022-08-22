package com.alevel.lesson10.shop.repository.impl.postgre;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.config.JDBCConfig;
import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class LaptopRepositoryPostgresImpl implements LaptopRepository {
    private final Connection connection;

    @Autowired
    public LaptopRepositoryPostgresImpl() {
        connection = JDBCConfig.getConnection();
    }

    @Override
    public void save(Laptop product) {
        String insert = "INSERT INTO \"shop\".\"laptop\" (id, count, price, cpu, title) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            setFields(product, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setFields(Laptop product, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, product.getId());
        preparedStatement.setInt(2, product.getCount());
        preparedStatement.setLong(3, product.getPrice());
        preparedStatement.setString(4, product.getCpu().toString());
        preparedStatement.setString(5, product.getTitle());
    }

    @Override
    public void saveAll(List<Laptop> products) {
        String save = "INSERT INTO \"shop\".\"laptop\" (id, count, price, cpu, title) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            for (Laptop product : products) {
                setFields(product, preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Laptop> findAll() {
        List<Laptop> laptops = new ArrayList<>();
        String selectAll = "SELECT * FROM \"shop\".\"laptop\";";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                laptops.add(mapLaptop(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return laptops;
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

    @Override
    public Optional<Laptop> findById(String id) {
        String selectById = "SELECT * FROM \"shop\".\"laptop\" WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapLaptop(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Laptop product) {
        String update = "UPDATE \"shop\".\"laptop\" SET count = ?, price = ?, cpu = ?, title = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setInt(1, product.getCount());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setString(3, product.getCpu().toString());
            preparedStatement.setString(4, product.getTitle());
            preparedStatement.setString(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(String id) {
        String delete = "DELETE FROM shop.laptop WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
