package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.contract.v1.request.create.CreateRoleRequest;
import nl.romano.moeubels.contract.v1.request.update.UpdateRoleRequest;
import nl.romano.moeubels.contract.v1.response.RoleResponse;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.v1.utils.RoleObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class RoleDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenRoleModelToRoleResponseDto_thenCorrect() {
        // Arrange
        Role role = RoleObjectMother.genericRole();

        // Act
        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);

        // Assert
        Assertions.assertEquals(role.getRoleId(), roleResponse.getRoleId());
        Assertions.assertEquals(role.getRoleName(), roleResponse.getRoleName());
    }

    @Test
    public void whenUpdateRoleRequestToRoleModel_thenCorrect() {
        // Arrange
        UpdateRoleRequest updateRoleRequest = RoleObjectMother.genericUpdateRoleRequest();

        // Act
        Role role = modelMapper.map(updateRoleRequest, Role.class);

        // Assert
        Assertions.assertEquals(updateRoleRequest.getRoleName(), role.getRoleName());
    }

    @Test
    public void whenCreateRoleRequestToRoleModel_thenCorrect() {
        // Arrange
        CreateRoleRequest createRoleRequest = RoleObjectMother.genericCreateRoleRequest();

        // Act
        Role role = modelMapper.map(createRoleRequest, Role.class);

        // Assert
        Assertions.assertEquals(createRoleRequest.getRoleName(), role.getRoleName());
    }

}
