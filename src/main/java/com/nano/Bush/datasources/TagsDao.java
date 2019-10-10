package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Company;
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
    private Statement statement;
    private ResultSet resultSet;

    @Autowired
    AssaysDao assaysDao;

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
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO (idTag,nombre,descripcion) VALUES (default, ?,?)");
        preparedStatement.setString(1, tag.getName());
        preparedStatement.setString(2, tag.getDescription());

        preparedStatement.executeUpdate();
    }

    public void delete(String tagName) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("DELETE FROM tag WHERE nombre ='" + tagName + "'");
        preparedStatement.executeUpdate();
    }

    public void modify(Tag tag) throws SQLException {
        this.delete(tag.getName());
        this.update(tag);
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
        resultSet = statement.executeQuery("select idTag,nombre,descripcion from tag where idTag in\n" +
                "(select idTag from tagEnsayo where idEnsayo = '" + idAssay + "')");
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(Optional.of(resultSet.getInt("idTag")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return tags;
    }

    public List<Assay> getAssayFrom(List<String> tagsNames) throws SQLException {
        Map<String, List<String>> assayWithTags = new HashMap<>();
        List<Assay> assays = new ArrayList<>();

        List<Integer> idTags = this.getTags().stream().map(tag -> tag.getIdTag().get()).collect(Collectors.toList());

        resultSet = statement.executeQuery("select idEnsayo, array_to_string(array_agg(idtag),',') as tagsByAssay" +
                "from tagEnsayo group by idensayo");

        while (resultSet.next()) {
            assayWithTags.put(resultSet.getString("idEnsayo"), Arrays.asList(resultSet.getString("tagsByAssay").split(",")));
        }

        List<Integer> idAssays = assayWithTags.entrySet().stream().filter(assay -> tagsNames.containsAll(assay.getValue()))
                .map(assayFiltered -> Integer.parseInt(assayFiltered.getKey())).collect(Collectors.toList());

        return assaysDao.getAssays().stream().filter(assay-> idAssays.contains(assay.getIdAssay().get())).collect(Collectors.toList());

    }

    public void insertIntoAssay(Integer idTag, Integer idEnsayo) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO tagEnsayo (idTagEnsayo,idTag,idEnsayo) VALUES (default, ?,?)");

        preparedStatement.setInt(1, idTag);
        preparedStatement.setInt(2, idEnsayo);
        preparedStatement.executeQuery();
    }

    public void deleteIntoAssay(Integer idTag, Integer idEnsayo) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("delete from tagEnsayo where idTag ='" + idTag + "' and idEnsayo ='" + idEnsayo + "'");

        preparedStatement.setInt(1, idTag);
        preparedStatement.setInt(2, idEnsayo);
        preparedStatement.executeQuery();
    }
}
