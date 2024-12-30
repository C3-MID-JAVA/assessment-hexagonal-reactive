package ec.com.sofka.mapper;

import ec.com.sofka.Customer;
import ec.com.sofka.data.CustomerInDTO;
import ec.com.sofka.data.CustomerOutDTO;

public class CustomerMapper {

    public static Customer toEntity(CustomerInDTO clientInDTO) {
        if (clientInDTO == null) {
            return null;
        }

        Customer client = new Customer();
        client.setIdentification(clientInDTO.getIdentification());
        client.setFirstName(clientInDTO.getFirstName());
        client.setLastName(clientInDTO.getLastName());
        client.setEmail(clientInDTO.getEmail());
        client.setPhone(clientInDTO.getPhone());
        client.setAddress(clientInDTO.getAddress());
        client.setBirthDate(clientInDTO.getBirthDate());

        return client;
    }

    public static CustomerOutDTO toDTO(Customer client) {
        if (client == null) {
            return null;
        }

        CustomerOutDTO clientOutDTO = new CustomerOutDTO();
        clientOutDTO.setId(client.getId());
        clientOutDTO.setIdentification(client.getIdentification());
        clientOutDTO.setFirstName(client.getFirstName());
        clientOutDTO.setLastName(client.getLastName());
        clientOutDTO.setEmail(client.getEmail());
        clientOutDTO.setPhone(client.getPhone());
        clientOutDTO.setAddress(client.getAddress());
        clientOutDTO.setBirthDate(client.getBirthDate());

        return clientOutDTO;
    }

}
