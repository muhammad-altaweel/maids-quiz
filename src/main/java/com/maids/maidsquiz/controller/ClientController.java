package com.maids.maidsquiz.controller;

import com.maids.maidsquiz.data.Client;
import com.maids.maidsquiz.dto.ClientDto;
import com.maids.maidsquiz.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}

	@GetMapping("/{id}")
	public Client getClientById(@PathVariable Long id) throws Exception {
		return clientService.getClientById(id);
	}

	@PostMapping
	public Client createClient(@RequestBody ClientDto client) {
		return clientService.createClient(client);
	}

	@PutMapping ("/{id}")
	public Client updateClient(@PathVariable Long id, @RequestBody ClientDto updatedClient) throws Exception {
		return clientService.updateClient(id, updatedClient);
	}

	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable Long id) throws Exception {
		clientService.deleteClient(id);
	}

	@GetMapping("/report")
	public ResponseEntity<String> getReport(){
		return clientService.generateReport();
	}
}
