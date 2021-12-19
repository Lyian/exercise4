package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Comment;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setKommentarid(rs.getInt("ROWID"));
        comment.setTitleid(rs.getInt("TitelID"));
        comment.setText(rs.getString("Kommentar"));
        return comment;
    }
}
