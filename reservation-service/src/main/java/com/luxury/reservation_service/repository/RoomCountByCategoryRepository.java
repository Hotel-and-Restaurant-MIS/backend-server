package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.exception.StoredProcedureCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Map;

@Repository
public class RoomCountByCategoryRepository {

    public static final Logger LOG = LoggerFactory.getLogger(RoomCountByCategoryRepository.class);

    private final SimpleJdbcCall roomCountByCategoryProcCall;

    // Constructor to initialize the SimpleJdbcCall with the procedure name
    @Autowired
    public RoomCountByCategoryRepository(DataSource dataSource) {
        this.roomCountByCategoryProcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("GetAvailableRoomCount");
    }

    // Method to call the stored procedure and get available room counts
    public Map<String, Object> getAvailableRoomCount(LocalDate checkinDate, LocalDate checkoutDate) {
        try {
            // Set input parameters for the stored procedure
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("checkin_date", checkinDate)
                    .addValue("checkout_date", checkoutDate);

            // Execute the stored procedure and return the result
            return roomCountByCategoryProcCall.execute(in);
        }
        catch (Exception e) {
            // Log error and throw a custom exception if an error occurs
            LOG.error("Error occurred while calling getAvailableRoomCount: " + e.getMessage());
            throw new StoredProcedureCallException(e.getMessage());
        }
    }
}
