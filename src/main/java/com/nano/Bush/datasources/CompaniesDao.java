package com.nano.Bush.datasources;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompaniesDao {

    @Autowired
    PostgresConnector postgresConnector;
    private Statement statement;
    private ResultSet resultSet;

    @PostConstruct
    public void init() throws SQLException {
        statement = postgresConnector.getConnection().createStatement();
    }

    public void insert(Company company) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO compania VALUES (default, ?,?)");

        preparedStatement.setString(1, company.getName());
        preparedStatement.setString(2, company.getDescription());

        preparedStatement.executeUpdate();
    }

    public List<Company> getCompanies() throws SQLException {
        resultSet = statement.executeQuery("SELECT idCompania,nombre,descripcion FROM compania");
        List<Company> companies = new ArrayList<>();
        while (resultSet.next()) {
            companies.add(new Company(Optional.of(resultSet.getInt("idCompania")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return companies;
    }

    public void getCompany(Company Company) throws SQLException {
        String query = "SELECT Nombre,Descripcion FROM compania WHERE nombre = '" + Company.getName() + "'";
        resultSet = statement.executeQuery(query);
    }

    public void delete(Company company) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor("DELETE FROM compania WHERE idCompania = " + company);
        preparedStatement.executeUpdate();
    }

    public void modify(Company company) throws SQLException {
        postgresConnector.update("compania", "nombre", company.getName(), "idCompania", company.getCompanyId().get());
        postgresConnector.update("compania", "descripcion", company.getDescription(), "idCompania", company.getCompanyId().get());
    }
}
