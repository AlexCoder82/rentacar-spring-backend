
package com.optima.javaServer.repository;

import com.optima.javaServer.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */

/**
 *  Peticiones a la base de datos para la tabla "users" 
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

    //Crea o modifica el usuario
    @Override
    public User save(User user);

    //Login
    public Optional<User> findByUserNameAndPasswrd(
            String userName,
            String passwrd);

    //Devuelve los datos del usuario y 
    //sus reservas ordenadas por "returnDate" DESC
    @Override
    public Optional<User> findById(Integer userId);

}
