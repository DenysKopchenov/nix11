package com.alevel.lesson10.shop.repository.impl.jdbc;

import com.alevel.lesson10.shop.config.JDBCConfig;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneRepositoryPostgresJDBCImpl implements PhoneRepository {

    private final Connection connection;

    public PhoneRepositoryPostgresJDBCImpl() {
        connection = JDBCConfig.getConnection();
    }

    @Override
    public void save(Phone product) {
        String insert = "INSERT INTO \"shop\".\"phone\" (id, count, price, manufacturer, title, model) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            setPhoneFields(product, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setPhoneFields(Phone product, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, product.getId());
        preparedStatement.setInt(2, product.getCount());
        preparedStatement.setLong(3, product.getPrice());
        preparedStatement.setString(4, product.getManufacturer().name());
        preparedStatement.setString(5, product.getTitle());
        preparedStatement.setString(6, product.getModel());
    }

    @Override
    public void saveAll(List<Phone> products) {
        String save = "INSERT INTO \"shop\".\"phone\" (id, count, price, manufacturer, title, model) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            for (Phone product : products) {
                setPhoneFields(product, preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Phone> findAll() {
        List<Phone> phones = new ArrayList<>();
        String selectAll = "SELECT * FROM \"shop\".\"phone\";";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                phones.add(mapPhone(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phones;
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
    public Optional<Phone> findById(String id) {
        String selectById = "SELECT * FROM \"shop\".\"phone\" WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapPhone(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Phone product) {
        String update = "UPDATE \"shop\".\"phone\" SET count = ?, price = ?, manufacturer = ?, title = ?, model = ?  WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setInt(1, product.getCount());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setString(3, product.getManufacturer().toString());
            preparedStatement.setString(4, product.getTitle());
            preparedStatement.setString(5, product.getModel());
            preparedStatement.setString(6, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        String delete = "DELETE FROM \"shop\".\"phone\" WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
