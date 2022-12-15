package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.contract.v1.request.create.CreateRoleRequest;
import nl.romano.moeubels.contract.v1.request.update.UpdateRoleRequest;
import nl.romano.moeubels.model.Role;

import java.util.UUID;

public class RoleObjectMother {
    public static Role genericRole() {
        return Role.builder()
            .roleId(UUID.randomUUID())
            .roleName("Actor")
            .build();
    }

    public static UpdateRoleRequest genericUpdateRoleRequest() {
        return UpdateRoleRequest.builder()
            .roleName("Actor")
            .build();
    }

    public static CreateRoleRequest genericCreateRoleRequest() {
        return CreateRoleRequest.builder()
            .roleName("Actor")
            .build();
    }
}
