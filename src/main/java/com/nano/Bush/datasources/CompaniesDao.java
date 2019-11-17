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

    public void insert(Company company) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector
                .getPreparedStatementFor("INSERT INTO compania VALUES (default, ?,?)");

        preparedStatement.setString(1, company.getName());
        preparedStatement.setString(2, company.getDescription());

        preparedStatement.executeUpdate();
    }

    public List<Company> getCompanies() throws SQLException {
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery("SELECT idCompania,nombre,descripcion FROM compania");
        List<Company> companies = new ArrayList<>();
        while (resultSet.next()) {
            companies.add(new Company(Optional.of(resultSet.getInt("idCompania")), resultSet.getString("nombre"), resultSet.getString("descripcion")));
        }
        return companies;
    }

    public void getCompany(Company Company) throws SQLException {
        String query = "SELECT Nombre,Descripcion FROM compania WHERE nombre = '" + Company.getName() + "'";
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery(query);
    }

    public Optional<Company> getCompany(Integer id) throws SQLException {
        Optional<Company> company = Optional.empty();
        String query = "SELECT idCompania,Nombre,Descripcion FROM compania WHERE idCompania = " + id;
        ResultSet resultSet = postgresConnector.getConnection().createStatement().executeQuery(query);
        while (resultSet.next()) {
            company = Optional.of(new Company(Optional.of(resultSet.getInt("idCompania")), resultSet.getString("nombre"),
                    resultSet.getString("descripcion")));
        }
        return company;
    }

    public void delete(Integer companyId) throws SQLException {
        PreparedStatement preparedStatement = postgresConnector.getPreparedStatementFor("DELETE FROM compania WHERE idCompania = " + companyId);
        preparedStatement.executeUpdate();
    }

    public void modify(Company company) throws SQLException {
        postgresConnector.update("compania", "nombre", company.getName(), "idCompania", company.getCompanyId().get());
        postgresConnector.update("compania", "descripcion", company.getDescription(), "idCompania", company.getCompanyId().get());
    }
}
