package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.Title;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.hhu.propra.exercise4.service.helpers.FilterHelpers.*;

@Service
public class TitleService {

    @Autowired
    TitleRepository titleRepository;

    public List<Title> getAllTitlesByFilter(Integer duration, String name) throws Exception{
        List<Title> titles = titleRepository.getAllTitles();

        return titles
                .stream()
                .filter(title -> filterStringContainsIfNotNull(name, title.getBezeichnung()))
                .filter(title -> filterSmallerThan(duration, title.getDauer()))
                .collect(Collectors.toList());
    }

    public int createNewTitleForUser(Title title, User user) throws Exception {
        int titleId = titleRepository.createNewTitle(title);
        title.setTitleid(titleId);
        titleRepository.insertNewTitleForArtist(user, title);
        return titleId;
    }
}
