package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;

import java.util.List;

public interface PersonMongoService {

    void add(PersonRequest request);

    List<PersonResponse> getAll();
}
