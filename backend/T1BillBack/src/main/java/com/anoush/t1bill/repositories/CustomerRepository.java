package com.anoush.t1bill.repositories;
import com.anoush.t1bill.Customer;
import java.util.List;

//import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository {
        Customer save(Customer customer);
        boolean delete(long id);
        Customer findOne(long id);

        List<Customer> findByUsername(String firstName, String lastName);
        List<Customer> findByEmailAddress(String address);
        List<Customer> findByPhone(String phoneNumber);
        List<Customer> findAll();

    // A lot more coming ...
}
                              