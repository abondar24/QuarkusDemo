package org.abondar.experimental.quarkusdemo.service;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.bson.Document;


import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PersonMongoService {

    @Inject
    MongoClient client;

    public void add(PersonRequest request) {
        var doc = new Document()
                .append("firstName", request.firstName())
                .append("lastName",request.lastName())
                .append("phoneNumber", request.phoneNumber());

        getCollection().insertOne(doc);
    }

    public List<PersonResponse> getAll() {
        List<PersonResponse> res = new ArrayList<>();

        try (var cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                var doc = cursor.next();
                res.add(new PersonResponse(0,doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("phoneNumber")));
            }
        }

        return res;
    }


    private MongoCollection<Document> getCollection() {
        return client.getDatabase("person").getCollection("person");
    }
}
