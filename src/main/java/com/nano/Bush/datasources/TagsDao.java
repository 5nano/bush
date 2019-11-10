package com.nano.Bush.datasources;

import com.google.common.collect.Maps;
import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.AssayStatesEnum;
import com.nano.Bush.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Service
public class TagsDao {

    private static final Logger logger = LoggerFactory.getLogger(TagsDao.class);

    @Autowired
    PostgresConnector postgresConnector;
    @Autowired
    AssaysDao assaysDao;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public Tag insert(Tag tag) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO tag (idTag,nombre,descripcion,color) VALUES (default, ?,?) RETURNING idTag,nombre,descripcion");

        preparedStatement.setString(1, tag.getName());
        preparedStatement.setString(2, tag.getDescription());
        preparedStatement.setString(3, tag.getColor());
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getString("color"));
    }

    public void update(Tag tag) throws SQLException {
        postgresConnector.update("tag", "nombre", tag.getName(), "idTag", tag.getIdTag().get());
        postgresConnector.update("tag", "descripcion", tag.getDescription(), "idTag", tag.getIdTag().get());
        postgresConnector.update("tag", "color", tag.getColor(), "idTag", tag.getIdTag().get());
    }

    public List<Tag> getTags() throws SQLException {
        resultSet = statement.executeQuery("SELECT idTag,nombre,descripcion,color FROM tag");
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getString("color")));
        }
        return tags;
    }

    public List<Tag> getTagsFrom(Integer idAssay) throws SQLException {
        logger.info("Getting tags for assay {}", idAssay);
        resultSet = statement.executeQuery("SELECT idTag,nombre,descripcion,color FROM tag WHERE idTag IN\n" +
                "(SELECT idTag FROM tagEnsayo WHERE idEnsayo = '" + idAssay + "')");
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getString("color")));
        }
        logger.info("Finish from getting tags for {}", idAssay);
        return tags;
    }

    public Map<Integer, Set<Integer>> assayWithTags() throws SQLException {
        Map<Integer, Set<Integer>> assayWithTags = Maps.newHashMap();

        resultSet = statement.executeQuery("SELECT idEnsayo, array_to_string(array_agg(idTag),',') AS tagsByAssay " +
                "FROM tagEnsayo GROUP BY idEnsayo");

        while (resultSet.next()) {
            assayWithTags.put(resultSet.getInt("idEnsayo"), Arrays.stream(resultSet.getString("tagsByAssay").split(",")).map(tagId -> Integer.parseInt(tagId)).collect(Collectors.toSet()));
        }

        return assayWithTags;
    }

    public List<Assay> getAllAssayFrom(Integer idCompany, List<String> tagsNames) throws SQLException {
        Map<Integer, Set<Integer>> assayWithTags = assayWithTags();

        List<Integer> idTags = this.getTags().stream()
                .filter(tag -> tagsNames.contains(tag.getName()))
                .map(tag -> tag.getIdTag().get()).collect(Collectors.toList());


        List<Integer> idAssays = assayWithTags.entrySet().stream()
                .filter(assayWithTagsElem -> CollectionUtils.containsAny(assayWithTagsElem.getValue(), idTags))
                .map(assayFiltered -> assayFiltered.getKey())
                .collect(Collectors.toList());

        return assaysDao.getAllAssays(idCompany).stream().filter(assay -> idAssays.contains(assay.getIdAssay().get())).collect(Collectors.toList());

    }

    public List<Assay> getAssaysFromByState(Integer idCompany, List<String> tagsNames, AssayStatesEnum state) throws SQLException {
        Map<Integer, Set<Integer>> assayWithTags = assayWithTags();

        List<Integer> idTags = this.getTags().stream()
                .filter(tag -> tagsNames.contains(tag.getName()))
                .map(tag -> tag.getIdTag().get()).collect(Collectors.toList());


        List<Integer> idAssays = assayWithTags.entrySet().stream()
                .filter(assayWithTagsElem -> CollectionUtils.containsAny(assayWithTagsElem.getValue(), idTags))
                .map(assayFiltered -> assayFiltered.getKey())
                .collect(Collectors.toList());

        return assaysDao.getAssaysByState(idCompany, state).stream().filter(assay -> idAssays.contains(assay.getIdAssay().get())).collect(Collectors.toList());

    }

    public void insertIntoAssay(Integer idTag, Integer idEnsayo) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO tagEnsayo (idTagEnsayo,idTag,idEnsayo) VALUES (default, ?,?)");

        preparedStatement.setInt(1, idTag);
        preparedStatement.setInt(2, idEnsayo);
        preparedStatement.executeUpdate();
    }

    public void deleteIntoAssay(Integer idTag, Integer idEnsayo) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("delete from tagEnsayo where idTag ='" + idTag + "' and idEnsayo ='" + idEnsayo + "'");

        preparedStatement.executeUpdate();
    }

    public void deleteTag(Integer idTag) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("DELETE FROM tag WHERE idTag = " + idTag);

        preparedStatement.executeUpdate();
    }
}
