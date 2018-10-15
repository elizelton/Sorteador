package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.PessoaRepository;
import repository.PremioRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = PessoaRepository.class)
//@Configuration
//@EnableMongoRepositories(basePackageClasses = PremioRepository.class)

public class DBConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "Sorteador";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost");
       
        return client;
    }
    
}
