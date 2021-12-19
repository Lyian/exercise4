package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Title;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TitleMapper implements RowMapper<Title> {

    @Override
    public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
        Title title = new Title();
        title.setTitleid(rs.getInt("ID"));
        title.setBezeichnung(rs.getString("Name"));
        title.setDauer(rs.getInt("Dauer"));
        title.setSpeicherort_lq(rs.getString("PfadLQ"));
        title.setSpeicherort_hq(rs.getString("PfadHQ"));
        return title;
    }
}
