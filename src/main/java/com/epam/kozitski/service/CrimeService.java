package com.epam.kozitski.service;

import com.epam.kozitski.domain.Crime;

import java.net.URISyntaxException;
import java.util.List;

public interface CrimeService {

    List<Crime> getAllCrimes(String poly, String date) throws URISyntaxException;
    void clearDb();
    void updateDb(List<Crime> crimes);

}
