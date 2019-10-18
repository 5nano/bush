package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .getPreparedStatementFor("INSERT INTO tag (idTag,nombre,descripcion) VALUES (default, ?,?) RETURNING idTag,nombre,descripcion");

        preparedStatement.setString(1, tag.getName());
        preparedStatement.setString(2, tag.getDescription());
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion"));
    }

    public void update(Tag tag) throws SQLException {
        postgresConnector.update("tag", "nombre", tag.getName(), "idTag", tag.getIdTag().get());
        postgresConnector.update("tag", "descripcion", tag.getDescription(), "idTag", tag.getIdTag().get());
    }

    public List<Tag> getTags() throws SQLException {
        resultSet = statement.executeQuery("SELECT idTag,nombre,descripcion FROM tag");
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return tags;
    }

    public List<Tag> getTagsFrom(Integer idAssay) throws SQLException {
        resultSet = statement.executeQuery("SELECT idTag,nombre,descripcion FROM tag WHERE idTag IN\n" +
                "(SELECT idTag FROM tagEnsayo WHERE idEnsayo = '" + idAssay + "')");
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return tags;
    }

    public List<Assay> getAssayFrom(List<String> tagsNames) throws SQLException {
        Map<String, List<String>> assayWithTags = new HashMap<>();

        List<String> idTags = this.getTags().stream()
                .filter(tag -> tagsNames.contains(tag.getName()))
                .map(tag -> tag.getIdTag().get().toString()).collect(Collectors.toList());

        resultSet = statement.executeQuery("SELECT idEnsayo, array_to_string(array_agg(idtag),',') AS tagsByAssay " +
                "FROM tagEnsayo GROUP BY idensayo");

        while (resultSet.next()) {
            assayWithTags.put(resultSet.getString("idEnsayo"), Arrays.asList(resultSet.getString("tagsByAssay").split(",")));
        }

        List<Integer> idAssays = assayWithTags.entrySet().stream()
                .filter(assayWithTagsElem -> assayWithTagsElem.getValue().containsAll(idTags))
                .map(assayFiltered -> Integer.parseInt(assayFiltered.getKey())).collect(Collectors.toList());

        return assaysDao.getAssays().stream().filter(assay -> idAssays.contains(assay.getIdAssay().get())).collect(Collectors.toList());

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
}
