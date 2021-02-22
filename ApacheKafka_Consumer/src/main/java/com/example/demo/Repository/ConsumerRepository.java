package com.example.demo.Repository;

import com.example.demo.Model.DBModelList;
import com.example.demo.Model.MessageModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sun.security.pkcs11.Secmod;

@Repository
public interface ConsumerRepository extends MongoRepository<DBModelList, String> {

}
