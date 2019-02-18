package com.epam.kozitski.dao;

import com.epam.kozitski.domain.Crime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class CrimeDaoImpl implements CrimeDao {
    private static final String DELETE_ALL_SREETS_SQL = "DELETE from streets";
    private static final String DELETE_ALL_LOCATIONS_SQL = "DELETE from locations";
    private static final String DELETE_ALL_STATUSES_SQL = "DELETE from outcomestatuses";
    private static final String DELETE_ALL_CRIMES_SQL = "DELETE from crimes";

    private static final String INSERT_STREET = "INSERT INTO streets VALUES (NULL, :name)";
    private static final String INSERT_LOCATION = "INSERT INTO locations VALUES (NULL, :latitude, :longtitude, :street_id)";
    private static final String INSERT_STATUS = "INSERT INTO outcomestatuses VALUES (NULL, :category, :status_date)";
    private static final String INSERT_CRIME = "INSERT INTO crimes VALUES (:crime_id, :location_type, " +
            ":location_id, :context, :outcome_status_id, :persistence_id, :location_subtype, :month, :category)";

    private static final String STREET_NAME = "name";
    private static final String LOCATION_LATITUDE = "latitude";
    private static final String LOCATION_LONGTITUDE = "longtitude";
    private static final String LOCATION_STREET_ID = "street_id";

    private static final String STATUS_CATEGORY = "category";
    private static final String STATUS_DATE = "status_date";

    private static final String CRIME_ID = "crime_id";
    private static final String CRIME_LOCATION_TYPE = "location_type";
    private static final String CRIME_LOCATION_ID = "location_id";
    private static final String CRIME_CONTEXT = "context";
    private static final String CRIME_STATUS_ID = "outcome_status_id";
    private static final String CRIME_PERSISTANCE_ID = "persistence_id";
    private static final String CRIME_LOCATION_SUBTYPE = "location_subtype";
    private static final String CRIME_MOUNTSH = "month";
    private static final String CRIME_CATEGORY = "category";


    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void clearDb() {
        jdbcTemplate.update(DELETE_ALL_CRIMES_SQL);
        jdbcTemplate.update(DELETE_ALL_STATUSES_SQL);
        jdbcTemplate.update(DELETE_ALL_LOCATIONS_SQL);
        jdbcTemplate.update(DELETE_ALL_SREETS_SQL);
    }

    @Override
    @Transactional
    public void insert(Crime crime) {
        MapSqlParameterSource streetParams = new MapSqlParameterSource();
        KeyHolder streetHolder = new GeneratedKeyHolder();
        streetParams.addValue(STREET_NAME, crime.getLocation().getStreet().getName());
        namedParameterJdbcTemplate.update(INSERT_STREET, streetParams, streetHolder);

        MapSqlParameterSource locationParams = new MapSqlParameterSource();
        KeyHolder locationHolder = new GeneratedKeyHolder();
        locationParams.addValue(LOCATION_LATITUDE, crime.getLocation().getLatitude());
        locationParams.addValue(LOCATION_LONGTITUDE, crime.getLocation().getLongitude());
        locationParams.addValue(LOCATION_STREET_ID, Objects.requireNonNull(streetHolder.getKey()).longValue());
        namedParameterJdbcTemplate.update(INSERT_LOCATION, locationParams, locationHolder);

        MapSqlParameterSource statusParams = new MapSqlParameterSource();
        KeyHolder statusHolder = new GeneratedKeyHolder();
        statusParams.addValue(STATUS_CATEGORY, crime.getOutcomeStatus().getCategory());
        statusParams.addValue(STATUS_DATE, crime.getOutcomeStatus().getDate());
        namedParameterJdbcTemplate.update(INSERT_STATUS, statusParams, statusHolder);

        MapSqlParameterSource crimesParams = new MapSqlParameterSource();
        crimesParams.addValue(CRIME_ID, crime.getId());
        crimesParams.addValue(CRIME_LOCATION_TYPE, crime.getLocationType());
        crimesParams.addValue(CRIME_LOCATION_ID, Objects.requireNonNull(locationHolder.getKey()).longValue());
        crimesParams.addValue(CRIME_CONTEXT, crime.getContext());
        crimesParams.addValue(CRIME_STATUS_ID, Objects.requireNonNull(statusHolder.getKey()).longValue());
        crimesParams.addValue(CRIME_PERSISTANCE_ID, crime.getPersistentId());
        crimesParams.addValue(CRIME_LOCATION_SUBTYPE, crime.getLocationSubtype());
        crimesParams.addValue(CRIME_MOUNTSH, crime.getMonth());
        crimesParams.addValue(CRIME_CATEGORY, crime.getCategory());
        namedParameterJdbcTemplate.update(INSERT_CRIME, crimesParams);
    }

    @Override
    public void insertAll(List<Crime> crimes) {
        crimes.forEach(this::insert);
    }

}
