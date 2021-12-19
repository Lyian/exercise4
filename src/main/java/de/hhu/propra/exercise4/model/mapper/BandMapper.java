package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Band;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BandMapper implements RowMapper<Band> {

    @Override
    public Band mapRow(ResultSet rs, int rowNum) throws SQLException {
        Band band = new Band();
        band.setBandid(rs.getInt("ID"));
        band.setName(rs.getString("Name"));
        band.setGeschichte(rs.getString("Geschichte"));
        return band;
    }
}
