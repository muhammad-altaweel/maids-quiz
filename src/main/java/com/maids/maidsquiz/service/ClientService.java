package com.maids.maidsquiz.service;

import com.maids.maidsquiz.data.Client;
import com.maids.maidsquiz.data.Sale;
import com.maids.maidsquiz.dto.ClientDto;
import com.maids.maidsquiz.repository.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientService {
	private final ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	public Client getClientById(Long id) throws Exception{
		return clientRepository.findById(id)
				.orElseThrow(() -> new Exception("Client not found"));
	}

	public Client createClient(ClientDto client) {
		Client newClient = new Client();
		newClient.setAddress(client.getAddress());
		newClient.setEmail(client.getEmail());
		newClient.setLastName(client.getLastName());
		newClient.setMobile(client.getMobile());
		return clientRepository.save(newClient);
	}

	public Client updateClient(Long id, ClientDto updatedClient) throws Exception {
		Client existingClient = clientRepository.findById(id)
				.orElseThrow(() -> new Exception("Client not found"));

		existingClient.setName(updatedClient.getName());
		existingClient.setLastName(updatedClient.getLastName());
		existingClient.setMobile(updatedClient.getMobile());
		existingClient.setEmail(updatedClient.getEmail());
		existingClient.setAddress(updatedClient.getAddress());

		return clientRepository.save(existingClient);
	}

	public void deleteClient(Long id) throws Exception {
		if (!clientRepository.existsById(id)) {
			throw new Exception("Client not found");
		}

		clientRepository.deleteById(id);
	}
	public ResponseEntity<String> generateReport() {
		StringBuilder report = new StringBuilder();

		List<Client> clients = clientRepository.findAll();

		// Total number of clients
		int totalClients = clients.size();
		report.append("Total number of clients: ").append(totalClients).append("\n\n");

		// Top-spending clients
		List<Client> topSpendingClients = clients.stream()
				.sorted(Comparator.comparingDouble(client -> calculateTotalSpending(client.getSales())))
				.collect(Collectors.toList());
		report.append("Top-Spending Clients:\n");
		for (Client client : topSpendingClients) {
			report.append("Client ID: ").append(client.getId()).append("\n");
			report.append("Name: ").append(client.getName()).append(" ").append(client.getLastName()).append("\n");
			report.append("Total Spending: ").append(calculateTotalSpending(client.getSales())).append("\n");
			report.append("\n");
		}

		// Client activity
		Map<String, Long> clientActivity = clients.stream()
				.collect(Collectors.groupingBy(Client::getName, Collectors.counting()));
		report.append("Client Activity:\n");
		for (Map.Entry<String, Long> entry : clientActivity.entrySet()) {
			report.append("Client Name: ").append(entry.getKey()).append("\n");
			report.append("Number of Sales: ").append(entry.getValue()).append("\n");
			report.append("\n");
		}

		// Client location statistics
		Map<String, Long> clientLocationStatistics = clients.stream()
				.collect(Collectors.groupingBy(Client::getAddress, Collectors.counting()));
		report.append("Client Location Statistics:\n");
		for (Map.Entry<String, Long> entry : clientLocationStatistics.entrySet()) {
			report.append("Location: ").append(entry.getKey()).append("\n");
			report.append("Number of Clients: ").append(entry.getValue()).append("\n");
			report.append("\n");
		}

		// Finally, return the report as a string
		return ResponseEntity.ok(report.toString());
	}

	private double calculateTotalSpending(List<Sale> sales) {
		return sales.stream()
				.flatMap(sale -> sale.getSoldProducts().stream())
				.mapToDouble(soldProduct -> soldProduct.getPrice() * soldProduct.getQuantity())
				.sum();
	}
}
