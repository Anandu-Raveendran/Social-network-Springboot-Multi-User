package artshop.Entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int roleId;
	private String role;
	@Column(name="role_id")
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + roleId +
				", role='" + role + '\'' +
				'}';
	}
}
