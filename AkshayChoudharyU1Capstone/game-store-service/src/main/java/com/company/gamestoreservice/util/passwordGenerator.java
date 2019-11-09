package com.company.gamestoreservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class passwordGenerator {
    public static void main(String[] args){
        PasswordEncoder enc = new BCryptPasswordEncoder();

        String password = "adminone";

        String encodedPassword =enc.encode(password);

        System.out.println(encodedPassword);

    }

//   / ROLE_USER
    // regular user : User01 password: userOne == $2a$10$NrSdQv5FVmIqS7G8O5sxfuk9xJ9h7V0aN3LivHLXdCyAK5ovLPcVC
    // ROLE_STAFF
    // staff user : Staff01 password: staffone == $2a$10$vd8j7.kqdT/pzdy6FiDbsODyRhIWeyeltpH0pWa6gG4eRdtSFFK/G
    // ROLE_MANAGER
    // manager user : Manager01 password: managerone == $2a$10$IB8fMnV.TFUMHm5LN/qZb.DmqPfBsyPA/Z3Y4.GkP9OYQB1hZalK6
    // ROLE_ADMIN
    // admin : Admin01 password: adminone == $2a$10$5v.AFkwpWuwn9.V75iSN8ugw3mWaFXZ5efpzTGRm3f2I0pl85GdKG
}
