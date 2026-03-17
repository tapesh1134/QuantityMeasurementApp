package org.quantitymeasurement.app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.quantitymeasurement.app.config.DatabaseConnection;
import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static QuantityMeasurementDatabaseRepository instance;

	private QuantityMeasurementDatabaseRepository() {
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {

		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}

		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {

		String sql = """
				INSERT INTO quantity_measurement_history (
				    this_value, this_unit, this_measurement_type,
				    that_value, that_unit, that_measurement_type,
				    operation,
				    result_value, result_unit, result_measurement_type,
				    result_string,
				    is_error, error_message
				) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDouble(1, entity.thisValue);
			stmt.setString(2, entity.thisUnit);
			stmt.setString(3, entity.thisMeasurementType);

			if (entity.thatUnit != null) {
				stmt.setDouble(4, entity.thatValue);
				stmt.setString(5, entity.thatUnit);
				stmt.setString(6, entity.thatMeasurementType);
			} else {
				stmt.setNull(4, Types.DOUBLE);
				stmt.setNull(5, Types.VARCHAR);
				stmt.setNull(6, Types.VARCHAR);
			}

			stmt.setString(7, entity.operation);

			if (entity.resultUnit != null) {
				stmt.setDouble(8, entity.resultValue);
				stmt.setString(9, entity.resultUnit);
				stmt.setString(10, entity.resultMeasurementType);
			} else {
				stmt.setNull(8, Types.DOUBLE);
				stmt.setNull(9, Types.VARCHAR);
				stmt.setNull(10, Types.VARCHAR);
			}

			stmt.setString(11, entity.resultString);
			stmt.setBoolean(12, entity.isError);
			stmt.setString(13, entity.errorMessage);

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("Error saving measurement to database", e);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> findAll() {

		List<QuantityMeasurementEntity> list = new ArrayList<>();

		String sql = "SELECT * FROM quantity_measurement_history ORDER BY created_at DESC";

		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				list.add(mapRowToEntity(rs));
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error fetching measurements", e);
		}

		return list;
	}

	private QuantityMeasurementEntity mapRowToEntity(ResultSet rs) throws SQLException {

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.thisValue = rs.getDouble("this_value");
		entity.thisUnit = rs.getString("this_unit");
		entity.thisMeasurementType = rs.getString("this_measurement_type");

		entity.thatValue = rs.getDouble("that_value");
		entity.thatUnit = rs.getString("that_unit");
		entity.thatMeasurementType = rs.getString("that_measurement_type");

		entity.operation = rs.getString("operation");

		entity.resultValue = rs.getDouble("result_value");
		entity.resultUnit = rs.getString("result_unit");
		entity.resultMeasurementType = rs.getString("result_measurement_type");

		entity.resultString = rs.getString("result_string");

		entity.isError = rs.getBoolean("is_error");
		entity.errorMessage = rs.getString("error_message");

		return entity;
	}
}