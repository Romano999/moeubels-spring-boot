package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RoleRepository extends PagingAndSortingRepository<Role, UUID> {
}
