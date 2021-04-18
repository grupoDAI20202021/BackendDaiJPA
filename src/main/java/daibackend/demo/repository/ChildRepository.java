package daibackend.demo.repository;

import daibackend.demo.model.Child;
import daibackend.demo.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    Child findDistinctByIdChild(Long id_child);

    Child findDistinctByLogin(Login login);

    @Override
    List<Child> findAll();

    List<Child> findAllByAddress(String address);

    @Transactional
    @Modifying
    @Query("UPDATE child C SET C.name= ?1, C.address = ?2 where C.idChild= ?3")
    void updateChild(String name ,String address, long id_child);

    @Transactional
    @Modifying
    @Query("UPDATE child C SET C.idAvatar= ?1 where C.idChild= ?2")
    void updateChildAvatar(long idAvatar , long id_child);

    @Override
    void delete(Child child);
}
