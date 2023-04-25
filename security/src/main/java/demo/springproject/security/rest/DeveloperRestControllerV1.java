package demo.springproject.security.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.springproject.security.model.Developer;
@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
	
	private List<Developer> DEVELOPERS = List.of(
			new Developer(1L, "Anna", "Karenina"),
			new Developer(2L, "Lev", "Tolstoy"),
			new Developer(3L, "Boris", "Godunov")
			);

	@GetMapping
	public List <Developer> getAll() {
		return DEVELOPERS;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('developers:read')")
	public Developer getById(@PathVariable long id) {
		return DEVELOPERS
				.stream()
				.filter(dev -> dev.getId()==id)
				.findFirst()
				.orElse(null);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('developers:write')")
	public Developer create(@RequestBody Developer developer) {
		this.DEVELOPERS.add(developer);
		return developer;
	}
	
	@DeleteMapping
	@PreAuthorize("hasAuthority('developers:write')")
	public void deleteById(long id) {
		this.DEVELOPERS.removeIf(dev -> dev.getId()==id);
	}
}
