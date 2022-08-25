package com.alevel.lesson10.shop.repository.impl.jdbc;

import com.alevel.lesson10.shop.config.JDBCConfig;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BallRepositoryPostgresJDBCImpl implements BallRepository {

    private final Connection connection;

    public BallRepositoryPostgresJDBCImpl() {
        connection = JDBCConfig.getConnection();
    }

    @Override
    public void save(Ball product) {
        String insert = "INSERT INTO \"shop\".\"ball\" (id, count, price, size, title) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            setBallFields(product, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setBallFields(Ball product, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, product.getId());
        preparedStatement.setInt(2, product.getCount());
        preparedStatement.setLong(3, product.getPrice());
        preparedStatement.setString(4, product.getSize().toString());
        preparedStatement.setString(5, product.getTitle());
    }

    @Override
    public void saveAll(List<Ball> products) {
        String save = "INSERT INTO \"shop\".\"ball\" (id, count, price, size, title) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            for (Ball product : products) {
                setBallFields(product, preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ball> findAll() {
        List<Ball> balls = new ArrayList<>();
        String selectAll = "SELECT * FROM \"shop\".\"ball\";";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                balls.add(mapBall(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balls;
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

    @Override
    public Optional<Ball> findById(String id) {
        String selectById = "SELECT * FROM \"shop\".\"ball\" WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapBall(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Ball product) {
        String update = "UPDATE \"shop\".\"ball\" SET count = ?, price = ?, size = ?, title = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setInt(1, product.getCount());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setString(3, product.getSize().toString());
            preparedStatement.setString(4, product.getTitle());
            preparedStatement.setString(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(String id) {
        String delete = "DELETE FROM \"shop\".\"ball\" WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
