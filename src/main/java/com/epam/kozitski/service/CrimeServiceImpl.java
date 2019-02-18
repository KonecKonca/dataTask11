package com.epam.kozitski.service;

import com.epam.kozitski.domain.Crime;
import com.epam.kozitski.dto.CrimeDto;
import com.epam.kozitski.util.CrimeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CrimeServiceImpl implements CrimeService{
    private static final String COMMON_URL = "https://data.police.uk/api/crimes-street/all-crime";
    private static final String PARAMETER_DELIMETER = "?";
    private static final String PARAMETER_INIT = "=";
    private static final String PARAMETER_UNION = "&";
    private static final String POLY_PARAMETER = "poly";
    private static final String DATE_PARAMETER = "date";

    private CrimeDtoMapper crimeDtoMapper;
    @Autowired
    public void setCrimeDtoMapper(CrimeDtoMapper crimeDtoMapper) {
        this.crimeDtoMapper = crimeDtoMapper;
    }

    @Override
    public List<Crime> getAllCrimes(String poly, String date) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        StringBuilder stringUri = new StringBuilder(COMMON_URL);
        stringUri.append(PARAMETER_DELIMETER);
        stringUri.append(POLY_PARAMETER);
        stringUri.append(PARAMETER_INIT);
        stringUri.append(poly);

        stringUri.append(PARAMETER_UNION);

        stringUri.append(DATE_PARAMETER);
        stringUri.append(PARAMETER_INIT);
        stringUri.append(date);

        URI uri = new URI(stringUri.toString());

        CrimeDto[] crimeDtos = restTemplate.getForObject(uri, CrimeDto[].class);

        ArrayList<CrimeDto> crimesList = new ArrayList<>(Arrays.asList(crimeDtos));
        List<Crime> crimes = crimeDtoMapper.mapToListCrimes(crimesList);

        return crimes;
    }

    @Override
    public void clearDb() { }

    @Override
    public void updateDb(List<Crime> crimes) { }

}
