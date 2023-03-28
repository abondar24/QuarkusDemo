//package org.abondar.experimental.quarkusdemo.service;
//
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import org.abondar.experimental.quarkusdemo.model.Person;
//import org.bson.Document;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import java.util.ArrayList;
//import java.util.List;
//
//@ApplicationScoped
//public class PersonMongoService {
//
//    @Inject
//    private MongoClient client;
//
//
//    public void add(Person person) {
//        var doc = new Document()
//                .append("firstName", person.getFirstName())
//                .append("lastName", person.getLastName())
//                .append("phoneNumber", person.getPhoneNumber());
//
//        getCollection().insertOne(doc);
//    }
//
//    public List<Person> getAll() {
//        List<Person> res = new ArrayList<>();
//
//        try (var cursor = getCollection().find().iterator()) {
//            while (cursor.hasNext()) {
//                var doc = cursor.next();
//                res.add(new Person(doc.getString("firstName"),
//                        doc.getString("lastName"),
//                        doc.getString("phoneNumber")));
//            }
//        }
//
//        return res;
//    }
//
//
//    private MongoCollection<Document> getCollection() {
//        return client.getDatabase("person").getCollection("person");
//    }
//}
