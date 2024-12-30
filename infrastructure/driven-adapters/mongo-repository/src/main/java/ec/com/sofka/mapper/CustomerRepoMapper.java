package ec.com.sofka.mapper;

import ec.com.sofka.Customer;
import ec.com.sofka.document.ClientEntity;

public class CustomerRepoMapper {

    public static Customer toDomain(ClientEntity client) {
        if (client == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(client.getId());
        customer.setIdentification(client.getIdentification());
        customer.setFirstName(client.getFirstName());
        customer.setLastName(client.getLastName());
        customer.setEmail(client.getEmail());
        customer.setPhone(client.getPhone());
        customer.setAddress(client.getAddress());
        customer.setBirthDate(client.getBirthDate());

        return customer;
    }

    public static ClientEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }

        ClientEntity client = new ClientEntity();
        client.setId(customer.getId());
        client.setIdentification(customer.getIdentification());
        client.setFirstName(customer.getFirstName());
        client.setLastName(customer.getLastName());
        client.setEmail(customer.getEmail());
        client.setPhone(customer.getPhone());
        client.setAddress(customer.getAddress());
        client.setBirthDate(customer.getBirthDate());

        return client;
    }
}
