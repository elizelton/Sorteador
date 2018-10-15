package repository;

import model.Premio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremioRepository extends MongoRepository<Premio, String>
{

}
