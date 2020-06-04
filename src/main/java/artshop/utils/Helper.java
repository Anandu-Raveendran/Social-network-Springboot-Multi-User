package artshop.utils;

import artshop.Entities.Role;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Helper {
    // check if the role is present in roles list
    public boolean checkRolePresentIn(Set<Role> roleSet, String role) {
        for (Role roleIterator : roleSet) {
            if (roleIterator.getRole().equalsIgnoreCase(role))
                return true;
        }
        return false;
    }
}
