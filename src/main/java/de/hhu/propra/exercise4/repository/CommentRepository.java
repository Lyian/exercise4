package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Comment;
import de.hhu.propra.exercise4.model.entity.Title;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.CommentMapper;
import de.hhu.propra.exercise4.model.mapper.TitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Comment> getAllCommentsByTitleId(Title title) throws  Exception{
        return jdbcTemplate.query("Select nutzer_kommentar_titel.ROWID as KommentarId, T.ID as TitelId, Kommentar from nutzer_kommentar_titel join Titel T on nutzer_kommentar_titel.TitelID = T.ID where TitelID = ?",
                new CommentMapper(),
                title.getTitleid()
        );
    }

    public Integer createComment(User user, int titleId, String comment) throws Exception {
        return jdbcTemplate.update("INSERT INTO nutzer_kommentar_titel (Email, TitelID, Kommentar) VALUES(?,?,?)", user.getEmail(), titleId, comment);
    }

    public void updateComment(User user, int kommentarid, String text) {
        return jdbcTemplate.update("UPDATE nutzer_kommentar_titel SET Kommentar=? where Email = ? and TitelID = ?", user.getEmail(), kommentarid, );
    }
}
