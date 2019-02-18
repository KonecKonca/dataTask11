package com.epam.kozitski.dao;

import com.epam.kozitski.domain.Crime;

import java.util.List;

public interface CrimeDao {

    void clearDb();
    void insertAll(List<Crime> crimes);
    void insert(Crime crime);

}
